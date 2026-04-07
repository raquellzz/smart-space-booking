package imd.ufrn.com.br.smart_space_booking.room.model;

import imd.ufrn.com.br.smart_space_booking.base.model.BaseEntity;
import imd.ufrn.com.br.smart_space_booking.base.model.BaseImage;
import jakarta.persistence.*;

@Entity
@Table(name = "room_images")
public class RoomImage extends BaseImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "display_order")
    private Integer displayOrder;

    public RoomImage() {}

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
