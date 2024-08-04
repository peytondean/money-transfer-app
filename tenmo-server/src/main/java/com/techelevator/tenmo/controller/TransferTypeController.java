package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.service.TransferTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/*
 * Controller Methods for transfer type services
 *
 * */


@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer_type")
public class TransferTypeController {

    private final TransferTypeService transferTypeService;

    @GetMapping()
    public List<TransferType> getAllTransferTypes() {
        return transferTypeService.findAllTransferTypes();
    }

    @GetMapping("/{id}")
    public Optional<Optional<TransferType>> getTransferTypeById(@PathVariable int id) {
        Optional<Optional<Optional<TransferType>>> transferType = Optional.ofNullable(Optional.ofNullable(transferTypeService.findTransferTypeById(id)));
        if (transferType.isPresent()) {
            return transferType.get();
        } else {
            throw new NoSuchElementException("No transfer type with id: " + id);
        }
    }
}
