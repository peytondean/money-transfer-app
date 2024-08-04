package com.techelevator.tenmo.repo;

import com.techelevator.tenmo.model.TransferType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * Transfer Type JPA Repo
 *
 * */



@Repository
public interface TransferTypeRepo extends JpaRepository<TransferType, Integer> {
}
