package imd.ufrn.com.br.smart_space_booking.reservation.repository;

import imd.ufrn.com.br.smart_space_booking.base.repository.GenericRepository;
import imd.ufrn.com.br.smart_space_booking.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface ReservationRepository extends GenericRepository<Reservation>, JpaSpecificationExecutor<Reservation> {
    @Query("""
        SELECT COUNT(r) > 0 FROM Reservation r
        WHERE r.room.id = :roomId
        AND r.status <> 'CANCELLED'
        AND r.startDateTime < :end
        AND r.endDateTime > :start
    """)
    boolean existsOverlapping(
            @Param("roomId") Long roomId,
            @Param("start") ZonedDateTime start,
            @Param("end") ZonedDateTime end
    );
}
