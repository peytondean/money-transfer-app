package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.service.TransferStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/*
 * Controller Methods for transfer status services
 *
 * */


@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer_status")
public class TransferStatusController {

    private final TransferStatusService transferStatusService;

    @GetMapping()
    public List<TransferStatus> getAllTransferStatus() {
        return transferStatusService.findAllTransferStatus();
    }

    @GetMapping("/{id}")
    public Optional<TransferStatus> getTransferStatusById(@PathVariable int id) {
        Optional<Optional<TransferStatus>> transferStatus = Optional.ofNullable(transferStatusService.findTransferStatusById(id));
        if (transferStatus.isPresent()) {
            return transferStatus.get();
        } else {
            throw new NoSuchElementException("No transfer status with id: " + id);
        }
    }
}
