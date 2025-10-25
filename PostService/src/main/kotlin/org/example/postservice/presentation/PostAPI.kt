package org.example.postservice.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.example.postservice.data.CreatePostDTO
import org.example.postservice.data.PostDTO
import org.example.postservice.security.policies.*
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/post")
interface PostAPI {
    @GetMapping("/hello")
    fun hello(): String

    @Operation(
        summary = "Get list of all posts",
        operationId = "getPosts",
        description = "Retrieves a list of all posts",
        responses = [
            ApiResponse(responseCode = "200", description = "List of posts retrieved successfully",
                content = [Content(array = ArraySchema(schema = Schema(implementation = PostDTO::class)))])
        ]
    )
    @GetMapping("", produces = ["application/json"])
    @CanReadAllPosts
    fun getPosts(@RequestParam(required = true, defaultValue = "0") page: Int,
                 @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<PostDTO>>

    @Operation(
        summary = "Create a new post",
        operationId = "createPost",
        description = "Creates a new post in the system",
        responses = [
            ApiResponse(responseCode = "200", description = "Post created successfully",
                content = [Content(schema = Schema(implementation = PostDTO::class))]),
                ApiResponse(responseCode = "409", description = "Post creation conflict")
        ]
    )
    @PostMapping("", consumes = ["application/json"], produces = ["application/json"])
    fun createPost(
        @Parameter(description = "Post data to create")
        @Valid @RequestBody post: CreatePostDTO
    ): ResponseEntity<PostDTO>

    @Operation(
        summary = "Get post by ID",
        operationId = "getPostById",
        description = "Retrieves a post by its ID",
        responses = [
            ApiResponse(responseCode = "200", description = "Post retrieved successfully",
                content = [Content(schema = Schema(implementation = PostDTO::class))]),
                ApiResponse(responseCode = "404", description = "Post not found")
            ]
    )
    @GetMapping("/{id}", produces = ["application/json"])
    @CanReadPost
    fun getPostById(
        @Parameter(description = "ID of the post to retrieve", required = true)
        @Min(1) @PathVariable id: Long): ResponseEntity<PostDTO>

    @Operation(
        summary = "Update post by ID",
        operationId = "updatePost",
        description = "Updates a post by its ID",
        responses = [
            ApiResponse(responseCode = "200", description = "Post updated successfully",
                content = [Content(schema = Schema(implementation = PostDTO::class))]),
                ApiResponse(responseCode = "404", description = "Post not found")
            ]
    )
    @PutMapping("/{id}", consumes = ["application/json"], produces = ["application/json"])
    @CanCRUDPost
    fun updatePost(
        @Parameter(description = "ID of the post to update", required = true)
        @Min(1) @PathVariable id: Long,
        @Parameter(description = "Post data to update")
        @Valid @RequestBody post: PostDTO): ResponseEntity<PostDTO>


    @Operation(
        summary = "Delete post by ID",
        operationId = "deletePost",
        description = "Deletes a post by its ID",
        responses = [
            ApiResponse(responseCode = "200", description = "Post deleted successfully",
                content = [Content(schema = Schema(implementation = PostDTO::class))]),
                ApiResponse(responseCode = "404", description = "Post not found")
            ]
    )
    @DeleteMapping("/{id}", produces = ["application/json"])
    @CanCRUDPost
    fun deletePost(
        @Parameter(description = "ID of the post to delete", required = true)
        @Min(1) @PathVariable id: Long): ResponseEntity<PostDTO>

    @Operation(
        summary = "Get list of posts by user",
        operationId = "getUserPosts",
        description = "Retrieves a list of posts by a user",
        responses = [
            ApiResponse(responseCode = "200", description = "List of posts retrieved successfully",
                content = [Content(array = ArraySchema(schema = Schema(implementation = PostDTO::class)))]),
                ApiResponse(responseCode = "404", description = "User not found")
            ]
    )
    @GetMapping("/user/{username}", produces = ["application/json"])
    @CanReadUserPosts
    fun getUserPosts(
        @Parameter(description = "Username of the user to retrieve posts for", required = true)
        @PathVariable username: String,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int):
            ResponseEntity<Page<PostDTO>>
    @Operation(
        summary = "Get list of posts by friends",
        operationId = "getFriendsPosts",
        description = "Retrieves a list of posts by friends",
        responses = [
            ApiResponse(responseCode = "200", description = "List of posts retrieved successfully",
                content = [Content(array = ArraySchema(schema = Schema(implementation = PostDTO::class)))]),
                ApiResponse(responseCode = "404", description = "User not found")
            ]
    )
    @GetMapping("/friends", produces = ["application/json"])
    fun getFriendsPosts(
        @Parameter(description = "List of usernames of friends to retrieve posts for", required = true)
        @RequestAttribute  friendUsernames: List<String>,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int):
            ResponseEntity<Page<PostDTO>>


    @Operation(
        summary = "Get the list of posts by group",
        operationId = "getGroupPosts",
        description = "Retrieves a list of posts by group",
        responses = [
            ApiResponse(responseCode = "200", description = "List of posts retrieved successfully",
                content = [Content(array = ArraySchema(schema = Schema(implementation = PostDTO::class)))]),
                ApiResponse(responseCode = "404", description = "No posts found for group")
            ]
    )
    @GetMapping("/group/{groupID}", produces = ["application/json"])
    fun getGroupPosts(
        @Parameter(description = "ID of the group to retrieve posts for", required = true)
        @Min(100) @PathVariable groupID: Int,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int):
            ResponseEntity<Page<PostDTO>>

    @Operation(
        summary = "Get the list of public posts",
        operationId = "getPublicPosts",
        description = "Retrieves a list of public posts",
        responses = [
            ApiResponse(responseCode = "200", description = "List of posts retrieved successfully",
                content = [Content(array = ArraySchema(schema = Schema(implementation = PostDTO::class)))]),
                ApiResponse(responseCode = "404", description = "No public posts found")
            ]
    )
    @GetMapping("/public", produces = ["application/json"])
    fun getPublicPosts(@RequestParam(required = true, defaultValue = "0") page: Int,
                       @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<PostDTO>>

}
