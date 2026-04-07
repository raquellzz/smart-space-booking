package imd.ufrn.com.br.smart_space_booking.score.model;

import imd.ufrn.com.br.smart_space_booking.audit.model.Audit;
import imd.ufrn.com.br.smart_space_booking.base.model.BaseEntity;
import imd.ufrn.com.br.smart_space_booking.reservation.model.Reservation;
import imd.ufrn.com.br.smart_space_booking.score.enums.TransactionOrigin;
import imd.ufrn.com.br.smart_space_booking.user.model.User;
import jakarta.persistence.*;

@Entity
@Table(name = "score_transactions")
public class ScoreTransaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "score_transaction_seq")
    @SequenceGenerator(name = "score_transaction_seq", sequenceName = "score_transactions_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionOrigin origin;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id")
    private ScoreRule rule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_id")
    private Audit audit;

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public TransactionOrigin getOrigin() { return origin; }
    public void setOrigin(TransactionOrigin origin) { this.origin = origin; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public ScoreRule getRule() { return rule; }
    public void setRule(ScoreRule rule) { this.rule = rule; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public Audit getAudit() { return audit; }
    public void setAudit(Audit audit) { this.audit = audit; }
}