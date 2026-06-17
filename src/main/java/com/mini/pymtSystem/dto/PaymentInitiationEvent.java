package com.mini.pymtSystem.dto;

public class PaymentInitiationEvent {

    // Account from API request
    private String debitAcct;

    // Currency from API request
    private String debitCCY;

    // Status from API request
    private String pymtStatus;

    // Getters and Setters

    public String getDebitAcct() {
        return debitAcct;
    }

    public String getDebitCCY() {
        return debitCCY;
    }

    public String getPymtStatus() {
        return pymtStatus;
    }

    public void setDebitCCY(String debitCCY) {
        this.debitCCY = debitCCY;
    }

    public void setDebitAcct(String debitAcct) {
        this.debitAcct = debitAcct;
    }

    public void setPymtStatus(String pymtStatus) {
        this.pymtStatus = pymtStatus;
    }
}
