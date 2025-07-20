package com.skypro.StarBank.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DynamicRuleRequest {
    private String name;
    private String id;
    private String text;
    private List<QueryRequest> rule;

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

