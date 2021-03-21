package com.lahiru.integrator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


public class FundTransferRequest {

    @JsonProperty(value = "sender")
    @NotNull(message = "Sender bank account no is required.")
    @Min(value = 1,message = "Sender bank account no is required.")
    @ApiModelProperty(value ="Sender Bank account no" ,required = true)
    private String senderAccountNo;

    @JsonProperty(value = "receiver")
    @NotNull(message = "Receiver bank account no is required.")
    @Min(value = 1,message = "Receiver bank account no is required.")
    @ApiModelProperty(value ="Receiver Bank account no" ,required = true)
    private String receiverAccountNo;

    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "0.00", inclusive = false,message = "Amount must be greater than 0.00")
    @ApiModelProperty(value ="Transferring amount" ,required = true)
    private BigDecimal amount;

    public FundTransferRequest(String senderAccountNo,String receiverAccountNo,BigDecimal amount) {
        this.senderAccountNo = senderAccountNo;
        this.receiverAccountNo = receiverAccountNo;
        this.amount = amount;
    }

    public FundTransferRequest() {
    }

    public String getSenderAccountNo() {
        return senderAccountNo;
    }

    public void setSenderAccountNo(String senderAccountNo) {
        this.senderAccountNo = senderAccountNo;
    }

    public String getReceiverAccountNo() {
        return receiverAccountNo;
    }

    public void setReceiverAccountNo(String receiverAccountNo) {
        this.receiverAccountNo = receiverAccountNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
