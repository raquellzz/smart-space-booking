package imd.ufrn.com.br.smart_space_booking.prompts;

import imd.ufrn.com.br.smart_space_booking.enums.AuditoriaCategoria;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AuditoriaPrompts {

    private AuditoriaPrompts() {}

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