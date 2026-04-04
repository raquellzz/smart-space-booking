package imd.ufrn.com.br.smart_space_booking.user.service;

import org.springframework.stereotype.Service;

import imd.ufrn.com.br.smart_space_booking.user.dto.UsuarioLoginDTO;
import imd.ufrn.com.br.smart_space_booking.user.dto.UsuarioResponseDTO;
import imd.ufrn.com.br.smart_space_booking.user.model.User;
import imd.ufrn.com.br.smart_space_booking.user.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

//    public UsuarioResponseDTO realizarAcesso(UsuarioLoginDTO loginDTO) {
//        return usuarioRepository.findByEmail(loginDTO.email())
//                .map(usuario -> convertToDTO(usuario)) // Se já existe, retorna o DTO
//                .orElseGet(() -> {
//                    User novoUsuario = new User();
//                    novoUsuario.setEmail(loginDTO.email());
//                    novoUsuario.setNome(loginDTO.nome());
//
//                    if (loginDTO.email().endsWith("@admin.com") || loginDTO.email().contains("@admin")) {
//                        novoUsuario.setPerfil("ADMIN");
//                    } else {
//                        novoUsuario.setPerfil("USER");
//                    }
//
//                    User usuarioSalvo = usuarioRepository.save(novoUsuario);
//                    return convertToDTO(usuarioSalvo);
//                });
//    }
//
//    private UsuarioResponseDTO convertToDTO(User usuario) {
//        return new UsuarioResponseDTO(
//                usuario.getId(),
//                usuario.getEmail(),
//                usuario.getNome(),
//                usuario.getTrustScore(),
//                usuario.getPerfil());
//    }
}