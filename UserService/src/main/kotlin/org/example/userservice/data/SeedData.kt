package org.example.userservice.data

import org.example.userservice.data.daos.FriendshipDAO
import org.example.userservice.data.daos.UserDAO
import org.example.userservice.repositories.FriendshipRepository
import org.example.userservice.repositories.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.time.OffsetDateTime

@Component
class SeedData(val userRepo: UserRepository,
               val friendshipRepo: FriendshipRepository) : CommandLineRunner {

    object SeedDataUsernames {
        val names = arrayOf("JaneKotlin", "JohnHaskell", "AdrianaAda", "AnthonyOCaml",
            "JenniferC", "BrianJava", "DominicDart", "BrunoGo")
    }

    fun nameToEmail(name: String): String =
        name.filterNot { it.isWhitespace() }.lowercase() + "@gmail.com"

    override fun run(vararg args: String?) {
        val names = SeedDataUsernames.names
        var idx = 1L
        val random = SecureRandom.getInstance("SHA1PRNG").apply { setSeed(1234567890L) }
        val encoder = BCryptPasswordEncoder(10, random)

        val adminName = "MikeWazowski"
        userRepo.save(UserDAO(adminName, adminName, encoder.encode("pwd$idx"),
            nameToEmail(adminName), OffsetDateTime.now(), UserDAO.Role.Admin))
        idx++

        val managerName = "JamesSullivan"
        userRepo.save(UserDAO(managerName, managerName, encoder.encode("pwd$idx"),
            nameToEmail(managerName), OffsetDateTime.now(), UserDAO.Role.Manager))
        idx++

        // add users
        for (name in names) {
            userRepo.save(
                UserDAO(name, name, encoder.encode("pwd$idx"),
                    nameToEmail(name), OffsetDateTime.now(), UserDAO.Role.User))
            idx++
        }

        // add friendship requests with no response
        friendshipRepo.save(FriendshipDAO("JaneKotlin", "JohnHaskell",
            OffsetDateTime.now(), false, false))
        friendshipRepo.save(FriendshipDAO("JohnHaskell", "AdrianaAda",
            OffsetDateTime.now(), false, false))

        // add friendship requests with negative response
        friendshipRepo.save(FriendshipDAO("AdrianaAda", "AnthonyOCaml",
                OffsetDateTime.now(), true, false))
        friendshipRepo.save(FriendshipDAO("AnthonyOCaml", "JenniferC",
            OffsetDateTime.now(), true, false))

        // add friendship requests with positive response
        friendshipRepo.save(FriendshipDAO("MikeWazowski", "BrianJava",
            OffsetDateTime.now(), true, true))
        friendshipRepo.save(FriendshipDAO("JaneKotlin", "DominicDart",
            OffsetDateTime.now(), true, true))
        friendshipRepo.save(FriendshipDAO("JamesSullivan", "BrianJava",
            OffsetDateTime.now(), true, true))
        friendshipRepo.save(FriendshipDAO("MikeWazowski", "DominicDart",
            OffsetDateTime.now(), true, true))
        friendshipRepo.save(FriendshipDAO("JaneKotlin", "BrianJava",
            OffsetDateTime.now(), true, true))
        friendshipRepo.save(FriendshipDAO("BrianJava", "DominicDart",
            OffsetDateTime.now(), true, true))
    }

}
