package imd.ufrn.com.br.smart_space_booking.user.model;

import imd.ufrn.com.br.smart_space_booking.base.model.BaseEntity;
import imd.ufrn.com.br.smart_space_booking.score.model.ScoreTransaction;
import imd.ufrn.com.br.smart_space_booking.user.enums.UserType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String email;

    private Integer score = 0;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @OneToMany(mappedBy = "user")
    private List<ScoreTransaction> transactions = new ArrayList<>();

    public void addScore(int amount) {
        this.score += amount;
    }

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public UserType getType() { return type; }
    public void setType(UserType type) { this.type = type; }

    public List<ScoreTransaction> getTransactions() { return transactions; }
    public void setTransactions(List<ScoreTransaction> transactions) { this.transactions = transactions; }
}