package imd.ufrn.com.br.smart_space_booking.reservation.dto;

import imd.ufrn.com.br.smart_space_booking.base.dto.EntityDTO;
import imd.ufrn.com.br.smart_space_booking.reservation.enums.ReservationStatus;
import imd.ufrn.com.br.smart_space_booking.reservation.enums.ReservationType;

import java.time.ZonedDateTime;

public record ReservationDTO(
        Long id,
        ZonedDateTime startDateTime,
        ZonedDateTime endDateTime,
        ReservationStatus status,
        ReservationType type,
        Long userId,
        Long roomId,
        ZonedDateTime createdAt
) implements EntityDTO {
    @Override
    public EntityDTO toResponse() { return this; }
}