package com.lahiru.integrator.service.connector;

import com.lahiru.integrator.exception.DataAccessException;
import com.lahiru.integrator.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.math.BigDecimal;
import java.util.Locale;

public class AccountClient extends WebServiceGatewaySupport {

    private static final Logger logger = LoggerFactory.getLogger(AccountClient.class);

    @Autowired
    private MessageSource messageSource;

    public GetAccountBalanceResponse getAccountBalance(String userId, String accountNo) throws DataAccessException {

        GetAccountBalanceRequest request = new GetAccountBalanceRequest();
        request.setUserId(userId);
        request.setAccount(accountNo);
        GetAccountBalanceResponse response = null;
        try {
            response = (GetAccountBalanceResponse) getWebServiceTemplate()
                    .marshalSendAndReceive("http://localhost:8686/ws/accounts", request,
                            new SoapActionCallback(
                                    "http://www.lahiru.com/corebank/gen/GetAccountBalanceRequest"));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new DataAccessException(
                    messageSource.getMessage("integrator.error.0007", null, Locale.getDefault()),
                    messageSource.getMessage("integrator.error.code.0007", null, Locale.getDefault()));
        }
        return response;
    }

    public GetTotalBalanceResponse getTotalBalance(String userId) throws DataAccessException {

        GetTotalBalanceRequest request = new GetTotalBalanceRequest();
        request.setUserId(userId);
        GetTotalBalanceResponse response = null;
        try {
            response = (GetTotalBalanceResponse) getWebServiceTemplate()
                    .marshalSendAndReceive("http://localhost:8686/ws/accounts", request,
                            new SoapActionCallback(
                                    "http://www.lahiru.com/corebank/gen/GetTotalBalanceRequest"));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new DataAccessException(
                    messageSource.getMessage("integrator.error.0007", null, Locale.getDefault()),
                    messageSource.getMessage("integrator.error.code.0007", null, Locale.getDefault()));
        }
        return response;
    }

    public GetOwnTransferResponse getOwnTransfer(String userId, String sender,
                                                 String receiver, BigDecimal amount) throws DataAccessException {

        GetOwnTransferRequest request = new GetOwnTransferRequest();
        request.setUserId(userId);
        request.setSenderAccount(sender);
        request.setReceiverAccount(receiver);
        request.setAmount(amount);

        GetOwnTransferResponse response = null;
        try {
            response = (GetOwnTransferResponse) getWebServiceTemplate()
                    .marshalSendAndReceive("http://localhost:8686/ws/accounts", request,
                            new SoapActionCallback(
                                    "http://www.lahiru.com/corebank/gen/GetOwnTransferRequest"));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new DataAccessException(
                    messageSource.getMessage("integrator.error.0007", null, Locale.getDefault()),
                    messageSource.getMessage("integrator.error.code.0007", null, Locale.getDefault()));
        }
        return response;
    }

    public GetOtherTransferResponse getOtherTransfer(String userId, String sender,
                                                     String receiver, BigDecimal amount) throws DataAccessException {

        GetOtherTransferRequest request = new GetOtherTransferRequest();
        request.setUserId(userId);
        request.setSenderAccount(sender);
        request.setReceiverAccount(receiver);
        request.setAmount(amount);

        GetOtherTransferResponse response = null;
        try {
            response = (GetOtherTransferResponse) getWebServiceTemplate()
                    .marshalSendAndReceive("http://localhost:8686/ws/accounts", request,
                            new SoapActionCallback(
                                    "http://www.lahiru.com/corebank/gen/GetOtherTransferRequest"));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new DataAccessException(
                    messageSource.getMessage("integrator.error.0007", null, Locale.getDefault()),
                    messageSource.getMessage("integrator.error.code.0007", null, Locale.getDefault()));
        }
        return response;
    }
}