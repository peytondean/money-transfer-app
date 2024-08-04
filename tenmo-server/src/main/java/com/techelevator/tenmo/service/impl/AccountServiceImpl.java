package com.techelevator.tenmo.service.impl;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.repo.UserRepo;
import com.techelevator.tenmo.repo.AccountRepo;
import com.techelevator.tenmo.service.AccountService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/*
 * Account Service JPA implementations
 *
 * */

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final UserRepo userRepo;


    @Override
    public List<Account> findAll() {
        return accountRepo.findAll();
    }

    @Override
    public Optional<Account> findById(int id) {
        return accountRepo.findById(id);
    }

    @Override
    public Account save(Account account) {
        return accountRepo.save(account);
    }

    @Override
    public Account update(int id, Account accountInfo) {
        Optional<Account> optionalAccount = accountRepo.findById(id);
        if(optionalAccount.isPresent()) {
            Account oldAccount = optionalAccount.get();
            oldAccount.setBalance(accountInfo.getBalance());
            oldAccount.setUser(accountInfo.getUser());
            oldAccount.setTransfersTo(accountInfo.getTransfersTo());
            oldAccount.setTransfersFrom(accountInfo.getTransfersFrom());
            return accountRepo.save(oldAccount);
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }

    @Override
    public void deleteById(int id) {
        accountRepo.deleteById(id);
    }

    @Override
    public BigDecimal getBalance(Principal principal){
        Account account = getAccountByUserName(principal.getName());
        return account.getBalance();
    }

    @Override
    public Account getAccountByUserName(String name) {
        User user = userRepo.findUserByUsername(name);

        if (user != null){
            Optional<Account> accountToReturn = accountRepo.findById(user.getId());
            if(accountToReturn.isPresent()){
                return accountToReturn.get();
            }else {
                throw new IllegalArgumentException("account not found!");
            }
        }else {
            throw new IllegalArgumentException("user not found!");
        }
    }

    @Override
    public Optional<Account> findAccountByUserId(int id) {

        return accountRepo.findAccountByUserId(id);
    }

    @Override
    public List<Account> findAccountsByUserId(int userId) {

        return accountRepo.findAccountsByUserId(userId);
    }


}
