package imd.ufrn.com.br.smart_space_booking.room.model;

import imd.ufrn.com.br.smart_space_booking.base.model.BaseEntity;
import imd.ufrn.com.br.smart_space_booking.reservation.model.Reservation;
import imd.ufrn.com.br.smart_space_booking.resource.model.Resource;
import imd.ufrn.com.br.smart_space_booking.room.enums.RoomStatus;
import imd.ufrn.com.br.smart_space_booking.room.enums.RoomType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String location;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @Column(nullable = false)
    private Integer capacity;

    @ElementCollection
    @CollectionTable(name = "room_characteristics", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "characteristics")
    private List<String> characteristics = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_id")
    private List<Resource> resources = new ArrayList<>();

    public Room() {}

    @Override
    public Long getId() {return id;}

    @Override
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public RoomStatus getStatus() {return status;}
    public void setStatus(RoomStatus status) {this.status = status;}

    public RoomType getType() {return type;}
    public void setType(RoomType type) {this.type = type;}

    public Integer getCapacity() {return capacity;}
    public void setCapacity(Integer capacity) {this.capacity = capacity;}

    public List<String> getCharacteristics() {return characteristics;}
    public void setCharacteristics(List<String> characteristics) {this.characteristics = characteristics;}

    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    public List<Resource> getResources() { return resources; }
    public void setResources(List<Resource> resources) { this.resources = resources;}
}
