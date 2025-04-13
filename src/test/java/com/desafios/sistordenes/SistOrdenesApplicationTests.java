package com.desafios.sistordenes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // 👉 Esto activa application-test.yml
class SistOrdenesApplicationTests {

    @Test
    void contextLoads() {
    }
}
