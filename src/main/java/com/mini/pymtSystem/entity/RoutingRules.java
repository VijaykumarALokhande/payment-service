package com.mini.pymtSystem.entity;


import javax.persistence.*;

@Entity
@Table(name = "routing_rules")
public class RoutingRules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String debitAcct;
    private String targetTopic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDebitAcct() {
        return debitAcct;
    }

    public void setDebitAcct(String debitAcct) {
        this.debitAcct = debitAcct;
    }

    public String getTargetTopic() {
        return targetTopic;
    }

    public void setTargetTopic(String targetTopic) {
        this.targetTopic = targetTopic;
    }
}
