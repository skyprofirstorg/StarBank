package com.skypro.StarBank.service;

import com.skypro.StarBank.repository.DynamicRulesStatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamicRuleStatServiceTest {

    @Mock
    private DynamicRulesStatRepository repository;

    @InjectMocks
    private DynamicRuleStatService statService;

    @Test
    void recordRuleExecution_shouldCallRepository() {
        // Подготовка
        String ruleId = "rule-123";

        // Выполнение
        statService.recordRuleExecution(ruleId);

        // Проверка
        verify(repository, times(1)).incrementRuleFireCount(ruleId);
    }

    @Test
    void getStats_shouldReturnListFromRepository() {
        // Подготовка
        List<Map<String, Object>> expectedStats = List.of(
                Map.of("ruleId", "r1", "count", 5),
                Map.of("ruleId", "r2", "count", 10)
        );
        when(repository.getAllStats()).thenReturn(expectedStats);

        // Выполнение
        List<Map<String, Object>> actualStats = statService.getStats();

        // Проверка
        assertEquals(2, actualStats.size());
        assertEquals("r1", actualStats.get(0).get("ruleId"));
        assertEquals(10, actualStats.get(1).get("count"));
        verify(repository, times(1)).getAllStats();
    }

    @Test
    void deleteStat_shouldCallRepository() {
        // Подготовка
        String ruleId = "rule-delete";

        // Выполнение
        statService.deleteStat(ruleId);

        // Проверка
        verify(repository, times(1)).clearStat(ruleId);
    }
}
