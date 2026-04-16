package imd.ufrn.com.br.smart_space_booking.enums;

public enum AuditoriaCategoria {
    // Resultados positivos (bonificação futura)
    SALA_LIMPA,
    SALA_REORGANIZADA,

    // Resultados neutros
    SALA_ADEQUADA,
    SEM_ALTERACOES,
    SALA_SUJA,
    DANO_IDENTIFICADO,
    ITEM_FALTANDO,
    SALA_DESORGANIZADA,

    // Resultado de erro/indeterminado
    NAO_IDENTIFICADO
}