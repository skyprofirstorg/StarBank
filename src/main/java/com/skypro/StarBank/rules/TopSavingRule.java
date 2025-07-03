package com.skypro.StarBank.rules;

import com.skypro.StarBank.dto.response.RecommendationDTO;
import com.skypro.StarBank.model.ProductCatalog;
import com.skypro.StarBank.repository.RecommendationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class TopSavingRule implements RecommendationRuleSet {

    private final RecommendationRepository repo;

    Logger logger = LoggerFactory.getLogger(TopSavingRule.class);


    public TopSavingRule(RecommendationRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<RecommendationDTO> check(String userId) {
        logger.info("Проверка пользователя {} на рекомендацию Top Saving", userId);

        if (!repo.hasUserProductOfType(userId, "DEBIT")) return Optional.empty();

        double depositDebit = repo.getTotalDepositByType(userId, "DEBIT");
        double depositSaving = repo.getTotalDepositByType(userId, "SAVING");

        if (!(depositDebit >= 50000 || depositSaving >= 50000)) return Optional.empty();

        double totalSpent = repo.getTotalWithdrawalByType(userId, "DEBIT");
        if (depositDebit <= totalSpent) return Optional.empty();

        logger.info("Пользователь {} избран top saving рекомендация is {}", userId, repo.getById("59efc529-2fff-41af-baff-90ccd7402925").getName());

        return Optional.of(repo.getById("59efc529-2fff-41af-baff-90ccd7402925"));
    }
}