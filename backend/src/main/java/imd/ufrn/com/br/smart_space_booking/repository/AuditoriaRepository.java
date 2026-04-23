package imd.ufrn.com.br.smart_space_booking.repository;

import imd.ufrn.com.br.smart_space_booking.enums.AuditoriaTipo;
import imd.ufrn.com.br.smart_space_booking.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    Optional<Auditoria> findByReservaIdAndTipo(Long reservaId, AuditoriaTipo tipo);
    List<Auditoria> findByReservaId(Long reservaId);
}