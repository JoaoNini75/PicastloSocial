package org.example.userservice.repositories

import com.example.groupservice.data.daos.GroupDAO
import org.example.userservice.data.daos.UserDAO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository : CrudRepository<UserDAO, String>, PagingAndSortingRepository<UserDAO, String> {

    @Query("SELECT u FROM UserDAO u WHERE u.username LIKE %:search% OR u.displayName LIKE %:search%")
    fun searchUsers(search: String,  pageable: Pageable): Page<UserDAO>
}