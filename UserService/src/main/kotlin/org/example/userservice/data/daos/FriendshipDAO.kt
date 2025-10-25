package org.example.userservice.data.daos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import java.io.Serializable
import java.time.OffsetDateTime

data class FriendshipId(val username1: String, val username2: String): Serializable {
    constructor(): this("", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FriendshipId

        if (username1 != other.username1) return false
        if (username2 != other.username2) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username1.hashCode()
        result = 31 * result + username2.hashCode()
        return result
    }
}

/**
 * 
 * @param username1
 * @param username2
 * @param createdAt 
 * @param wasAccepted 
 */
@Entity
@IdClass(FriendshipId::class)
data class FriendshipDAO (

    @Id
    @Schema(example = "johnMaster45", required = true, description = "username of the user that sends the friendship invite")
    @get:JsonProperty("username1", required = true) val username1: String,

    @Id
    @Schema(example = "brianSpilner2001", required = true, description = "username of the user receiving the friendship invite")
    @get:JsonProperty("username2", required = true) val username2: String,

    @Schema(example = "2007-12-03T10:15:30+01:00", required = true, description = "date and time when the friendship invite was sent")
    @get:JsonProperty("created_at", required = true) val createdAt: OffsetDateTime,

    @Schema(example = "true", required = true, description = "wheter the user2Id user responded to the friendship invite or not")
    @get:JsonProperty("got_response", required = true) val gotResponse: Boolean,

    @Schema(example = "false", required = true, description = "the response that the user2Id user gave to the friendship invite")
    @get:JsonProperty("was_accepted", required = true) val wasAccepted: Boolean

) {
    constructor() : this("", "", OffsetDateTime.now(), false, false)
}
