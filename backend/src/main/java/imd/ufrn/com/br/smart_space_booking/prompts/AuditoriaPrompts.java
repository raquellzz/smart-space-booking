package imd.ufrn.com.br.smart_space_booking.prompts;

import imd.ufrn.com.br.smart_space_booking.enums.AuditoriaCategoria;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AuditoriaPrompts {

    private AuditoriaPrompts() {}

    private static final String PROMPT_CHECKIN = """
            Você é um auditor de espaços corporativos.

            As PRIMEIRAS imagens mostram o estado PADRÃO esperado da sala (cadastrado no sistema).
            As imagens SEGUINTES mostram o estado ATUAL da sala, fotografado agora pelo usuário.

            ANTES de avaliar, verifique se as imagens enviadas pelo usuário mostram claramente
            um ambiente interno (sala, escritório ou espaço de trabalho).
            Se alguma imagem claramente não for de um ambiente interno, responda APENAS:
            {
              "imagemValida": false,
              "aprovado": false,
              "observacoes": "As imagens enviadas não correspondem a um ambiente interno."
            }

            Se as imagens forem válidas, avalie se o estado atual está em condições ADEQUADAS
            para receber um usuário, comparando com o padrão esperado: organização, limpeza
            e integridade dos itens. Responda SOMENTE com um JSON válido, sem texto adicional:
            {
              "imagemValida": true,
              "aprovado": true,
              "observacoes": "Descreva brevemente o estado atual em relação ao padrão em até 200 caracteres."
            }

            Regras:
            - "imagemValida": false se as imagens do usuário não forem de um ambiente interno.
            - "aprovado": true se o estado atual está condizente com o padrão esperado, false caso contrário.
            - "observacoes": destaque diferenças relevantes em relação ao padrão, se houver.
            """;

    private static final String PROMPT_CHECKOUT_TEMPLATE = """
            Você é um auditor de espaços corporativos.

            As PRIMEIRAS imagens mostram o estado PADRÃO esperado da sala (cadastrado no sistema).
            As imagens SEGUINTES mostram o estado ATUAL da sala APÓS o uso do usuário.

            ANTES de avaliar, verifique se as imagens enviadas pelo usuário mostram claramente
            um ambiente interno (sala, escritório ou espaço de trabalho).
            Se alguma imagem claramente não for de um ambiente interno, responda APENAS:
            {
              "imagemValida": false,
              "aprovado": false,
              "observacoes": "As imagens enviadas não correspondem a um ambiente interno.",
              "categoria": "NAO_IDENTIFICADO"
            }

            Se as imagens forem válidas, avalie o estado geral comparando com o padrão:
            limpeza, organização, integridade dos itens e possíveis danos.

            Classifique o resultado em UMA das seguintes categorias (use exatamente este nome):
            %s

            Responda SOMENTE com um JSON válido, sem texto adicional:
            {
              "imagemValida": true,
              "aprovado": true,
              "observacoes": "Descreva o estado em relação ao padrão em até 200 caracteres.",
              "categoria": "NOME_DA_CATEGORIA"
            }

            Regras:
            - "imagemValida": false se as imagens do usuário não forem de um ambiente interno.
            - "aprovado": true para resultados neutros ou positivos, false para negativos (dano, sujeira, item faltando).
            - "observacoes": destaque o que mudou em relação ao padrão esperado.
            - "categoria": escolha APENAS uma das categorias listadas acima, com o nome exato.
            """;

    public static String promptCheckIn() {
        return PROMPT_CHECKIN;
    }

    public static String promptCheckOut() {
        String categorias = Arrays.stream(AuditoriaCategoria.values())
                .map(Enum::name)
                .collect(Collectors.joining("\n- ", "- ", ""));

        return String.format(PROMPT_CHECKOUT_TEMPLATE, categorias);
    }
}