package org.example.userservice.resources

import com.example.groupservice.data.dtos.GroupDTO
import com.example.groupservice.data.dtos.GroupMembershipDTO
import feign.RequestInterceptor
import feign.codec.ErrorDecoder
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.example.userservice.repositories.FriendshipRepository
import org.example.userservice.security.filters.Capability
import org.example.userservice.security.filters.JWTUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import kotlin.collections.HashMap

@FeignClient(
    name = "GroupService",
    path = "/group",
    configuration = [GroupAPIConfig::class],
    fallbackFactory = GroupFallbackFactory::class
)
interface GroupAPI {

    @GetMapping("/hello")
    fun hello(): String

    @DeleteMapping("/{id}")
    fun deleteGroupId(@PathVariable("id") id: Long): ResponseEntity<GroupDTO>

    @DeleteMapping("/{id}/members")
    fun deleteGroupMember(
        @PathVariable("id") id: Long,
        @RequestParam("username") username: String
    ): ResponseEntity<GroupMembershipDTO>

    @GetMapping("/{id}")
    fun getGroupId(@PathVariable("id") id: Long): ResponseEntity<GroupDTO>

    @GetMapping("/{id}/members")
    fun listGroupMembers(
        @PathVariable("id") id: Long,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): ResponseEntity<Page<GroupMembershipDTO>>

    @PostMapping("")
    fun createGroup(@RequestBody(required = true) group: GroupDTO): ResponseEntity<GroupDTO>

    @PostMapping("/{id}/members")
    fun addMemberToGroup(
        @PathVariable("id") id: Long,
        @RequestParam("username") username: String
    ): ResponseEntity<GroupMembershipDTO>

    @PutMapping("/{id}")
    fun updateGroup(
        @PathVariable("id") id: Long,
        @RequestBody(required = true) group: GroupDTO
    ): ResponseEntity<GroupDTO>

    @GetMapping("/{username}/groups")
    fun listUserGroups(
        @PathVariable("username") username: String,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponseEntity<Page<GroupMembershipDTO>>
}


//TODO: Change the token config?
class GroupAPIConfig(
    @Value("\${jwt.groupSubject}") val jwtSubject: String,
    @Value("\${jwt.expiration}") val jwtExpiration: Long,
    @Value("\${jwt.groupSecret}") val jwtSecret: String,
    val friends: FriendshipRepository,
    val utils: JWTUtils
) {

    @Bean
    fun feignErrorDecoder(): ErrorDecoder = CustomFeignErrorDecoder()

    val logger = LoggerFactory.getLogger(GroupAPIConfig::class.java)

    @Bean
    fun groupAPIInterceptor(): RequestInterceptor {
        return RequestInterceptor { template ->
            val userToken = getUserTokenFromRequest()
            val username = extractUsername(userToken)
            val role = extractRole(userToken)
            logger.info("User $username is accessing GroupService.")
            val groupServiceToken = generateGroupServiceToken(username, role)
            template.header("Authorization", "Bearer $groupServiceToken")
        }
    }

    private fun getUserTokenFromRequest(): String? {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
        val request: HttpServletRequest? = requestAttributes?.request
        val cookies = request?.cookies

        return cookies?.firstOrNull { it.name == "jwt" }?.value
    }


    private fun extractUsername(userToken: String?): String {
        if (userToken == null) {
            logger.error("User token is missing from the request extract username.")
            throw IllegalArgumentException("User token is required for accessing GroupService.")
        }

        val claims = Jwts.parser().setSigningKey(utils.key).parseClaimsJws(userToken).body

        return claims["username"] as String
    }

    private fun extractRole(userToken: String?): String {
        if (userToken == null) {
            logger.error("User token is missing from the request to extract role.")
            throw IllegalArgumentException("User token is required for accessing GroupService.")
        }

        val claims = Jwts.parser().setSigningKey(utils.key).parseClaimsJws(userToken).body

        return claims["role"] as String
    }

    private fun generateGroupServiceToken(username: String, role: String): String {
        val claims = HashMap<String, Any?>()

        claims["username"] = username
        claims["friends"] = getFriends(username)
        claims["role"] = role

        val key = Base64.getEncoder().encodeToString(jwtSecret.toByteArray())
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(jwtSubject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, key)
            .compact()
    }

    private fun getFriends(username:String) : List<Capability> {
        val capabilities = mutableListOf<Capability>()


        friends.listUserFriends(username, Pageable.unpaged()).content.forEach {
            capabilities.add(Capability(it.username, "friend"))
        }

        logger.info("Adding capabilities: ${capabilities.toString()}")
        return capabilities
    }

}



@Component
class GroupFallbackFactory : FallbackFactory<GroupAPI> {
    private val logger = LoggerFactory.getLogger(GroupFallbackFactory::class.java)

    override fun create(cause: Throwable): GroupAPI {
        logger.error("Fallback initiated due to: ${cause.message}")

        return object : GroupAPI {
            override fun hello(): String = "Fallback: Hello from GroupService"
            override fun deleteGroupId(id: Long): ResponseEntity<GroupDTO> = ResponseEntity.internalServerError().build()
            override fun deleteGroupMember(id: Long, username: String): ResponseEntity<GroupMembershipDTO> = ResponseEntity.internalServerError().build()
            override fun getGroupId(id: Long): ResponseEntity<GroupDTO> = ResponseEntity.internalServerError().build()
            override fun listGroupMembers(id: Long, page: Int, size: Int): ResponseEntity<Page<GroupMembershipDTO>> = ResponseEntity.internalServerError().build()
            override fun createGroup(group: GroupDTO): ResponseEntity<GroupDTO> = ResponseEntity.internalServerError().build()
            override fun addMemberToGroup(id: Long, username: String): ResponseEntity<GroupMembershipDTO> = ResponseEntity.internalServerError().build()
            override fun updateGroup(id: Long, group: GroupDTO): ResponseEntity<GroupDTO> = ResponseEntity.internalServerError().build()
            override fun listUserGroups(
                username: String,
                page: Int,
                size: Int
            ): ResponseEntity<Page<GroupMembershipDTO>> {
                return ResponseEntity.internalServerError().build()
            }
        }
    }
}
