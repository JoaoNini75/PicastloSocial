package com.example.groupservice.presentation

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class NotEnoughGroupPermissionsException : RuntimeException("You are not allowed to perform this operation")

@ResponseStatus(HttpStatus.NOT_FOUND)
class GroupMembershipNotFoundException: RuntimeException("Group Membership does not exist")