package com.skypro.StarBank.service;

import com.skypro.StarBank.dto.response.RecommendationDTO;
import com.skypro.StarBank.repository.DynamicRuleRepository;
import com.skypro.StarBank.model.DynamicRule;
import com.skypro.StarBank.rules.DynamicRuleChecker;
import com.skypro.StarBank.rules.RecommendationRuleSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamicRecommendationServiceTest {

    @Mock
    private List<RecommendationRuleSet> staticRules;

    @Mock
    private DynamicRuleRepository dynamicRuleRepository;

    @Mock
    private DynamicRuleChecker dynamicRuleChecker;

    @InjectMocks
    private DynamicRecommendationService service;

    @Test
    void getRecommendations_whenNoRules_shouldReturnEmptyList() {
        // Подготовка
        String userId = "user-123";
        when(staticRules.stream()).thenReturn(List.<RecommendationRuleSet>of().stream());
        when(dynamicRuleRepository.findAll()).thenReturn(List.of());

        // Выполнение
        List<RecommendationDTO> result = service.getRecommendations(userId);

        // Проверка
        assertTrue(result.isEmpty());
        verify(staticRules, times(1)).stream();
        verify(dynamicRuleRepository, times(1)).findAll();
    }

    @Test
    void getRecommendations_whenOnlyStaticRules_shouldReturnStaticRecs() {
        // Подготовка
        String userId = "user-static";
        RecommendationRuleSet rule1 = mock(RecommendationRuleSet.class);
        RecommendationRuleSet rule2 = mock(RecommendationRuleSet.class);
        
        when(staticRules.stream()).thenReturn(List.of(rule1, rule2).stream());
        when(rule1.check(eq(userId))).thenReturn(Optional.of(new RecommendationDTO("s1", "Static 1", "Desc 1")));
        when(rule2.check(eq(userId))).thenReturn(Optional.empty());
        when(dynamicRuleRepository.findAll()).thenReturn(List.of());

        // Выполнение
        List<RecommendationDTO> result = service.getRecommendations(userId);

        // Проверка
        assertEquals(1, result.size());
        assertEquals("Static 1", result.get(0).getName());
        verify(rule1).check(userId);
        verify(rule2).check(userId);
    }

    @Test
    void getRecommendations_whenOnlyDynamicRules_shouldReturnDynamicRecs() {
        // Подготовка
        String userId = "user-dynamic";
        DynamicRule rule1 = new DynamicRule();
        rule1.setName("Dynamic 1");
        rule1.setText("Text 1");
        DynamicRule rule2 = new DynamicRule();
        rule2.setName("Dynamic 2");
        rule2.setText("Text 2");
        
        when(staticRules.stream()).thenReturn(List.<RecommendationRuleSet>of().stream());
        when(dynamicRuleRepository.findAll()).thenReturn(List.of(rule1, rule2));
        when(dynamicRuleChecker.checkRule(eq(userId), eq(rule1))).thenReturn(true);
        when(dynamicRuleChecker.checkRule(eq(userId), eq(rule2))).thenReturn(false);

        // Выполнение
        List<RecommendationDTO> result = service.getRecommendations(userId);

        // Проверка
        assertEquals(1, result.size());
        assertEquals("Dynamic 1", result.get(0).getName());
        assertEquals("Text 1", result.get(0).getText());
        verify(dynamicRuleChecker).checkRule(userId, rule1);
        verify(dynamicRuleChecker).checkRule(userId, rule2);
    }

    @Test
    void getRecommendations_whenMixedRules_shouldCombineResults() {
        // Подготовка
        String userId = "userId";
        
        // Статические правила
        RecommendationRuleSet staticRule = mock(RecommendationRuleSet.class);
        when(staticRules.stream()).thenReturn(List.of(staticRule).stream());
        when(staticRule.check(eq(userId))).thenReturn(Optional.of(new RecommendationDTO("s1", "Static", "Static Desc")));
        
        // Динамические правила
        DynamicRule dynamicRule = new DynamicRule();
        dynamicRule.setName("Dynamic");
        when(dynamicRuleRepository.findAll()).thenReturn(List.of(dynamicRule));
        when(dynamicRuleChecker.checkRule(eq(userId), eq(dynamicRule))).thenReturn(true);

        // Выполнение
        List<RecommendationDTO> result = service.getRecommendations(userId);

        // Проверка
        assertEquals(2, result.size());
        assertEquals("Static", result.get(0).getName());
        assertEquals("Dynamic", result.get(1).getName());
    }

    @Test
    void getRecommendations_shouldFilterInvalidRules() {
        // Подготовка
        String userId = "user-filter";
        
        // Статические: 1 валидное, 2 невалидных
        RecommendationRuleSet validStatic = mock(RecommendationRuleSet.class);
        RecommendationRuleSet invalidStatic1 = mock(RecommendationRuleSet.class);
        RecommendationRuleSet invalidStatic2 = mock(RecommendationRuleSet.class);
        
        when(staticRules.stream()).thenReturn(List.of(validStatic, invalidStatic1, invalidStatic2).stream());
        when(validStatic.check(eq(userId))).thenReturn(Optional.of(new RecommendationDTO("valid", "Valid Static", "Desc")));
        when(invalidStatic1.check(eq(userId))).thenReturn(Optional.empty());
        when(invalidStatic2.check(eq(userId))).thenReturn(Optional.empty());
        
        // Динамические: 1 валидное, 1 невалидное
        DynamicRule validDynamic = new DynamicRule();
        validDynamic.setName("Valid Dynamic");
        validDynamic.setText("Valid Dynamic");
        DynamicRule invalidDynamic = new DynamicRule();
        invalidDynamic.setName("nonName");
        invalidDynamic.setText("Valid Dynamic");
        
        when(dynamicRuleRepository.findAll()).thenReturn(List.of(validDynamic, invalidDynamic));
        when(dynamicRuleChecker.checkRule(eq(userId), eq(validDynamic))).thenReturn(true);
        when(dynamicRuleChecker.checkRule(eq(userId), eq(invalidDynamic))).thenReturn(false);

        // Выполнение
        List<RecommendationDTO> result = service.getRecommendations(userId);

        // Проверка
        assertEquals(2, result.size()); 
        assertTrue(result.stream().anyMatch(r -> r.getName().equals("Valid Static")));
        assertTrue(result.stream().anyMatch(r -> r.getName().equals("Valid Dynamic")));
    }
}