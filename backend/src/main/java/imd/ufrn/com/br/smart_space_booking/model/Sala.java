package imd.ufrn.com.br.smart_space_booking.model;

import imd.ufrn.com.br.smart_space_booking.enums.StatusSala;
import imd.ufrn.com.br.smart_space_booking.enums.TipoSala;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sala")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 500, nullable = false)
    private String local;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusSala status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSala tipoSala;

    @Column(nullable = false)
    private Integer capacidade;

    @ElementCollection
    @CollectionTable(name = "sala_caracteristicas", joinColumns = @JoinColumn(name = "sala_id"))
    @Column(name = "caracteristica")
    private List<String> caracteristicas = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "sala_imagens", joinColumns = @JoinColumn(name = "sala_id"))
    @Column(name = "imagem")
    @NotEmpty(message = "A sala deve ter pelo menos uma imagem.")
    private List<String> imagens = new ArrayList<>();

    public Sala() {}

    public Sala(String nome, String local, StatusSala status, TipoSala tipoSala, Integer capacidade, List<String> caracteristicas, List<String> imagens) {
        this.nome = nome;
        this.local = local;
        this.status = status;
        this.tipoSala = tipoSala;
        this.capacidade = capacidade;
        this.caracteristicas = caracteristicas;
        this.imagens = imagens;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getLocal() {return local;}
    public void setLocal(String local) {this.local = local;}

    public StatusSala getStatus() {return status;}
    public void setStatus(StatusSala status) {this.status = status;}

    public TipoSala getTipoSala() {return tipoSala;}
    public void setTipoSala(TipoSala tipoSala) {this.tipoSala = tipoSala;}

    public Integer getCapacidade() {return capacidade;}
    public void setCapacidade(Integer capacidade) {this.capacidade = capacidade;}

    public List<String> getCaracteristicas() {return caracteristicas;}
    public void setCaracteristicas(List<String> caracteristicas) {this.caracteristicas = caracteristicas;}

    public List<String> getImagens() {return imagens;}
    public void setImagens(List<String> imagens) {this.imagens = imagens;}
}