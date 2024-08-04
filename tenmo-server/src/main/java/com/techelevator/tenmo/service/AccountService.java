package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/*
 * Account Service JPA interface
 *
 * */

public interface AccountService {
    List<Account> findAll();
    Optional<Account> findById(int id);
    Account save(Account account);
    Account update(int id, Account accountInfo);
    void deleteById(int id);

    BigDecimal getBalance(Principal principal);
    Account getAccountByUserName(String name);
    Optional<Account> findAccountByUserId(int id);

    List<Account> findAccountsByUserId(int userId);
}
