package org.example.userservice

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.example.userservice.data.SeedData
import org.example.userservice.data.daos.UserDAO
import org.example.userservice.data.dtos.UserCreateDTO
import org.example.userservice.data.dtos.UserDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.OffsetDateTime


@SpringBootTest
@AutoConfigureMockMvc
class UserTestsWithDatabase {

    private final val servicePath = "/user"
    private final val usernames = SeedData.SeedDataUsernames.names

    private final val date1: OffsetDateTime = OffsetDateTime.now()
    private final val date2: OffsetDateTime = OffsetDateTime.now()
    val user1 = UserCreateDTO("testuser1", "Test User 1", "testuser1pwd", "testuser1@gmail.com", date1, UserDAO.Role.User)
    val user2 = UserCreateDTO("testuser2", "Test User 2", "testuser2pwd", "testuser2@gmail.com", date2, UserDAO.Role.User)

    @Autowired lateinit var mvc:MockMvc

    companion object {
        val objectMapper: ObjectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())
    }

    @Test
    fun `Just to see if the controller exists`() {
        mvc.perform(get(servicePath))
        .andExpect(status().isOk)
    }

    @Test
    fun `Just to see if 404 is real`() {
        mvc.perform(get("/use"))
        .andExpect(status().isNotFound)
    }

    @Test
    fun `List users`() {
        val body = mvc.perform(get(servicePath))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)
        val users = objectMapper.readValue(body, object : TypeReference<List<UserDTO>>() {})
        assertEquals(8, users.size)
    }

    @Test
    fun `Create User`() {
        val response1 = mvc.perform(post(servicePath)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user1)))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val createdUser1 = objectMapper.readValue(response1, UserDTO::class.java)
        TestUtils.assertUserEquals(TestUtils.userCreateToUserDTO(user1), createdUser1)

        val response2 = mvc.perform(post(servicePath)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user2)))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val createdUser2 = objectMapper.readValue(response2, UserDTO::class.java)
        TestUtils.assertUserEquals(TestUtils.userCreateToUserDTO(user2), createdUser2)

        // remove users so list works as expected
        mvc.perform(delete("$servicePath/${user1.username}"))
            .andExpect(status().isOk)

        mvc.perform(delete("$servicePath/${user2.username}"))
            .andExpect(status().isOk)
    }

    @Test
    fun `Update User`() {
        val updatedUser = UserCreateDTO(usernames[0], "Jane Kotlin Updated", "pwd1updt", "janekotlinupdt@gmail.com", OffsetDateTime.now(), UserDAO.Role.User)

        val body = mvc.perform(put("$servicePath/${usernames[0]}")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(updatedUser)))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)
        val existingUser = objectMapper.readValue(body, UserDTO::class.java)
        TestUtils.assertUserEquals(TestUtils.userCreateToUserDTO(updatedUser), existingUser)
    }

    @Test
    fun `Delete User`() {
        val user = UserCreateDTO(usernames[1], "John Haskell", "pwd2", "johnhaskell@gmail.com", OffsetDateTime.now(), UserDAO.Role.User)

        val body = mvc.perform(get("$servicePath/${usernames[1]}"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)
        val responseUser = objectMapper.readValue(body, UserDTO::class.java)
        TestUtils.assertUserEquals(TestUtils.userCreateToUserDTO(user), responseUser)

        mvc.perform(delete("$servicePath/${usernames[1]}"))
            .andExpect(status().isOk)

        mvc.perform(get("$servicePath/${usernames[1]}"))
            .andExpect(status().isNotFound)

        // add user again, so other tests do not fail sometimes (order of tests not guaranteed)
        mvc.perform(post(servicePath)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk)
    }
}