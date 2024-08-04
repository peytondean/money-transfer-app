package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/*
 * client side user service class to send http requests from the client to the server and get the response to use in App.java
 *
 * */


@AllArgsConstructor
public class UserService {

    private final String API_BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<User> getAllUsers(String token) {
        String url = API_BASE_URL + "users";
        User[] arrayOfUsers = restTemplate.exchange(url, HttpMethod.GET, makeAuthEntity(token), User[].class).getBody();
        return Arrays.asList(arrayOfUsers);
    }

    public User getUserById(int userId, String token){
        User user = null;
        try{
            user = restTemplate.exchange(API_BASE_URL + "users/" + userId, HttpMethod.GET, makeAuthEntity(token), User.class).getBody();
        }catch (RestClientResponseException e){
            BasicLogger.log(e.getMessage());
        }
        return user;
    }

    private HttpEntity<Void> makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }


}
