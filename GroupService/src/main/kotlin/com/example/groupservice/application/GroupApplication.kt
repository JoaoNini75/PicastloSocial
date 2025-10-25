package com.example.groupservice.application

import com.example.groupservice.data.daos.GroupDAO
import com.example.groupservice.data.daos.GroupMembershipDAO
import com.example.groupservice.data.daos.GroupMembershipId
import com.example.groupservice.data.dtos.GroupDTO
import com.example.groupservice.data.dtos.GroupMembershipDTO
import com.example.groupservice.presentation.GroupAlreadyExistsException
import com.example.groupservice.repositories.GroupRepository
import com.example.groupservice.presentation.GroupNotFoundException
import com.example.groupservice.presentation.GroupMembershipNotFoundException
import com.example.groupservice.presentation.MemberAlreadyInGroupException
import com.example.groupservice.repositories.GroupMembershipRepository
import org.springframework.stereotype.Component
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.time.OffsetDateTime

@Component
class GroupApplication (val groupRepo: GroupRepository, val groupMembershipRepo: GroupMembershipRepository) {


    fun createGroup(group: GroupDTO): GroupDTO {
        groupRepo.findById(group.id).ifPresent { throw GroupAlreadyExistsException() }

        return groupRepo.save(GroupDAO(groupRepo.count()+1, group.name, group.ownerId, group.createdAt)).let { GroupDTO(it.id, it.name, it.ownerId, it.createdAt) }
    }


    fun groupDAOToDTO(dao: GroupDAO) =
        GroupDTO(dao.id, dao.name, dao.ownerId, dao.createdAt)

    fun getGroup(id: Long) : GroupDTO {
        val tempGroup = groupRepo.findById(id).orElseThrow { GroupNotFoundException() }

        return tempGroup.let { GroupDTO(it.id, it.name, it.ownerId, it.createdAt) }
    }

    fun deleteGroupWithId(id: Long): GroupDTO {
        val tempGroup = groupRepo.findById(id).orElseThrow { GroupNotFoundException() }

        return groupRepo.delete(tempGroup).let { GroupDTO(tempGroup.id, tempGroup.name, tempGroup.ownerId, tempGroup.createdAt) }
    }

    fun updateGroup(id: Long, group: GroupDTO): GroupDTO {
        groupRepo.findById(id).orElseThrow { GroupNotFoundException() }

        return groupRepo.save(GroupDAO(group.id, group.name, group.ownerId, group.createdAt)).let { GroupDTO(it.id, it.name, it.ownerId, it.createdAt) }
    }

    fun getGroupMembers(id: Long, page: Int, size: Int): Page<GroupMembershipDTO> {
        groupRepo.findById(id).orElseThrow { GroupNotFoundException() }

        return groupMembershipRepo.listGroupMembers(id, PageRequest.of(page, size)).map {
            it?.let { it1 -> GroupMembershipDTO(it1.id.username, it.id.groupId, it.name, it.createdAt) }
        }
    }

    fun addMemberToGroup(id: Long, memberUsername: String): GroupMembershipDTO {
        val tempGroup = groupRepo.findById(id).orElseThrow { GroupNotFoundException() }
        //CHECK IF USER ALREADY EXISTS IN GROUP
        groupMembershipRepo.findMember(memberUsername, id).let { if (it != null) throw MemberAlreadyInGroupException() }

        return groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId(memberUsername, id), tempGroup.name, OffsetDateTime.now())).let { GroupMembershipDTO(it.id.username, it.id.groupId, it.name, it.createdAt) }
    }

    fun deleteGroupMember(id: Long, memberUsername: String): GroupMembershipDTO {
        // Check if the group exists
        groupRepo.findById(id).orElseThrow { GroupNotFoundException() }

        // Check if the member exists in the group
        val member = groupMembershipRepo.findMember(memberUsername, id)
            ?: throw GroupMembershipNotFoundException()

        // Delete the member
        groupMembershipRepo.delete(member)

        // Return the deleted member's details
        return GroupMembershipDTO(
            username = member.id.username,
            groupId = member.id.groupId,
            name = member.name, // Assuming `name` exists in the entity
            createdAt = member.createdAt
        )

    }


    fun listUserGroups(username: String, page: Int, size: Int): Page<GroupMembershipDTO> {
        return groupMembershipRepo.getUserGroupIds(username, PageRequest.of(page, size))
    }
}
