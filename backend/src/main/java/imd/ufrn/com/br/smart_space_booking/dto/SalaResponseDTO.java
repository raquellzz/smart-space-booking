package imd.ufrn.com.br.smart_space_booking.dto;

import java.util.List;

public record SalaResponseDTO(Long id, String nome, String local, Integer capacidade, String tipo, String status, List<String> caracteristicas,  List<String> imagens) {
}