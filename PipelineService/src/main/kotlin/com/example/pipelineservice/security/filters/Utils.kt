package com.example.pipelineservice.security.filters

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import java.util.*

data class Capability(val username:String, val operation: String)

@Configuration
class JWTUtils(@Value("\${jwt.secret}") val jwtSecret: String,
               @Value("\${jwt.expiration}") val expiration: Long,
               @Value("\${jwt.subject}") val subject: String) {

    val key: String = Base64.getEncoder().encodeToString(jwtSecret.toByteArray())

    fun addResponseToken(authentication: Authentication, response: HttpServletResponse) {

        val claims = HashMap<String, Any?>()
        val username = authentication.name


        val role = determineRole(authentication)

        claims["username"] = username
        claims["role"] = role

        //TODO: delete if not needed
//        if (authentication.name == "admin" || authentication.name == "JaneKotlin")
//            claims["capabilities"] = listOf(Capability(role, "ALL"))
//        else
//            claims["capabilities"] = emptyList<Capability>()

        val token = Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()

        response.addHeader("Authorization", "Bearer $token")
    }


    fun determineRole(authentication: Authentication): String {
        return authentication.authorities
            .map(GrantedAuthority::getAuthority)
            .firstOrNull()
            ?: throw IllegalArgumentException("User has no roles assigned!")


    }
}