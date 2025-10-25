package com.example.imageservice.security.capabilities

import com.example.imageservice.data.ImageRepository
import com.example.imageservice.data.ImageTokenDTO
import com.example.imageservice.security.filters.JWTAuthenticationFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class SecurityPolicies(val imageRepo: ImageRepository) {

    val logger: Logger = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)

    fun canCRUDImage(user: Principal, imageId: Long): Boolean {
        val role = (user as ImageTokenDTO).role
        val isOwner = imageRepo.findById(imageId).get().uploadedBy == user.name
        logger.info(" ROLE $role ---- User: ${user.name} ---- isOwner: $isOwner")
        return role == "Admin" || role == "Manager" || isOwner
    }

    fun canReadAllImages(user: Principal): Boolean {
        val role = (user as ImageTokenDTO).role
        logger.info(" ROLE $role ---- User: ${user.name}")
        return role == "Admin" || role == "Manager"
    }

    fun canReadUserImages(user: Principal, username:String): Boolean {
        val role = (user as ImageTokenDTO).role
        logger.info(" ROLE $role ---- User: ${user.name} ---- userId: $username")
        return role == "Admin" || role == "Manager" || user.name == username
    }

    fun canReadImage(user: Principal, imageId: Long): Boolean {
        val role = (user as ImageTokenDTO).role
        val isMember = imageRepo.findById(imageId).get().uploadedBy == user.name
        logger.info(" ROLE $role ---- User: ${user.name} ---- isMember: $isMember")
        return role == "Admin" || role == "Manager"  || isMember
    }

}

@PreAuthorize("@securityPolicies.canCRUDImage(principal, #id)")
annotation class CanCRUDImage

@PreAuthorize("@securityPolicies.canReadImage(principal, #id)")
annotation class CanReadImage

@PreAuthorize("@securityPolicies.canReadAllImages(principal)")
annotation class CanReadAllImages

@PreAuthorize("@securityPolicies.canReadUserImages(principal, #username)")
annotation class CanReadImages

