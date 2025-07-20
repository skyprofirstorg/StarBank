package com.skypro.StarBank.dto.response;

import com.skypro.StarBank.dto.request.QueryRequest;
import lombok.Data;

import java.util.List;

@Data
public class DynamicRuleResponse {
    private String name;
    private String id;
    private String text;
    private List<QueryRequest> rule;

    public DynamicRuleResponse(String name,
                               String id,
                               String text,
                               List<QueryRequest> rule) {
        this.name = name;
        this.id = id;
        this.text = text;
        this.rule = rule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public List<QueryRequest> getRule() {
        return rule;
    }

    public void setRule(List<QueryRequest> rule) {
        this.rule = rule;
    }

}
