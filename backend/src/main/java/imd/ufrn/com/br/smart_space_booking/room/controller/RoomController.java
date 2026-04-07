package imd.ufrn.com.br.smart_space_booking.room.controller;

import imd.ufrn.com.br.smart_space_booking.base.controller.GenericController;
import imd.ufrn.com.br.smart_space_booking.room.dto.RoomDTO;
import imd.ufrn.com.br.smart_space_booking.room.model.Room;
import imd.ufrn.com.br.smart_space_booking.room.service.RoomService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
public class RoomController extends GenericController<Room, RoomDTO, RoomService> {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        super(roomService);
        this.roomService = roomService;
    }

}
