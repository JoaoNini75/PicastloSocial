package com.example.groupservice.security.policies


import com.example.groupservice.repositories.GroupRepository
import com.example.groupservice.data.dtos.GroupTokenDTO
import com.example.groupservice.repositories.GroupMembershipRepository
import com.example.groupservice.security.filters.JWTAuthenticationFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class SecurityPolicies(val groupRepo: GroupRepository, val groupMembershipRepo: GroupMembershipRepository) {

    val logger: Logger = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)

    fun canCRUDGroup(user: Principal, groupId: Long): Boolean {
        val role = (user as GroupTokenDTO).role
        val isOwner = groupRepo.findById(groupId).get().ownerId == user.name

        logger.info(" ROLE $role ---- User: ${user.name} ---- isOwner: $isOwner")
        return role == "Admin" || role == "Manager" || isOwner
    }

    fun canReadGroup(user: Principal, groupId: Long): Boolean {
        val role = (user as GroupTokenDTO).role
        val isMember = groupMembershipRepo.findMember(user.name, groupId) != null
        logger.info("ROLE $role --- User: ${user.name} ---- isMember: $isMember")
        return role == "Admin" || role == "Manager"  || isMember
    }

    fun canReadMembers(user: Principal, groupId: Long): Boolean {
        val role = (user as GroupTokenDTO).role
        val isMember = groupMembershipRepo.findMember(user.name, groupId) != null
        logger.info("ROLE $role  ---- User: ${user.name} ---- isMember: $isMember")
        return role == "Admin" || role == "Manager" || isMember
    }
}

@PreAuthorize("@securityPolicies.canCRUDGroup(principal, #id)")
annotation class CanCRUDGroup

@PreAuthorize("@securityPolicies.canReadGroup(principal, #id)")
annotation class CanReadGroup

@PreAuthorize("@securityPolicies.canReadMembers(principal, #id)")
annotation class CanReadMembers