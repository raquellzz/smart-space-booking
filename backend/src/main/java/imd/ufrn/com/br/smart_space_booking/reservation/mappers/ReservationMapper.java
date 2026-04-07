package imd.ufrn.com.br.smart_space_booking.reservation.mappers;

import imd.ufrn.com.br.smart_space_booking.base.mappers.BaseMapperConfig;
import imd.ufrn.com.br.smart_space_booking.base.mappers.DtoMapper;
import imd.ufrn.com.br.smart_space_booking.reservation.dto.ReservationDTO;
import imd.ufrn.com.br.smart_space_booking.reservation.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface ReservationMapper extends DtoMapper<Reservation, ReservationDTO> {

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "room.id", target = "roomId")
    ReservationDTO toDto(Reservation entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "audits", ignore = true)
    @Mapping(target = "scoreTransactions", ignore = true)
    @Override
    Reservation toEntity(ReservationDTO dto);
}