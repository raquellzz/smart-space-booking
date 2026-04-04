package imd.ufrn.com.br.smart_space_booking.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.com.br.smart_space_booking.user.model.User;

public interface UsuarioRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}