package com.example.pipelineservice

import com.example.pipelineservice.data.CreatePipeDTO
import com.example.pipelineservice.data.PipelineDTO
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class PipelineIntegrationTestsWithDatabase {

    @Autowired
    lateinit var mvc: MockMvc

    companion object {
        val objectMapper = jacksonObjectMapper()
    }

    @Test
    fun `should return 200 for existing endpoint`() {
        mvc.perform(get("/pipeline"))
            .andExpect(status().isOk)
    }

    @Test
    fun `should return 404 for non-existing endpoint`() {
        mvc.perform(get("/pipelines"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should create a pipeline`() {
        val pipeline1 = CreatePipeDTO(ByteArray(0), 2)
        val pipeline2 = CreatePipeDTO(ByteArray(0), 4)
        val response1 = mvc.perform(post("/pipeline")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(pipeline1)))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val createdPipeline1 = objectMapper.readValue(response1, PipelineDTO::class.java)

        assertEquals(createdPipeline1.id, 6)
        assertEquals(createdPipeline1.uploadedBy, pipeline1.uploadedBy)

        val response2 = mvc.perform(post("/pipeline")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(pipeline2)))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val createdPipeline2 = objectMapper.readValue(response2, PipelineDTO::class.java)

        assertEquals(createdPipeline2.id, 7)
        assertEquals(createdPipeline2.uploadedBy, pipeline2.uploadedBy)
    }

    @Test
    fun `should delete a pipeline`() {
        val imageId = 4
        mvc.perform(delete("/pipeline/$imageId"))
            .andExpect(status().isOk)

        val response = mvc.perform(get("/pipeline"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val pipelines = objectMapper.readValue(response, object : TypeReference<List<PipelineDTO>>() {})

        assertEquals(4, pipelines.size)
    }

}