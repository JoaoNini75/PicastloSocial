package com.example.groupservice.data.daos

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import java.time.OffsetDateTime

/**
 * Represents a member in a group with a composite primary key.
 *
 * @param id Composite key containing username and groupId.
 * @param createdAt The timestamp when the user was added to the group.
 */
@Entity
data class GroupMembershipDAO(

    @EmbeddedId
    val id: GroupMembershipId,

    @Schema(example = "Java Artists", required = true, description = "Name of the group")
    @get:JsonProperty("name", required = true) val name: String,

    @Schema(example = "2007-12-03T10:15:30+01:00", required = true, description = "Timestamp when the membership was created")
    @get:JsonProperty("created_at", required = true) val createdAt: OffsetDateTime
) {
    constructor() : this(GroupMembershipId("", 0),"", OffsetDateTime.now())
}

/**
 * Composite key class for GroupMembershipDAO.
 *
 * @param username The username of the group member.
 * @param groupId The ID of the group.
 */
@Embeddable
data class GroupMembershipId(

    @Schema(example = "Fernandico", required = true, description = "Username of the user that is a member of the group")
    @get:JsonProperty("username", required = true) val username: String = "",

    @get:Min(0L)
    @Schema(example = "3", required = true, description = "ID of the group the user is a member of")
    @get:JsonProperty("group_id", required = true) val groupId: Long = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GroupMembershipId

        if (username != other.username) return false
        if (groupId != other.groupId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + groupId.hashCode()
        return result
    }
}

