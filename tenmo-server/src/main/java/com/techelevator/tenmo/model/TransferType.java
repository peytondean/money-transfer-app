package com.techelevator.tenmo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/*
 * Transfer Type JPA Model
 *
 * */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transfer_type")
public class TransferType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_type_id")
    private Integer transferTypeId;

    @Column(name = "transfer_type_desc", length = 10, nullable = false)
    private String transferTypeDescription;
}
