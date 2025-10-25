package com.example.pipelineservice.data

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository


/**
 *
 * @param id Unique identifier of the pipeline
 * @param pipelineData Raw pipeline data in bytes
 * @param uploadedBy User who uploaded the pipeline
 * @param createdAt Timestamp for when the pipeline was created
 */
@Entity
@Table(name = "pipeline")
class PipelineDAO (

    @Id
    @get:Min(0L)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", required = true, description = "Unique identifier of the pipeline")
    @get:JsonProperty("id", required = true)
    val id: Long,

    @Lob
    @Basic(fetch = FetchType.LAZY) // Lazily fetch large data like blobs
    @Column(name = "pipeline_data", nullable = false, columnDefinition = "BLOB") //The column is explicitly marked as BLOB to ensure proper storage of binary data.
    @Schema(required = true, description = "Raw pipeline data in bytes")
    val pipelineData: ByteArray,

    @Column(name = "uploaded_by", nullable = false)
    @Schema(example= "FernandoMendes", required = true, description = "User who uploaded the image")
    @get:JsonProperty("uploaded_by", required = true)
    val uploadedBy: String,

    @Column(name = "created_at", nullable = false)
    @Schema(example = "2024-01-01T10:00:00Z", required = true, description = "Timestamp for when the pipeline was created")
    @get:JsonProperty("created_at", required = true) val createdAt: java.time.OffsetDateTime
    ) {
    constructor() : this(0, ByteArray(0), "", java.time.OffsetDateTime.now())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PipelineDAO

        if (id != other.id) return false
        if (!pipelineData.contentEquals(other.pipelineData)) return false
        if (uploadedBy != other.uploadedBy) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + pipelineData.contentHashCode()
        result = 31 * result + uploadedBy.hashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }
}

interface PipelineRepository : CrudRepository<PipelineDAO, Long>, PagingAndSortingRepository<PipelineDAO, Long> {

    @Query("select p from PipelineDAO p where p.uploadedBy = :userId")
    fun findByUploadedBy(userId: String, pageable: Pageable): Page<PipelineDAO>
}

