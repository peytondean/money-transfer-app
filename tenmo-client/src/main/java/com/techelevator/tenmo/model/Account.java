package com.techelevator.tenmo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/*
 * Client side Account model
 *
 * */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private BigDecimal balance;
    private int id;


    @Override
    public String toString() {
        return "Your current account balance is: $" + balance;
    }
}
