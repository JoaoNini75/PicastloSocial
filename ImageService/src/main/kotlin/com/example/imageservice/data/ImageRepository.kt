package com.example.imageservice.data

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import java.time.OffsetDateTime
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

/**
 *
 * @param id Unique identifier of the image
 * @param imageData Raw image data in bytes
 * @param uploadedBy User who uploaded the image
 * @param createdAt Timestamp for when the image was created
 */

@Entity
@Table(name = "image")
data class ImageDAO(

    @Id
    @get:Min(0L)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", required = true, description = "Unique identifier of the image")
    @get:JsonProperty("id", required = true)
    val id: Long,


    @Lob
    @Basic(fetch = FetchType.LAZY) // Lazily fetch large data like blobs
    @Column(name = "image_data", nullable = false, columnDefinition = "BLOB") //The column is explicitly marked as BLOB to ensure proper storage of binary data.
    @Schema(required = true, description = "Raw image data in bytes")
    val imageData: ByteArray,


    @Column(name = "uploaded_by", nullable = false)
    @Schema(example= "FernandoMendes", required = true, description = "User who uploaded the image")
    @get:JsonProperty("uploaded_by", required = true)
    val uploadedBy: String,

    @Column(name = "created_at", nullable = false)
    @Schema(example = "2024-01-01T10:00:00Z", required = true, description = "Timestamp for when the image was created")
    @get:JsonProperty("created_at", required = true)
    val createdAt: OffsetDateTime
)
{
    constructor() : this(0, ByteArray(0), "", OffsetDateTime.now())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageDAO

        if (id != other.id) return false
        if (!imageData.contentEquals(other.imageData)) return false
        if (uploadedBy != other.uploadedBy) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + imageData.contentHashCode()
        result = 31 * result + uploadedBy.hashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }
}

interface ImageRepository : CrudRepository<ImageDAO, Long>, PagingAndSortingRepository<ImageDAO, Long> {
    @Query("select i from ImageDAO i where i.uploadedBy = :username")
    fun findByUploadedBy(username: String, pageable: Pageable): Page<ImageDAO>
}



