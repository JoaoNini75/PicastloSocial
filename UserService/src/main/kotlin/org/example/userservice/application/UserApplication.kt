package org.example.userservice.application

import com.example.groupservice.data.daos.GroupDAO
import com.example.groupservice.data.dtos.GroupDTO
import org.example.userservice.data.*
import org.example.userservice.data.daos.FriendshipDAO
import org.example.userservice.data.daos.FriendshipId
import org.example.userservice.data.daos.UserDAO
import org.example.userservice.data.dtos.*
import org.example.userservice.exceptions.FriendshipAlreadyExistsException
import org.example.userservice.exceptions.FriendshipNotFoundException
import org.example.userservice.exceptions.UsernamesDoNotMatchException
import org.example.userservice.exceptions.UserNotFoundException
import org.example.userservice.repositories.FriendshipRepository
import org.example.userservice.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.*

@Component
class UserApplication(val userRepo: UserRepository, val friendshipRepo: FriendshipRepository) {

    val logger: org.slf4j.Logger = LoggerFactory.getLogger(UserApplication::class.java)

    fun listUsers(page: Int, size: Int): Page<UserDTO> =
        userRepo.findAll(PageRequest.of(page, size)).map { userDAOToDTO(it) }

    fun searchUsers(search: String, page: Int, size: Int): Page<UserDTO> =
        userRepo.searchUsers(search, PageRequest.of(page, size)).map { userDAOToDTO(it) }

    fun createUser(user: UserCreateDTO): UserDTO =
        userRepo
            .save(UserDAO(user.username, user.displayName, BCryptPasswordEncoder().encode(user.passwordHash),
                user.email, OffsetDateTime.now(), user.role))
            .let { userDAOToDTO(it) }


    fun getUserById(username: String): UserDTO {
        val user = userRepo.findById(username).orElseThrow{ UserNotFoundException("username: $username") }
        return userDAOToDTO(user)
    }

    fun getUserDetails(username: String): UserDetails? {
        val user = userRepo.findById(username).orElseThrow{ UserNotFoundException(username) }
        logger.info("User found: $user")

        return user?.let {
            User.builder()
                .username(it.username)
                .password(it.passwordHash) // Ensure the password is encoded
                .roles(it.role.toString()) // Convert roles to varargs
                .build()
        }
    }

    fun updateUser(username: String, updatedUser: UserUpdateDTO): UserDTO {
        if (username != updatedUser.username)
            throw UsernamesDoNotMatchException()

        val currentUser = userRepo.findById(username)
            .orElseThrow{ UserNotFoundException(updatedUser.username) }

        val resultUser = UserDAO(username, updatedUser.displayName,
            BCryptPasswordEncoder().encode(updatedUser.passwordHash),
            updatedUser.email, currentUser.createdAt, currentUser.role)

        return userDAOToDTO(userRepo.save(resultUser))
    }

    fun deleteUser(username: String): UserDTO {
        val user = userRepo.findById(username).orElseThrow{ UserNotFoundException("username: $username") }
        userRepo.deleteById(username)
        return userDAOToDTO(user)
    }



    fun sendFriendshipInvite(username: String, friendUsername: String) {
        val friendship = getFriendship(username, friendUsername)
        if (friendship.isPresent && friendship.get().wasAccepted)
            throw FriendshipAlreadyExistsException()

        friendshipRepo.save(FriendshipDAO(username, friendUsername,
            OffsetDateTime.now(), false, false))
    }

    fun respondToFriendshipInvite(username: String, friendUsername: String, inviteResponse: InviteResponse) {
        val friendshipOpt = getFriendship(username, friendUsername)
        if (friendshipOpt.isEmpty)
            throw FriendshipNotFoundException()
        val friendship = friendshipOpt.get()

        friendshipRepo.save(FriendshipDAO(friendship.username1, friendship.username2,
            friendship.createdAt, true, inviteResponse.acceptedInvite))
    }

    fun removeFriendship(username: String, friendUsername: String) {
        val friendship = getFriendship(username, friendUsername)
        if (friendship.isEmpty)
            throw FriendshipNotFoundException()

        friendshipRepo.delete(friendship.get())
    }


    fun listUserFriends(username: String, page: Int, size: Int): Page<UserDTO> =
        friendshipRepo.listUserFriends(username, PageRequest.of(page, size)).map { userDAOToDTO(it) }


    fun listUserFriendshipInvites(username: String, page: Int, size: Int): Page<UserDTO> =
        friendshipRepo.listUserFriendshipInvites(username, PageRequest.of(page, size)).map { userDAOToDTO(it) }


    fun listAllFriendships(page: Int, size: Int): Page<FriendshipDTO> =
        friendshipRepo.findAll(PageRequest.of(page, size)).map { friendshipDAOToDTO(it) }


    fun getFriendship(username: String, friendUsername: String): Optional<FriendshipDAO> {
        userRepo.findById(username).orElseThrow{ UserNotFoundException("username: $username") }
        userRepo.findById(friendUsername).orElseThrow{ UserNotFoundException("friendUsername: $friendUsername") }
        return friendshipRepo.findById(FriendshipId(username, friendUsername))
    }

    fun userDAOToDTO(dao: UserDAO) =
        UserDTO(dao.username, dao.displayName, dao.email, dao.createdAt, dao.role)

    fun friendshipDAOToDTO(dao: FriendshipDAO) =
        FriendshipDTO(dao.username1, dao.username2, dao.createdAt, dao.gotResponse, dao.wasAccepted)

//    fun loginUser(user: UserAuthDTO): String {
//        val u = userRepo.findByUsernameAndPass(user.username, user.passwordHash) ?: throw UserNotFoundException();
//
//        //TODO: Hardcoded, change later on.
//        val key = Base64.getEncoder().encodeToString("\${jwt.secret}".toByteArray())
//        val subject = "interservicetoken"
//        val expiration = 600000L
//
//        val claims = HashMap<String, Any?>()
//        claims["username"] = u.username
//
//        //TODO: Insert the user's capabilities in here :)
//
//        val token = Jwts.builder()
//            .setClaims(claims)
//            .setSubject(subject)
//            .setIssuedAt(Date(System.currentTimeMillis()))
//            .setExpiration(Date(System.currentTimeMillis() + expiration))
//            .signWith(SignatureAlgorithm.HS256, key)
//            .compact()
//
//        return token
//    }
}