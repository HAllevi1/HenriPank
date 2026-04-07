package com.example.HenriPank.service;

import com.example.HenriPank.dto.AccountDTO;
import com.example.HenriPank.model.Account;
import com.example.HenriPank.model.User;
import com.example.HenriPank.repository.AccountRespository;
import com.example.HenriPank.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    AccountRespository accountRespository;
    UserRepository userRepository;

    public AccountService(AccountRespository accountRespository, UserRepository userRepository) {
        this.accountRespository = accountRespository;
        this.userRepository = userRepository;
    }

    public void openAccount(String userEmail, Account newAccount) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

        newAccount.setUser(user);
        accountRespository.save(newAccount);

    }

    public AccountDTO getAccountInfo(String iban) {
        Account account = accountRespository.findByIban(iban).orElseThrow(() -> new RuntimeException("Account not found"));

        return new AccountDTO(account.getIban(), account.getBalance());
    }
}
