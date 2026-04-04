package imd.ufrn.com.br.smart_space_booking.score.model;

import imd.ufrn.com.br.smart_space_booking.base.model.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "score_rules")
public class ScoreRule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "score_rule_seq")
    @SequenceGenerator(name = "score_rule_seq", sequenceName = "score_rules_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private Integer defaultPoints;

    private String description;

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getDefaultPoints() { return defaultPoints; }
    public void setDefaultPoints(Integer defaultPoints) { this.defaultPoints = defaultPoints; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}