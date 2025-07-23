package com.skypro.StarBank.service;

import com.skypro.StarBank.dto.request.DynamicRuleRequest;
import com.skypro.StarBank.dto.request.QueryRequest;
import com.skypro.StarBank.dto.response.DynamicRuleResponse;
import com.skypro.StarBank.model.DynamicRule;
import com.skypro.StarBank.repository.DynamicRuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamicRuleServiceTest {

    @Mock
    private DynamicRuleRepository ruleRepository;

    @InjectMocks
    private DynamicRuleService dynamicRuleService;

    private DynamicRuleRequest request;

    @BeforeEach
    void setUp() {
        QueryRequest query1 = new QueryRequest();
        query1.setQuery("age > ?");
        query1.setArguments(List.of("30"));
        query1.setNegate(false);

        QueryRequest query2 = new QueryRequest();
        query2.setQuery("country = ?");
        query2.setArguments(List.of("RU"));
        query2.setNegate(true);

        request = new DynamicRuleRequest();
        request.setName("Test Rule");
        request.setText("Rule description");
        request.setRule(List.of(query1, query2));
    }

    @Test
    void createRule_shouldSaveRuleCorrectly() {
        // Выполнение
        DynamicRuleResponse response = dynamicRuleService.createRule(request);

        // Проверка сохранения
        ArgumentCaptor<DynamicRule> captor = ArgumentCaptor.forClass(DynamicRule.class);
        verify(ruleRepository, times(1)).save(captor.capture());
        DynamicRule savedRule = captor.getValue();

        assertEquals("Test Rule", savedRule.getName());
        assertEquals("Rule description", savedRule.getText());
        assertEquals(2, savedRule.getRule().size());
        assertEquals("age > ?", savedRule.getRule().get(0).getQuery());
        assertEquals(List.of("30"), savedRule.getRule().get(0).getArguments());
        assertFalse(savedRule.getRule().get(0).isNegate());
        assertTrue(savedRule.getRule().get(1).isNegate());

        // Проверка ответа
        assertEquals("Test Rule", response.getName());
        assertEquals("Rule description", response.getText());
        assertEquals(2, response.getRule().size());
    }

    @Test
    void createRule_shouldHandleEmptyQueries() {
        // Подготовка
        DynamicRuleRequest emptyRequest = new DynamicRuleRequest();
        emptyRequest.setName("Empty Rule");
        emptyRequest.setText("No queries");
        emptyRequest.setRule(List.of());

        // Выполнение
        DynamicRuleResponse response = dynamicRuleService.createRule(emptyRequest);

        // Проверка
        ArgumentCaptor<DynamicRule> captor = ArgumentCaptor.forClass(DynamicRule.class);
        verify(ruleRepository, times(1)).save(captor.capture());
        DynamicRule savedRule = captor.getValue();

        assertEquals("Empty Rule", savedRule.getName());
        assertEquals("No queries", savedRule.getText());
        assertTrue(savedRule.getRule().isEmpty());

        assertEquals("Empty Rule", response.getName());
        assertEquals("No queries", response.getText());
        assertTrue(response.getRule().isEmpty());
    }
}
