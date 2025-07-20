package com.skypro.StarBank.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "dynamic_rules")
public class DynamicRule {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "VARCHAR(255)")
    public String id;

    public String name;
    public String text;

    @ElementCollection
    private List<Query> rule;

    public DynamicRule() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRule(List<Query> rule) {
        this.rule = rule;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getText() {
        return text;
    }

    public List<Query> getRule() {
        return rule;
    }
}
