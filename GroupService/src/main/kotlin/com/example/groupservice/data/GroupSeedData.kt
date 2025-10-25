package com.example.groupservice.data

import com.example.groupservice.data.daos.GroupDAO
import com.example.groupservice.data.daos.GroupMembershipDAO
import com.example.groupservice.data.daos.GroupMembershipId
import com.example.groupservice.repositories.GroupMembershipRepository
import com.example.groupservice.repositories.GroupRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class GroupSeedData(val groupRepo: GroupRepository, val groupMembershipRepo: GroupMembershipRepository) : CommandLineRunner {

        override fun run(vararg args: String?) {
            seedData()
        }

        fun seedData() {
            val group1 = GroupDAO(1, "Group 1", "MikeWazowski", OffsetDateTime.now())
            val group2 = GroupDAO(2, "Group 2", "JamesSullivan", OffsetDateTime.now())
            val group3 = GroupDAO(3, "Group 3", "JaneKotlin", OffsetDateTime.now())
            val group4 = GroupDAO(4, "Group 4", "MikeWazowski", OffsetDateTime.now())
            val group5 = GroupDAO(5, "Group 5", "JaneKotlin", OffsetDateTime.now())
            val group6 = GroupDAO(6, "Group 6", "JohnHaskell", OffsetDateTime.now())

            groupRepo.save(group1)
            groupRepo.save(group2)
            groupRepo.save(group3)
            groupRepo.save(group4)
            groupRepo.save(group5)
            groupRepo.save(group6)

            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("MikeWazowski",1),"Group 1", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JamesSullivan",1),"Group 1", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JaneKotlin",1),"Group 1", OffsetDateTime.now()))

            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JamesSullivan",2),"Group 2", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JaneKotlin",2),"Group 2", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JohnHaskell",2),"Group 2", OffsetDateTime.now()))

            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JaneKotlin",3),"Group 3", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("MikeWazowski",3),"Group 3", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JamesSullivan",3),"Group 3", OffsetDateTime.now()))

            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("MikeWazowski",4),"Group 4", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JamesSullivan",4),"Group 4", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JaneKotlin",4),"Group 4", OffsetDateTime.now()))

            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JohnHaskell",5),"Group 5", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JaneKotlin",5),"Group 5", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JamesSullivan",5),"Group 5", OffsetDateTime.now()))

            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JohnHaskell",6),"Group 6", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("MikeWazowski",6),"Group 6", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JamesSullivan",6),"Group 6", OffsetDateTime.now()))
            groupMembershipRepo.save(GroupMembershipDAO(GroupMembershipId("JaneKotlin",6),"Group 6", OffsetDateTime.now()))

        }
}