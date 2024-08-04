package com.techelevator.tenmo.repo;

import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Transfer Status JPA Repo
 *
 * */


@Repository
public interface TransferStatusRepo extends JpaRepository<TransferStatus, Integer> {
}
