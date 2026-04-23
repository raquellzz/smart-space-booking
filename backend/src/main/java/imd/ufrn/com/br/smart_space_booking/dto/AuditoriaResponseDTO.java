package imd.ufrn.com.br.smart_space_booking.dto;

import imd.ufrn.com.br.smart_space_booking.enums.AuditoriaCategoria;
import imd.ufrn.com.br.smart_space_booking.enums.AuditoriaTipo;
import imd.ufrn.com.br.smart_space_booking.model.Auditoria;

import java.time.LocalDateTime;
import java.util.List;

public record AuditoriaResponseDTO(
        Long id,
        Long reservaId,
        AuditoriaTipo tipo,
        Boolean aprovado,
        String observacoes,
        AuditoriaCategoria categoria,
        List<String> imageIds,
        LocalDateTime dateCreated
) {
    public static AuditoriaResponseDTO fromEntity(Auditoria auditoria) {
        return new AuditoriaResponseDTO(
                auditoria.getId(),
                auditoria.getReserva().getId(),
                auditoria.getTipo(),
                auditoria.getAprovado(),
                auditoria.getObservacoes(),
                auditoria.getCategoria(),
                auditoria.getImageIds(),
                auditoria.getDateCreated()
        );
    }
}