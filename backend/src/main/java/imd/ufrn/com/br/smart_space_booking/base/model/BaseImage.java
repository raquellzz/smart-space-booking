package imd.ufrn.com.br.smart_space_booking.base.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseImage extends BaseEntity {

    /**
     * Bucket name in MinIO
     * Ex: "smart-space-booking"
     */
    @Column(name = "bucket_name", nullable = false)
    protected String bucketName;

    /**
     * Bucket object path
     * Ex: "rooms/42/foto_sala.jpg"
     */
    @Column(name = "object_key", nullable = false)
    protected String objectKey;

    /**
     * File original name .
     * Ex: "foto_sala.jpg"
     */
    @Column(name = "original_filename")
    protected String originalFilename;

    /**
     * Archive MIME type.
     * Ex: "image/jpeg", "image/png"
     */
    @Column(name = "content_type")
    protected String contentType;

    /**
     * File Size.
     */
    @Column(name = "size_bytes")
    protected Long sizeBytes;

    public String getBucketName() { return bucketName; }
    public void setBucketName(String bucketName) { this.bucketName = bucketName; }

    public String getObjectKey() { return objectKey; }
    public void setObjectKey(String objectKey) { this.objectKey = objectKey; }

    public String getOriginalFilename() { return originalFilename; }
    public void setOriginalFilename(String originalFilename) { this.originalFilename = originalFilename; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public Long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(Long sizeBytes) { this.sizeBytes = sizeBytes; }
}
