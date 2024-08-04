package com.techelevator.tenmo.config;

import com.techelevator.tenmo.repo.AccountRepo;
import com.techelevator.tenmo.repo.TransferRepo;
import com.techelevator.tenmo.repo.TransferStatusRepo;
import com.techelevator.tenmo.repo.TransferTypeRepo;
import com.techelevator.tenmo.service.AccountService;
import com.techelevator.tenmo.service.TransferService;
import com.techelevator.tenmo.service.TransferStatusService;
import com.techelevator.tenmo.service.TransferTypeService;
import com.techelevator.tenmo.service.impl.TransferServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransferServiceConfig {

    @Bean
    public TransferService transferService(TransferRepo transferRepo, AccountService accountService, TransferStatusService transferStatusService, TransferTypeService transferTypeService) {
        return new TransferServiceImpl(transferRepo, accountService, transferStatusService, transferTypeService);
    }
}
