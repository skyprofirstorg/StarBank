package com.skypro.StarBank.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.info.BuildProperties;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@ExtendWith(MockitoExtension.class)
class ManagementControllerTest {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private BuildProperties buildProperties;

    @InjectMocks
    private ManagementController managementController;

    private MockMvc mockMvc;

    @org.junit.jupiter.api.BeforeEach
    void setup() {
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(managementController).build();
    }

    // Removed duplicate BuildProperties field

    @Test
    @DisplayName("POST /management/clear-caches - должен очистить все кеши")
    void testClearAllCaches() throws Exception {
        Cache cache1 = mock(Cache.class);
        Cache cache2 = mock(Cache.class);

        when(cacheManager.getCacheNames()).thenReturn(List.of("cache1", "cache2"));
        when(cacheManager.getCache("cache1")).thenReturn(cache1);
        when(cacheManager.getCache("cache2")).thenReturn(cache2);

        mockMvc.perform(post("/management/clear-caches"))
                .andExpect(status().isOk());

        verify(cache1, times(1)).clear();
        verify(cache2, times(1)).clear();
    }

    @Test
    @DisplayName("GET /management/info - должен вернуть имя и версию приложения")
    void testGetInfo() throws Exception {
        when(buildProperties.getName()).thenReturn("StarBank");
        when(buildProperties.getVersion()).thenReturn("1.0.0");

        mockMvc.perform(get("/management/info")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("StarBank"))
                .andExpect(jsonPath("$.version").value("1.0.0"));
    }
}
