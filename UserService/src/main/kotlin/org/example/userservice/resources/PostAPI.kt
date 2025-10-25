package org.example.userservice.resources

import org.example.postservice.data.CreatePostDTO
import org.example.postservice.data.PostDTO
import feign.RequestInterceptor
import feign.codec.ErrorDecoder
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.example.userservice.repositories.FriendshipRepository
import org.example.userservice.security.filters.Capability
import org.example.userservice.security.filters.JWTUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import kotlin.collections.HashMap

@FeignClient(name = "PostService",
    path = "/post",
    configuration = [PostAPIConfig::class],
    fallbackFactory = PostFallbackFactory::class)
interface PostAPI {

    @GetMapping("/hello")
    fun hello(): String

    @GetMapping("", produces = ["application/json"])
    fun getPosts(@RequestParam(required = true, defaultValue = "0") page: Int,
                 @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<PostDTO>>

    @PostMapping("", consumes = ["application/json"], produces = ["application/json"])
    fun createPost(@RequestBody post: CreatePostDTO): ResponseEntity<PostDTO>

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<PostDTO>

    @PutMapping("/{id}")
    fun updatePostById(@PathVariable id: Long, @RequestBody post: PostDTO): ResponseEntity<PostDTO>

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<PostDTO>

    @GetMapping("/user/{username}")
    fun getUserPosts(@PathVariable username: String,
                     @RequestParam(required = true, defaultValue = "0") page: Int,
                     @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<PostDTO>>

    @GetMapping("/friends")
    fun getFriendsPosts(@RequestParam(required = true, defaultValue = "0") page: Int,
                        @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<PostDTO>>

    @GetMapping("/group/{groupID}")
    fun getGroupPosts(@PathVariable groupID: Long,
                      @RequestParam(required = true, defaultValue = "0") page: Int,
                      @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<PostDTO>>

    @GetMapping("/public")
    fun getPublicPosts(@RequestParam(required = true, defaultValue = "0") page: Int,
                       @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<PostDTO>>
}

//TODO: Change the token config?
class PostAPIConfig(
    @Value("\${jwt.postSubject}") val jwtSubject: String,
    @Value("\${jwt.expiration}") val jwtExpiration: Long,
    @Value("\${jwt.postSecret}") val jwtSecret: String,
    val friends: FriendshipRepository,
    val utils: JWTUtils
) {

    @Bean
    fun feignErrorDecoder(): ErrorDecoder = CustomFeignErrorDecoder()

    val logger = LoggerFactory.getLogger(PostAPIConfig::class.java)

    @Bean
    fun postAPIInterceptor(): RequestInterceptor {
        return RequestInterceptor { template ->
            val userToken = getUserTokenFromRequest()
            val username = extractUsername(userToken)
            val role = extractRole(userToken)
            logger.info("User $username is accessing PostService.")
            val postServiceToken = generatePostServiceToken(username, role)
            logger.info("Generated PostService token: $postServiceToken")
            template.header("Authorization", "Bearer $postServiceToken")
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
            throw IllegalArgumentException("User token is required for accessing PostService.")
        }

        val claims = Jwts.parser().setSigningKey(utils.key).parseClaimsJws(userToken).body

        return claims["username"] as String
    }

    private fun extractRole(userToken: String?): String {
        if (userToken == null) {
            logger.error("User token is missing from the request to extract role.")
            throw IllegalArgumentException("User token is required for accessing PostService.")
        }

        val claims = Jwts.parser().setSigningKey(utils.key).parseClaimsJws(userToken).body

        return claims["role"] as String
    }

    private fun generatePostServiceToken(username: String, role: String): String {
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

class PostFallbackFactory {

}
