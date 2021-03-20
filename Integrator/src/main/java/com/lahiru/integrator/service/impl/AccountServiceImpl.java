package com.lahiru.integrator.service.impl;

import com.lahiru.integrator.exception.DataAccessException;
import com.lahiru.integrator.service.AccountService;
import com.lahiru.integrator.service.connector.AccountClient;
import com.lahiru.integrator.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountClient accountClient;
    @Autowired
    private MessageSource messageSource;

    @Override
    public BigDecimal getBalance(String userId, String accountId) throws DataAccessException {

        GetAccountBalanceResponse response = accountClient.getAccountBalance(userId, accountId);

        if (response.getStatus() == Status.FAILED) {
            handleFailedResponse(response.getStatusCode());
        }
        return response.getBalance();
    }

    @Override
    public BigDecimal getBalance(String userId) throws DataAccessException {

        GetTotalBalanceResponse response = accountClient.getTotalBalance(userId);

        if (response.getStatus() == Status.FAILED) {
            handleFailedResponse(response.getStatusCode());
        }
        return response.getBalance();
    }

    @Override
    public void ownTransfer(String userId, String senderAccountNo,
                            String receiverAccountNo, BigDecimal amount) throws DataAccessException {

        GetOwnTransferResponse response =
                accountClient.getOwnTransfer(userId, senderAccountNo, receiverAccountNo, amount);

        if (response.getStatus() == Status.FAILED) {
            handleFailedResponse(response.getStatusCode());
        }
    }

    @Override
    public void otherTransfer(String userId, String senderAccountNo,
                              String receiverAccountNo, BigDecimal amount) throws DataAccessException {

        GetOtherTransferResponse response =
                accountClient.getOtherTransfer(userId, senderAccountNo, receiverAccountNo, amount);

        if (response.getStatus() == Status.FAILED) {
            handleFailedResponse(response.getStatusCode());
        }
    }

    public void handleFailedResponse(String responseCode) throws DataAccessException {
        try {
            throw new DataAccessException(
                    messageSource.getMessage("integrator.error." + responseCode,
                            null, Locale.getDefault()),
                    messageSource.getMessage("integrator.error.code." + responseCode,
                            null, Locale.getDefault()));
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            //handle unknown response codes
            throw new DataAccessException(
                    messageSource.getMessage("integrator.error.0000",
                            null, Locale.getDefault()),
                    messageSource.getMessage("integrator.error.code.0000",
                            null, Locale.getDefault()));
        }

    }
}
