package com.lahiru.corebank.soap;

import com.lahiru.corebank.exception.DataAccessException;
import com.lahiru.corebank.gen.*;
import com.lahiru.corebank.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class AccountEndPoint {

    private static final String NAMESPACE_URI = "http://www.lahiru.com/corebank/gen";
    private static final Logger logger = LoggerFactory.getLogger(AccountEndPoint.class);

    @Autowired
    private AccountService accountService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccountBalanceRequest")
    @ResponsePayload
    public GetAccountBalanceResponse getAccountBalance(@RequestPayload GetAccountBalanceRequest request) {
        GetAccountBalanceResponse response = new GetAccountBalanceResponse();
        try {
            response.setBalance(accountService.getBalance(request.getUserId(), request.getAccount()));
            response.setStatus(Status.SUCCESS);
        } catch (DataAccessException e) {
            response.setStatus(Status.FAILED);
            response.setStatusCode(e.getErrorCode());
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTotalBalanceRequest")
    @ResponsePayload
    public GetTotalBalanceResponse getTotalBalance(@RequestPayload GetTotalBalanceRequest request) {
        GetTotalBalanceResponse response = new GetTotalBalanceResponse();
        try {
            response.setBalance(accountService.getBalance(request.getUserId()));
            response.setStatus(Status.SUCCESS);
        } catch (DataAccessException e) {
            response.setStatus(Status.FAILED);
            response.setStatusCode(e.getErrorCode());
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOwnTransferRequest")
    @ResponsePayload
    public GetOwnTransferResponse ownTransfer(@RequestPayload GetOwnTransferRequest request) {
        GetOwnTransferResponse response = new GetOwnTransferResponse();
        try {
            accountService.ownTransfer(request.getUserId(), request.getSenderAccount(),
                    request.getReceiverAccount(), request.getAmount());

            response.setStatus(Status.SUCCESS);
        } catch (DataAccessException e) {
            response.setStatus(Status.FAILED);
            response.setStatusCode(e.getErrorCode());
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOtherTransferRequest")
    @ResponsePayload
    public GetOtherTransferResponse otherTransfer(@RequestPayload GetOtherTransferRequest request) {
        GetOtherTransferResponse response = new GetOtherTransferResponse();
        try {
            accountService.otherTransfer(request.getUserId(), request.getSenderAccount(),
                    request.getReceiverAccount(), request.getAmount());

            response.setStatus(Status.SUCCESS);
        } catch (DataAccessException e) {
            response.setStatus(Status.FAILED);
            response.setStatusCode(e.getErrorCode());
        }
        return response;
    }
}
