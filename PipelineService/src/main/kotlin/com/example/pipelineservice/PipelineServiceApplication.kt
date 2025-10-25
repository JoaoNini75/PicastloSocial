package com.example.pipelineservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
class PipelineServiceApplication

fun main(args: Array<String>) {
    runApplication<PipelineServiceApplication>(*args)
}
