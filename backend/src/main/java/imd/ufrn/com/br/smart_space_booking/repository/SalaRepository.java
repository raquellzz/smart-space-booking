package imd.ufrn.com.br.smart_space_booking.repository;

import imd.ufrn.com.br.smart_space_booking.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
}
