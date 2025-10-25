package org.example.userservice.data

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 
 * @param acceptedInvite
 */
data class InviteResponse(

    @Schema(example = "true", required = true, description = "the response to the friendship invite")
    @get:JsonProperty("acceptedInvite", required = true) val acceptedInvite: Boolean

)
