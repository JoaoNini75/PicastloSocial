package org.example.userservice

import com.fasterxml.jackson.core.type.TypeReference
import org.example.userservice.UserTestsWithDatabase.Companion.objectMapper
import org.example.userservice.data.daos.FriendshipDAO
import org.example.userservice.data.dtos.FriendshipDTO
import org.example.userservice.data.daos.UserDAO
import org.example.userservice.data.dtos.UserDTO
import org.example.userservice.repositories.FriendshipRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.OffsetDateTime
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class FriendshipTestsWithoutDatabase {

    private final val servicePath = "/user"

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var friendshipRepo: FriendshipRepository

    companion object {
        val dbUser1 = UserDAO("JaneDoe1", "Jane Doe", "pwd1", "janedoe@gmail.com", OffsetDateTime.now(), UserDAO.Role.User)
        val dbUser2 = UserDAO("JaneSmith2", "Jane Smith", "pwd2", "janesmith@gmail.com", OffsetDateTime.now(), UserDAO.Role.User)
        val dbUser3 = UserDAO("JohnHaskell3", "John Haskell", "pwd3", "johnhaskell@gmail.com", OffsetDateTime.now(), UserDAO.Role.User)
        val dbUsers = listOf(dbUser1, dbUser2, dbUser3)
        val apiUser1 = dbUser1.let { UserDTO( it.username, it.displayName, it.email, it.createdAt, it.role) }
        val apiUsers = dbUsers.map { UserDTO( it.username, it.displayName, it.email, it.createdAt, it.role) }

        val dbFriendship1 = FriendshipDAO("JaneDoe1", "JaneSmith2", OffsetDateTime.now(), false, false)
        val dbFriendship2 = FriendshipDAO("JaneSmith2", "JohnHaskell3", OffsetDateTime.now(), true, false)
        val dbFriendship3 = FriendshipDAO("JaneDoe1", "JohnHaskell3", OffsetDateTime.now(), true, true)
        val dbFriendships = listOf(dbFriendship1, dbFriendship2, dbFriendship3)
        val apiFriendship1 = dbFriendship1.let { FriendshipDTO(it.username1, it.username2, it.createdAt, it.gotResponse, it.wasAccepted) }
        val apiFriendships = dbFriendships.map { FriendshipDTO(it.username1, it.username2, it.createdAt, it.gotResponse, it.wasAccepted) }
    }

    @Test
    fun `Just to see if the database can be faked or mocked`() {
        Mockito.`when`(friendshipRepo.findAll()).thenReturn(dbFriendships)

        val body = mvc.perform(get("/friendships"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)

        val friendshipsMapped = objectMapper.readValue(body, object : TypeReference<List<FriendshipDTO>>() {})
        assertEquals(apiFriendships.size, friendshipsMapped.size)

        for (i in friendshipsMapped.indices)
            TestUtils.assertFriendshipEquals(apiFriendships[i], friendshipsMapped[i])
    }

    @Test
    fun `testing the getting of user friends`() { // not working
        val key = 3L

        Mockito.`when`(friendshipRepo.findById(any())).then {
            assertEquals(key, it.getArgument(0)); Optional.of(dbFriendship3)
        }

        val body = mvc.perform(get("${servicePath}/${key}/friends"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)

        val friendsMapped = objectMapper.readValue(body, object : TypeReference<List<UserDTO>>() {})
        assertEquals(1, friendsMapped.size)
        TestUtils.assertUserEquals(apiUser1, friendsMapped[0])
    }
}