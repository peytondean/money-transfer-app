package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.service.TransferService;
import com.techelevator.tenmo.service.TransferStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
/*
* Controller Methods for transfer services
*
* */


@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;
    private final TransferStatusService transferStatusService;

    @GetMapping("")
    public List<Transfer> getTransfers() {
        return transferService.getTransfers();
    }

    @GetMapping("/{id}")
    public Optional<Transfer> getTransferById(@PathVariable int id) {

            return transferService.getTransferById(id);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transfer>> getTransfersByUserId(@PathVariable int userId){
        List<Transfer> transfers = transferService.getTransfersByUserId(userId);
        if(transfers.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transfers);
    }

    @PostMapping("/create")
    public ResponseEntity<Transfer> createTransfer(@Valid @RequestBody Transfer transfer, @RequestParam Integer transferTypeId, @RequestParam Integer transferStatusId) {
        Transfer newTransfer = transferService.createTransfer(transfer, transferTypeId,transferStatusId);
        return ResponseEntity.ok(newTransfer);
    }

    @PostMapping("/send")
    public ResponseEntity<Transfer> sendTeBucks(@RequestParam Integer userFrom, @RequestParam Integer userTo, @RequestParam BigDecimal amount) {
        Transfer transfer = transferService.sendTeBucks(userFrom, userTo, amount);
        return ResponseEntity.ok(transfer);
    }

    //TODO William!!! If you figure out how to do the request without a dedicated endpoint this can be removed.
    @PostMapping("/request")
    public ResponseEntity<Transfer> requestTeBucks(@RequestParam Integer userFrom, @RequestParam Integer userTo, @RequestParam BigDecimal amount){

        Transfer transfer = transferService.RequestTeBucks(userFrom, userTo, amount);
        return ResponseEntity.ok(transfer);

    }


    @DeleteMapping("/{id}")
    public void deleteTransfer(@PathVariable Integer id){
        transferService.deleteTransfer(id);
    }

    @GetMapping("/{id}/pending")
    public ResponseEntity<List<Transfer>> getPendingTransfers(@PathVariable int id) {
        Optional<TransferStatus> transferStatusOpt = transferStatusService.findTransferStatusById(1);
        TransferStatus transferStatus = transferStatusOpt.get();
        List<Transfer> transfers = transferService.getPendingTransfersById(id, transferStatus);
        if(transfers.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transfers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transfer> updateTransfer(@PathVariable int id, @RequestParam int transferStatus) {
        Optional<Transfer> transfer = getTransferById(id);
        if (transfer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean updated = false;
        if (transferStatus == 1) {
            transferService.approveTransfer(id);
        } else if (transferStatus == 2) {
            transferService.rejectTransfer(id);
        }

        return ResponseEntity.noContent().build();
    }


}
