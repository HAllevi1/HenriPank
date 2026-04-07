package com.example.HenriPank.service;

import com.example.HenriPank.dto.AccountDTO;
import com.example.HenriPank.dto.TransferRequestDTO;
import com.example.HenriPank.model.Account;
import com.example.HenriPank.model.User;
import com.example.HenriPank.repository.AccountRespository;
import com.example.HenriPank.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    AccountRespository accountRepository;
    UserRepository userRepository;

    public AccountService(AccountRespository accountRespository, UserRepository userRepository) {
        this.accountRepository = accountRespository;
        this.userRepository = userRepository;
    }

    public void openAccount(String userEmail, Account newAccount) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

        newAccount.setUser(user);
        accountRepository.save(newAccount);

    }

    public AccountDTO getAccountInfo(String iban) {
        Account account = accountRepository.findByIban(iban).orElseThrow(() -> new RuntimeException("Account not found"));

        return new AccountDTO(account.getIban(), account.getBalance());
    }

    @Transactional //If anything goes wrong with server, transactional should rollback
    public TransferRequestDTO transferMoney(TransferRequestDTO request) {
        Account fromAccount = accountRepository.findByIban(request.getFromIban())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Account toAccount = accountRepository.findByIban(request.getToIban())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds on account" + fromAccount.getIban());
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return request;

    }

}
