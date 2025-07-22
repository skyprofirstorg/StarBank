package com.skypro.StarBank.controller;

import com.skypro.StarBank.dto.request.DynamicRuleRequest;
import com.skypro.StarBank.dto.response.DynamicRuleResponse;
import com.skypro.StarBank.model.DynamicRule;
import com.skypro.StarBank.repository.DynamicRuleRepository;
import com.skypro.StarBank.service.DynamicRuleService;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {
    private final DynamicRuleRepository ruleRepository;
    private final DynamicRuleService ruleService;

    public DynamicRuleController(DynamicRuleRepository ruleRepository, DynamicRuleService ruleService) {
        this.ruleRepository = ruleRepository;
        this.ruleService = ruleService;
    }

    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public DynamicRuleResponse createRule(@RequestBody DynamicRuleRequest request) {
        return ruleService.createRule(request);
    }



    @GetMapping
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Map<String, List<DynamicRule>> getAllRules() {
        Map<String, List<DynamicRule>> response = new HashMap<>();
        response.put("data", ruleRepository.findAll());
        return response;
    }

    @DeleteMapping("/{product_id}")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public void deleteRule(@PathVariable String product_id) {
        ruleRepository.deleteById(product_id);

    }
}
