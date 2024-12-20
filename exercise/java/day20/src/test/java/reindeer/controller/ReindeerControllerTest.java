package reindeer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import reindeer.model.ReindeerColor;
import reindeer.model.ReindeerToCreateRequest;
import reindeer.service.ReindeerService;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReindeerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetReindeer() throws Exception {
        mockMvc.perform(get("/reindeer/40f9d24d-d3e0-4596-adc5-b4936ff84b19"))
                .andExpect(status().isOk());
    }

    @Test
    void notFoundForNotExistingReindeer() throws Exception {
        var nonExistingReindeer = UUID.randomUUID();
        mockMvc.perform(get("/reindeer/" + nonExistingReindeer))
                .andExpect(status().isNotFound());
    }

    @Test
    void conflictWhenTryingToCreateExistingOne() throws Exception {
        var request = objectMapper.writeValueAsString(
                new ReindeerToCreateRequest("Petar", ReindeerColor.PURPLE)
        );

        mockMvc.perform(post("/reindeer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isConflict());
    }
}
