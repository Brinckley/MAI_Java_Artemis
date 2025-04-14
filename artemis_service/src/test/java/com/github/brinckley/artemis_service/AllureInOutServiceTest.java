package com.github.brinckley.artemis_service;

import com.github.brinckley.artemis_service.model.InputMessage;
import com.github.brinckley.artemis_service.repository.MessageRepository;
import com.github.brinckley.artemis_service.repository.impl.InMemoryMessageRepository;
import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AllureInOutServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryMessageRepository messageRepository;

    @Test
    @SneakyThrows
    public void testMessageSendSuccess() {
        // Given
        String messageJson = """
            {
              "message": "Hello from unit-test!"
            }
        """;

        String producerExpectedMessage = "Successfully sent the message to Artemis";
        String consumerExpectedMessage = "Hello from unit-test!";

        // When
        Allure.step("POST /artemis/produce");
        mockMvc.perform(post("/artemis/produce")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(messageJson))
                .andExpect(status().isOk())
                .andExpect(content().string(producerExpectedMessage));

        Thread.sleep(10000); // waiting to receive from the artemis

        Allure.step("GET /artemis/consume");
        mockMvc.perform(get("/artemis/consume"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(consumerExpectedMessage));
    }
}
