package imd.ufrn.com.br.smart_space_booking.dto;
import jakarta.validation.constraints.NotBlank;

public record CheckinRequestDTO(
    
    @NotBlank(message = "O ID da foto é obrigatório para confirmar o check-in")
    String fotoCheckinId

) {}