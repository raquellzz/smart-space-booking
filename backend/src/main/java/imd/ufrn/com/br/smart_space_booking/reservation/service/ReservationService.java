package imd.ufrn.com.br.smart_space_booking.reservation.service;

import imd.ufrn.com.br.smart_space_booking.base.mappers.DtoMapper;
import imd.ufrn.com.br.smart_space_booking.base.repository.GenericRepository;
import imd.ufrn.com.br.smart_space_booking.base.service.GenericService;
import imd.ufrn.com.br.smart_space_booking.reservation.dto.ReservationDTO;
import imd.ufrn.com.br.smart_space_booking.reservation.enums.ReservationStatus;
import imd.ufrn.com.br.smart_space_booking.reservation.mappers.ReservationMapper;
import imd.ufrn.com.br.smart_space_booking.reservation.model.Reservation;
import imd.ufrn.com.br.smart_space_booking.reservation.repository.ReservationRepository;
import imd.ufrn.com.br.smart_space_booking.room.repository.RoomRepository;
import imd.ufrn.com.br.smart_space_booking.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReservationService implements GenericService<Reservation, ReservationDTO> {
    private final ReservationRepository repository;
    private final ReservationMapper mapper;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository repository, ReservationMapper mapper,
                              UserRepository userRepository, RoomRepository roomRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public ReservationDTO create(ReservationDTO dto) {
        if (dto.endDateTime().isBefore(dto.startDateTime())) {
            throw new RuntimeException("A data de fim deve ser após a data de início.");
        }

        boolean hasConflict = repository.existsOverlapping(
                dto.roomId(), dto.startDateTime(), dto.endDateTime());

        if (hasConflict) {
            throw new RuntimeException("A sala já está ocupada neste horário.");
        }

        Reservation reservation = mapper.toEntity(dto);
        reservation.setUser(userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
        reservation.setRoom(roomRepository.findById(dto.roomId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada")));

        reservation.setStatus(ReservationStatus.CONFIRMED);

        return mapper.toDto(repository.save(reservation));
    }

    @Override public ReservationRepository getRepository() { return repository; }
    @Override public ReservationMapper getDtoMapper() { return mapper; }
}
