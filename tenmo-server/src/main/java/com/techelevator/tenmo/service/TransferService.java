package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/*
 * Transfer Service JPA interface
 *
 * */


public interface TransferService {

    List<Transfer> getTransfers();

    Optional<Transfer> getTransferById(int id);
    List<Transfer> getPendingTransfersById(int id, TransferStatus transferStatus);

    Transfer createTransfer(Transfer transfer, int transferTypeId, int transferStatusId);

    void deleteTransfer(int id);
    List<Transfer> getTransfersByUserId(int userId);
    Transfer approveTransfer(int transferId);
    Transfer rejectTransfer(int transferId);
    Transfer sendTeBucks(int fromUser, int toUser, BigDecimal amount);
    //TODO William --> once you figure out how to complete request te bucks going through just the create endpoint you could delete this method below
    Transfer RequestTeBucks(int fromUser, int toUser, BigDecimal amount);
}
