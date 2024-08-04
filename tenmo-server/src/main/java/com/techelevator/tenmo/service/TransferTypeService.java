package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.TransferType;

import java.util.List;
import java.util.Optional;

/*
 * Transfer Type Service JPA interface
 *
 * */

public interface TransferTypeService {
    List<TransferType> findAllTransferTypes();
    Optional<TransferType > findTransferTypeById(int id);


}
