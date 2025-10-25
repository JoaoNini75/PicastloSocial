package org.example.userservice

import com.fasterxml.jackson.core.type.TypeReference
import org.example.userservice.UserTestsWithDatabase.Companion.objectMapper
import org.example.userservice.data.daos.UserDAO
import org.example.userservice.data.dtos.UserDTO
import org.example.userservice.repositories.UserRepository
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
class UserTestsWithoutADataBase {

    private final val servicePath = "/user"

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var userRepo: UserRepository

    companion object {
        val dbUser1 = UserDAO("JaneDoe1", "JaneDoe", "pwd1", "janedoe@gmail.com", OffsetDateTime.now(), UserDAO.Role.User)
        val dbUser2 = UserDAO("JaneSmith2", "JaneSmith", "pwd2", "janesmith@gmail.com", OffsetDateTime.now(), UserDAO.Role.User)
        val dbUsers = listOf(dbUser1, dbUser2)
        val apiUser1 = dbUser1.let { UserDTO( it.username, it.displayName, it.email, it.createdAt, it.role) }
        val apiUsers = dbUsers.map { UserDTO( it.username, it.displayName, it.email, it.createdAt, it.role) }
    }

    @Test
    fun `Just to see if the database can be faked or mocked`() {
        Mockito.`when`(userRepo.findAll()).thenReturn(dbUsers)

        val body = mvc.perform(get(servicePath))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)

        val usersMapped = objectMapper.readValue(body, object : TypeReference<List<UserDTO>>() {})
        assertEquals(apiUsers.size, usersMapped.size)

        for (i in usersMapped.indices)
            TestUtils.assertUserEquals(apiUsers[i], usersMapped[i])
    }

    @Test
    fun `testing the getting of an user`() {
        val key = 1L

        Mockito.`when`(userRepo.findById(any())).then {
            assertEquals(key, it.getArgument(0)); Optional.of(dbUser1)
        }

        val body = mvc.perform(get("${servicePath}/${key}"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)

        val userMapped = objectMapper.readValue(body, UserDTO::class.java)
        TestUtils.assertUserEquals(apiUser1, userMapped)
    }

}
