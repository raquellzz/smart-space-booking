package imd.ufrn.com.br.smart_space_booking.dto;

import imd.ufrn.com.br.smart_space_booking.enums.ReservaStatus;
import imd.ufrn.com.br.smart_space_booking.enums.ReservaTipo;
import imd.ufrn.com.br.smart_space_booking.model.Reserva;

import java.time.ZonedDateTime;

public record ReservaResponseDTO(
        Long id,
        ZonedDateTime inicioDateTime,
        ZonedDateTime fimDateTime,
        ReservaStatus status,
        ReservaTipo tipo,
        Long usuarioId,
        Long salaId,
        String salaNome,
        ZonedDateTime dataHoraCheckin,
        ZonedDateTime dataHoraCheckout,
        String motivoCancelamento,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt
) {
    public static ReservaResponseDTO fromEntity(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getInicioDateTime(),
                reserva.getFimDateTime(),
                reserva.getStatus(),
                reserva.getTipo(),
                reserva.getUsuario() != null ? reserva.getUsuario().getId() : null,
                reserva.getSala().getId(),
                reserva.getSala().getNome(),
                reserva.getDataHoraCheckin(),
                reserva.getDataHoraCheckout(),
                reserva.getMotivoCancelamento(),
                reserva.getCreatedAt(),
                reserva.getUpdatedAt()
        );
    }
}