package com.example.pipelineservice


import com.example.pipelineservice.data.PipelineDAO
import com.example.pipelineservice.data.PipelineDTO
import com.example.pipelineservice.data.PipelineRepository
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.OffsetDateTime
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class PipelineTestsWithoutDatabase {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var pipelineRepository: PipelineRepository

    companion object {
        val objectMapper = jacksonObjectMapper()
        val mockPipelineList = listOf(
            PipelineDAO(1, ByteArray(0), 2, OffsetDateTime.now()),
            PipelineDAO(2, ByteArray(0), 4, OffsetDateTime.now()),
            PipelineDAO(3, ByteArray(0), 6, OffsetDateTime.now())
        )

        val expectedPipelineDTOs = mockPipelineList.map { PipelineDTO(it.id, it.pipelineData, it.uploadedBy, it.createdAt) }
    }

    @Test
    fun `Just to see if the database can be faked or mocked`() {
        Mockito.`when`(pipelineRepository.findAll()).thenReturn(mockPipelineList)

        val body = mvc.perform(get("/pipeline"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val pipelines = objectMapper.readValue(body, object : TypeReference<List<PipelineDTO>>() {})

        assertEquals(3, pipelines.size)
    }

    @Test
    fun `testing the getting of a pipeline`() {
        Mockito.`when`(pipelineRepository.findById(1)).thenReturn(Optional.of(mockPipelineList[0]))
        val pipelineId = 1L

        val body = mvc.perform(get("/pipeline/$pipelineId"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val pipeline = objectMapper.readValue(body, PipelineDTO::class.java)

        assertEquals(expectedPipelineDTOs[0], pipeline)
    }

    @Test
    fun `testing the deletion of a pipeline`() {
        Mockito.`when`(pipelineRepository.findById(1)).thenReturn(Optional.of(mockPipelineList[0]))
        val pipelineId = 1L

        mvc.perform(delete("/pipeline/$pipelineId"))
            .andExpect(status().isOk)
    }
}