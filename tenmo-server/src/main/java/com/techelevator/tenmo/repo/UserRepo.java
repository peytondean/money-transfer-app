package com.techelevator.tenmo.repo;

import com.techelevator.tenmo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * User JPA Repo
 *
 * */



@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findUserByUsername(String user);

}
