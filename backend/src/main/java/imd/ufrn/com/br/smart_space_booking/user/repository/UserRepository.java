package imd.ufrn.com.br.smart_space_booking.user.repository;

import imd.ufrn.com.br.smart_space_booking.base.repository.GenericRepository;
import imd.ufrn.com.br.smart_space_booking.user.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericRepository<User>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
}