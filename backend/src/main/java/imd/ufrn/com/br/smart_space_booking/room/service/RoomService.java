package imd.ufrn.com.br.smart_space_booking.room.service;

import imd.ufrn.com.br.smart_space_booking.base.mappers.DtoMapper;
import imd.ufrn.com.br.smart_space_booking.base.repository.GenericRepository;
import imd.ufrn.com.br.smart_space_booking.base.service.GenericService;
import imd.ufrn.com.br.smart_space_booking.room.dto.RoomDTO;
import imd.ufrn.com.br.smart_space_booking.room.mappers.RoomMapper;
import imd.ufrn.com.br.smart_space_booking.room.model.Room;
import imd.ufrn.com.br.smart_space_booking.room.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomService implements GenericService<Room, RoomDTO> {

    private final RoomRepository repository;
    private final RoomMapper mapper;

    public RoomService(RoomRepository repository, RoomMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public GenericRepository<Room> getRepository() {
        return repository;
    }

    @Override
    public DtoMapper<Room, RoomDTO> getDtoMapper() {
        return mapper;
    }

}