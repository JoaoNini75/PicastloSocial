package org.example.postservice.data

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.OffsetDateTime

/**
 * 
 * @param id 
 * @param userId 
 * @param description 
 * @param resultImageId 
 * @param originalImageId 
 * @param pipelineId 
 * @param createdAt 
 */
@Entity
data class PostDAO(

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @get:Min(0L)
    @Schema(example = "1", required = true, description = "id of the post")
    @get:JsonProperty("id", required = true) val id: Long,

    @Schema(example = "johnmaster120", required = true, description = "username of the user")
    @get:JsonProperty("username", required = true) val username: String,

    @Schema(example = "making awesome pictures", required = true, description = "description of the post")
    @get:JsonProperty("description", required = true) val description: String,

    @get:Min(0L)
    @Schema(example = "3", required = true, description = "id of the image after using the pipeline")
    @get:JsonProperty("result_image_id", required = true) val resultImageId: Long,

    @Schema(example = "4", required = true, description = "id of the image before using the pipeline")
    @get:JsonProperty("original_image_id", required = true) val originalImageId: Long,

    @get:Min(0L)
    @Schema(example = "5", required = true, description = "id of the pipeline")
    @get:JsonProperty("pipeline_id", required = true) val pipelineId: Long,

    @Schema(
        example = "0",
        required = true,
        description = "Visibility of the post: 0 - private, 1 - friends, 2 - public, 10x where x is group ID for specific groups"
    )
    @get:JsonProperty("visibility", required = true)
    val visibility: Int,

    @Schema(example = "2007-12-03T10:15:30", required = true, description = "date of creation of the post")
    @get:JsonProperty("created_at", required = true) val createdAt: java.time.OffsetDateTime
    
) {
    constructor() : this(0, "", "", 0, 0, 0,0, OffsetDateTime.now())
}
