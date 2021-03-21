package com.lahiru.integrator.web.restful;

import com.lahiru.integrator.dto.BalanceResponse;
import com.lahiru.integrator.dto.FundTransferRequest;
import com.lahiru.integrator.dto.FundTransferResponse;
import com.lahiru.integrator.exception.DataAccessException;
import com.lahiru.integrator.service.AccountService;
import com.lahiru.integrator.web.util.ValidateUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/account")
public class AccountRestService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ValidateUtil validateUtil;

    @Value("${core.bank.account.no.mask.length:4}")
    private int maskLength;
    @Value("${core.bank.account.no.mask.content:xxxxxx}")
    private String maskContent;

    DecimalFormat df = new DecimalFormat();

    @PostConstruct
    public void init() {
        df.setMinimumFractionDigits(2);
    }

    @GetMapping(value = "/{userId}/balance/{accountId}")
    @ApiOperation(value = "Get account balance of a particular account",
            notes = "needs to provide an userId and account number for get the account balance of a particular account",
            response = BalanceResponse.class)
    public ResponseEntity<BalanceResponse> getAccountBalance(@ApiParam(value = "Id of the user", required = true)
                                                             @PathVariable(value = "userId") String userId,
                                                             @ApiParam(value = "Bank account number of the user",
                                                                     required = true)
                                                             @PathVariable(value = "accountId") String accountId)
            throws DataAccessException { //DataAccessException will handle by the ExceptionHandler

        validateUtil.validateStringField(userId,"integrator.error.0001",
                "integrator.error.code.0001");
        validateUtil.validateStringField(accountId,"integrator.error.0002",
                "integrator.error.code.0002");

        BigDecimal balance = accountService.getBalance(userId, accountId);
        BalanceResponse response = new BalanceResponse(df.format(balance));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/balance")
    @ApiOperation(value = "Get total account balance of a user",
            notes = "needs to provide an user id for get the total balance of the accounts",
            response = BalanceResponse.class)
    public ResponseEntity<BalanceResponse> getTotalBalance(@ApiParam(value = "Id of the user", required = true)
                                                           @PathVariable(value = "userId") String userId)
            throws DataAccessException {

        validateUtil.validateStringField(userId,"integrator.error.0001",
                "integrator.error.code.0001");

        BigDecimal balance = accountService.getBalance(userId);
        BalanceResponse response = new BalanceResponse(df.format(balance));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/transfer/own")
    @ApiOperation(value = "Transfer funds between own accounts",
            notes = "needs to provide an user id, sender,receiver accounts and amount",
            response = FundTransferResponse.class)
    public ResponseEntity<FundTransferResponse> fundTransferOwn(@ApiParam(value = "Id of the user", required = true)
                                                                @PathVariable(value = "userId") String userId,
                                                                @Valid
                                                                @RequestBody FundTransferRequest fundTransferRequest)
            throws DataAccessException {

        validateUtil.validateStringField(userId,"integrator.error.0001",
                "integrator.error.code.0001");

        accountService.ownTransfer(userId, fundTransferRequest.getSenderAccountNo(),
                fundTransferRequest.getReceiverAccountNo(), fundTransferRequest.getAmount());

        FundTransferResponse response = new FundTransferResponse(new Date(), "success",
                getMaskedAccountNo(fundTransferRequest.getSenderAccountNo()),
                getMaskedAccountNo(fundTransferRequest.getReceiverAccountNo()),
                df.format(fundTransferRequest.getAmount()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/transfer/other")
    @ApiOperation(value = "Transfer funds to a different user",
            notes = "needs to provide an user id, sender,receiver accounts and amount",
            response = FundTransferResponse.class)
    public ResponseEntity<FundTransferResponse> fundTransferOther(@ApiParam(value = "Id of the user", required = true)
                                                                  @PathVariable(value = "userId") String userId,
                                                                  @Valid
                                                                  @RequestBody FundTransferRequest fundTransferRequest)
            throws DataAccessException {

        validateUtil.validateStringField(userId,"integrator.error.0001",
                "integrator.error.code.0001");

        accountService.otherTransfer(userId, fundTransferRequest.getSenderAccountNo(),
                fundTransferRequest.getReceiverAccountNo(), fundTransferRequest.getAmount());

        FundTransferResponse response = new FundTransferResponse(new Date(), "success",
                getMaskedAccountNo(fundTransferRequest.getSenderAccountNo()),
                getMaskedAccountNo(fundTransferRequest.getReceiverAccountNo()),
                df.format(fundTransferRequest.getAmount()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String getMaskedAccountNo(String accountNo) {
        return accountNo.substring(0, accountNo.length() < maskLength ? accountNo.length() : maskLength)
                + maskContent;
    }
}
