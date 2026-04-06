package imd.ufrn.com.br.smart_space_booking.room.mappers;

import imd.ufrn.com.br.smart_space_booking.base.mappers.BaseMapperConfig;
import imd.ufrn.com.br.smart_space_booking.base.mappers.DtoMapper;
import imd.ufrn.com.br.smart_space_booking.room.dto.RoomDTO;
import imd.ufrn.com.br.smart_space_booking.room.model.Room;
import imd.ufrn.com.br.smart_space_booking.user.dto.UserDTO;
import imd.ufrn.com.br.smart_space_booking.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface RoomMapper extends DtoMapper<Room, RoomDTO> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "resources", ignore = true)
    @Override
    Room toEntity(RoomDTO dto);
}