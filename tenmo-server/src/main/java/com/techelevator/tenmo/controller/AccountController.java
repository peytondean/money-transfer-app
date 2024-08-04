package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Controller Methods for Account

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.findAll();
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        Account newAccount = accountService.save(account);
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Integer id, @RequestBody Account accountInfo){
        Optional<Account> optionalAccount = accountService.findById(id);

        if (optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            Account updated = accountService.update(id, accountInfo);
            return  ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Integer id) {
        if (accountService.findById(id).isPresent()){
            accountService.deleteById(id);
        }
    }

    @GetMapping("/{id}")
    public Optional<Account> getAccountByUserId(@PathVariable Integer id) {
        return accountService.findAccountByUserId(id);
    }


}
