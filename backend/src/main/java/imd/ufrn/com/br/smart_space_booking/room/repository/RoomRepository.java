package imd.ufrn.com.br.smart_space_booking.room.repository;

import imd.ufrn.com.br.smart_space_booking.base.repository.GenericRepository;
import imd.ufrn.com.br.smart_space_booking.room.model.Room;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoomRepository extends GenericRepository<Room>, JpaSpecificationExecutor<Room> {
}
