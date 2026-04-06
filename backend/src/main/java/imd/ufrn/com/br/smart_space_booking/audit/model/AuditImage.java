package imd.ufrn.com.br.smart_space_booking.audit.model;

import imd.ufrn.com.br.smart_space_booking.base.model.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name="audit_images")
public class AuditImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_image_seq")
    @SequenceGenerator(name = "audit_image_seq", sequenceName = "audit_image_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_id", nullable = false)
    private Audit audit;

    @Lob
    @Column(name = "file", nullable = false)
    private byte[] file;

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    public Audit getAudit() { return audit; }
    public void setAudit(Audit audit) { this.audit = audit; }

    public byte[] getFile() { return file; }
    public void setFile(byte[] file) { this.file = file; }

}
