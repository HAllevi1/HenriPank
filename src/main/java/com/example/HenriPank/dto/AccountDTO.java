package com.example.HenriPank.dto;


import com.example.HenriPank.model.Account;
import com.example.HenriPank.model.User;
import com.example.HenriPank.repository.AccountRespository;

import java.util.Optional;

public class AccountDTO {

    private String iban;
    private Double balance;

    public AccountDTO(String iban, Double balance) {
        this.iban = iban;
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public Double getBalance() {
        return balance;
    }

}
