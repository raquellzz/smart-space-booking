package imd.ufrn.com.br.smart_space_booking.user.service;

import imd.ufrn.com.br.smart_space_booking.base.mappers.DtoMapper;
import imd.ufrn.com.br.smart_space_booking.base.repository.GenericRepository;
import imd.ufrn.com.br.smart_space_booking.base.service.GenericService;
import imd.ufrn.com.br.smart_space_booking.user.dto.UserDTO;
import imd.ufrn.com.br.smart_space_booking.user.dto.UserLoginDTO;
import imd.ufrn.com.br.smart_space_booking.user.enums.UserType;
import imd.ufrn.com.br.smart_space_booking.user.mappers.UserMapper;
import imd.ufrn.com.br.smart_space_booking.user.model.User;
import org.springframework.stereotype.Service;

import imd.ufrn.com.br.smart_space_booking.user.repository.UserRepository;

@Service
public class UserService implements GenericService<User, UserDTO> {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public GenericRepository<User> getRepository() {
        return repository;
    }

    @Override
    public DtoMapper<User, UserDTO> getDtoMapper() {
        return mapper;
    }

    public UserDTO makeAccess(UserLoginDTO loginDTO) {
        return repository.findByEmail(loginDTO.email())
                .map(mapper::toDto)
                .orElseGet(() -> {

                    User newUser = new User();
                    newUser.setEmail(loginDTO.email());
                    newUser.setName(loginDTO.name());
                    newUser.setScore(100);

                    boolean conditionAdmin = loginDTO.email().endsWith("@admin.com") || loginDTO.email().contains("@admin");
                    newUser.setType(
                            conditionAdmin
                                    ? UserType.ADMIN
                                    : UserType.USER);

                    User savedUser = repository.save(newUser);
                    return mapper.toDto(savedUser);
                });
    }
}