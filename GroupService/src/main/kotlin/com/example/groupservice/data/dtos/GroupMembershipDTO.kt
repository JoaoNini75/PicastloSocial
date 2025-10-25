package com.example.groupservice.data.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

data class GroupMembershipDTO(

    @Schema(example = "Fernandico", required = true, description = "username of the user that is a member of the group")
    @get:JsonProperty("username", required = true) val username: String,

    @get:Min(0L)
    @Schema(example = "3", required = true, description = "id of the group that has a member identified by userId")
    @get:JsonProperty("group_id", required = true) val groupId: Long,


    @Schema(example = "Java Artists", required = true, description = "")
    @get:JsonProperty("name", required = true) val name: String,

    @Schema(example = "2007-12-03T10:15:30+01:00", required = true, description = "")
    @get:JsonProperty("created_at", required = true) val createdAt: OffsetDateTime
)

