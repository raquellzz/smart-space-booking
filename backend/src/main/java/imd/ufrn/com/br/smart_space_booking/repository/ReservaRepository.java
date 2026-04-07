package imd.ufrn.com.br.smart_space_booking.repository;

import imd.ufrn.com.br.smart_space_booking.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r WHERE r.sala.id = :salaId " +
            "AND r.inicioDateTime >= :inicioDia AND r.fimDateTime <= :fimDia " +
            "ORDER BY r.inicioDateTime ASC")
    List<Reserva> findReservasPorSalaNoDia(
            @Param("salaId") Long salaId,
            @Param("inicioDia") ZonedDateTime inicioDia,
            @Param("fimDia") ZonedDateTime fimDia);

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
