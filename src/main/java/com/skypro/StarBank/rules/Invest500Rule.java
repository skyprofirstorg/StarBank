package com.skypro.StarBank.rules;

import com.skypro.StarBank.dto.response.RecommendationDTO;
import com.skypro.StarBank.model.ProductCatalog;
import com.skypro.StarBank.repository.RecommendationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Invest500Rule implements RecommendationRuleSet {

    private final RecommendationRepository repo;
    private  final ProductCatalog productCatalog;

    Logger logger = LoggerFactory.getLogger(Invest500Rule.class);

    public Invest500Rule(ProductCatalog productCatalog, RecommendationRepository repo) {
        this.repo = repo;
        this.productCatalog = productCatalog;
    }

    @Override
    public Optional<RecommendationDTO> check(String userId) {

        logger.info("Проверка invest 500 рекомендации для пользователя {}", userId);

        if (!repo.hasUserProductOfType(userId, "DEBIT")) return Optional.empty();
        if (repo.hasUserProductOfType(userId, "INVEST")) return Optional.empty();
        if (repo.getTotalDepositByType(userId, "SAVING") <= 1000) return Optional.empty();

        logger.info("Invest 500 рекоммендация для пользователя {} is {}", userId, repo.getById("147f6a0f-3b91-413b-ab99-87f081d60d5a").getName());

        return Optional.of(repo.getById("147f6a0f-3b91-413b-ab99-87f081d60d5a"));
    }


}
