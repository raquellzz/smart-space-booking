package imd.ufrn.com.br.smart_space_booking.audit.model;

import imd.ufrn.com.br.smart_space_booking.base.model.BaseEntity;
import imd.ufrn.com.br.smart_space_booking.reservation.model.Reservation;
import imd.ufrn.com.br.smart_space_booking.score.model.ScoreTransaction;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "audits")
public class Audit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_seq")
    @SequenceGenerator(name = "audit_seq", sequenceName = "audits_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "result_ia", columnDefinition = "TEXT")
    private String resultAi;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @OneToMany(mappedBy = "audit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuditImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "audit")
    private List<ScoreTransaction> scoreTransactions = new ArrayList<>();

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    public String getResultAi() { return resultAi; }
    public void setResultAi(String resultIa) { this.resultAi = resultAi; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public List<AuditImage> getImages() { return images; }
    public void setImages(List<AuditImage> images) { this.images = images; }

    public List<ScoreTransaction> getScoreTransactions() { return scoreTransactions; }
    public void setScoreTransactions(List<ScoreTransaction> scoreTransactions) { this.scoreTransactions = scoreTransactions; }

}