package org.example.userservice.data.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class UserLoginDTO (

    @Schema(example = "johnmaster120", required = true, description = "username of the user")
    @get:JsonProperty("username", required = true) var username: String,

    @Schema(example = "pwd1", required = true, description = "the password of the user")
    @get:JsonProperty("password", required = true) var password: String,

) {
    constructor() : this("", "")
}