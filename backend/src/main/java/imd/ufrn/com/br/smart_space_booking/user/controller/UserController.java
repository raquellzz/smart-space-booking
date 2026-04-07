package imd.ufrn.com.br.smart_space_booking.user.controller;

import imd.ufrn.com.br.smart_space_booking.base.controller.GenericController;
import imd.ufrn.com.br.smart_space_booking.base.dto.ApiResponseDTO;
import imd.ufrn.com.br.smart_space_booking.user.dto.UserDTO;
import imd.ufrn.com.br.smart_space_booking.user.dto.UserLoginDTO;
import imd.ufrn.com.br.smart_space_booking.user.model.User;
import imd.ufrn.com.br.smart_space_booking.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController extends GenericController<User, UserDTO, UserService> {

    private final UserService userService;

    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @PostMapping("/access")
    public ResponseEntity<ApiResponseDTO<UserDTO>> makeAccess(@RequestBody UserLoginDTO dto) {
        UserDTO userDTO = userService.makeAccess(dto);

        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                userDTO,
                null
        ));
    }
}