package org.example.postservice.repositories

import org.example.postservice.data.PostDAO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface PostRepository: CrudRepository<PostDAO, Long>, PagingAndSortingRepository<PostDAO, Long> {

    @Query("SELECT p FROM PostDAO p WHERE p.username = :username")
    fun listUserPosts(username: String, pageable: Pageable): Page<PostDAO>

    @Query("""
    SELECT p 
    FROM PostDAO p 
    WHERE (p.visibility = 1 OR p.visibility = 2) AND p.username IN :friendUsernames
""")
    fun friendsPosts(friendUsernames: List<String>, pageable: Pageable): Page<PostDAO>

    @Query("SELECT p FROM PostDAO p WHERE p.visibility = :groupID")
    fun groupPosts(groupID: Int, pageable: Pageable): Page<PostDAO>

    @Query("SELECT p FROM PostDAO p WHERE p.visibility = 2")
    fun publicPosts(pageable: Pageable): Page<PostDAO>

}