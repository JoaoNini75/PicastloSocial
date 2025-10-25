package com.example.groupservice.presentation

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FORBIDDEN)
class GroupResourcePermissionsException : RuntimeException("You are not allowed to access such resource")

@ResponseStatus(HttpStatus.NOT_FOUND)
class GroupNotFoundException: RuntimeException("Group with given ID does not exist")

@ResponseStatus(HttpStatus.CONFLICT)
class GroupAlreadyExistsException: RuntimeException("Group with given ID already exists")

@ResponseStatus(HttpStatus.CONFLICT)
class MemberAlreadyInGroupException: RuntimeException("Member is already in the group")