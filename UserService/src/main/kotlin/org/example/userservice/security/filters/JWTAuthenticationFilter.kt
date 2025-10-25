package org.example.userservice.security.filters

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.userservice.data.daos.UserDAO
import org.example.userservice.data.dtos.UserTokenDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean


class JWTAuthenticationFilter(val utils: JWTUtils) : GenericFilterBean() {

    val logger: Logger = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val cookies = (request as HttpServletRequest).cookies

        val token = cookies?.firstOrNull { it.name == "jwt" }?.value

        if (token != null) {
            try {
                val claims = Jwts.parser().setSigningKey(utils.key).parseClaimsJws(token).body

                val sub = claims["sub"] as String
                if (sub != utils.subject) {
                    (response as HttpServletResponse).sendError(HttpServletResponse.SC_PRECONDITION_FAILED)
                    return
                }

                // Extract roles and determine capabilities
                val userRole = claims["role"] as String
                val role = UserDAO.Role.valueOf(userRole.split("_")[1])

                val authorities = listOf(SimpleGrantedAuthority(userRole.toString()))

                val authentication = UserTokenDTO(
                    claims["username"] as String,
                    authorities,
                    role
                )

                SecurityContextHolder.getContext().authentication = authentication

                // Optionally refresh token (add new token as a cookie)
                utils.addResponseToken(authentication, response as HttpServletResponse)

                chain.doFilter(request, response)
            } catch (e: ExpiredJwtException) {
                sendErrorResponse(response as HttpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, "Token expired")
                return
            } catch (e: MalformedJwtException) {
                sendErrorResponse(response as HttpServletResponse, HttpServletResponse.SC_BAD_REQUEST, "Malformed token")
                return
            } catch (e: SignatureException) {
                sendErrorResponse(response as HttpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token signature")
                return
            } catch (e: Exception) {
                sendErrorResponse(response as HttpServletResponse, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error")
                return
            }
        } else {
            chain.doFilter(request, response)
        }
    }

    private fun sendErrorResponse(response: HttpServletResponse, status: Int, message: String) {
        response.status = status
        response.contentType = "application/json"
        response.writer.write("""{"error": "$message"}""")
        response.writer.flush()
    }
}
