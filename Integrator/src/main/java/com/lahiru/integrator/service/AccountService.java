package com.lahiru.integrator.service;

import com.lahiru.integrator.exception.DataAccessException;

import java.math.BigDecimal;

public interface AccountService {
    BigDecimal getBalance(String userId, String accountId) throws DataAccessException;

    BigDecimal getBalance(String userId) throws DataAccessException;

    void ownTransfer(String userId, String senderAccountNo,
                     String receiverAccountNo, BigDecimal amount) throws DataAccessException;

    void otherTransfer(String userId, String senderAccountNo,
                       String receiverAccountNo, BigDecimal amount) throws DataAccessException;
}
