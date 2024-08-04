package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/*
 * Transfer Status JPA Model
 *
 * */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transfer_status")
public class TransferStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_status_id")
    private Integer transferStatusId;

    @Column(name = "transfer_status_desc", nullable = false)
    private String transferStatusDescription;
}