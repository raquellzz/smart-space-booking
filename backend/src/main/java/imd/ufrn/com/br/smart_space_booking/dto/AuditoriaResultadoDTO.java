package imd.ufrn.com.br.smart_space_booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import imd.ufrn.com.br.smart_space_booking.enums.AuditoriaCategoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditoriaResultadoDTO {

    @JsonProperty("imagemValida")
    private boolean imagemValida = true;

    private boolean aprovado;
    private String observacoes;
    private AuditoriaCategoria categoria;

    public AuditoriaResultadoDTO() {}
}