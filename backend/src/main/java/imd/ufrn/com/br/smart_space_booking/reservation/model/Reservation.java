package imd.ufrn.com.br.smart_space_booking.reservation.model;

import imd.ufrn.com.br.smart_space_booking.audit.model.Audit;
import imd.ufrn.com.br.smart_space_booking.base.model.BaseEntity;
import imd.ufrn.com.br.smart_space_booking.event.model.Event;
import imd.ufrn.com.br.smart_space_booking.reservation.enums.ReservationStatus;
import imd.ufrn.com.br.smart_space_booking.reservation.enums.ReservationType;
import imd.ufrn.com.br.smart_space_booking.room.model.Room;
import imd.ufrn.com.br.smart_space_booking.score.model.ScoreTransaction;
import imd.ufrn.com.br.smart_space_booking.user.model.User;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq")
    @SequenceGenerator(name = "reservation_seq", sequenceName = "reservation_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "start_datetime", nullable = false)
    private ZonedDateTime startDateTime;

    @Column(name = "end_datetime", nullable = false)
    private ZonedDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<Audit> audits = new ArrayList<>();

    @OneToMany(mappedBy = "reservation")
    private List<ScoreTransaction> scoreTransactions = new ArrayList<>();

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

    public ReservationType getType() { return type; }
    public void setType(ReservationType type) { this.type = type; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }

    public List<Audit> getAudits() { return audits; }
    public void setAudits(List<Audit> audits) { this.audits = audits; }

    public List<ScoreTransaction> getScoreTransactions() { return scoreTransactions; }
    public void setScoreTransactions(List<ScoreTransaction> scoreTransactions) { this.scoreTransactions = scoreTransactions; }
}