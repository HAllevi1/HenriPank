package com.example.HenriPank.service;

import com.example.HenriPank.dto.AccountDTO;
import com.example.HenriPank.dto.TransactionDTO;
import com.example.HenriPank.dto.TransferRequestDTO;
import com.example.HenriPank.model.Account;
import com.example.HenriPank.model.Transaction;
import com.example.HenriPank.model.User;
import com.example.HenriPank.repository.AccountRespository;
import com.example.HenriPank.repository.TransactionRepository;
import com.example.HenriPank.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    AccountRespository accountRepository;
    UserRepository userRepository;
    TransactionRepository transactionRepository;

    public AccountService(AccountRespository accountRespository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRespository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
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

    public List<TransactionDTO> getTransactions(String iban) {
        Account account = accountRepository.findByIban(iban).orElseThrow(() -> new RuntimeException("Account not found"));

        /*

        Kirjapilt teine.

        List<TransactionDTO> transactions = new ArrayList<>();

        for (Transaction transaction : account.getTransactions()) {
            transactions.add(new TransactionDTO(
                    transaction.getSenderIban(),
                    transaction.getReceiverIban(),
                    transaction.getAmount(),
                    transaction.getDescription(),
                    transaction.getTransactionDate()
            ));
        }

        return transactions;
         */

        return account.getTransactions().stream()
                .map(t-> new TransactionDTO(
                        t.getSenderIban(),
                        t.getReceiverIban(),
                        t.getAmount(),
                        t.getDescription(),
                        t.getTransactionDate()
                )).collect(Collectors.toList());

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

        transactionRepository.save(new Transaction(request.getAmount().negate(), fromAccount.getIban(), toAccount.getIban(), "Hardcoded Transaction Desc", LocalDateTime.now(), fromAccount));
        transactionRepository.save(new Transaction(request.getAmount(), fromAccount.getIban(), toAccount.getIban(), "Hardcoded Transaction Desc", LocalDateTime.now(), toAccount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return request;

    }

}
