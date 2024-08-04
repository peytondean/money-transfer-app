package com.techelevator.tenmo.config;

import com.techelevator.tenmo.repo.AccountRepo;
import com.techelevator.tenmo.repo.UserRepo;
import com.techelevator.tenmo.service.AccountService;
import com.techelevator.tenmo.service.impl.AccountServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountServiceConfig {

    @Bean
    public AccountService accountService(AccountRepo accountRepo, UserRepo userRepo){
        return new AccountServiceImpl(accountRepo, userRepo);
    }
}
