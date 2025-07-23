package com.skypro.StarBank.controller;

import com.skypro.StarBank.service.DynamicRuleStatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DynamicRuleStatController.class)
class DynamicRuleStatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DynamicRuleStatService service;

    @Test
    @DisplayName("GET /rules/stats - должен вернуть корректный JSON со статистикой")
    void testGetAllStats() throws Exception {
        List<Map<String, Object>> mockStats = List.of(
                Map.of("RULE_ID", 101, "COUNT", 5),
                Map.of("RULE_ID", 202, "COUNT", 12)
        );

        when(service.getStats()).thenReturn(mockStats);
        
        mockMvc.perform(get("/rules/stats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.stats[0].rule_id").value("101"))
                .andExpect(jsonPath("$.stats[0].count").value("5"))
                .andExpect(jsonPath("$.stats[1].rule_id").value("202"))
                .andExpect(jsonPath("$.stats[1].count").value("12"));
    }

    @Test
    @DisplayName("POST /rules/stats/{ruleId} - должен вызвать deleteStat()")
    void testDeleteStat() throws Exception {
        String ruleId = "123";
        doNothing().when(service).deleteStat(ruleId);

        mockMvc.perform(post("/rules/stats/{ruleId}", ruleId))
                .andExpect(status().isOk());

        Mockito.verify(service).deleteStat(ruleId);
    }
}
