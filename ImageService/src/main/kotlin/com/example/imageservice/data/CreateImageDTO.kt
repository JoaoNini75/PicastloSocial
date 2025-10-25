package com.example.imageservice.data

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.Lob

class CreateImageDTO(
    @Lob
    @Basic(fetch = FetchType.LAZY) // Lazily fetch large data like blobs
    @Column(name = "image_data", nullable = false, columnDefinition = "BLOB") //The column is explicitly marked as BLOB to ensure proper storage of binary data.
    @Schema(required = true, description = "Raw image data in bytes")
    val imageData: ByteArray,


    @Column(name = "uploaded_by", nullable = false)
    @Schema(example= "FernandoMendes", required = true, description = "User who uploaded the image")
    @get:JsonProperty("uploaded_by", required = true)
    val uploadedBy: String
) {
    constructor() : this(ByteArray(0), "")
}