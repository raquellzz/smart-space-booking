package imd.ufrn.com.br.smart_space_booking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imd.ufrn.com.br.smart_space_booking.dto.UsuarioLoginDTO;
import imd.ufrn.com.br.smart_space_booking.dto.UsuarioResponseDTO;
import imd.ufrn.com.br.smart_space_booking.service.UsuarioService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/usuarios")
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