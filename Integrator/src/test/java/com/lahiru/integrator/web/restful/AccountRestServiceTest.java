package com.lahiru.integrator.web.restful;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lahiru.integrator.dto.FundTransferRequest;
import com.lahiru.integrator.exception.DataAccessException;
import com.lahiru.integrator.service.AccountService;
import com.lahiru.integrator.web.util.ValidateUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@WebMvcTest(AccountRestService.class)
class AccountRestServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @MockBean
    private ValidateUtil validateUtil;

    @Test
    @DisplayName("Test getTotalBalance service with valid user id")
    void getTotalBalance() throws Exception {
        Mockito.when(accountService.getBalance("111")).thenReturn(new BigDecimal(1000));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/account/111/balance")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("balance").value("1,000.00"));
    }

    @Test
    @DisplayName("Test getAccountBalance service with valid user id and account id")
    void getAccountBalance() throws Exception {
        Mockito.when(accountService.getBalance("111", "123123123")).thenReturn(new BigDecimal(800));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/account/111/balance/123123123")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("balance").value("800.00"));
    }

    @Test
    @DisplayName("Test getTotalBalance service with invalid user id")
    void getTotalBalanceOfInvalidUser() throws Exception {
        Mockito.when(accountService.getBalance("45")).thenThrow(new DataAccessException("Invalid user id", "0001"));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/account/45/balance")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("0001"));
    }

    @Test
    @DisplayName("Test fundTransferOwn service with valid request")
    void fundTransferOwn() throws Exception {
        Mockito.doNothing()
                .when(accountService)
                .ownTransfer("111", "123123123",
                "456456456", new BigDecimal(100));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/account/111/transfer/own")
                .content(new ObjectMapper()
                        .writeValueAsString(new FundTransferRequest("123123123",
                                "456456456", new BigDecimal(100))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test fundTransferOwn service with invalid request")
    void fundTransferOwnWithInvalidRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/account/111/transfer/own")
                .content(new ObjectMapper()
                        .writeValueAsString(new FundTransferRequest("123123123",
                                null, new BigDecimal(100))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("0000"));
    }

    @Test
    @DisplayName("Test fundTransferOther service with valid request")
    void fundTransferOther() throws Exception{
        Mockito
                .doNothing()
                .when(accountService)
                .otherTransfer("111", "123123123",
                        "789789789", new BigDecimal(100));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/account/111/transfer/other")
                .content(new ObjectMapper()
                        .writeValueAsString(new FundTransferRequest("123123123",
                                "789789789", new BigDecimal(100))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test fundTransferOther service with insufficient balance")
    void fundTransferOtherWithInsufficientBalance() throws Exception{
        Mockito
                .doThrow(new DataAccessException("Insufficient balance","0005"))
                .when(accountService)
                .otherTransfer("111", "123123123",
                "789789789", new BigDecimal(10000));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/account/111/transfer/other")
                .content(new ObjectMapper()
                        .writeValueAsString(new FundTransferRequest("123123123",
                                "789789789", new BigDecimal(10000))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("0005"));
    }
}