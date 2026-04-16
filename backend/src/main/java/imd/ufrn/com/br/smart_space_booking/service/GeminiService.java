package imd.ufrn.com.br.smart_space_booking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import imd.ufrn.com.br.smart_space_booking.config.GeminiConfig;
import imd.ufrn.com.br.smart_space_booking.dto.AuditoriaResultadoDTO;
import imd.ufrn.com.br.smart_space_booking.dto.GeminiRequestDTO;
import imd.ufrn.com.br.smart_space_booking.dto.GeminiResponseDTO;
import imd.ufrn.com.br.smart_space_booking.enums.AuditoriaCategoria;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeminiService {

    private final RestTemplate restTemplate;
    private final GeminiConfig geminiConfig;
    private final ObjectMapper objectMapper;

    private static final String PROMPT_CHECKIN = """
            Você é um auditor de espaços corporativos. Analise as imagens de uma sala e avalie
            se ela está em condições ADEQUADAS para receber um usuário: organizada, limpa e sem danos visíveis.
            Considere todas as imagens enviadas para formar sua avaliação.

            Responda SOMENTE com um JSON válido, sem texto adicional:
            {
              "aprovado": true,
              "observacoes": "Descreva brevemente o estado atual da sala em até 200 caracteres."
            }

            Regras:
            - "aprovado": true se a sala está em condições aceitáveis para uso, false caso contrário.
            - "observacoes": descreva o que foi observado de forma objetiva.
            """;

    private static final String PROMPT_CHECKOUT_TEMPLATE = """
            Você é um auditor de espaços corporativos. Analise as imagens de uma sala APÓS o uso de um usuário
            e avalie o estado geral do ambiente: limpeza, organização, integridade dos itens e possíveis danos.
            Considere todas as imagens enviadas para formar sua avaliação.

            Classifique o resultado em UMA das seguintes categorias (use exatamente este nome):
            %s

            Responda SOMENTE com um JSON válido, sem texto adicional:
            {
              "aprovado": true,
              "observacoes": "Descreva o estado da sala em até 200 caracteres.",
              "categoria": "NOME_DA_CATEGORIA"
            }

            Regras:
            - "aprovado": true para resultados neutros ou positivos, false para negativos (dano, sujeira, item faltando).
            - "observacoes": descreva objetivamente o que motivou a classificação.
            - "categoria": escolha APENAS uma das categorias listadas acima, com o nome exato.
            """;

    public GeminiService(RestTemplate restTemplate, GeminiConfig geminiConfig, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.geminiConfig = geminiConfig;
        this.objectMapper = objectMapper;
    }

    public AuditoriaResultadoDTO analisarCheckIn(List<MultipartFile> imagens) {
        return executarAnalise(imagens, PROMPT_CHECKIN);
    }

    public AuditoriaResultadoDTO analisarCheckOut(List<MultipartFile> imagens) {
        String categoriasDisponiveis = Arrays.stream(AuditoriaCategoria.values())
                .map(Enum::name)
                .collect(Collectors.joining("\n- ", "- ", ""));

        String prompt = String.format(PROMPT_CHECKOUT_TEMPLATE, categoriasDisponiveis);
        return executarAnalise(imagens, prompt);
    }

    private AuditoriaResultadoDTO executarAnalise(List<MultipartFile> imagens, String prompt) {
        if (imagens == null || imagens.isEmpty()) {
            throw new IllegalArgumentException("É necessário enviar ao menos uma imagem para a auditoria.");
        }
        try {
            GeminiRequestDTO request = montarRequest(prompt, imagens);
            String respostaTexto = chamarApi(request);
            return parsearResposta(respostaTexto);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao analisar imagens com Gemini: " + e.getMessage(), e);
        }
    }

    private GeminiRequestDTO montarRequest(String prompt, List<MultipartFile> imagens) throws Exception {
        List<GeminiRequestDTO.Part> parts = new ArrayList<>();

        parts.add(new GeminiRequestDTO.Part(prompt));

        // Parts seguintes: uma por imagem
        for (MultipartFile imagem : imagens) {
            String mimeType = imagem.getContentType() != null ? imagem.getContentType() : "image/jpeg";
            String base64 = converterParaBase64(imagem);
            parts.add(new GeminiRequestDTO.Part(new GeminiRequestDTO.InlineData(mimeType, base64)));
        }

        return new GeminiRequestDTO(List.of(new GeminiRequestDTO.Content(parts)));
    }

    private String chamarApi(GeminiRequestDTO request) {
        String url = geminiConfig.getApiUrl()
                + "/v1beta/models/" + geminiConfig.getModel()
                + ":generateContent?key=" + geminiConfig.getApiKey();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GeminiRequestDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<GeminiResponseDTO> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, GeminiResponseDTO.class);

        if (response.getBody() == null) {
            throw new RuntimeException("Resposta vazia da API Gemini.");
        }

        String texto = response.getBody().extrairTexto();
        if (texto == null || texto.isBlank()) {
            throw new RuntimeException("Gemini não retornou texto na resposta.");
        }

        return texto;
    }

    private AuditoriaResultadoDTO parsearResposta(String jsonResposta) throws Exception {
        String limpo = jsonResposta
                .replaceAll("(?s)```json\\s*", "")
                .replaceAll("(?s)```\\s*", "")
                .trim();
        return objectMapper.readValue(limpo, AuditoriaResultadoDTO.class);
    }

    private String converterParaBase64(MultipartFile arquivo) throws Exception {
        return Base64.getEncoder().encodeToString(arquivo.getBytes());
    }
}