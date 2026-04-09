package imd.ufrn.com.br.smart_space_booking.service;

import imd.ufrn.com.br.smart_space_booking.dto.SalaResponseDTO;
import imd.ufrn.com.br.smart_space_booking.model.Sala;
import imd.ufrn.com.br.smart_space_booking.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<SalaResponseDTO> listarTodas() {
        return salaRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Optional<SalaResponseDTO> buscarPorId(Long id) {
        return salaRepository.findById(id)
                .map(this::convertToDTO);
    }

    public SalaResponseDTO salvar(Sala sala) {
        Sala salaSalva = salaRepository.save(sala);
        return convertToDTO(salaSalva);
    }

    public SalaResponseDTO atualizar(Long id, Sala salaDadosNovos) {
        return salaRepository.findById(id).map(salaExistente -> {

            salaExistente.setNome(salaDadosNovos.getNome());
            salaExistente.setCapacidade(salaDadosNovos.getCapacidade());
            salaExistente.setLocal(salaDadosNovos.getLocal());
            salaExistente.setStatus(salaDadosNovos.getStatus());
            salaExistente.setTipoSala(salaDadosNovos.getTipoSala());
            salaExistente.setCaracteristicas(salaDadosNovos.getCaracteristicas());
            salaExistente.setImagens(salaDadosNovos.getImagens());
            Sala salaAtualizada = salaRepository.save(salaExistente);
            return convertToDTO(salaAtualizada);

        }).orElseThrow(() -> new RuntimeException("Sala com ID " + id + " não encontrada!"));
    }

    public boolean deletar(Long id) {
        return salaRepository.findById(id).map(sala -> {
            salaRepository.delete(sala);
            return true;
        }).orElse(false);
    }

    private SalaResponseDTO convertToDTO(Sala sala) {
        return new SalaResponseDTO(
                sala.getId(),
                sala.getNome(),
                sala.getLocal(),
                sala.getCapacidade(),
                sala.getTipoSala().toString(),
                sala.getStatus().toString(),
                sala.getCaracteristicas(),
                sala.getImagens());
    }
}