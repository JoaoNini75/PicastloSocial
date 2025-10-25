package com.example.imageservice.presentation

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.BAD_REQUEST)
class ImageIdMismatchException() : RuntimeException("Image id mismatch")

@ResponseStatus(HttpStatus.NOT_FOUND)
class ImageNotFoundException() : RuntimeException("Image not found")

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserDoNotHaveImagesException() : RuntimeException("User do not have images")

@ResponseStatus(HttpStatus.CONFLICT)
class ImageAlreadyExistsException() : RuntimeException("Image already exists")
