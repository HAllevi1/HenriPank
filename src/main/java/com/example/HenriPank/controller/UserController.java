package com.example.HenriPank.controller;

import com.example.HenriPank.dto.AccountDTO;
import com.example.HenriPank.model.Account;
import com.example.HenriPank.service.AccountService;
import com.example.HenriPank.service.UserService;
import com.example.HenriPank.model.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class UserController {

    UserService userService;
    AccountService accountService;
    Random rand = new Random();

    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping("/api/user/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/api/user/add")
    public String addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        accountService.openAccount(user.getEmail(), new Account("EE" + rand.nextLong(0, 1000000), 0.0));
        return "User added successfully";
    }

    @GetMapping("/api/user/accountinfo/{iban}")
    public AccountDTO getAccountInfo(@PathVariable String iban) {
        return accountService.getAccountInfo(iban);
    }

}
