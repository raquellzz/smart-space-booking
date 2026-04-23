package imd.ufrn.com.br.smart_space_booking.client;

import imd.ufrn.com.br.smart_space_booking.config.GeminiConfig;
import imd.ufrn.com.br.smart_space_booking.dto.GeminiRequestDTO;
import imd.ufrn.com.br.smart_space_booking.dto.GeminiResponseDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class GeminiClient {

    private final GeminiConfig geminiConfig;
    private final RestTemplate restTemplate;

    public GeminiClient(GeminiConfig geminiConfig) {
        this.geminiConfig = geminiConfig;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Envia um prompt e uma lista de imagens para o Gemini.
     * Retorna o texto bruto da resposta — sem interpretar o conteúdo.
     */
    public String analisar(String prompt, List<MultipartFile> imagens) {
        try {
            GeminiRequestDTO request = montarRequest(prompt, imagens);
            return chamarApi(request);
        } catch (Exception e) {
            throw new RuntimeException("Erro na comunicação com o Gemini: " + e.getMessage(), e);
        }
    }

    private GeminiRequestDTO montarRequest(String prompt, List<MultipartFile> imagens) throws Exception {
        List<GeminiRequestDTO.Part> parts = new ArrayList<>();

        parts.add(new GeminiRequestDTO.Part(prompt));

        for (MultipartFile imagem : imagens) {
            String mimeType = imagem.getContentType() != null ? imagem.getContentType() : "image/jpeg";
            String base64 = Base64.getEncoder().encodeToString(imagem.getBytes());
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

        ResponseEntity<GeminiResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                GeminiResponseDTO.class
        );

        if (response.getBody() == null) {
            throw new RuntimeException("Resposta vazia da API Gemini.");
        }

        String texto = response.getBody().extrairTexto();
        if (texto == null || texto.isBlank()) {
            throw new RuntimeException("Gemini não retornou texto na resposta.");
        }

        return texto;
    }
}