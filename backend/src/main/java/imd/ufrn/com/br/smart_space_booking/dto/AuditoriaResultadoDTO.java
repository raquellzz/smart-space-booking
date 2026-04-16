package imd.ufrn.com.br.smart_space_booking.dto;

import imd.ufrn.com.br.smart_space_booking.enums.AuditoriaCategoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditoriaResultadoDTO {

    private boolean aprovado;
    private String observacoes;

    private AuditoriaCategoria categoria;

    public AuditoriaResultadoDTO() {}

    public AuditoriaResultadoDTO(boolean aprovado, String observacoes) {
        this.aprovado = aprovado;
        this.observacoes = observacoes;
    }

    public AuditoriaResultadoDTO(boolean aprovado, String observacoes, AuditoriaCategoria categoria) {
        this.aprovado = aprovado;
        this.observacoes = observacoes;
        this.categoria = categoria;
    }
}