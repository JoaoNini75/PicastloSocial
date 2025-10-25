package org.example.userservice.repositories

import org.example.userservice.data.daos.FriendshipDAO
import org.example.userservice.data.daos.FriendshipId
import org.example.userservice.data.daos.UserDAO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface FriendshipRepository : CrudRepository<FriendshipDAO, FriendshipId>,
                                 PagingAndSortingRepository<FriendshipDAO, FriendshipId> {

    @Query("""
        SELECT DISTINCT u
        FROM FriendshipDAO f
        JOIN FETCH UserDAO u ON (u.username = f.username1 OR u.username = f.username2)
        WHERE (f.username1 = :username OR f.username2 = :username)
        AND u.username != :username AND f.wasAccepted = true
    """)
    fun listUserFriends(username: String, pageable: Pageable): Page<UserDAO>



    @Query("""
        SELECT DISTINCT u
        FROM FriendshipDAO f
        JOIN FETCH UserDAO u ON (u.username = f.username1 OR u.username = f.username2)
        WHERE f.username2 = :username
        AND u.username != :username AND f.gotResponse = false
    """)
    fun listUserFriendshipInvites(username: String, pageable: Pageable): Page<UserDAO>
}