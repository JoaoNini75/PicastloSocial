package com.example.pipelineservice.presentation

import com.example.pipelineservice.application.PipelineApplication
import com.example.pipelineservice.data.CreatePipeDTO
import com.example.pipelineservice.data.PipelineDAO
import com.example.pipelineservice.data.PipelineDTO
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
class PipelineController(val app: PipelineApplication) : PipelineAPI {
    override fun hello(): String {
        return "Hello from Pipeline Service"
    }

    override fun getPipelines(page: Int, size: Int): ResponseEntity<Page<PipelineDTO>> {
        return ResponseEntity.ok(app.getPipelines(page, size))
    }

    override fun createPipeline(pipeline: CreatePipeDTO): ResponseEntity<PipelineDTO> {

        return ResponseEntity.ok(app.createPipeline(pipeline))
    }

    override fun getPipelineById(id: Long): ResponseEntity<PipelineDTO> {
        return ResponseEntity.ok(app.getPipeline(id))
    }

    override fun updatePipelineById(id: Long, pipeline: PipelineDTO): ResponseEntity<PipelineDTO> {
        return ResponseEntity.ok(app.updatePipeline(id, pipeline))
    }

    override fun deletePipeline(id: Long) : ResponseEntity<PipelineDTO> {
        return ResponseEntity.ok(app.deletePipeline(id))
    }

    override fun getPipelinesByUser(username: String, page: Int, size: Int): ResponseEntity<Page<PipelineDTO>> {
        return ResponseEntity.ok(app.pipelinesOfUser(username, page, size))
    }

    override fun getPipelinesByIds(pipelineIds: List<Long>): ResponseEntity<List<PipelineDTO>> {
        return ResponseEntity.ok(app.getPipelinesByIds(pipelineIds))
    }


}
