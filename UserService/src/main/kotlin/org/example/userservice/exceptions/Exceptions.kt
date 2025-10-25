package org.example.userservice.exceptions

open class NotFoundException(message: String) : RuntimeException(message)
open class ConflictException(message: String) : RuntimeException(message)
open class UnauthorizedException(message: String) : RuntimeException(message)
open class ForbiddenException(message: String) : RuntimeException(message)
open class BadRequestException(message: String) : RuntimeException(message)
open class NoContentException(message: String) : RuntimeException(message)
open class InternalServerErrorException(message: String) : RuntimeException(message)

class UserNotFoundException(s: String) : NotFoundException("User not found: $s")
class UsernamesDoNotMatchException: BadRequestException("The user username in the url does not match the one in the body")
class InvalidCredentialsException: UnauthorizedException("Invalid credentials")
class InvalidUserException(s: String): BadRequestException("Invalid user data. $s")
class FriendshipNotFoundException: NotFoundException("Friendship not found")
class FriendshipAlreadyExistsException: ConflictException("Friendship already exists")
