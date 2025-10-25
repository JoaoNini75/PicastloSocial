package com.example.groupservice.security

import com.example.groupservice.security.filters.JWTAuthenticationFilter
import com.example.groupservice.security.filters.JWTUtils
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
) {
    val logger: org.slf4j.Logger = LoggerFactory.getLogger(SecurityConfig::class.java)


    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(
        http: HttpSecurity,
        utils: JWTUtils
    ): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeHttpRequests {
                authorize("swagger-ui/**", permitAll)
                authorize(anyRequest, authenticated) // All endpoints require authentication
            }
            addFilterBefore<BasicAuthenticationFilter>(JWTAuthenticationFilter(utils)) // Custom filter for imageToken
            httpBasic { disable() } // Disable basic auth
        }
        return http.build()
    }


    @Bean
    fun authenticationManager(authConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()



}
