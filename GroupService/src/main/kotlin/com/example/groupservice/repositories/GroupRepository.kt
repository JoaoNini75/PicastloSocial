package com.example.groupservice.repositories

import org.springframework.data.repository.CrudRepository
import com.example.groupservice.data.daos.GroupDAO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface GroupRepository : CrudRepository<GroupDAO, Long>, PagingAndSortingRepository<GroupDAO, Long> {
}

