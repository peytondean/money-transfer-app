package com.techelevator.tenmo.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/*
 * Transfer JPA Model
 *
 * */


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Integer transferId;



    @JoinColumn(name = "account_from", nullable = false)
    private Integer accountFrom;


    @JoinColumn(name = "account_to", nullable = false)
    private Integer accountTo;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transfer_type_id", nullable = false)
    private TransferType transferType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transfer_status_id", nullable = false)
    private TransferStatus transferStatus;
}
