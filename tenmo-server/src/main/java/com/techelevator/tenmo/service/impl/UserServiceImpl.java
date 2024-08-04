package com.techelevator.tenmo.service.impl;

import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.repo.UserRepo;
import com.techelevator.tenmo.security.SecurityUtils;
import com.techelevator.tenmo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.crypto.password.PasswordEncoder.*;

/*
 * User Service JPA implementations
 *
 * */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
// this is a change.
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<User> findAllUsers() {

        return userRepo.findAll();
    }

    @Override
    public Optional<User> findUserById(int id) {

            return userRepo.findById(id);
    }

    @Override
    public User saveUser(User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setActivated(true);
        user.setAuthorities(user.getAuthorities().toString());
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setAccounts(user.getAccounts());
        return userRepo.save(user);
    }

    @Override
    public User updateUser(int id, User userInfo) {
        User oldUserInfo = userRepo.findById(id).orElse(null);
        if(oldUserInfo != null){
            if (userInfo.getPassword() != null){
                oldUserInfo.setPassword(userInfo.getPassword());
            }
            if (userInfo.getUsername() != null){
                oldUserInfo.setUsername(userInfo.getUsername());
            }
            if (userInfo.getPasswordHash() != null){
                oldUserInfo.setPasswordHash(userInfo.getPasswordHash());
            }
            if (userInfo.getAccounts() != null){
                oldUserInfo.getAccounts().clear();
                oldUserInfo.getAccounts().addAll(userInfo.getAccounts());
            }

            return userRepo.save(oldUserInfo);
        }else {
            throw new UsernameNotFoundException("we cannot find the user associated with the id entered");
        }
    }

    @Override
    public void deleteUserById(int id) {
        userRepo.deleteById(id);
    }

    @Override
    public User findUserByUsername(String user) {
        return userRepo.findUserByUsername(user);
    }

}
