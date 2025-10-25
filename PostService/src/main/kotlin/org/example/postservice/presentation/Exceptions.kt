package org.example.postservice.presentation

open class NotFoundException(message: String) : RuntimeException(message)
open class ConflictException(message: String) : RuntimeException(message)
open class UnauthorizedException(message: String) : RuntimeException(message)
open class ForbiddenException(message: String) : RuntimeException(message)
open class BadRequestException(message: String) : RuntimeException(message)
open class NoContentException(message: String) : RuntimeException(message)


class PostNotFoundException: NotFoundException("Post not found")
class PostAlreadyExistsException: ConflictException("Post already exists")
class UserHasNoPostsException: NoContentException("User has no Posts")
