package com.example.groupservice.repositories

import com.example.groupservice.data.daos.GroupMembershipDAO
import com.example.groupservice.data.dtos.GroupMembershipDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param

interface GroupMembershipRepository : CrudRepository<GroupMembershipDAO, Long>,
                            PagingAndSortingRepository<GroupMembershipDAO, Long> {

    @Query("""
    SELECT gm 
    FROM GroupMembershipDAO gm 
    WHERE gm.id.groupId = :groupId
""")
    fun listGroupMembers(@Param("groupId") groupId: Long, pageable: Pageable): Page<GroupMembershipDAO?>


    @Query("""
    SELECT gm
    FROM GroupMembershipDAO gm
    WHERE gm.id.username = :username AND gm.id.groupId = :groupId
""")
    fun findMember(@Param("username") username: String, @Param("groupId") groupId: Long): GroupMembershipDAO?


    @Query("""
    SELECT new com.example.groupservice.data.dtos.GroupMembershipDTO(gm.id.username, gm.id.groupId, gm.name, gm.createdAt)
    FROM GroupMembershipDAO gm
    WHERE gm.id.username = :username
""")
    fun getUserGroupIds(@Param("username") username: String, pageable: Pageable): Page<GroupMembershipDTO>



}