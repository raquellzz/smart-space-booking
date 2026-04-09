package imd.ufrn.com.br.smart_space_booking.service;

import imd.ufrn.com.br.smart_space_booking.dto.HorarioOcupadoDTO;
import imd.ufrn.com.br.smart_space_booking.dto.ReservaRequestDTO;
import imd.ufrn.com.br.smart_space_booking.dto.ReservaResponseDTO;
import imd.ufrn.com.br.smart_space_booking.enums.ReservaStatus;
import imd.ufrn.com.br.smart_space_booking.enums.ReservaTipo;
import imd.ufrn.com.br.smart_space_booking.model.Reserva;
import imd.ufrn.com.br.smart_space_booking.model.Sala;
import imd.ufrn.com.br.smart_space_booking.model.Usuario;
import imd.ufrn.com.br.smart_space_booking.repository.ReservaRepository;
import imd.ufrn.com.br.smart_space_booking.repository.SalaRepository;
import imd.ufrn.com.br.smart_space_booking.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;

    public ReservaService(ReservaRepository reservaRepository, UsuarioRepository usuarioRepository,SalaRepository salaRepository ) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
    }

    @Transactional
    public ReservaResponseDTO create(ReservaRequestDTO dto) {
        if (dto.fimDateTime().isBefore(dto.inicioDateTime())) {
            throw new RuntimeException("A data de fim deve ser após a data de início.");
        }

        ZonedDateTime fimComBuffer = dto.fimDateTime().plusMinutes(15);

        boolean existeConflito = reservaRepository.existeConflito(
                dto.salaId(), dto.inicioDateTime(), fimComBuffer);

        if (existeConflito) {
            throw new RuntimeException("A sala já está ocupada neste horário (considerando limpeza).");
        }

        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Reserva reserva = new Reserva();
        reserva.setInicioDateTime(dto.inicioDateTime());
        reserva.setFimDateTime(dto.fimDateTime());
        reserva.setTipo(dto.tipo());
        reserva.setStatus(ReservaStatus.CONFIRMADA);
        reserva.setUsuario(usuario);
        reserva.setSala(sala);

        reservaRepository.save(reserva);

        Reserva manutencao = new Reserva();
        manutencao.setInicioDateTime(dto.fimDateTime());
        manutencao.setFimDateTime(fimComBuffer);
        manutencao.setTipo(ReservaTipo.MANUTENCAO);
        manutencao.setStatus(ReservaStatus.CONFIRMADA);
        manutencao.setSala(sala);
        manutencao.setUsuario(null);

        reservaRepository.save(manutencao);

        return ReservaResponseDTO.fromEntity(reserva);
    }

    public List<HorarioOcupadoDTO> findOcupados(Long salaId, LocalDate data) {
        ZonedDateTime inicioDia = data.atStartOfDay(ZoneId.of("America/Fortaleza"));
        ZonedDateTime fimDia = inicioDia.plusDays(1).minusNanos(1);

        return reservaRepository.findReservasPorSalaNoDia(salaId, inicioDia, fimDia)
                .stream()
                .map(HorarioOcupadoDTO::fromEntity)
                .toList();}

    public List<ReservaResponseDTO> findAll() {
        return reservaRepository.findAll()
                .stream()
                .map(ReservaResponseDTO::fromEntity)
                .toList();
    }

    public ReservaResponseDTO findById(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        return ReservaResponseDTO.fromEntity(reserva);
    }

    @Transactional
    public void delete(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        reservaRepository.delete(reserva);
    }

    public List<ReservaResponseDTO> findByUsuario(Long usuarioId) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return reservaRepository.findReservasPorUsuario(usuarioId)
                .stream()
                .map(ReservaResponseDTO::fromEntity)
                .toList();
    }
}
