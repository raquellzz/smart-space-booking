package imd.ufrn.com.br.smart_space_booking.user.mappers;

import imd.ufrn.com.br.smart_space_booking.base.mappers.BaseMapperConfig;
import imd.ufrn.com.br.smart_space_booking.base.mappers.DtoMapper;
import imd.ufrn.com.br.smart_space_booking.user.dto.UserDTO;
import imd.ufrn.com.br.smart_space_booking.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface UserMapper extends DtoMapper<User, UserDTO> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "scoreTransactions", ignore = true)
    @Override
    User toEntity(UserDTO dto);
}
