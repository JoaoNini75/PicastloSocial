package org.example.postservice.presentation

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.bind.MethodArgumentNotValidException

// custom error response class
data class ErrorResponse(val message: String, val status: Int)

// to make possible passing custom error messages when api errors happen
@ControllerAdvice
class PostServiceExceptionHandler {

    // no content / 204
    @ExceptionHandler(NoContentException::class)
    fun handleNoContentExceptions(ex: RuntimeException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(ex.message ?: "No content", HttpStatus.NO_CONTENT.value())
        return ResponseEntity(errorResponse, HttpStatus.NO_CONTENT)
    }

    // bad request / 400
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.allErrors.map { it.defaultMessage }.joinToString(", ")
        val errorResponse = ErrorResponse("Validation failed: $errors", HttpStatus.BAD_REQUEST.value())
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    // bad request / 400 (application level)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestExceptions(ex: RuntimeException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(ex.message ?: "Bad request", HttpStatus.BAD_REQUEST.value())
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    // not found / 404
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundExceptions(ex: RuntimeException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(ex.message ?: "Resource not found", HttpStatus.NOT_FOUND.value())
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    // conflict / 409
    @ExceptionHandler(ConflictException::class)
    fun handleConflictExceptions(ex: RuntimeException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(ex.message ?: "Conflict occurred", HttpStatus.CONFLICT.value())
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }
}
