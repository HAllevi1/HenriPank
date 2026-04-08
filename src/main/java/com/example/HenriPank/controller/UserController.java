package com.example.HenriPank.controller;

import com.example.HenriPank.dto.AccountDTO;
import com.example.HenriPank.dto.TransactionDTO;
import com.example.HenriPank.dto.TransferRequestDTO;
import com.example.HenriPank.model.Account;
import com.example.HenriPank.service.AccountService;
import com.example.HenriPank.service.UserService;
import com.example.HenriPank.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * REST Controller providing endpoints for user management and banking operations.
 * Handles user registration, account inquiries, and fund transfers.
 */
@RestController
public class UserController {

    UserService userService;
    AccountService accountService;
    Random rand = new Random();

    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    /**
     * Retrieves a user profile by their unique database ID.
     *
     * @param id The unique identifier of the user.
     * @return The User entity associated with the given ID.
     */
    @GetMapping("/api/user/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Fetches the complete transaction history for a specific account.
     *
     * @param iban The IBAN of the account to query.
     * @return A list of TransactionDTOs representing all past transfers.
     */
    @GetMapping("/api/user/transactions/{iban}")
    public List<TransactionDTO> getTransaction(@PathVariable String iban) { return accountService.getTransactions(iban); }

    /**
     * Retrieves public account information, including current balance.
     *
     * @param iban The IBAN of the account.
     * @return An AccountDTO containing the account details.
     */
    @GetMapping("/api/user/accountinfo/{iban}")
    public AccountDTO getAccountInfo(@PathVariable String iban) {
        return accountService.getAccountInfo(iban);
    }

    /**
     * Registers a new user and automatically initializes a bank account for them.
     * Generates a random IBAN and sets the starting balance to zero.
     *
     * @param user The User entity provided in the request body (validated).
     * @return A success message confirmation.
     */
    @PostMapping("/api/user/add")
    public String addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        accountService.openAccount(user.getEmail(), new Account("EE" + rand.nextLong(0, 1000000), BigDecimal.ZERO));
        return "User added successfully";
    }

    /**
     * Executes a secure money transfer between two accounts.
     *
     * @param request TransferRequestDTO containing source, destination, and amount.
     * @return A ResponseEntity containing the confirmed transfer details.
     */
    @PostMapping("/api/transfer")
    public ResponseEntity<TransferRequestDTO> transferMoney(@Valid @RequestBody TransferRequestDTO request) {
        TransferRequestDTO result = accountService.transferMoney(request);
        return ResponseEntity.ok(result);
    }

}
