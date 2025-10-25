package org.example.postservice.data

import org.example.postservice.repositories.PostRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.time.OffsetDateTime

@Component
class SeedData(val postRepo: PostRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        seedData()
    }

    fun seedData() {
        val post1 = PostDAO(1, "JaneKotlin", "Post 1", 2, 1,4,1, OffsetDateTime.now())
        val post2 = PostDAO(2, "AdrianaAda", "Post 2", 3, 1,5,0, OffsetDateTime.now())
        val post3 = PostDAO(3, "MikeWazowski", "Post 3", 4, 3,2,1, OffsetDateTime.now())
        val post4 = PostDAO(4, "JohnHaskell", "Post 4", 5, 2,3,2, OffsetDateTime.now())
        val post5 = PostDAO(5, "AdrianaAda", "Post 5", 6, 2,1,0, OffsetDateTime.now())
        val post6 = PostDAO(6, "DominicDart", "Post 6", 7, 2,6,1, OffsetDateTime.now())
        val post7 = PostDAO(7, "MikeWazowski", "Post 7", 1, 1,7,101, OffsetDateTime.now())
        val post8 = PostDAO(8, "BrianJava", "Post 8", 9, 1,8,2, OffsetDateTime.now())
        val post9 = PostDAO(9, "JamesSullivan", "Post 9", 10, 1,9,102, OffsetDateTime.now())


        postRepo.save(post1)
        postRepo.save(post2)
        postRepo.save(post3)
        postRepo.save(post4)
        postRepo.save(post5)
        postRepo.save(post6)
        postRepo.save(post7)
        postRepo.save(post8)
        postRepo.save(post9)
    }
}
