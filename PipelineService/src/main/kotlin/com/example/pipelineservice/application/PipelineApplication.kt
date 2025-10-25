package com.example.pipelineservice.application

import com.example.pipelineservice.data.CreatePipeDTO
import com.example.pipelineservice.data.PipelineDAO
import com.example.pipelineservice.data.PipelineDTO
import com.example.pipelineservice.data.PipelineRepository
import com.example.pipelineservice.presentation.PipelineNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class PipelineApplication(val pipelineRepo: PipelineRepository) {

    fun createPipeline(pipeline : CreatePipeDTO): PipelineDTO =
        pipelineRepo.save(PipelineDAO(99, pipeline.pipelineData, pipeline.uploadedBy, OffsetDateTime.now()))
            .let { PipelineDTO(it.id, it.pipelineData, it.uploadedBy, it.createdAt) }

    fun deletePipeline(id: Long) : PipelineDTO {
        // Check if pipeline exists
        val pipelineDAO = pipelineRepo.findById(id).orElseThrow { PipelineNotFoundException() }
        pipelineRepo.deleteById(id)
        return PipelineDTO(pipelineDAO.id, pipelineDAO.pipelineData, pipelineDAO.uploadedBy, pipelineDAO.createdAt)
    }

    fun updatePipeline(id: Long, pipeline: PipelineDTO): PipelineDTO {
        // Check if pipeline exists
        val pipelineDAO = pipelineRepo.findById(id).orElseThrow { PipelineNotFoundException() }
        val updatedPipeline = PipelineDAO(id, pipeline.pipelineData, pipeline.uploadedBy, pipelineDAO.createdAt)
        return PipelineDTO(pipelineRepo.save(updatedPipeline).id, updatedPipeline.pipelineData, updatedPipeline.uploadedBy, updatedPipeline.createdAt)
    }

    fun getPipelines(page: Int, size: Int): Page<PipelineDTO> {
        return pipelineRepo.findAll(PageRequest.of(page, size)).map { PipelineDTO(it.id, it.pipelineData, it.uploadedBy, it.createdAt) }
    }

    fun pipelinesOfUser(userId: String, page: Int, size: Int): Page<PipelineDTO> {
        return pipelineRepo.findByUploadedBy(userId, PageRequest.of(page, size)).map { PipelineDTO(it.id, it.pipelineData, it.uploadedBy, it.createdAt) }
    }

    fun getPipeline(id: Long): PipelineDTO {
        return pipelineRepo.findById(id).orElseThrow { PipelineNotFoundException() }.let { PipelineDTO(it.id, it.pipelineData, it.uploadedBy, it.createdAt) }
    }

    fun getPipelinesByIds(pipelineIds: List<Long>): List<PipelineDTO>? {
        return pipelineRepo.findAllById(pipelineIds).map { PipelineDTO(it.id, it.pipelineData, it.uploadedBy, it.createdAt) }
    }
}