package com.example.HenriPank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {

    private String fromIban;
    private String toIban;
    private BigDecimal amount;
    private String description;
    private LocalDateTime transactionDate;

    public TransactionDTO(String fromIban, String toIban, BigDecimal amount, String description, LocalDateTime transactionDate) {
        this.fromIban = fromIban;
        this.toIban = toIban;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
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

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}
