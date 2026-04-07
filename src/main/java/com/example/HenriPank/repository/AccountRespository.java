package com.example.HenriPank.repository;

import com.example.HenriPank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRespository extends JpaRepository<Account, Long> {

    Optional<Account> findByIban(String iban);
}
