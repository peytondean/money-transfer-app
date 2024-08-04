package com.techelevator.tenmo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/*
 * Client side Transfer Type model
 *
 * */

@Getter
@Setter
@RequiredArgsConstructor
public class TransferType {
    private int transferTypeId;
    private String transferTypeDescription;
}
