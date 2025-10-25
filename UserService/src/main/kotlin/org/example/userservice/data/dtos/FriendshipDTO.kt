package org.example.userservice.data.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

/**
 *
 * @param username1
 * @param username2
 * @param createdAt
 * @param wasAccepted
 */
data class FriendshipDTO (

    @Schema(example = "johnMaster45", required = true, description = "username of the user that sends the friendship invite")
    @get:JsonProperty("username1", required = true) val username1: String,

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
