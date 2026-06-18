package com.mini.pymtSystem.entity;


import javax.persistence.*;

@Entity
@Table(name = "simulator_rules")
public class SimulatorRules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String debitAcct;
    private String responseStatus;

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

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }
}
