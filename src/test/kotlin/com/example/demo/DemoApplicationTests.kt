package com.example.demo

import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@Test
	fun contextLoads() {
	}

}
