package com.techelevator.tenmo.service.impl;

import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.repo.TransferTypeRepo;
import com.techelevator.tenmo.service.TransferTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * Transfer Type Service JPA implementations
 *
 * */
@Service
@AllArgsConstructor
public class TransferTypeServiceImpl implements TransferTypeService {

    private final TransferTypeRepo transferTypeRepo;

    @Override
    public List<TransferType> findAllTransferTypes() {
        return transferTypeRepo.findAll();
    }

    @Override
    public Optional<TransferType> findTransferTypeById(int id) {
        return transferTypeRepo.findById(id);
    }
}
