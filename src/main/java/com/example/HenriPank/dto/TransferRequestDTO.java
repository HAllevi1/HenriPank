package com.example.HenriPank.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransferRequestDTO {
    private String fromIban;
    private String toIban;
    private BigDecimal amount;

    public TransferRequestDTO(String fromIban, String toIban, BigDecimal amount) {
        this.fromIban = fromIban;
        this.toIban = toIban;
        this.amount = amount;
    }

    public String getFromIban() {
        return fromIban;
    }

    public String getToIban() {
        return toIban;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
