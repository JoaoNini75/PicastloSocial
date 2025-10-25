package com.example.pipelineservice.security.capabilities

import com.example.pipelineservice.data.PipelineRepository
import com.example.pipelineservice.data.PipelineTokenDTO
import com.example.pipelineservice.security.filters.JWTAuthenticationFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class SecurityPolicies(val pipelineRepo: PipelineRepository) {

    val logger: Logger = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)

    fun canCRUDPipeline(user: Principal, pipelineId: Long): Boolean {
        val role = (user as PipelineTokenDTO).role
        val isOwner = pipelineRepo.findById(pipelineId).get().uploadedBy == user.name

        logger.info(" ROLE $role ---- User: ${user.name} ---- isOwner: $isOwner")
        return role == "Admin" || role == "Manager" || isOwner
    }

    fun canReadAllPipelines(user: Principal): Boolean {
        val role = (user as PipelineTokenDTO).role
        return role == "Admin" || role == "Manager"
    }

    fun canReadUserPipelines(user: Principal, username:String): Boolean {
        val role = (user as PipelineTokenDTO).role
        logger.info("ROLE $role --- User: ${user.name} ---- username: $username")
        return role == "Admin" || role == "Manager" || user.name == username
    }


    fun canReadPipeline(user: Principal, pipelineId: Long): Boolean {
        val role = (user as PipelineTokenDTO).role
        val isMember = pipelineRepo.findById(pipelineId).get().uploadedBy == user.name
        logger.info("ROLE $role --- User: ${user.name} ---- isMember: $isMember")
        return role == "Admin" || role == "Manager"  || isMember
    }
}

@PreAuthorize("@securityPolicies.canCRUDPipeline(principal, #id)")
annotation class CanCRUDPipeline

@PreAuthorize("@securityPolicies.canReadPipeline(principal, #id)")
annotation class CanReadPipeline

@PreAuthorize("@securityPolicies.canReadAllPipelines(principal)")
annotation class CanReadAllPipelines

@PreAuthorize("@securityPolicies.canReadUserPipelines(principal, #username)")
annotation class CanReadPipelines

