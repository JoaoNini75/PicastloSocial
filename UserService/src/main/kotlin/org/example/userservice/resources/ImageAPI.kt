package org.example.userservice.resources

import com.example.imageservice.data.CreateImageDTO
import com.example.imageservice.data.ImageDTO
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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import kotlin.collections.HashMap


@FeignClient(name = "ImageService",
    path = "/image",
    configuration = [ImageAPIConfig::class],
    fallbackFactory = ImageFallbackFactory::class)
interface ImageAPI {

    @GetMapping("/hello")
    fun hello(): String

    @GetMapping("")
    fun getImages(
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): ResponseEntity<Page<ImageDTO>>

    @PostMapping("")
    fun createImage(image: CreateImageDTO): ResponseEntity<ImageDTO>

    @GetMapping("/{id}")
    fun getImageById( @PathVariable id: Long): ResponseEntity<ImageDTO>

    @PostMapping("/{id}")
    fun updateImageById( @PathVariable id: Long, image: ImageDTO): ResponseEntity<ImageDTO>

    @DeleteMapping("/{id}")
    fun deleteImage( @PathVariable id: Long) : ResponseEntity<ImageDTO>

    @GetMapping("/user/{userId}")
    fun getUserImages( @PathVariable userId: String,
                       @RequestParam(required = true, defaultValue = "0") page: Int,
                       @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<ImageDTO>>

    @GetMapping("/ids")
    fun getImagesByIds(@RequestParam ids: List<Long>): ResponseEntity<List<ImageDTO>>
}

//TODO: Change the token config?
class ImageAPIConfig(
    @Value("\${jwt.imageSubject}") val jwtSubject: String,
    @Value("\${jwt.expiration}") val jwtExpiration: Long,
    @Value("\${jwt.imageSecret}") val jwtSecret: String,
    val friends: FriendshipRepository,
    private val utils: JWTUtils
) {

    @Bean
    fun feignErrorDecoder(): ErrorDecoder = CustomFeignErrorDecoder()

    val logger = LoggerFactory.getLogger(ImageAPIConfig::class.java)

    @Bean
    fun imageAPIInterceptor(): RequestInterceptor {
        return RequestInterceptor { template ->
            val userToken = getUserTokenFromRequest()
            val username = extractUsername(userToken)
            val role = extractRole(userToken)
            logger.info("User $username is accessing ImageService.")
            val imageServiceToken = generateImageServiceToken(username, role)
            template.header("Authorization", "Bearer $imageServiceToken")
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
            throw IllegalArgumentException("User token is required for accessing ImageService.")
        }

        val claims = Jwts.parser().setSigningKey(utils.key).parseClaimsJws(userToken).body

        return claims["username"] as String
    }

    private fun extractRole(userToken: String?): String {
        if (userToken == null) {
            logger.error("User token is missing from the request to extract role.")
            throw IllegalArgumentException("User token is required for accessing ImageService.")
        }

        val claims = Jwts.parser().setSigningKey(utils.key).parseClaimsJws(userToken).body

        return claims["role"] as String
    }

    private fun generateImageServiceToken(username: String, role: String): String {
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

class ImageFallbackFactory {

}
