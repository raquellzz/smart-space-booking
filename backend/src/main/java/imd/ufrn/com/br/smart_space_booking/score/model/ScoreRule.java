package imd.ufrn.com.br.smart_space_booking.score.model;

import imd.ufrn.com.br.smart_space_booking.base.model.BaseEntity;
import imd.ufrn.com.br.smart_space_booking.score.enums.ScoreRuleType;
import imd.ufrn.com.br.smart_space_booking.score.enums.ScoreTransactionType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "score_rules")
public class ScoreRule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "score_rule_seq")
    @SequenceGenerator(name = "score_rule_seq", sequenceName = "score_rules_id_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "score_transaction_type", nullable = false)
    private ScoreTransactionType scoreTransactionType;

    @Column(nullable = false)
    private Integer points;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScoreRuleType type;

    @OneToMany(mappedBy = "rule")
    private List<ScoreTransaction> transactions = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ScoreTransactionType getScoreTransactionType() {
        return scoreTransactionType;
    }

    public void setScoreTransactionType(ScoreTransactionType scoreTransactionType) {
        this.scoreTransactionType = scoreTransactionType;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public ScoreRuleType getType() {
        return type;
    }

    public void setType(ScoreRuleType type) {
        this.type = type;
    }

    public List<ScoreTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<ScoreTransaction> transactions) {
        this.transactions = transactions;
    }
}