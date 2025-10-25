package org.example.userservice.presentation

import com.example.groupservice.data.dtos.GroupDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.example.userservice.data.InviteResponse
import org.example.userservice.data.dtos.*
import org.example.userservice.security.security.*
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface UserAPI {
    @GetMapping("/hello")
    fun hello(): String

    @Operation(
        summary = "List Users",
        operationId = "list-users",
        description = """List all users in the application""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK") ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/user"],
        produces = ["application/json"]
    )
    fun listUsers(@RequestParam(required = true, defaultValue = "0") page: Int,
                  @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<UserDTO>>


    @Operation(
        summary = "Search Users",
        operationId = "searchUsers",
        description = """Search for users by username or name using a text filter""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(array = ArraySchema(schema = Schema(implementation = UserDTO::class)))]),
            ApiResponse(responseCode = "400", description = "Bad Request"),
            ApiResponse(responseCode = "404", description = "Not Found")
        ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/user/search"],
        produces = ["application/json"]
    )
    fun searchUsers(
        @Parameter(description = "Text to filter users by username or name", required = true)
        @RequestParam("filter") filter: String,

        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponseEntity<Page<UserDTO>>

    @Operation(
        summary = "Delete User",
        operationId = "deleteUser",
        description = """Deletes a user given its username""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "401", description = "Unauthorized"),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/user/{username}"]
    )
    @CanReadOneUser
    fun deleteUser(@Parameter(description = "the username of the user", required = true) @PathVariable("username") username: String): ResponseEntity<UserDTO>

    @Operation(
        summary = "Remove Friendship",
        operationId = "deleteUserusernameFriendsfriendUsername",
        description = """Delete a friendship between two users whose usernames are given by username and friendUsername""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/user/{username}/friends/{friendUsername}"]
    )
    @CanDeleteFriendShip
    fun removeFriendship(
        @Parameter(description = "username of one of the users in the friendship", required = true)
        @PathVariable("username") username: String,

        @Parameter(description = "username of the other user in the friendship", required = true)
        @PathVariable("friendUsername") friendUsername: String): ResponseEntity<Unit>

    @Operation(
        summary = "Get User",
        operationId = "getUser",
        description = """get the data of a user given its username""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = UserDTO::class))]),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/user/{username}"],
        produces = ["application/json"]
    )
    @CanReadOneUser
    fun getUser(@PathVariable("username") username: String): ResponseEntity<UserDTO>

    @Operation(
        summary = "List User Friends",
        operationId = "listUserusernameFriends",
        description = """list the friends of a user given its username (only friendships where there was a positive response to the invite""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(array = ArraySchema(schema = Schema(implementation = UserDTO::class)))]),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/user/{username}/friends"],
        produces = ["application/json"]
    )
    @CanReadOneUser
    fun listUserFriends(@Parameter(description = "username of the user", required = true) @PathVariable("username") username: String,
                          @RequestParam(required = true, defaultValue = "0") page: Int,
                          @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<UserDTO>>

    @Operation(
        summary = "List User Friendship Invites",
        operationId = "listUserusernameFriendshipInvites",
        description = """list the friendship invites of a user""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(array = ArraySchema(schema = Schema(implementation = UserDTO::class)))]),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/user/{username}/friends/invites"],
        produces = ["application/json"]
    )
    @CanReadOneUser
    fun listUserFriendshipInvites(
        @Parameter(description = "username of the user", required = true) @PathVariable("username") username: String,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<UserDTO>>

    @Operation(
        summary = "List All Friendships",
        operationId = "listAllFriendships",
        description = """list all friendships in the application (more for debug than for actual use)""",
        responses = [ApiResponse(responseCode = "200", description = "OK", content = [Content(array = ArraySchema(schema = Schema(implementation = UserDTO::class)))])]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/friendships"],
        produces = ["application/json"]
    )
    @CanReadAllUsers
    fun listAllFriendships(@RequestParam(required = true, defaultValue = "0") page: Int,
                           @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<FriendshipDTO>>

    @Operation(
        summary = "Create User",
        operationId = "postUser",
        description = """Create/register a user in the application""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK") ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/user"],
        consumes = ["application/json"]
    )
    fun createUser(@Parameter(description = "data of the user to be created") @Valid @RequestBody(required = true) user: UserCreateDTO): ResponseEntity<UserDTO>

    @Operation(
        summary = "Send Friendship Invite",
        operationId = "postUserusernameFriendsfriendUsername",
        description = """Send a friendship invite from username to friendUsername""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "Not Found"),
            ApiResponse(responseCode = "409", description = "Conflict") ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/user/{username}/friends/{friendUsername}"]
    )
    @CanReadOneUser
    fun sendFriendshipInvite(
        @Parameter(description = "username of the user making the request", required = true)
        @PathVariable("username") username: String,

        @Parameter(description = "username of the (to be) friend of the user", required = true)
        @PathVariable("friendUsername") friendUsername: String): ResponseEntity<Unit>

    @Operation(
        summary = "Update User",
        operationId = "putUserusername",
        description = """Update the data of the user given its username""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.PUT],
        value = ["/user/{username}"],
        consumes = ["application/json"]
    )
    @CanReadOneUser
    fun updateUser(
        @Parameter(description = "username of the user", required = true)
        @PathVariable("username") username: String,

        @Parameter(description = "updated data of the user") @Valid
        @RequestBody(required = true) user: UserUpdateDTO): ResponseEntity<UserDTO>

    @Operation(
        summary = "Respond to Friendship Invite",
        operationId = "putUserusernameFriendsfriendUsername",
        description = """username responds to friendship invite from friendUsername""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.PUT],
        value = ["/user/{username}/friends/{friendUsername}"],
        consumes = ["application/json"]
    )
    @CanReadOneUser
    fun respondToFriendshipInvite(
        @Parameter(description = "username of the user making the request", required = true)
        @PathVariable("username") username: String,

        @Parameter(description = "username of the (to be) friend of the user", required = true)
        @PathVariable("friendUsername") friendUsername: String,

        @Parameter(description = "boolean response")
        @Valid @RequestBody(required = true) inviteResponse: InviteResponse): ResponseEntity<Unit>

}
