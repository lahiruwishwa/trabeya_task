package com.lahiru.corebank.domain;

import java.math.BigDecimal;

public class Account {
    private String userId;
    private String accountNo;
    private BigDecimal balance;

    public Account(String userId, String accountNo, BigDecimal balance) {
        this.userId = userId;
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public Account() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
