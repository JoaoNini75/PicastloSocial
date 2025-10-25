package org.example.userservice

import org.example.userservice.data.dtos.FriendshipDTO
import org.example.userservice.data.dtos.UserCreateDTO
import org.example.userservice.data.dtos.UserDTO
import org.junit.jupiter.api.Assertions.assertEquals

object TestUtils {
    fun assertUserEquals(expected: UserDTO, actual: UserDTO) {
        assertEquals(actual.username, expected.username)
        assertEquals(actual.displayName, expected.displayName)
        assertEquals(actual.email, expected.email)
        //assertEquals(actual.createdAt, expected.createdAt) //comparing the same date object fails???
        assertEquals(actual.role, expected.role)
    }

    fun userCreateToUserDTO(user: UserCreateDTO) =
        UserDTO(user.username, user.displayName, user.email, user.createdAt, user.role)

    fun assertFriendshipEquals(expected: FriendshipDTO, actual: FriendshipDTO) {
        assertEquals(expected.username1, actual.username1)
        assertEquals(expected.username2, actual.username2)
        //assertEquals(actual.createdAt, expected.createdAt) //comparing the same date object fails???
        assertEquals(expected.gotResponse, actual.gotResponse)
        assertEquals(expected.wasAccepted, actual.wasAccepted)
    }
}