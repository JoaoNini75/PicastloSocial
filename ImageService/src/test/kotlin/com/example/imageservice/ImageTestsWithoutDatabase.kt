package com.example.imageservice

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.example.imageservice.data.ImageDAO
import com.example.imageservice.data.ImageRepository
import com.example.imageservice.data.ImageDTO
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
class ImageUnitTestsWithoutDatabase {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var imageRepository: ImageRepository

    companion object {
        val objectMapper = jacksonObjectMapper()
        val mockImageList = listOf(
            ImageDAO(1, ByteArray(0), 2, OffsetDateTime.now()),
            ImageDAO(2, ByteArray(0), 4, OffsetDateTime.now()),
            ImageDAO(3, ByteArray(0), 6, OffsetDateTime.now())
        )

        val expectedImageDTOs = mockImageList.map { ImageDTO(it.id, it.imageData, it.uploadedBy, it.createdAt) }
    }


    @Test
    fun `Just to see if the database can be faked or mocked`() {
        Mockito.`when`(imageRepository.findAll()).thenReturn(mockImageList)

        val body = mvc.perform(get("/image/"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val images = objectMapper.readValue(body, object : TypeReference<List<ImageDTO>>() {})

        assertEquals(3, images.size)
    }

    @Test
    fun `testing the getting of an image`() {
        val imageId = 1L
        Mockito.`when`(imageRepository.findById(imageId)).thenReturn(Optional.of(mockImageList[0]))

        val body = mvc.perform(get("/image/$imageId"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        val image = objectMapper.readValue(body, ImageDTO::class.java)

        assertEquals(expectedImageDTOs[0], image)
    }

    @Test
    fun `should delete an image`() {
        val imageId = 1L
        Mockito.`when`(imageRepository.findById(imageId)).thenReturn(Optional.of(mockImageList[0]))
        mvc.perform(delete("/image/$imageId"))
            .andExpect(status().isOk)
    }

}
