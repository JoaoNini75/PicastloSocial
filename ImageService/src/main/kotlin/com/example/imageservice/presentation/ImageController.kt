package com.example.imageservice.presentation

import com.example.imageservice.application.ImageApplication
import com.example.imageservice.data.CreateImageDTO
import com.example.imageservice.data.ImageDAO
import com.example.imageservice.data.ImageDTO
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime


@RestController
class ImageController(val app: ImageApplication) : ImageAPI {
    override fun hello(): String {
        return "Hello from Image Service"}

    override fun getImages(page: Int, size: Int): ResponseEntity<Page<ImageDTO>> {
        return ResponseEntity.ok(app.getImages(page, size))
    }

    override fun createImage(image: CreateImageDTO): ResponseEntity<ImageDTO> {
        return ResponseEntity.ok(app.createImage(image))
    }

    override fun getImageById(id: Long): ResponseEntity<ImageDTO> {
        return ResponseEntity.ok(app.getImage(id))
    }

    override fun deleteImage(id: Long): ResponseEntity<ImageDTO> {
        return ResponseEntity.ok(app.deleteImage(id))
    }

    override fun updateImage(id: Long, image: ImageDTO): ResponseEntity<ImageDTO> {
        return ResponseEntity.ok(app.updateImage(id, image))
    }

    override fun getUserImages(username: String, page: Int, size: Int): ResponseEntity<Page<ImageDTO>> {
        return ResponseEntity.ok(app.imagesOfUser(username, page, size))
    }

    override fun getImagesByIds(imageIds: List<Long>): ResponseEntity<List<ImageDTO>> {
        return ResponseEntity.ok(app.getImagesByIds(imageIds))
    }


}