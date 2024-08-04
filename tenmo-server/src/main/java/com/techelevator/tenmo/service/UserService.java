package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.User;

import java.util.List;
import java.util.Optional;

/*
 * User Service JPA interface
 *
 * */

public interface UserService {
    //  Generic business logic that we may or may not need
    List<User> findAllUsers();
    Optional<User> findUserById(int id);
    User saveUser(User user);
    User updateUser(int id, User user);
    void deleteUserById(int id);

    User findUserByUsername(String user);

}
