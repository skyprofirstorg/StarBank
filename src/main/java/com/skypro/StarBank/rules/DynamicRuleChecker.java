package com.skypro.StarBank.rules;

import com.skypro.StarBank.model.DynamicRule;
import com.skypro.StarBank.model.Query;
import com.skypro.StarBank.repository.DynamicRecommendationRepository;
import org.springframework.stereotype.Component;

@Component
public class DynamicRuleChecker {
    private final DynamicRecommendationRepository repo;

    public DynamicRuleChecker(DynamicRecommendationRepository repo) {
        this.repo = repo;
    }

    public boolean checkRule(String userId, DynamicRule rule) {
        return rule.getRule().stream()
                .allMatch(q -> evaluate(userId, q));
    }

    private boolean evaluate(String userId, Query q) {
        boolean result = switch (q.getQuery()) {
            case "USER_OF" -> repo.hasUserProductOfType(userId, q.getArguments().get(0));
            case "ACTIVE_USER_OF" -> repo.isUserActive(userId, q.getArguments().get(0));
            case "TRANSACTION_SUM_COMPARE" ->
                    compare(repo.getTransactionSum(userId, q.getArguments().get(0), q.getArguments().get(1)),
                            Double.parseDouble(q.getArguments().get(3)), q.getArguments().get(2));
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" ->
                    compare(repo.getTotalDepositByType(userId, q.getArguments().get(0)),
                            repo.getTotalWithdrawalByType(userId, q.getArguments().get(0)),
                            q.getArguments().get(1));
            default -> false;
        };
        return q.isNegate() ? !result : result;
    }

    private boolean compare(double sum1, double sum2, String op) {
        return switch (op) {
            case ">" -> sum1 > sum2;
            case "<" -> sum1 < sum2;
            case "=" -> sum1 == sum2;
            case ">=" -> sum1 >= sum2;
            case "<=" -> sum1 <= sum2;
            default -> false;
        };
    }

    private boolean compare(double sum, String op, double value) {
        return compare(sum, value, op);
    }
}
