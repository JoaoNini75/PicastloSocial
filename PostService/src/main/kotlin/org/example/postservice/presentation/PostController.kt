package org.example.postservice.presentation

import com.netflix.discovery.EurekaClient
import org.example.postservice.data.PostDTO
import org.example.postservice.application.PostApplication
import org.example.postservice.data.CreatePostDTO
import org.example.postservice.security.filters.JWTAuthenticationFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RestController
import org.springframework.data.domain.Page

@RestController
@Validated
class PostController(var eurekaClient: EurekaClient, val appPosts: PostApplication) : PostAPI {
    @Value("\${spring.application.name}")
    var appName: String? = null

    val logger: Logger = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)

    override fun hello(): String {
        return "Hello World! from ${eurekaClient.getApplication(appName).name}"
    }

    override fun getPosts(page: Int, size: Int): ResponseEntity<Page<PostDTO>> {
        return ResponseEntity.ok(appPosts.getPosts(page, size))
    }

    override fun createPost(post: CreatePostDTO): ResponseEntity<PostDTO> {
        return ResponseEntity.ok(appPosts.createPost(post))
    }

    override fun getPostById(id: Long): ResponseEntity<PostDTO> {
        return ResponseEntity.ok(appPosts.getPost(id))
    }

    override fun updatePost(id: Long, post: PostDTO): ResponseEntity<PostDTO> {
        return ResponseEntity.ok(appPosts.updatePost(id, post))
    }

    override fun deletePost(id: Long): ResponseEntity<PostDTO> {
        return ResponseEntity.ok(appPosts.deletePost(id))
    }

    override fun getUserPosts(username: String, page: Int, size: Int): ResponseEntity<Page<PostDTO>> {
        return ResponseEntity.ok(appPosts.postsOfUser(username, page, size))
    }

    override fun getFriendsPosts(friendUsernames: List<String>, page: Int, size: Int): ResponseEntity<Page<PostDTO>> {
        logger.info("Getting posts for friends: $friendUsernames")
        return ResponseEntity.ok(appPosts.friendsPosts(friendUsernames, page, size))
    }

    override fun getPublicPosts(page: Int, size: Int): ResponseEntity<Page<PostDTO>> {
        return ResponseEntity.ok(appPosts.publicPosts(page, size))
    }

    override fun getGroupPosts(groupID: Int, page: Int, size: Int): ResponseEntity<Page<PostDTO>> {
        return ResponseEntity.ok(appPosts.groupPosts(groupID, page, size))
    }

}