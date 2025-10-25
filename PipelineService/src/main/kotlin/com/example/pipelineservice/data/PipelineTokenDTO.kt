package com.example.pipelineservice.data

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

data class PipelineTokenDTO (

    @Schema(
        example = "johnmaster120",
        required = true,
        description = "username of the user")
    val username: String,

    @Schema(
        description = "List of granted authorities for the user",
        example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]",
        required = true
    )
    val grantedAuthorities: List<GrantedAuthority>,

    @Schema(
        example = "Default",
        required = true,
        description = "role of the user")
    val role: String
) : Authentication {

    override fun getAuthorities() = grantedAuthorities

    override fun setAuthenticated(isAuthenticated: Boolean) {}

    override fun getName() = username

    override fun getCredentials() = null

    override fun getPrincipal() = this

    override fun isAuthenticated() = true

    override fun getDetails() = username
}