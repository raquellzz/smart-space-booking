package imd.ufrn.com.br.smart_space_booking.controller;

import imd.ufrn.com.br.smart_space_booking.dto.ReservaRequestDTO;
import imd.ufrn.com.br.smart_space_booking.dto.ReservaResponseDTO;
import imd.ufrn.com.br.smart_space_booking.service.ReservaService;
import imd.ufrn.com.br.smart_space_booking.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> create(@RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> findAll() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}