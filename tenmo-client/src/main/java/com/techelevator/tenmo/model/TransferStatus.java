package com.techelevator.tenmo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/*
 * Client side Transfer Status model
 *
 * */
@Getter
@Setter
@RequiredArgsConstructor
public class TransferStatus {
    private int transferStatusId;
    private String transferStatusDescription;
}
