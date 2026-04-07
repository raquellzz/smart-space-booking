package imd.ufrn.com.br.smart_space_booking.event.model;

import imd.ufrn.com.br.smart_space_booking.base.model.BaseEntity;
import imd.ufrn.com.br.smart_space_booking.event.enums.EventOrigin;
import imd.ufrn.com.br.smart_space_booking.event.enums.EventType;
import imd.ufrn.com.br.smart_space_booking.reservation.model.Reservation;
import jakarta.persistence.*;

@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventOrigin origin;

    public Event() {}

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public EventType getType() { return type; }
    public void setType(EventType type) { this.type = type; }

    public EventOrigin getOrigin() { return origin; }
    public void setOrigin(EventOrigin origin) { this.origin = origin; }
}