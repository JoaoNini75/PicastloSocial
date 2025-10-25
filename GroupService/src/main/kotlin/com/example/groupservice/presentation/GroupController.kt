package com.example.groupservice.presentation

import com.example.groupservice.application.GroupApplication
import com.example.groupservice.data.dtos.GroupDTO
import com.example.groupservice.data.dtos.GroupMembershipDTO
import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.data.domain.Page
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class GroupController(var eurekaClient: EurekaClient, val app: GroupApplication) : GroupAPI {
    @Value("\${spring.application.name}")
    var appName: String? = null

    override fun hello(): String {
        return "Hello World! from ${eurekaClient.getApplication(appName).getName()}"
    }

    override fun listUserGroups(username: String, page: Int, size: Int): ResponseEntity<Page<GroupMembershipDTO>> {
        return ResponseEntity.ok(app.listUserGroups(username, page, size))
    }

    override fun createGroup(group: GroupDTO): ResponseEntity<GroupDTO> {
        return ResponseEntity.ok(app.createGroup(group))
    }

    override fun getGroupId(id: Long): ResponseEntity<GroupDTO> {
        return ResponseEntity.ok(app.getGroup(id))
    }

    override fun deleteGroupId(id: Long): ResponseEntity<GroupDTO> {
        return ResponseEntity.ok(app.deleteGroupWithId(id))
    }

    override fun updateGroup(id: Long, group: GroupDTO): ResponseEntity<GroupDTO> {
        return ResponseEntity.ok(app.updateGroup(id, group))
    }

    override fun getGroupMembers(id: Long, page: Int, size: Int): ResponseEntity<Page<GroupMembershipDTO>> {
        return ResponseEntity.ok(app.getGroupMembers(id, page, size))
    }

    override fun addMemberToGroup(id: Long, username: String): ResponseEntity<GroupMembershipDTO> {
        return ResponseEntity.ok(app.addMemberToGroup(id, username))
    }

    override fun deleteGroupMember(id: Long, username: String): ResponseEntity<GroupMembershipDTO> {
        return ResponseEntity.ok(app.deleteGroupMember(id, username))
    }


}