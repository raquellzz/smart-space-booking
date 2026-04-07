package imd.ufrn.com.br.smart_space_booking.repository;

import imd.ufrn.com.br.smart_space_booking.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("""
        SELECT COUNT(r) > 0 FROM Reserva r
        WHERE r.sala.id = :salaId
        AND r.status <> 'CANCELLED'
        AND r.inicioDateTime < :fim
        AND r.fimDateTime > :inicio
    """)
    boolean existeConflito(
            @Param("salaId") Long salaId,
            @Param("inicio") ZonedDateTime inicio,
            @Param("fim") ZonedDateTime fim
    );
}
