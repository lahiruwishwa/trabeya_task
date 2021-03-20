package com.lahiru.integrator.dto;

public class BalanceResponse {

    private String balance;

    public BalanceResponse(String balance) {
        this.balance = balance;
    }

    public BalanceResponse() {
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
