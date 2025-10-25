package com.example.imageservice.presentation

import com.example.imageservice.data.CreateImageDTO
import com.example.imageservice.data.ImageDTO
import com.example.imageservice.security.capabilities.CanCRUDImage
import com.example.imageservice.security.capabilities.CanReadAllImages
import com.example.imageservice.security.capabilities.CanReadImage
import com.example.imageservice.security.capabilities.CanReadImages
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/image")
interface ImageAPI {

    //TODO:: DELETE THIS METHOD
    @GetMapping("/hello")
    fun hello(): String

    @Operation(
        summary = "Get list of all images",
        operationId = "getImages",
        description = "Retrieves a list of all images",
        responses = [
            ApiResponse(responseCode = "200", description = "List of images retrieved successfully",
                content = [Content(schema = Schema(implementation = ImageDTO::class))])
        ]
    )
    @GetMapping("", produces = ["application/json"])
    @CanReadAllImages
    fun getImages(@RequestParam(required = true, defaultValue = "0") page: Int,
                  @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<ImageDTO>>


    @Operation(
        summary = "Create a new image",
        operationId = "createImage",
        description = "Creates a new image in the system",
        responses = [
            ApiResponse(responseCode = "200", description = "Image created successfully",
                content = [Content(schema = Schema(implementation = ImageDTO::class))]),
            ApiResponse(responseCode = "409", description = "Image creation conflict")
        ]
    )
    @PostMapping("", consumes = ["application/json"], produces = ["application/json"])
    fun createImage(
        @Parameter(description = "Image data to create")
        @Valid @RequestBody image: CreateImageDTO
    ): ResponseEntity<ImageDTO>

    @Operation(
        summary = "Get image by ID",
        operationId = "getImageById",
        description = "Retrieves an image by its unique ID",
        responses = [
            ApiResponse(responseCode = "200", description = "Image retrieved successfully",
                content = [Content(schema = Schema(implementation = ImageDTO::class))]),
            ApiResponse(responseCode = "404", description = "Image not found")
        ]
    )
    @GetMapping("/{id}", produces = ["application/json"])
    @CanReadImage
    fun getImageById(
        @Parameter(description = "ID of the image to retrieve", required = true)
        @Min(1) @PathVariable("id") id: Long
    ): ResponseEntity<ImageDTO>


    //TODO: if you receive the image why do you need the id? Shouldn't we add a Username to the request ?
    @Operation(
        summary = "Update an existing image",
        operationId = "updateImage",
        description = "Updates an existing image by ID",
        responses = [
            ApiResponse(responseCode = "200", description = "Image updated successfully",
                content = [Content(schema = Schema(implementation = ImageDTO::class))]),
            ApiResponse(responseCode = "404", description = "Image not found"),
            ApiResponse(responseCode = "400", description = "Invalid request data")
        ]
    )
    @PutMapping("/{id}", consumes = ["application/json"], produces = ["application/json"])
    @CanCRUDImage
    fun updateImage(
        @Parameter(description = "ID of the image to update", required = true)
        @Min(1) @PathVariable("id") id: Long,

        @Parameter(description = "Updated image data", required = true)
        @Valid @RequestBody image: ImageDTO
    ): ResponseEntity<ImageDTO>

    @Operation(
        summary = "Delete an image",
        operationId = "deleteImage",
        description = "Deletes an image by its unique ID",
        responses = [
            ApiResponse(responseCode = "204", description = "Image deleted successfully"),
            ApiResponse(responseCode = "404", description = "Image not found")
        ]
    )
    @DeleteMapping("/{id}")
    @CanCRUDImage
    fun deleteImage(
        @Parameter(description = "ID of the image to delete", required = true)
        @Min(1) @PathVariable("id") id: Long
    ) : ResponseEntity<ImageDTO>

    @Operation(
        summary = "Get images uploaded by a user",
        operationId = "getUsersImages",
        description = "Retrieves a list of images uploaded by a user",
        responses = [
            ApiResponse(responseCode = "200", description = "List of images retrieved successfully",
                content = [Content(schema = Schema(implementation = ImageDTO::class))]),
            ApiResponse(responseCode = "404", description = "User not found")
        ]
    )
    @GetMapping("/user/{username}", produces = ["application/json"])
    @CanReadImages
    fun getUserImages(
        @Parameter(description = "Username of the user to retrieve images for", required = true)
        @PathVariable("username") username: String,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int):
            ResponseEntity<Page<ImageDTO>>


    @Operation(
        summary = "Get a list of images by their IDs",
        operationId = "getImagesByIds",
        description = "Retrieves a list of images by their unique IDs",
        responses = [
            ApiResponse(responseCode = "200", description = "List of images retrieved successfully",
                content = [Content(schema = Schema(implementation = ImageDTO::class))]),
            ApiResponse(responseCode = "404", description = "Some images not found"),
            ApiResponse(responseCode = "400", description = "Invalid request data")
        ]
    )
    @GetMapping("/ids", consumes = ["application/json"], produces = ["application/json"])
    fun getImagesByIds(
        @Parameter(description = "List of image IDs to retrieve", required = true)
        @RequestBody imageIds: List<Long>
    ): ResponseEntity<List<ImageDTO>>
}
