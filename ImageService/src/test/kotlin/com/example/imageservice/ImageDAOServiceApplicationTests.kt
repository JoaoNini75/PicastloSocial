package com.example.imageservice

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import kotlin.test.assertNotNull

@SpringBootTest
class ImageDAOServiceApplicationTests {

    @Autowired
    lateinit var ctx: ApplicationContext

    @Test
    fun contextLoads() {
        assertNotNull(ctx)
    }

}
