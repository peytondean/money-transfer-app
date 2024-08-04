package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.TransferStatus;

import java.util.List;
import java.util.Optional;

/*
 * Transfer Status Service JPA interface
 *
 * */

public interface TransferStatusService {

    List<TransferStatus> findAllTransferStatus();
    Optional<TransferStatus> findTransferStatusById(int id);
}
