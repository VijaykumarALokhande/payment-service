package com.mini.pymtSystem.entity;

import javax.persistence.*;

@Entity
@Table(name = "pymt_details")
public class PymtDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String debitAcct;
    private String debitCCY;
    private String pymtStatus;

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

    public String getDebitCCY() {
        return debitCCY;
    }

    public void setDebitCCY(String debitCCY) {
        this.debitCCY = debitCCY;
    }

    public String getPymtStatus() {
        return pymtStatus;
    }

    public void setPymtStatus(String pymtStatus) {
        this.pymtStatus = pymtStatus;
    }
}
