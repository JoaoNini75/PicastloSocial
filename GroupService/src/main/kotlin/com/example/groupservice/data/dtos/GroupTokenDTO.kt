package com.example.groupservice.data.dtos

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

data class GroupTokenDTO(
    @Schema(
    example = "johnmaster120",
    required = true,
    description = "username of the user")
    private val username: String,


    @Schema(
        description = "List of granted authorities for the user",
        example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]",
        required = true
    )
    private val authorities: List<GrantedAuthority>,

    @Schema(
        example = "Default",
        required = true,
        description = "role of the user")
    val role: String

) : Authentication {

    override fun getAuthorities() = authorities

    override fun setAuthenticated(isAuthenticated: Boolean) {}

    override fun getName() = username

    override fun getCredentials() = null

    override fun getPrincipal() = this

    override fun isAuthenticated() = true

    override fun getDetails() = username
}
