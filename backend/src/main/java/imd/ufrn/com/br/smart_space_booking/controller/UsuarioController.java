package imd.ufrn.com.br.smart_space_booking.controller;

import imd.ufrn.com.br.smart_space_booking.dto.UsuarioLoginDTO;
import imd.ufrn.com.br.smart_space_booking.dto.UsuarioResponseDTO;
import imd.ufrn.com.br.smart_space_booking.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/acesso")
    public ResponseEntity<UsuarioResponseDTO> acessar(@RequestBody UsuarioLoginDTO loginDTO) {
        UsuarioResponseDTO response = usuarioService.realizarAcesso(loginDTO);
        return ResponseEntity.ok(response);
    }
}