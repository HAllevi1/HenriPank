package com.example.HenriPank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal amount;
    private String senderIban;
    private String receiverIban;
    private String description;
    private LocalDateTime transactionDate;
    @ManyToOne //Bidirectional relation with account Transaction list
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction(Long id, BigDecimal amount, String senderIban, String receiverIban, String description, LocalDateTime transactionDate, Account account) {
        this.id = id;
        this.amount = amount;
        this.senderIban = senderIban;
        this.receiverIban = receiverIban;
        this.description = description;
        this.transactionDate = transactionDate;
        this.account = account;
    }

    public Transaction(BigDecimal amount, String senderIban, String receiverIban, String description, LocalDateTime transactionDate, Account account) {
        this.amount = amount;
        this.senderIban = senderIban;
        this.receiverIban = receiverIban;
        this.description = description;
        this.transactionDate = transactionDate;
        this.account = account;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getSenderIban() {
        return senderIban;
    }

    public String getReceiverIban() {
        return receiverIban;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setSenderIban(String senderIban) {
        this.senderIban = senderIban;
    }

    public void setReceiverIban(String receiverIban) {
        this.receiverIban = receiverIban;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
