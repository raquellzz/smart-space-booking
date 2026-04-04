package imd.ufrn.com.br.smart_space_booking.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import imd.ufrn.com.br.smart_space_booking.user.dto.UsuarioLoginDTO;
import imd.ufrn.com.br.smart_space_booking.user.dto.UsuarioResponseDTO;
import imd.ufrn.com.br.smart_space_booking.user.service.UsuarioService;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping()
    public String getHelloWorld (){
        return "HELLO WORLD";
    }
//    @PostMapping("/acesso")
//    public ResponseEntity<UsuarioResponseDTO> acessar(@RequestBody UsuarioLoginDTO loginDTO) {
//        UsuarioResponseDTO response = usuarioService.realizarAcesso(loginDTO);
//        return ResponseEntity.ok(response);
//    }
}