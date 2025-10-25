package org.example.userservice.resources
import feign.Response
import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class CustomFeignErrorDecoder : ErrorDecoder {

    private val logger = LoggerFactory.getLogger(CustomFeignErrorDecoder::class.java)

    override fun decode(methodKey: String, response: Response): Exception {
        logger.error("Feign error occurred. Method: $methodKey, Status: ${response.status()}, Reason: ${response.reason()}")

        val errorMessage = response.body()?.asReader()?.readText() ?: "No error message provided"
        return when (response.status()) {
            400 -> ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request: $errorMessage")
            401 -> ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: $errorMessage")
            403 -> ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: $errorMessage")
            404 -> ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found: $errorMessage")
            409 -> ResponseStatusException(HttpStatus.CONFLICT, "Conflict: $errorMessage")
            500 -> ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error: $errorMessage")
            else -> ResponseStatusException(HttpStatus.valueOf(response.status()), "Error: $errorMessage")
        }
    }
}
