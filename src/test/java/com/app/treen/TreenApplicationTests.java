package com.app.treen;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {RedisAutoConfiguration.class})
class TreenApplicationTests {

	@Test
	void contextLoads() {
	}

}
