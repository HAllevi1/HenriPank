package com.example.HenriPank.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransferRequestDTO {
    private String fromIban;
    private String toIban;
    private BigDecimal amount;
    private String description;

    public TransferRequestDTO(String fromIban, String toIban, BigDecimal amount, String description) {
        this.fromIban = fromIban;
        this.toIban = toIban;
        this.amount = amount;
        this.description = description;
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

    public String getDescription() { return description; }
}
