package com.skypro.StarBank.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryRequest {

    private String query;
    private List<String> arguments;
    private boolean negate;

    public String getQuery() {
        return query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public boolean isNegate() {
        return negate;
    }

}