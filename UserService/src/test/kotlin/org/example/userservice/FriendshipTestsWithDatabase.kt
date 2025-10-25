package org.example.userservice

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.example.userservice.data.dtos.FriendshipDTO
import org.example.userservice.data.InviteResponse
import org.example.userservice.data.SeedData
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
class FriendshipTestsWithDatabase {

    private final val userServicePath = "/user"
    private final val usernames = SeedData.SeedDataUsernames.names

    private final val date1: OffsetDateTime = OffsetDateTime.now()
    private final val date2: OffsetDateTime = OffsetDateTime.now()
    val friendship1 = FriendshipDTO( usernames[0], usernames[6], date1, false, false)
    val friendship2 = FriendshipDTO( usernames[1], usernames[7], date2, true, true)

    @Autowired
    lateinit var mvc: MockMvc

    companion object {
        val objectMapper: ObjectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())
    }

    @Test
    fun `Just to see if the controller exists`() {
        for (username in usernames)
            mvc.perform(get("${userServicePath}/$username/friends"))
                .andExpect(status().isOk)
    }

    @Test
    fun `Just to see if 404 is real`() {
        mvc.perform(get("${userServicePath}/${usernames[1]}/friend"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `List user friends`() {
        var body = mvc.perform(get("${userServicePath}/${usernames[1]}/friends"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)
        var friends = objectMapper.readValue(body, object : TypeReference<List<UserDTO>>() {})
        assertEquals(0, friends.size)

        body = mvc.perform(get("${userServicePath}/${usernames[5]}/friends"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)
        friends = objectMapper.readValue(body, object : TypeReference<List<UserDTO>>() {})
        assertEquals(2, friends.size)
    }

    @Test
    fun `List user friendship invites`() {
        sendFriendshipInvite(FriendshipDTO(usernames[6], usernames[3], OffsetDateTime.now(), false, false))

        var body = mvc.perform(get("${userServicePath}/${usernames[3]}/friends/invites"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)
        var friendships = objectMapper.readValue(body, object : TypeReference<List<UserDTO>>() {})
        assertEquals(1, friendships.size)

        body = mvc.perform(get("${userServicePath}/${usernames[4]}/friends/invites"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)
        friendships = objectMapper.readValue(body, object : TypeReference<List<UserDTO>>() {})
        assertEquals(0, friendships.size)
    }

    @Test
    fun `Send friendship invite`() {
        sendFriendshipInvite(friendship1)
        sendFriendshipInvite(friendship2)
    }

    fun sendFriendshipInvite(friendship: FriendshipDTO) {
        mvc.perform(
            post("${userServicePath}/${friendship.username1}/friends/${friendship.username2}")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(null)))
            .andExpect(status().isOk)

        val body = mvc.perform(get("${userServicePath}/${friendship.username2}/friends/invites"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)
        val friends = objectMapper.readValue(body, object : TypeReference<List<UserDTO>>() {})

        var resultFriend : UserDTO? = null
        for (f in friends)
            if (f.username == friendship.username1)
                resultFriend = f

        assert(resultFriend != null)
    }

    @Test
    fun `Respond to friendship invite`() {
        var expectedFriendship = FriendshipDTO(usernames[0], usernames[1], OffsetDateTime.now(), true, true)
        respondToFriendshipInvite(expectedFriendship)
        expectedFriendship = FriendshipDTO( usernames[1], usernames[2], OffsetDateTime.now(), true, false)
        respondToFriendshipInvite(expectedFriendship)
    }

    fun respondToFriendshipInvite(expectedFriendship: FriendshipDTO) {
        mvc.perform(
            put("$userServicePath/${expectedFriendship.username1}/friends/${expectedFriendship.username2}")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(InviteResponse(expectedFriendship.wasAccepted))))
            .andExpect(status().isOk)

        val body = mvc.perform(get("/friendships"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)
        val friendships = objectMapper.readValue(body, object : TypeReference<List<FriendshipDTO>>() {})

        var resultFriendship : FriendshipDTO? = null
        for (f in friendships)
            if (f.username1 == expectedFriendship.username1 && f.username2 == expectedFriendship.username2)
                resultFriendship = f

        if (resultFriendship != null)
            TestUtils.assertFriendshipEquals(expectedFriendship, resultFriendship)
    }

    @Test
    fun `Remove friendship`() {
        val friendship1 = FriendshipDTO( usernames[0], usernames[1], OffsetDateTime.now(), false, false)
        val friendship2 = FriendshipDTO( usernames[5], usernames[6], OffsetDateTime.now(), true, true)
        removeFriendship(friendship1)
        removeFriendship(friendship2)
    }

    fun removeFriendship(friendship: FriendshipDTO) {
        assert(friendshipExists(friendship.username1, friendship.username2))

        mvc.perform(delete("$userServicePath/${friendship.username1}/friends/${friendship.username2}"))
            .andExpect(status().isOk)

        assert(!friendshipExists(friendship.username1, friendship.username2))

        // add friendship again, so other tests do not fail sometimes (order of tests not guaranteed)
        mvc.perform(
            post("$userServicePath/${friendship.username1}/friends/${friendship.username2}")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(friendship)))
            .andExpect(status().isOk)
    }

    fun friendshipExists(username1: String, username2: String) : Boolean {
        val body = mvc.perform(get("/friendships"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val friendships = objectMapper.readValue(body, object : TypeReference<List<FriendshipDTO>>() {})
        for (f in friendships)
            if (f.username1 == username1 && f.username2 == username2)
                return true

        return false
    }
}
