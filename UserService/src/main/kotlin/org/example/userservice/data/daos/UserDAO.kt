package org.example.userservice.data.daos

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import jakarta.validation.constraints.Email
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import java.time.OffsetDateTime

/**
 * 
 * @param username
 * @param displayName
 * @param passwordHash 
 * @param email 
 * @param createdAt 
 * @param role 
 */
@Entity
data class UserDAO(

    @Id
    @Schema(example = "johnmaster120", required = true, description = "username of the user")
    @get:JsonProperty("username", required = true) val username: String,

    @Schema(example = "John Smith", required = true, description = "display name of the user")
    @get:JsonProperty("display_name", required = true) val displayName: String,

    @Schema(example = "$2a$05\$qA7qlcDjDctTnlrylmtdhO4DarCguVr2O2TVI.U7l/qf7ASfeO57C", required = true, description = "hash of the password using the bcrypt encoder")
    @get:JsonProperty("password_hash", required = true) val passwordHash: String,

    @get:Email
    @Schema(example = "faiscamcqueenisthebest@carros.com", required = true, description = "email of the user")
    @get:JsonProperty("email", required = true) val email: String,

    @Schema(example = "2007-12-03T10:15:30+01:00", required = true, description = "date of creation of the user")
    @get:JsonProperty("created_at", required = true) val createdAt: OffsetDateTime,

    @Schema(example = "Default", required = true, description = "role of the user")
    @get:JsonProperty("role", required = true) val role: Role/*,

    @OneToMany
    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("friendships", required = true) val friendships: List<FriendshipDAO>
*/) {
    constructor() : this("", "", "", "", OffsetDateTime.now(), Role.User/*, listOf()*/)

    /**
     *
     * Values: USER,ADMIN,MANAGER
     */
    enum class Role(@get:JsonValue val value: String) {

        User("USER"),
        Admin("ADMIN"),
        Manager("MANAGER");

        companion object {
            @JvmStatic
            @JsonCreator
            fun forValue(value: String): Role {
                return entries.first{ it.value == value}
            }
        }
    }
}
