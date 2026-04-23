package imd.ufrn.com.br.smart_space_booking.controller;

import imd.ufrn.com.br.smart_space_booking.model.Auditoria;
import imd.ufrn.com.br.smart_space_booking.service.AuditoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/auditorias")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @PostMapping(value = "/checkin/{reservaId}", consumes = "multipart/form-data")
    public ResponseEntity<Auditoria> checkIn(
            @PathVariable Long reservaId,
            @RequestPart("imagens") List<MultipartFile> imagens,
            @RequestPart("imageIds") List<String> imageIds) {

        return ResponseEntity.ok(auditoriaService.realizarCheckIn(reservaId, imagens, imageIds));
    }

    @PostMapping(value = "/checkout/{reservaId}", consumes = "multipart/form-data")
    public ResponseEntity<Auditoria> checkOut(
            @PathVariable Long reservaId,
            @RequestPart("imagens") List<MultipartFile> imagens,
            @RequestPart("imageIds") List<String> imageIds) {

        return ResponseEntity.ok(auditoriaService.realizarCheckOut(reservaId, imagens, imageIds));
    }
}