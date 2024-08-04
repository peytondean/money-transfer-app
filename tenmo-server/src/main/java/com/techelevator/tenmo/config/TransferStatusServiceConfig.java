package com.techelevator.tenmo.config;

import com.techelevator.tenmo.repo.TransferStatusRepo;
import com.techelevator.tenmo.service.TransferStatusService;
import com.techelevator.tenmo.service.impl.TransferStatusServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransferStatusServiceConfig {

    @Bean
    public TransferStatusService transferStatusService(TransferStatusRepo transferStatusRepo){
        return new TransferStatusServiceImpl(transferStatusRepo);
    }
}
