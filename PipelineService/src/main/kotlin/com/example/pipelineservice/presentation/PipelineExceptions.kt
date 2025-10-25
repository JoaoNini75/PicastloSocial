package com.example.pipelineservice.presentation

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class PipelineMismatchException() : RuntimeException("Pipeline id mismatch")

@ResponseStatus(HttpStatus.NOT_FOUND)
class PipelineNotFoundException() : RuntimeException("Pipeline not found")

@ResponseStatus(HttpStatus.CONFLICT)
class PipelineAlreadyExistsException() : RuntimeException("Pipeline already exists")

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserDoNotHavePipelinesException() : RuntimeException("User do not have pipelines")