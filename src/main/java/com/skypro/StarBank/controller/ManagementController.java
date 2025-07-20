package com.skypro.StarBank.controller;

import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.boot.info.BuildProperties;
import org.springframework.cache.Cache;

@RestController
@RequestMapping("/management")
public class ManagementController {
    private final CacheManager cacheManager;
    private final BuildProperties buildProperties;

    public ManagementController(CacheManager cacheManager, BuildProperties buildProperties) {
        this.cacheManager = cacheManager;
        this.buildProperties = buildProperties;
    }

    @PostMapping("/clear-caches")
    public ResponseEntity<Void> clearAllCaches() {
        cacheManager.getCacheNames().forEach(name -> {
            Cache cache = cacheManager.getCache(name);
            if (cache != null) {
                cache.clear();
            }
        });
        return ResponseEntity.ok().build();
    }
     @GetMapping("/info")
    public Map<String, String> getInfo() {
        return Map.of(
            "name", buildProperties.getName(),
            "version", buildProperties.getVersion()
        );
    }
}
