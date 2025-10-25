package com.example.groupservice.data.daos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.Min
import java.time.OffsetDateTime

/**
 * 
 * @param id 
 * @param name 
 * @param ownerId 
 * @param createdAt 
 */
@Entity
data class GroupDAO(

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @get:Min(0L)
    @Schema(example = "1", required = true, description = "id of the group")
    @get:JsonProperty("id", required = true) val id: Long,

    @Schema(example = "Java Artists", required = true, description = "name of the group")
    @get:JsonProperty("name", required = true) val name: String,

    @Schema(example = "FernandoMendes", required = true, description = "user who created/owns the group")
    @get:JsonProperty("owner_id", required = true) val ownerId: String,

    @Schema(example = "2007-12-03T10:15:30+01:00", required = true, description = "dateTime of creation of the group")
    @get:JsonProperty("created_at", required = true) val createdAt: OffsetDateTime

) {
    constructor() : this(0, "", "", OffsetDateTime.now())
}


