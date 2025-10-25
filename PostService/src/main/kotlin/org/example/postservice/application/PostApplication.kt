package org.example.postservice.application

import org.example.postservice.data.CreatePostDTO
import org.example.postservice.data.PostDAO
import org.example.postservice.data.PostDTO
import org.example.postservice.presentation.ForbiddenException
import org.example.postservice.presentation.PostNotFoundException
import org.example.postservice.presentation.UserHasNoPostsException
import org.example.postservice.repositories.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class PostApplication(val postRepo: PostRepository) {
    fun createPost(post: CreatePostDTO): PostDTO =
        postRepo.save(PostDAO(9999, post.username, post.description,post.resultImageId, post.originalImageId, post.pipelineId, post.visibility, OffsetDateTime.now()))
            .let { PostDTO(it.id, it.username, it.description, it.resultImageId, it.originalImageId, it.pipelineId, it.visibility, it.createdAt) }


    fun deletePost(postId: Long) : PostDTO {
        val postDAO = postRepo.findById(postId).orElseThrow { PostNotFoundException() }
        postRepo.deleteById(postId)
        return PostDTO(postDAO.id, postDAO.username, postDAO.description, postDAO.resultImageId, postDAO.originalImageId, postDAO.pipelineId, postDAO.visibility, postDAO.createdAt)
    }

    fun updatePost(postId: Long, post: PostDTO): PostDTO {
        val postDAO = postRepo.findById(postId).orElseThrow { PostNotFoundException() }
        val updatedPost = PostDAO(postId, post.username, post.description, post.resultImageId, post.originalImageId, post.pipelineId, postDAO.visibility, postDAO.createdAt)
        return PostDTO(postRepo.save(updatedPost).id, updatedPost.username, updatedPost.description, updatedPost.resultImageId, updatedPost.originalImageId, updatedPost.pipelineId, updatedPost.visibility, updatedPost.createdAt)
    }

    fun getPosts(page: Int, size: Int): Page<PostDTO> = postRepo.findAll(PageRequest.of(page, size)).map {
        PostDTO(it.id, it.username, it.description, it.resultImageId, it.originalImageId, it.pipelineId, it.visibility, it.createdAt)}

    fun getPost(postId: Long): PostDTO {
        return postRepo.findById(postId).orElseThrow { PostNotFoundException() }.let {
            PostDTO(it.id, it.username, it.description, it.resultImageId, it.originalImageId, it.pipelineId, it.visibility, it.createdAt)}
    }

    fun postsOfUser(username: String, page: Int, size: Int): Page<PostDTO> {
        val posts = postRepo.listUserPosts(username, PageRequest.of(page, size))
        if (posts.isEmpty) {
            throw UserHasNoPostsException()
        }
        return posts.map { PostDTO(it.id, it.username, it.description, it.resultImageId, it.originalImageId, it.pipelineId, it.visibility, it.createdAt) }
    }

    fun friendsPosts(friendUsernames: List<String>, page: Int, size: Int): Page<PostDTO>? {
        val posts = postRepo.friendsPosts(friendUsernames, PageRequest.of(page, size))
        return posts.map { PostDTO(it.id, it.username, it.description, it.resultImageId, it.originalImageId, it.pipelineId, it.visibility, it.createdAt) }

    }

    fun publicPosts(page: Int, size: Int): Page<PostDTO>? {
        val posts = postRepo.publicPosts(PageRequest.of(page, size))
        return posts.map { PostDTO(it.id, it.username, it.description, it.resultImageId, it.originalImageId, it.pipelineId, it.visibility, it.createdAt) }
    }

    fun groupPosts(groupID: Int, page: Int, size: Int): Page<PostDTO>? {
        val posts = postRepo.groupPosts(groupID, PageRequest.of(page, size))
        return posts.map { PostDTO(it.id, it.username, it.description, it.resultImageId, it.originalImageId, it.pipelineId, it.visibility, it.createdAt) }
    }

}