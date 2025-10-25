package org.example.userservice.security

import org.example.userservice.application.UserApplication
import org.example.userservice.security.filters.UserPasswordAuthenticationFilterToJWT
import org.example.userservice.exceptions.UserNotFoundException
import org.example.userservice.security.filters.JWTAuthenticationFilter
import org.example.userservice.security.filters.JWTUtils
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val userApp: UserApplication
) {
    val logger: org.slf4j.Logger = LoggerFactory.getLogger(SecurityConfig::class.java)


    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(
        http: HttpSecurity,
        authenticationManager: AuthenticationManager,
        utils: JWTUtils
    ): SecurityFilterChain {
        http.invoke {
            csrf { disable() }
            authorizeHttpRequests {
                authorize("swagger-ui/**", permitAll)
                authorize("v3/api-docs/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            addFilterBefore<BasicAuthenticationFilter>(UserPasswordAuthenticationFilterToJWT("/login", authenticationManager, utils))
            addFilterBefore<BasicAuthenticationFilter>(JWTAuthenticationFilter(utils))
            httpBasic { }
        }
        return http.build()
    }


    @Bean
    fun authenticationManager(authConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            userApp.getUserDetails(username)
                ?: throw UserNotFoundException(username)
        }
    }

}