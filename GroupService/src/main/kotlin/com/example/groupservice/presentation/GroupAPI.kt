package com.example.groupservice.presentation

import com.example.groupservice.data.dtos.GroupDTO
import com.example.groupservice.data.dtos.GroupMembershipDTO
import com.example.groupservice.security.policies.CanCRUDGroup
import com.example.groupservice.security.policies.CanReadGroup
import com.example.groupservice.security.policies.CanReadMembers
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.Page

@RequestMapping("/group")
interface GroupAPI {
    @GetMapping("/hello")
    fun hello(): String

    @Operation(
        summary = "List user groups",
        operationId = "listUserGroups",
        description = """list groups that a user is a member of""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(array = ArraySchema(schema = Schema(implementation = GroupDTO::class)))]),
            ApiResponse(responseCode = "400", description = "Bad Request"),
            ApiResponse(responseCode = "404", description = "Not Found")
        ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/{username}/groups"],
        produces = ["application/json"]
    )
    fun listUserGroups(
        @Parameter(description = "username", required = true)
        @PathVariable("username") username: String,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponseEntity<Page<GroupMembershipDTO>>

    @Operation(
        summary = "Create Group",
        operationId = "postGroup",
        description = """Create a group in the application""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "409", description = "Conflict") ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = [""],
        consumes = ["application/json"]
    )
    fun createGroup(@Parameter(description = "data of the group to be created") @Valid @RequestBody(required = true) group: GroupDTO): ResponseEntity<GroupDTO>

    @Operation(
        summary = "Get Group",
        operationId = "getGroupId",
        description = """Get the information of a group given its id""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = GroupDTO::class))]),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/{id}"],
        produces = ["application/json"]
    )
    fun getGroupId(@Parameter(description = "id of the group", required = true) @PathVariable("id") id: Long): ResponseEntity<GroupDTO>


    @Operation(
        summary = "Delete Group",
        operationId = "deleteGroupId",
        description = """Delete a group given its id""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "401", description = "Unauthorized"),
            ApiResponse(responseCode = "403", description = "Forbidden"),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/{id}"]
    )
    @CanCRUDGroup
    fun deleteGroupId(@Parameter(description = "id of the group", required = true) @PathVariable("id") id: Long): ResponseEntity<GroupDTO>


    @Operation(
        summary = "Update Group",
        operationId = "putGroupId",
        description = """Update the information about a group given its id""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "401", description = "Unauthorized"),
            ApiResponse(responseCode = "403", description = "Forbidden"),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.PUT],
        value = ["/{id}"],
        consumes = ["application/json"]
    )
    @CanCRUDGroup
    fun updateGroup(
        @Parameter(description = "id of the group", required = true)
        @PathVariable("id") id: Long,
        @Parameter(description = "updated information of the group")
        @Valid
        @RequestBody(required = true) group: GroupDTO
    ): ResponseEntity<GroupDTO>


    @Operation(
        summary = "List Group Members",
        operationId = "listGroupMembers",
        description = """List the members of a group given its id""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(array = ArraySchema(schema = Schema(implementation = GroupMembershipDTO::class)))]),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/{id}/members"],
        produces = ["application/json"]
    )
    @CanReadMembers
    fun getGroupMembers(
        @Parameter(description = "id of the group", required = true)
        @PathVariable("id") id: Long,
        @Parameter(description = "page number", required = true)
        @RequestParam("page", required = true, defaultValue = "0") @Min(0) page: Int,
        @Parameter(description = "number of elements per page", required = true)
        @RequestParam("size", required = false, defaultValue = "5") @Min(1) size: Int): ResponseEntity<Page<GroupMembershipDTO>>

    @Operation(
        summary = "Add Member to Group",
        operationId = "postGroupIdMembersMemberId",
        description = """Add a member to a group given the group id and the username of the user to be added""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/{id}/members"],
        produces = ["application/json"]
    )
    @CanCRUDGroup
    fun addMemberToGroup(
        @Parameter(description = "id of the group", required = true)
        @PathVariable("id") id: Long,
        @Parameter(description = "username of the user to be added to the group", required = true)
        @RequestParam("username", required = true) username: String): ResponseEntity<GroupMembershipDTO>

    @Operation(
        summary = "Delete Group Member",
        operationId = "deleteGroupIdMembersMemberId",
        description = """Delete a member from a group given the group id and the username of the user to be removed""",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "Not Found") ]
    )
    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/{id}/members"],
        produces = ["application/json"]
    )
    @CanCRUDGroup
    fun deleteGroupMember(
        @Parameter(description = "id of the group", required = true)
        @PathVariable("id") id: Long,
        @Parameter(description = "username of the user to be removed from the group", required = true)
        @RequestParam("username", required = true) username: String): ResponseEntity<GroupMembershipDTO>

}
