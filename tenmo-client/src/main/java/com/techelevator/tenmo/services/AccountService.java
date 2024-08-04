package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/*
 * client side account service class to send http requests from the client to the server and get the response to use in App.java
 *
 * */

public class AccountService {

    public static final String API_BASE_URL = "http://localhost:8080/accounts/";

    private RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public List<Account> getAllAccounts(String token) {
        authToken = token;
        Account[] accounts = restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(), Account[].class).getBody();
        return Arrays.asList(accounts);
    }

    /**
     *
     * @param id An account id
     * @param token The JWT authToken
     * @return The account with the passed in account id
     */
    public Account getAccount(int id, String token) {
        Account account = null;
        authToken = token;
        try {
            ResponseEntity<Account> response = restTemplate.exchange(API_BASE_URL + id,
                    HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
