package com.techelevator.tenmo.service.impl;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.repo.TransferRepo;
import com.techelevator.tenmo.service.AccountService;
import com.techelevator.tenmo.service.TransferService;
import com.techelevator.tenmo.service.TransferStatusService;
import com.techelevator.tenmo.service.TransferTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * Transfer Service JPA implementations
 *
 * */


@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepo transferRepo;
    private final AccountService accountService;
    private final TransferStatusService transferStatusService;
    private final TransferTypeService transferTypeService;
    private final int APPROVED_STATUS = 1;
    private final int REJECTED_STATUS = 2;

    @Override
    public List<Transfer> getTransfers() {
        return transferRepo.findAll();
    }

    @Override
    public Optional<Transfer> getTransferById(int id) {
        return transferRepo.findById(id);
    }

    @Override
    public List<Transfer> getPendingTransfersById(int accountFromId, TransferStatus transferStatus) {
        return transferRepo.findByAccountFromAndTransferStatus(accountFromId, transferStatus);
    }

    @Override
    public Transfer createTransfer(Transfer transfer, int transferTypeId, int transferStatusId) {
        TransferType transferType = transferTypeService.findTransferTypeById(transferTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Transfer type ID not found: " + transferTypeId));
        TransferStatus transferStatus = transferStatusService.findTransferStatusById(transferStatusId)
                .orElseThrow(() -> new IllegalArgumentException("Transfer status ID not found: " + transferStatusId));

        transfer.setTransferType(transferType);
        transfer.setTransferStatus(transferStatus);

        return transferRepo.save(transfer);
    }

    @Override
    public void deleteTransfer(int id) {
        transferRepo.deleteById(id);
    }

    @Override
    public List<Transfer> getTransfersByUserId(int userId) {
        List<Account> accounts = accountService.findAccountsByUserId(userId);
        if (accounts.isEmpty()) {
            throw new IllegalArgumentException("User ID not found: " + userId);
        }

        List<Integer> accountIds = new ArrayList<>();
        for (Account account : accounts) {
            accountIds.add(account.getId());
        }

        List<Transfer> transfers = new ArrayList<>();
        transfers.addAll(transferRepo.findByAccountFromIn(accountIds));
        transfers.addAll(transferRepo.findByAccountToIn(accountIds));

        return transfers;
    }

    @Override
    public Transfer approveTransfer(int transferId) {
        Transfer transfer = getTransferById(transferId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find from user ID: " + transferId));
        Account accountFrom = accountService.findById(transfer.getAccountFrom())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find from user ID: "));
        Account accountTo = accountService.findById(transfer.getAccountTo())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find from user ID: "));

        if (accountFrom.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new IllegalArgumentException("From user does not have enough funds available to make TE bucks transfer.");
        }
            accountFrom.setBalance(accountFrom.getBalance().subtract(transfer.getAmount()));
            accountTo.setBalance(accountTo.getBalance().add(transfer.getAmount()));

            accountService.save(accountFrom);
            accountService.save(accountTo);

            transfer.setTransferStatus(transferStatusService.findTransferStatusById(2).orElseThrow(() -> new IllegalArgumentException("Cannot find transfer status 2")));
            return transferRepo.save(transfer);



    }

    @Override
    public Transfer rejectTransfer(int transferId) {
        Transfer transfer = getTransferById(transferId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find from user ID: " + transferId));
        transfer.setTransferStatus(transferStatusService.findTransferStatusById(3).orElseThrow(() -> new IllegalArgumentException("Cannot find transfer status 2")));
        return transferRepo.save(transfer);
    }

    @Override
    public Transfer sendTeBucks(int fromUser, int toUser, BigDecimal amount) {
        Account accountFrom = accountService.findAccountByUserId(fromUser)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find from user ID: " + fromUser));
        Account accountTo = accountService.findAccountByUserId(toUser)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find to user ID: " + toUser));

        if (accountFrom.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("From user does not have enough funds available to make TE bucks transfer.");
        }

        accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
        accountTo.setBalance(accountTo.getBalance().add(amount));

        accountService.save(accountFrom);
        accountService.save(accountTo);

        Transfer transfer = new Transfer();
        transfer.setAccountFrom(accountFrom.getId());
        transfer.setAccountTo(accountTo.getId());
        transfer.setAmount(amount);
        transfer.setTransferType(transferTypeService.findTransferTypeById(2)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find transfer type ID 2")));
        transfer.setTransferStatus(transferStatusService.findTransferStatusById(2)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find transfer status ID 2")));

        return transferRepo.save(transfer);
    }

    //TODO William --> Update this method as you see fit below / or delete it once you figure out how to setup a request through just create
    public Transfer RequestTeBucks(int fromUser, int toUser, BigDecimal amount) {
        Account accountFrom = accountService.findAccountByUserId(fromUser)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find from user ID: " + fromUser));
        Account accountTo = accountService.findAccountByUserId(toUser)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find to user ID: " + toUser));

//        if (accountFrom.getBalance().compareTo(amount) < 0) {
//            throw new IllegalArgumentException("From user does not have enough funds available to make TE bucks transfer.");
//        }
        //I commented these lines out because this is not meant to be updated until the transaction request is approved
        /*
        accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
        accountTo.setBalance(accountTo.getBalance().add(amount));

        accountService.save(accountFrom);
        accountService.save(accountTo);

         */

        Transfer transfer = new Transfer();
        transfer.setAccountFrom(accountFrom.getId());
        transfer.setAccountTo(accountTo.getId());
        transfer.setAmount(amount);
        // type 1 is request
        transfer.setTransferType(transferTypeService.findTransferTypeById(1)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find transfer type ID")));
        // status 1 is pending
        transfer.setTransferStatus(transferStatusService.findTransferStatusById(1)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find transfer status ID")));

        return transferRepo.save(transfer);
    }
}