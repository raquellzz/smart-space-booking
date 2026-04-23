package imd.ufrn.com.br.smart_space_booking.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import imd.ufrn.com.br.smart_space_booking.dto.CheckinRequestDTO;
import imd.ufrn.com.br.smart_space_booking.dto.HorarioOcupadoDTO;
import imd.ufrn.com.br.smart_space_booking.dto.ReservaRequestDTO;
import imd.ufrn.com.br.smart_space_booking.dto.ReservaResponseDTO;
import imd.ufrn.com.br.smart_space_booking.enums.ReservaStatus;
import imd.ufrn.com.br.smart_space_booking.enums.ReservaTipo;
import imd.ufrn.com.br.smart_space_booking.exception.ConflitoHorarioException;
import imd.ufrn.com.br.smart_space_booking.exception.RegraNegocioException;
import imd.ufrn.com.br.smart_space_booking.exception.ReservaNotFoundException;
import imd.ufrn.com.br.smart_space_booking.exception.SalaNotFoundException;
import imd.ufrn.com.br.smart_space_booking.exception.UsuarioNotFoundException;
import imd.ufrn.com.br.smart_space_booking.model.Reserva;
import imd.ufrn.com.br.smart_space_booking.model.Sala;
import imd.ufrn.com.br.smart_space_booking.model.Usuario;
import imd.ufrn.com.br.smart_space_booking.repository.ReservaRepository;
import imd.ufrn.com.br.smart_space_booking.repository.SalaRepository;
import imd.ufrn.com.br.smart_space_booking.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

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
            throw new RegraNegocioException("A data de fim não pode ser anterior à data de início.");
        }

        ZonedDateTime fimComBuffer = dto.fimDateTime().plusMinutes(15);

        boolean existeConflito = reservaRepository.existeConflito(
                dto.salaId(), dto.inicioDateTime(), fimComBuffer);

        if (existeConflito) {
            throw new ConflitoHorarioException("A sala já está ocupada neste horário (considerando limpeza).");
        }

        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new SalaNotFoundException("Nenhuma sala encontrada com o ID: " + dto.salaId()));
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new UsuarioNotFoundException("Nenhum usuário encontrado com o ID: " + dto.usuarioId()));

        Reserva reserva = new Reserva();
        reserva.setInicioDateTime(dto.inicioDateTime());
        reserva.setFimDateTime(dto.fimDateTime());
        reserva.setTipo(dto.tipo());
        reserva.setStatus(ReservaStatus.CONFIRMADA);
        reserva.setUsuario(usuario);
        reserva.setSala(sala);
        reserva.setFotoCheckinId(null);
        reserva.setDataHoraCheckin(null);
        reserva.setMotivoCancelamento(null);

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
                .orElseThrow(() -> new ReservaNotFoundException("Reserva não encontrada com o ID: " + id));
        return ReservaResponseDTO.fromEntity(reserva);
    }

    @Transactional
    public void delete(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNotFoundException("Não é possível deletar. Reserva não encontrada com o ID: " + id));
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
    @Transactional
    public void realizarCheckin(Long reservaId, Long usuarioLogadoId, CheckinRequestDTO dto) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservaNotFoundException("Reserva não encontrada"));
        
        if (reserva.getUsuario() == null) {
            throw new RuntimeException("Erro interno: A reserva no banco está sem usuário associado.");
        }

        if (!reserva.getUsuario().getId().equals(usuarioLogadoId)) {
            throw new RegraNegocioException("Acesso negado: Você não pode fazer check-in em uma reserva de outro usuário.");
        }

        if (reserva.getStatus() == ReservaStatus.CANCELADA) {
            throw new RegraNegocioException("Não é possível fazer check-in de uma reserva cancelada.");
        }

        if (reserva.getDataHoraCheckin() != null) {
            throw new RegraNegocioException("Check-in já foi realizado para esta reserva.");
        }
        ZonedDateTime agora = ZonedDateTime.now();
        ZonedDateTime inicio = reserva.getInicioDateTime();
        ZonedDateTime limiteCheckin = inicio.plusMinutes(10);

        if (agora.isBefore(inicio)) {
            throw new RuntimeException("O horário da reserva ainda não começou.");
        }

        if (agora.isAfter(limiteCheckin)) {
            reserva.setStatus(ReservaStatus.CANCELADA);
            reserva.setMotivoCancelamento("NO_SHOW");
            reservaRepository.save(reserva);
            throw new RegraNegocioException("Tempo de check-in expirado. Reserva cancelada por No-Show.");
        }

        reserva.setFotoCheckinId(dto.fotoCheckinId());
        reserva.setDataHoraCheckin(agora);
        
        reservaRepository.save(reserva);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cancelarReservasExpiradasPorNoShow() {
        ZonedDateTime agora = ZonedDateTime.now();
        ZonedDateTime limiteTolerancia = agora.minusMinutes(10);

        List<Reserva> reservasExpiradas = reservaRepository.findReservasPendentesExpiradas(limiteTolerancia);

        if (!reservasExpiradas.isEmpty()) {
            for (Reserva reserva : reservasExpiradas) {
                reserva.setStatus(ReservaStatus.CANCELADA);
                reserva.setMotivoCancelamento("NO_SHOW_AUTOMATICO");
            }
            reservaRepository.saveAll(reservasExpiradas);
            System.out.println("Scheduler: Canceladas " + reservasExpiradas.size() + " reservas por No-Show.");
        }
    }
}
