package com.skypro.StarBank.controller;

import com.skypro.StarBank.dto.response.RecommendationDTO;
import com.skypro.StarBank.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService recommendationService;

    @Test
    void getRecommendations_shouldReturn200AndValidResponse() throws Exception {
        // Подготовка данных
        String id = "user-123";
        List<RecommendationDTO> recommendations = List.of(
            new RecommendationDTO("Description A", "Product A","1"),
            new RecommendationDTO("Description B", "Product B","2")
        );

        when(recommendationService.getRecommendations(id))
            .thenReturn(recommendations);

        mockMvc.perform(get("/recommendation/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.recommendations.length()").value(2))
            .andExpect(jsonPath("$.recommendations[0].name").value("Product A"))
            .andExpect(jsonPath("$.recommendations[1].name").value("Product B"));
    }

    @Test
    void getRecommendations_whenEmptyList_shouldReturnEmptyArray() throws Exception {
        String id = "user-empty";
        when(recommendationService.getRecommendations(id))
            .thenReturn(List.of());

        mockMvc.perform(get("/recommendation/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.recommendations").isEmpty());
    }
}