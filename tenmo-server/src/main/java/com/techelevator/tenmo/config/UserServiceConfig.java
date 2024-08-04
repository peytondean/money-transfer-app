package com.techelevator.tenmo.config;

import com.techelevator.tenmo.repo.UserRepo;
import com.techelevator.tenmo.service.UserService;
import com.techelevator.tenmo.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserServiceConfig {

    @Bean
    public UserService userService(UserRepo userRepo, PasswordEncoder passwordEncoder){
        return new UserServiceImpl(userRepo, passwordEncoder);
    }
}

