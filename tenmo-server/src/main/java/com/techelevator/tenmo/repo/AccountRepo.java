package com.techelevator.tenmo.repo;

import com.techelevator.tenmo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * Account JPA Repo
 *
 * */


@Repository
public interface AccountRepo extends JpaRepository<Account,Integer> {
    Optional<Account> findAccountByUserId(int id);
    List<Account> findAccountsByUserId(int id);
}
