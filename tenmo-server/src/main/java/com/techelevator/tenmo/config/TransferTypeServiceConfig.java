package com.techelevator.tenmo.config;

import com.techelevator.tenmo.repo.TransferTypeRepo;
import com.techelevator.tenmo.service.TransferTypeService;
import com.techelevator.tenmo.service.impl.TransferTypeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransferTypeServiceConfig {

    @Bean
    public TransferTypeService transferTypeService(TransferTypeRepo transferTypeRepo){
        return new TransferTypeServiceImpl(transferTypeRepo);
    }
}
