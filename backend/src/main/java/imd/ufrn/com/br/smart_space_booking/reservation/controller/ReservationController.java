package imd.ufrn.com.br.smart_space_booking.reservation.controller;

import imd.ufrn.com.br.smart_space_booking.base.controller.GenericController;
import imd.ufrn.com.br.smart_space_booking.reservation.dto.ReservationDTO;
import imd.ufrn.com.br.smart_space_booking.reservation.model.Reservation;
import imd.ufrn.com.br.smart_space_booking.reservation.service.ReservationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController extends GenericController<Reservation, ReservationDTO, ReservationService> {

    public ReservationController(ReservationService service) {
        super(service);
    }
}
