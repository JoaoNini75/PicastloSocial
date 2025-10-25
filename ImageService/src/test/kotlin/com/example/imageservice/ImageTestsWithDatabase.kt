package com.example.imageservice

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.example.imageservice.data.ImageDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.OffsetDateTime


@SpringBootTest
@AutoConfigureMockMvc
class ImageIntegrationTestsWithDatabase {

    @Autowired
    lateinit var mvc: MockMvc

    companion object {
        val objectMapper = jacksonObjectMapper()
    }

    @Test
    fun `should return 200 for existing endpoint`() {
        mvc.perform(get("/image"))
            .andExpect(status().isOk)
    }

    @Test
    fun `should return 404 for non-existing endpoint`() {
        mvc.perform(get("/images"))
            .andExpect(status().isNotFound)
    }


    @Test
    fun `should create an image`() {
        val image1 = ImageDTO(999, ByteArray(0), 2, OffsetDateTime.now())
        val image2 = ImageDTO(999, ByteArray(0), 4, OffsetDateTime.now())
        val response1 = mvc.perform(post("/image")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(image1)))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val createdImage1 = objectMapper.readValue(response1, ImageDTO::class.java)

        assertEquals(createdImage1.id, 6)
        assertEquals(createdImage1.uploadedBy, 6)

        val response2 = mvc.perform(post("/image")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(image2)))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString
        val createdImage2 = objectMapper.readValue(response2, ImageDTO::class.java)

        assertEquals(createdImage2.id, 7)
        assertEquals(createdImage2.uploadedBy, 20)
    }


    @Test
    fun `should return list of images from database`() {
        val response = mvc.perform(get("/image"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val images = objectMapper.readValue(response, object : TypeReference<List<ImageDTO>>() {})

        assertEquals(7, images.size)
    }


    @Test
    fun `should delete an image`() {
        val imageId = 6

        mvc.perform(delete("/image/$imageId"))
            .andExpect(status().isOk)

        val response = mvc.perform(get("/image"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val images = objectMapper.readValue(response, object : TypeReference<List<ImageDTO>>() {})

        assertEquals(6, images.size)
    }


}
