package com.techelevator.tenmo.repo;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/*
 * Transfer JPA Repo
 *
 * */


@Repository
public interface TransferRepo extends JpaRepository<Transfer,Integer> {
    List<Transfer> findByAccountFromIn(List<Integer> accountFromId);

    List<Transfer> findByAccountToIn(List<Integer> accountToId);

    List<Transfer> findByAccountFromAndTransferStatus(int accountFrom, TransferStatus transferStatus);
}
