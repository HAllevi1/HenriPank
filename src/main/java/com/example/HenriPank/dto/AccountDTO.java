package com.example.HenriPank.dto;


import com.example.HenriPank.model.Account;
import com.example.HenriPank.model.User;
import com.example.HenriPank.repository.AccountRespository;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Optional;

public class AccountDTO {

    private String iban;
    @Positive
    private BigDecimal balance;

    public AccountDTO(String iban, BigDecimal balance) {
        this.iban = iban;
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

}
