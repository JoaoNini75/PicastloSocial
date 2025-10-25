package org.example.postservice.security.policies

import org.example.postservice.data.PostTokenDTO
import org.example.postservice.repositories.PostRepository
import org.example.postservice.security.filters.JWTAuthenticationFilter
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class SecurityPolicies(val postRepo: PostRepository) {

    val logger = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)

    fun canCRUDPost(user: Principal, postId: Long): Boolean {
        val role = (user as PostTokenDTO).role
        val isOwner = postRepo.findById(postId).get().username == user.name
        logger.info(" ROLE $role ---- User: ${user.name} ---- isOwner: $isOwner")
        return role == "Admin" || role == "Manager" || isOwner
    }

    fun canReadAllPosts(user: Principal): Boolean {
        val role = (user as PostTokenDTO).role
        logger.info(" ROLE $role ---- User: ${user.name}")
        return role == "Admin" || role == "Manager"
    }

    fun canReadUserPosts(user: Principal, username:String): Boolean {
        val role = (user as PostTokenDTO).role
        logger.info(" ROLE $role ---- User: ${user.name} ---- userId: $username")
        return role == "Admin" || role == "Manager" || user.name == username
    }

    fun canReadPost(user: Principal, postId: Long): Boolean {
        val role = (user as PostTokenDTO).role
        val isMember = postRepo.findById(postId).get().username == user.name
        logger.info(" ROLE $role ---- User: ${user.name} ---- isMember: $isMember")
        return role == "Admin" || role == "Manager"  || isMember
    }

}

@PreAuthorize("@securityPolicies.canCRUDPost(principal, #id)")
annotation class CanCRUDPost

@PreAuthorize("@securityPolicies.canReadPost(principal, #id)")
annotation class CanReadPost

@PreAuthorize("@securityPolicies.canReadAllPosts(principal)")
annotation class CanReadAllPosts

@PreAuthorize("@securityPolicies.canReadUserPosts(principal, #username)")
annotation class CanReadUserPosts