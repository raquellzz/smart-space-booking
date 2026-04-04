package imd.ufrn.com.br.smart_space_booking.user.dto;

import imd.ufrn.com.br.smart_space_booking.base.dto.EntityDTO;
import imd.ufrn.com.br.smart_space_booking.user.enums.UserType;

import java.time.ZonedDateTime;

public record UserDTO(
        Long id,
        String name,
        String email,
        Integer score,
        UserType type,
        ZonedDateTime createdAt
) implements EntityDTO {

    @Override
    public EntityDTO toResponse() {
        return this;
    }
}