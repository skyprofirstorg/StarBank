-- Создание основной таблицы dynamic_rules
CREATE TABLE IF NOT EXISTS dynamic_rules (
                                             id VARCHAR(255) PRIMARY KEY,
                                             name VARCHAR(255),
                                             text TEXT
);

-- Создание таблицы для хранения запросов (query) в рамках правила
CREATE TABLE IF NOT EXISTS dynamic_rule_queries (
                                                    id BIGSERIAL PRIMARY KEY,
                                                    dynamic_rule_id VARCHAR(255) REFERENCES dynamic_rules(id) ON DELETE CASCADE,
                                                    query_type VARCHAR(255),
                                                    negate BOOLEAN
);

-- Создание таблицы для аргументов каждого запроса
CREATE TABLE IF NOT EXISTS dynamic_rule_query_arguments (
                                                            id BIGSERIAL PRIMARY KEY,
                                                            rule_query_id BIGINT REFERENCES dynamic_rule_queries(id) ON DELETE CASCADE,
                                                            argument_value VARCHAR(255)
);
-- Создание таблицы для хранения статистики по выполнению правил
CREATE TABLE IF NOT EXISTS DYNAMIC_RULES_STAT (
                RULE_ID BIGINT PRIMARY KEY,
                COUNT BIGINT NOT NULL DEFAULT 0,
                LAST_EXECUTED TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );

