package imd.ufrn.com.br.smart_space_booking.controller;

import imd.ufrn.com.br.smart_space_booking.dto.AuditoriaResponseDTO;
import imd.ufrn.com.br.smart_space_booking.model.Auditoria;
import imd.ufrn.com.br.smart_space_booking.service.AuditoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auditorias")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @GetMapping
    public ResponseEntity<List<AuditoriaResponseDTO>> findAll() {
        return ResponseEntity.ok(auditoriaService.findAll());
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<AuditoriaResponseDTO>> findByReserva(@PathVariable Long reservaId) {
        return ResponseEntity.ok(auditoriaService.findByReservaId(reservaId));
    }


    @PostMapping(value = "/checkin/{reservaId}", consumes = "multipart/form-data")
    public ResponseEntity<AuditoriaResponseDTO> checkIn(
            @PathVariable Long reservaId,
            @RequestPart("imagens") List<MultipartFile> imagens,
            @RequestParam(value = "imageIds", required = false) List<String> imageIds) {

        Auditoria auditoria = auditoriaService.realizarCheckIn(reservaId, imagens, imageIds);
        return ResponseEntity.ok(AuditoriaResponseDTO.fromEntity(auditoria));
    }

//    @PostMapping(value = "/checkout/{reservaId}", consumes = "multipart/form-data")
//    public ResponseEntity<AuditoriaResponseDTO> checkOut(
//            @PathVariable Long reservaId,
//            @RequestPart("imagens") List<MultipartFile> imagens,
//            @RequestParam(value = "imageIds", required = false) List<String> imageIds) {
//
//        Auditoria auditoria = auditoriaService.realizarCheckOut(reservaId, imagens, imageIds);
//        return ResponseEntity.ok(AuditoriaResponseDTO.fromEntity(auditoria));
//    }
}