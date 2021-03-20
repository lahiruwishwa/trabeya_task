package com.lahiru.corebank.service.impl;

import com.lahiru.corebank.domain.Account;
import com.lahiru.corebank.exception.DataAccessException;
import com.lahiru.corebank.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    public static List<Account> accountList = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private MessageSource messageSource;

    @PostConstruct
    public void init() {
        accountList.add(new Account("111", "123123123", new BigDecimal(1000)));
        accountList.add(new Account("111", "456456456", new BigDecimal(2000)));
        accountList.add(new Account("222", "789789789", new BigDecimal(2320)));
        accountList.add(new Account("333", "567567567", new BigDecimal(1230)));
    }

    @Override
    public BigDecimal getBalance(String userId, String accountId) throws DataAccessException {

        Optional<Account> optionalAccount = accountList
                .stream()
                .filter(i -> i.getUserId().equalsIgnoreCase(userId) && i.getAccountNo().equalsIgnoreCase(accountId))
                .findFirst();

        if (optionalAccount.isPresent()) {
            return optionalAccount.get().getBalance();
        }
        throw new DataAccessException(
                messageSource.getMessage("core.bank.error.message.invalid.account", null, Locale.getDefault()),
                messageSource.getMessage("core.bank.error.code.invalid.account", null, Locale.getDefault()));
    }

    @Override
    public BigDecimal getBalance(String userId) throws DataAccessException {

        List<Account> accounts = accountList
                .stream()
                .filter(i -> i.getUserId().equalsIgnoreCase(userId))
                .collect(Collectors.toList());

        if (accounts == null || accounts.size() == 0) {
            throw new DataAccessException(
                    messageSource.getMessage("core.bank.error.message.invalid.user", null, Locale.getDefault()),
                    messageSource.getMessage("core.bank.error.code.invalid.user", null, Locale.getDefault()));
        }

        BigDecimal total = accounts
                .stream()
                .map(i -> i.getBalance())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total;
    }

    @Override
    public void ownTransfer(String userId, String senderAccountNo,
                            String receiverAccountNo, BigDecimal amount) throws DataAccessException {

        Optional<Account> senderAccount = getValidatedSender(userId, senderAccountNo, amount);

        Optional<Account> receiverAccount = accountList
                .stream()
                .filter(i ->
                        i.getUserId().equalsIgnoreCase(userId) && i.getAccountNo().equalsIgnoreCase(receiverAccountNo))
                .findFirst();

        if (!receiverAccount.isPresent()) {
            throw new DataAccessException(
                    messageSource.getMessage("core.bank.error.message.invalid.account.receiver",
                            null, Locale.getDefault()),
                    messageSource.getMessage("core.bank.error.code.invalid.account.receiver",
                            null, Locale.getDefault()));
        }

        updateAmount(senderAccountNo, senderAccount.get().getBalance().subtract(amount));
        updateAmount(receiverAccountNo, receiverAccount.get().getBalance().add(amount));
    }

    @Override
    public void otherTransfer(String userId, String senderAccountNo,
                              String receiverAccountNo, BigDecimal amount) throws DataAccessException {


        Optional<Account> senderAccount = getValidatedSender(userId, senderAccountNo, amount);

        Optional<Account> receiverAccount = accountList
                .stream()
                .filter(i ->
                        !(i.getUserId().equalsIgnoreCase(userId)) &&
                                i.getAccountNo().equalsIgnoreCase(receiverAccountNo))
                .findFirst();

        if (!receiverAccount.isPresent()) {
            throw new DataAccessException(
                    messageSource.getMessage("core.bank.error.message.invalid.account.receiver",
                            null, Locale.getDefault()),
                    messageSource.getMessage("core.bank.error.code.invalid.account.receiver",
                            null, Locale.getDefault()));
        }

        updateAmount(senderAccountNo, senderAccount.get().getBalance().subtract(amount));
        updateAmount(receiverAccountNo, receiverAccount.get().getBalance().add(amount));

    }

    private Optional<Account> getValidatedSender(String userId, String senderAccountNo,
                                                 BigDecimal amount) throws DataAccessException {

        if (amount == null || amount.doubleValue() <= 0) {
            throw new DataAccessException(
                    messageSource.getMessage("core.bank.error.message.invalid.amount",
                            null, Locale.getDefault()),
                    messageSource.getMessage("core.bank.error.code.invalid.amount",
                            null, Locale.getDefault()));
        }
        Optional<Account> senderAccount = accountList
                .stream()
                .filter(i ->
                        i.getUserId().equalsIgnoreCase(userId) && i.getAccountNo().equalsIgnoreCase(senderAccountNo))
                .findFirst();

        if (!senderAccount.isPresent()) {
            throw new DataAccessException(
                    messageSource.getMessage("core.bank.error.message.invalid.account.sender",
                            null, Locale.getDefault()),
                    messageSource.getMessage("core.bank.error.code.invalid.account.sender",
                            null, Locale.getDefault()));
        }
        if (senderAccount.get().getBalance().compareTo(amount) < 1) {
            throw new DataAccessException(
                    messageSource.getMessage("core.bank.error.message.insufficient.balance",
                            null, Locale.getDefault()),
                    messageSource.getMessage("core.bank.error.code.insufficient.balance",
                            null, Locale.getDefault()));
        }
        return senderAccount;
    }

    private void updateAmount(String senderAccountNo, BigDecimal amount) {
        for (Account account: accountList){
            if(account.getAccountNo().equalsIgnoreCase(senderAccountNo)){
                account.setBalance(amount);
                break;
            }
        }
    }
}
