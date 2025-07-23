package com.skypro.StarBank.service;

import com.skypro.StarBank.dto.response.RecommendationDTO;
import com.skypro.StarBank.repository.RecommendationRepository;
import com.skypro.StarBank.rules.RecommendationRuleSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private RecommendationRepository repository; // Не используется, но нужен для конструктора

    @Mock
    private RecommendationRuleSet rule1;

    @Mock
    private RecommendationRuleSet rule2;

    @InjectMocks
    private RecommendationService service;

    @Test
    void getRecommendations_whenNoRules_shouldReturnEmptyList() {
        // Подготовка
        RecommendationService emptyService = new RecommendationService(List.of(), repository);

        // Выполнение
        List<RecommendationDTO> result = emptyService.getRecommendations("user-1");

        // Проверка
        assertEquals(0, result.size());
    }

    @Test
    void getRecommendations_whenAllRulesFail_shouldReturnEmptyList() {
        // Подготовка
        when(rule1.check(eq("user-1"))).thenReturn(Optional.empty());
        when(rule2.check(eq("user-1"))).thenReturn(Optional.empty());
        RecommendationService testService = new RecommendationService(List.of(rule1, rule2), repository);

        // Выполнение
        List<RecommendationDTO> result = testService.getRecommendations("user-1");

        // Проверка
        assertEquals(0, result.size());
        verify(rule1).check("user-1");
        verify(rule2).check("user-1");
    }

    @Test
    void getRecommendations_whenSomeRulesPass_shouldReturnOnlyValidRecommendations() {
        // Подготовка
        RecommendationDTO rec1 = new RecommendationDTO("r1", "Card 1", "Best card");
        when(rule1.check(eq("user-1"))).thenReturn(Optional.of(rec1));
        when(rule2.check(eq("user-1"))).thenReturn(Optional.empty());
        RecommendationService testService = new RecommendationService(List.of(rule1, rule2), repository);

        // Выполнение
        List<RecommendationDTO> result = testService.getRecommendations("user-1");

        // Проверка
        assertEquals(1, result.size());
        assertEquals("Card 1", result.get(0).getName());
        verify(rule1).check("user-1");
        verify(rule2).check("user-1");
    }

    @Test
    void getRecommendations_whenMultipleRulesPass_shouldReturnAllValidRecommendations() {
        // Подготовка
        RecommendationDTO rec1 = new RecommendationDTO("r1", "Card 1", "Best card");
        RecommendationDTO rec2 = new RecommendationDTO("r2", "Loan 1", "Good loan");
        when(rule1.check(eq("user-1"))).thenReturn(Optional.of(rec1));
        when(rule2.check(eq("user-1"))).thenReturn(Optional.of(rec2));
        RecommendationService testService = new RecommendationService(List.of(rule1, rule2), repository);

        // Выполнение
        List<RecommendationDTO> result = testService.getRecommendations("user-1");

        // Проверка
        assertEquals(2, result.size());
        assertEquals("Card 1", result.get(0).getName());
        assertEquals("Loan 1", result.get(1).getName());
        verify(rule1).check("user-1");
        verify(rule2).check("user-1");
    }
}
