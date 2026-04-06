package imd.ufrn.com.br.smart_space_booking.room.dto;

import imd.ufrn.com.br.smart_space_booking.base.dto.EntityDTO;
import imd.ufrn.com.br.smart_space_booking.room.enums.RoomStatus;
import imd.ufrn.com.br.smart_space_booking.room.enums.RoomType;
import imd.ufrn.com.br.smart_space_booking.user.enums.UserType;

import java.time.ZonedDateTime;
import java.util.List;

public record RoomDTO(
        Long id,
        String name,
        String location,
        RoomStatus status,
        RoomType type,
        Integer capacity,
        List<String> characteristics,
        ZonedDateTime createdAt
) implements EntityDTO {

    @Override
    public EntityDTO toResponse() {
        return this;
    }
}