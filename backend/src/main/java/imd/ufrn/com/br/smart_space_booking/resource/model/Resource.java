package imd.ufrn.com.br.smart_space_booking.resource.model;

import imd.ufrn.com.br.smart_space_booking.base.model.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name="resources")
public class Resource extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public Long getId() {return id;}

    @Override
    public void setId(Long id) {this.id = id;}
}
