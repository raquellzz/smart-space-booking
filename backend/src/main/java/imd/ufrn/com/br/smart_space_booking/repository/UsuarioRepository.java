package imd.ufrn.com.br.smart_space_booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.com.br.smart_space_booking.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}