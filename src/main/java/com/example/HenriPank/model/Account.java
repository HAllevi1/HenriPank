package com.example.HenriPank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String iban;

    @Min(value = 0)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_account")
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL) //Bidirectional relation with Transaction account, Cascade because when we want to delete the account, it also deletes the transactions and we get no error.
    private List<Transaction> transactions;

    public Account() {}

    public Account(Long id, String iban, BigDecimal balance) {
        this.id = id;
        this.iban = iban;
        this.balance = balance;
    }

    public Account(String iban, BigDecimal balance) {
        this.iban = iban;
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    //Getters
    public Long getId() {
        return id;
    }
    public String getIban() {
        return iban;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public User getUser() {
        return user;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setIban(String iban) {
        this.iban = iban;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
