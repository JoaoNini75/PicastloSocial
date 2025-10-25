package org.example.userservice.security.security

import org.example.userservice.data.daos.FriendshipId
import org.example.userservice.data.daos.UserDAO
import org.example.userservice.data.dtos.UserTokenDTO
import org.example.userservice.repositories.FriendshipRepository
import org.example.userservice.repositories.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class SecurityPolicies(val userRepo: UserRepository, val friendRepo: FriendshipRepository) {

    fun canReadAllUsers(user: Principal): Boolean {
        val role = (user as UserTokenDTO).role
        return role == UserDAO.Role.Admin || role == UserDAO.Role.Manager
    }

    fun canCRUDUser(user: Principal, id:String): Boolean {
        val role = (user as UserTokenDTO).role
        val userToRead = userRepo.findById(id)
        return role == UserDAO.Role.Admin || role == UserDAO.Role.Manager
                || userToRead.isPresent && userToRead.get().username == user.name
    }

    fun canDeleteFriendShip(user: Principal, userId: String, friendId: String): Boolean {
        val role = (user as UserTokenDTO).role
        val userToRead = userRepo.findById(userId)
        val friendShip = FriendshipId(userId, friendId)
        val friendShipToRead = friendRepo.findById(friendShip)
        return (role == UserDAO.Role.Admin || role == UserDAO.Role.Manager
                || userToRead.isPresent && userToRead.get().username == user.name) && (friendShipToRead.isPresent)
    }

}

@PreAuthorize("@securityPolicies.canReadAllUsers(principal)")
annotation class CanReadAllUsers

@PreAuthorize("@securityPolicies.canCRUDUser(principal, #username)")
annotation class CanReadOneUser

@PreAuthorize("@securityPolicies.canDeleteFriendShip(principal, #username, #friendId)")
annotation class CanDeleteFriendShip