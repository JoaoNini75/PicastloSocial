package com.example.imageservice.data

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.time.OffsetDateTime

@Component
class ImageSeedData(val imageRepository: ImageRepository) : CommandLineRunner {

    private var IMAGE = imageToByteArray("ImageService/src/main/kotlin/com/example/imageservice/data/Rick.jpg")

    private fun imageToByteArray(filePath: String): ByteArray  {
        val imageFile = File(filePath)
        if (!imageFile.exists() || !imageFile.isFile) {
            throw IllegalArgumentException("Invalid file path: $filePath")
        }
        return Files.readAllBytes(imageFile.toPath())
    }

    override fun run(vararg args: String?) {
        imageRepository.save(ImageDAO(1, IMAGE, "JaneKotlin", OffsetDateTime.now()))
        imageRepository.save(ImageDAO(2, IMAGE, "AdrianaAda", OffsetDateTime.now()))
        imageRepository.save(ImageDAO(3, IMAGE, "JohnHaskell", OffsetDateTime.now()))
        imageRepository.save(ImageDAO(4, IMAGE, "JohnHaskell", OffsetDateTime.now()))
        imageRepository.save(ImageDAO(5, IMAGE, "AdrianaAda", OffsetDateTime.now()))

    }

}




