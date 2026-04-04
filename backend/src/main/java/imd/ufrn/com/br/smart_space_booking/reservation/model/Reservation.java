package imd.ufrn.com.br.smart_space_booking.reservation.model;

import imd.ufrn.com.br.smart_space_booking.base.model.BaseEntity;
import imd.ufrn.com.br.smart_space_booking.reservation.enums.ReservationStatus;
import imd.ufrn.com.br.smart_space_booking.user.model.User;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq")
    @SequenceGenerator(name = "reservation_seq", sequenceName = "reservations_id_seq", allocationSize = 1)
    private Long id;

    private ZonedDateTime startDateTime;

    private ZonedDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public boolean isActiveReservation() {
        return this.status == ReservationStatus.CONFIRMED;
    }

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    public ZonedDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(ZonedDateTime startDateTime) { this.startDateTime = startDateTime; }

    public ZonedDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(ZonedDateTime endDateTime) { this.endDateTime = endDateTime; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}