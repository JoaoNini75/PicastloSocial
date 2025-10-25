package com.example.imageservice.application

import com.example.imageservice.data.CreateImageDTO
import com.example.imageservice.data.ImageDAO
import com.example.imageservice.data.ImageDTO
import com.example.imageservice.data.ImageRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class ImageApplication(val imageRepo: ImageRepository) {

    fun createImage(image: CreateImageDTO): ImageDTO =
        imageRepo.save(ImageDAO(9999, image.imageData, image.uploadedBy, OffsetDateTime.now()))
            .let { ImageDTO(it.id, it.imageData, it.uploadedBy, it.createdAt) }

    fun deleteImage(id: Long) : ImageDTO {
        val imageDAO = imageRepo.findById(id).orElseThrow { IllegalArgumentException("Image not found") }
        imageRepo.deleteById(id)
        return ImageDTO(imageDAO.id, imageDAO.imageData, imageDAO.uploadedBy, imageDAO.createdAt)
    }

    fun updateImage(id: Long, image: ImageDTO): ImageDTO {
        val imageDAO = imageRepo.findById(id).orElseThrow { IllegalArgumentException("Image not found") }
        val updatedImage = ImageDAO(id, image.imageData, image.uploadedBy, imageDAO.createdAt)
        return ImageDTO(imageRepo.save(updatedImage).id, updatedImage.imageData, updatedImage.uploadedBy, updatedImage.createdAt)
    }

    fun getImages(page: Int, size: Int): Page<ImageDTO> = imageRepo.findAll(PageRequest.of(page, size)).map { ImageDTO(it.id, it.imageData, it.uploadedBy, it.createdAt) }

    fun imagesOfUser(username: String, page: Int, size: Int): Page<ImageDTO> =
        imageRepo.findByUploadedBy(username, PageRequest.of(page, size)).map { ImageDTO(it.id, it.imageData, it.uploadedBy, it.createdAt) }

    fun getImage(id: Long): ImageDTO {
        return imageRepo.findById(id).orElseThrow { IllegalArgumentException("Image not found") }.let { ImageDTO(it.id, it.imageData, it.uploadedBy, it.createdAt) }
    }

    fun getImagesByIds(imageIds: List<Long>): List<ImageDTO>? {
        return imageRepo.findAllById(imageIds).map { ImageDTO(it.id, it.imageData, it.uploadedBy, it.createdAt) }
    }
}