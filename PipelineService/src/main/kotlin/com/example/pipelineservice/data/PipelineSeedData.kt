package com.example.pipelineservice.data

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class PipelineSeedData(val pipelineRepo: PipelineRepository) : CommandLineRunner {

    private var PIPELINE = ByteArray(0)
    override fun run(vararg args: String?) {
        pipelineRepo.save(PipelineDAO(1, PIPELINE, "MikeWazowski", OffsetDateTime.now()))
        pipelineRepo.save(PipelineDAO(2, PIPELINE, "JohnHaskell", OffsetDateTime.now()))
        pipelineRepo.save(PipelineDAO(3, PIPELINE, "JamesSullivan", OffsetDateTime.now()))
        pipelineRepo.save(PipelineDAO(4, PIPELINE, "MikeWazowski", OffsetDateTime.now()))
        pipelineRepo.save(PipelineDAO(5, PIPELINE, "JaneKotlin", OffsetDateTime.now()))
    }
}