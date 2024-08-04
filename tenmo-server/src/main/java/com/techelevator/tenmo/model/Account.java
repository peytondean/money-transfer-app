package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/*
 * JPA Account Model
 *
 * */


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal balance;

    @JsonIgnore
    @OneToMany(mappedBy = "accountFrom", cascade = CascadeType.ALL)
    private Set<Transfer> transfersFrom;

    @JsonIgnore
    @OneToMany(mappedBy = "accountTo", cascade = CascadeType.ALL)
    private Set<Transfer> transfersTo;
}