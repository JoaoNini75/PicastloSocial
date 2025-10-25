package com.example.imageservice.security.filters

import com.example.imageservice.data.ImageTokenDTO
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean


class JWTAuthenticationFilter(val utils: JWTUtils) : GenericFilterBean() {

    val logger: Logger = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val authHeader = (request as HttpServletRequest).getHeader("Authorization")

        logger.info("Auth header: $authHeader")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7) // Skip 7 characters for "Bearer "
            try {
                val claims = Jwts.parser().setSigningKey(utils.key).parseClaimsJws(token).body

                val sub = claims["sub"] as String
                if (sub != utils.subject) {
                    (response as HttpServletResponse).sendError(HttpServletResponse.SC_PRECONDITION_FAILED)
                    return
                }

                // Extract roles and determine capabilities
                val userRole = claims["role"] as String
                val role = userRole.split("_")[1]

                val authorities = listOf(SimpleGrantedAuthority(userRole.toString()))

                val authentication = ImageTokenDTO(
                    claims["username"] as String,
                    authorities,
                    role
                )

                SecurityContextHolder.getContext().authentication = authentication

                logger.info("User authenticated: $authentication")
                chain.doFilter(request, response)
                logger.info("DONE")
            } catch (e: ExpiredJwtException) {
                logger.error("JWT error: ${e.message}")
                sendErrorResponse(response as HttpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, "Token expired")
                return
            } catch (e: MalformedJwtException) {
                logger.error("JWT error: ${e.message}")
                sendErrorResponse(response as HttpServletResponse, HttpServletResponse.SC_BAD_REQUEST, "Malformed token")
                return
            } catch (e: SignatureException) {
                logger.error("JWT error: ${e.message}")
                sendErrorResponse(response as HttpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token signature")
                return
            } catch (e: Exception) {
                logger.error("JWT error: ${e.message}")
                sendErrorResponse(response as HttpServletResponse, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error")
                return
            }
        } else {
            chain.doFilter(request, response)
        }
    }

    private fun sendErrorResponse(response: HttpServletResponse, status: Int, message: String) {
        if (response.isCommitted) {
            logger.warn("Response already committed. Skipping error response.")
            return
        }

        response.resetBuffer() // Reset the response buffer if it hasn't been committed
        response.status = status
        response.contentType = "application/json"
        response.outputStream.use { outputStream ->
            outputStream.write("""{"error": "$message"}""".toByteArray(Charsets.UTF_8))
            outputStream.flush()
        }
    }
}