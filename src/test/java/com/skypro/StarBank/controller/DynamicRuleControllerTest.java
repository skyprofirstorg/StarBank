package com.skypro.StarBank.controller;

import com.skypro.StarBank.dto.request.DynamicRuleRequest;
import com.skypro.StarBank.dto.request.QueryRequest;
import com.skypro.StarBank.dto.response.DynamicRuleResponse;
import com.skypro.StarBank.model.DynamicRule;
import com.skypro.StarBank.repository.DynamicRuleRepository;
import com.skypro.StarBank.service.DynamicRuleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DynamicRuleControllerTest {

    private DynamicRuleRepository ruleRepository;
    private DynamicRuleService ruleService;
    private DynamicRuleController controller;

    @BeforeEach
    public void setUp() {
        ruleRepository = mock(DynamicRuleRepository.class);
        ruleService = mock(DynamicRuleService.class);
        controller = new DynamicRuleController(ruleRepository, ruleService);
    }

    @Test
    public void testCreateRule_shouldReturnCreatedRuleResponse() {
        
    DynamicRuleRequest request = new DynamicRuleRequest();

        DynamicRuleResponse response = new DynamicRuleResponse(
                "Test Rule",
                "123",
                "This is a test rule",
                List.of(new QueryRequest())
        );

        when(ruleService.createRule(request)).thenReturn(response);

        DynamicRuleResponse result = controller.createRule(request);

        assertNotNull(result);
        assertEquals(response, result);
        verify(ruleService, times(1)).createRule(request);
    }

    @Test
    public void testGetAllRules_shouldReturnMapWithData() {
        List<DynamicRule> mockList = List.of(new DynamicRule(/* параметры */), new DynamicRule(/* параметры */));
        when(ruleRepository.findAll()).thenReturn(mockList);

        Map<String, List<DynamicRule>> result = controller.getAllRules();

        assertNotNull(result);
        assertTrue(result.containsKey("data"));
        assertEquals(mockList, result.get("data"));
        verify(ruleRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteRule_shouldCallRepositoryDeleteById() {
        String productId = "123";

        controller.deleteRule(productId);
        verify(ruleRepository, times(1)).deleteById(productId);
    }
}
