package com.techelevator.tenmo.service.impl;

import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.repo.TransferStatusRepo;
import com.techelevator.tenmo.service.TransferStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * Transfer Status Service JPA implementations
 *
 * */

@Service
@RequiredArgsConstructor
public class TransferStatusServiceImpl implements TransferStatusService {

    private final TransferStatusRepo transferStatusRepo;


    @Override
    public List<TransferStatus> findAllTransferStatus() {
        return transferStatusRepo.findAll();
    }

    @Override
    public Optional<TransferStatus> findTransferStatusById(int id) {
        return transferStatusRepo.findById(id);
    }
}
