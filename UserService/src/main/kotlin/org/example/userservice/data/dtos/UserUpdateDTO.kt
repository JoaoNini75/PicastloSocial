package org.example.userservice.data.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email

data class UserUpdateDTO (

    @Schema(example = "johnmaster120", required = true, description = "username of the user")
    @get:JsonProperty("username", required = true) val username: String,

    @Schema(example = "John Smith", required = true, description = "display name of the user")
    @get:JsonProperty("display_name", required = true) val displayName: String,

    @Schema(example = "$2a$05\$qA7qlcDjDctTnlrylmtdhO4DarCguVr2O2TVI.U7l/qf7ASfeO57C", required = true, description = "hash of the password using the bcrypt encoder")
    @get:JsonProperty("password_hash", required = true) val passwordHash: String,

    @get:Email
    @Schema(example = "faiscamcqueenisthebest@carros.com", required = true, description = "email of the user")
    @get:JsonProperty("email", required = true) val email: String

) {
    constructor() : this("", "", "", "")
}