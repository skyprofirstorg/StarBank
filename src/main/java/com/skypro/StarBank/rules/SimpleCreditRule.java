package com.skypro.StarBank.rules;

import com.skypro.StarBank.dto.response.RecommendationDTO;
import com.skypro.StarBank.model.ProductCatalog;
import com.skypro.StarBank.repository.RecommendationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SimpleCreditRule implements RecommendationRuleSet {

    private final RecommendationRepository repo;

    public SimpleCreditRule(RecommendationRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<RecommendationDTO> check(String userId) {
        if (repo.hasUserProductOfType(userId, "CREDIT")) return Optional.empty();

        double totalDeposits = repo.getTotalDepositByType(userId, "DEBIT");
        double totalSpent = repo.getTotalWithdrawalByType(userId, "DEBIT");

        if (totalDeposits <= totalSpent) return Optional.empty();
        if (totalSpent <= 100000) return Optional.empty();


        return Optional.of(repo.getById("ab138afb-f3ba-4a93-b74f-0fcee86d447f"));
    }
}
