package com.lahiru.integrator.dto;

import java.util.Date;

public class FundTransferResponse {

    private Date date;
    private String status;
    private String sender;
    private String receiver;
    private String amount;

    public FundTransferResponse(Date date, String status, String sender, String receiver, String amount) {
        this.date = date;
        this.status = status;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public FundTransferResponse() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
