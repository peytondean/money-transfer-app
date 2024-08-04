package com.techelevator.tenmo.model;

import lombok.*;

import java.math.BigDecimal;

/*
 * Client side transfer model
 *
 * */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Transfer {

    private Integer transferId;
    private Integer accountFrom;
    private Integer accountTo;
    private BigDecimal amount;
    private TransferType transferType;
    private TransferStatus transferStatus;

    @Override
    public String toString(){
        return "Id: " + transferId +
                "\nFrom: " + accountFrom +
                "\nTo: " + accountTo +
                "\nAmount: $" + amount +
                "\nType: " + transferType.getTransferTypeDescription() +
                "\nStatus: " + transferStatus.getTransferStatusDescription();
    }

}
