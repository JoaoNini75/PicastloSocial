package com.example.pipelineservice.presentation

import com.example.pipelineservice.data.CreatePipeDTO
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.media.*
import io.swagger.v3.oas.annotations.responses.*
import com.example.pipelineservice.data.PipelineDTO
import com.example.pipelineservice.security.capabilities.CanCRUDPipeline
import com.example.pipelineservice.security.capabilities.CanReadAllPipelines
import com.example.pipelineservice.security.capabilities.CanReadPipeline
import com.example.pipelineservice.security.capabilities.CanReadPipelines
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/pipeline")
interface PipelineAPI {

    @GetMapping("/hello")
    fun hello(): String

    @Operation(
        summary = "Get list of all pipelines",
        operationId = "getPipelines",
        description = "Retrieves a list of all pipelines",
        responses = [
            ApiResponse(responseCode = "200", description = "List of pipelines retrieved successfully",
                content = [Content(mediaType = "application/json")])
        ])
    @GetMapping("", produces = ["application/json"])
    @CanReadAllPipelines
    fun getPipelines(@RequestParam(required = true, defaultValue = "0") page: Int,
                     @RequestParam(required = false, defaultValue = "5") size: Int): ResponseEntity<Page<PipelineDTO>>

    @Operation(
        summary = "Create a new pipeline",
        operationId = "createPipeline",
        description = "Creates a new pipeline in the system",
        responses = [
            ApiResponse(responseCode = "200", description = "Pipeline created successfully",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "409", description = "Pipeline creation conflict")
        ])
    @PostMapping("", consumes = ["application/json"], produces = ["application/json"])
    fun createPipeline(
        @Parameter(description = "Pipeline data to create", required = true)
        @Valid @RequestBody pipeline: CreatePipeDTO
    ): ResponseEntity<PipelineDTO>


    @Operation(
        summary = "Get pipeline by ID",
        operationId = "getPipelineById",
        description = "Retrieves a pipeline by its unique ID",
        responses = [
            ApiResponse(responseCode = "200", description = "Pipeline retrieved successfully",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "404", description = "Pipeline not found")
        ])
    @GetMapping("/{id}", produces = ["application/json"])
    @CanReadPipeline
    fun getPipelineById(
        @Parameter(description = "ID of the pipeline to retrieve", required = true)
        @Min(1) @PathVariable("id") id: Long
    ): ResponseEntity<PipelineDTO>

    @Operation(
        summary = "Update pipeline by ID",
        operationId = "updatePipelineById",
        description = "Updates a pipeline by its unique ID",
        responses = [
            ApiResponse(responseCode = "200", description = "Pipeline updated successfully",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "404", description = "Pipeline not found")
        ])
    @PutMapping("/{id}", consumes = ["application/json"], produces = ["application/json"])
    @CanCRUDPipeline
    fun updatePipelineById(
        @Parameter(description = "ID of the pipeline to update", required = true)
        @Min(1) @PathVariable("id") id: Long,
        @Parameter(description = "Pipeline data to update", required = true)
       @Valid @RequestBody pipeline: PipelineDTO
    ): ResponseEntity<PipelineDTO>

    @Operation(
        summary = "Delete pipeline by ID",
        operationId = "deletePipeline",
        description = "Deletes a pipeline by its unique ID",
        responses = [
            ApiResponse(responseCode = "200", description = "Pipeline deleted successfully",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "404", description = "Pipeline not found")
        ])
    @DeleteMapping("/{id}")
    @CanCRUDPipeline
    fun deletePipeline(
        @Parameter(description = "ID of the pipeline to delete", required = true)
        @Min(1) @PathVariable("id") id: Long
    ) : ResponseEntity<PipelineDTO>

    @Operation(
        summary = "Get pipelines by user ID",
        operationId = "getPipelinesByUser",
        description = "Retrieves a list of pipelines uploaded by a user",
        responses = [
            ApiResponse(responseCode = "200", description = "List of pipelines retrieved successfully",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "404", description = "User not found")
        ])
    @GetMapping("/user/{username}", produces = ["application/json"])
    @CanReadPipelines
    fun getPipelinesByUser(
        @Parameter(description = "Username of the user to retrieve pipelines for", required = true)
        @PathVariable("username") username: String,
        @RequestParam(required = true, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): ResponseEntity<Page<PipelineDTO>>


    @Operation(
        summary = "Get a list of pipelines by their IDs",
        operationId = "getPipelinesByIds",
        description = "Retrieves a list of pipelines by their unique IDs",
        responses = [
            ApiResponse(responseCode = "200", description = "List of pipelines retrieved successfully",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "404", description = "Pipeline not found")
        ])
    @GetMapping("/ids", produces = ["application/json"])
    fun getPipelinesByIds(
        @Parameter(description = "List of pipeline IDs to retrieve", required = true)
        @RequestParam(required = true) pipelineIds: List<Long>
    ): ResponseEntity<List<PipelineDTO>>

}