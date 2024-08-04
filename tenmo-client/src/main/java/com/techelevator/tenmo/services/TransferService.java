package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * client side transfer service class to send http requests from the client to the server and get the response to use in App.java
 *
 * */

@Service
@AllArgsConstructor
public class TransferService {

    private final String API_BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();
    private final int APPROVED_STATUS = 1;
    private final int REJECTED_STATUS = 2;

    // Sends money from one user to another, passes in User id's, from and to, the amount of money sending, and the authentication token.
    //Uses Parameters to send the HTTP request in the URL
    public Transfer sendTeBucks(Integer userFrom, Integer userTo, BigDecimal amount, String token) {
        String url = API_BASE_URL + "transfer/send?userFrom=" + userFrom + "&userTo=" + userTo + "&amount=" +amount;
        return restTemplate.exchange(url, HttpMethod.POST, makeAuthEntity(token), Transfer.class).getBody();
    }

    public List<Transfer> getTransfersByUser(int userId, String token){
        List<Transfer> transfers = new ArrayList<>();
        try {
            Transfer[] transArr = restTemplate.exchange(API_BASE_URL + "transfer/user/" + userId, HttpMethod.GET, makeAuthEntity(token), Transfer[].class).getBody();
            if(transArr != null){
                transfers = Arrays.asList(transArr);
            }
        }catch (RestClientResponseException e){
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer getTransferById(int transferId, String token){
        Transfer transfer = null;
        try{
            transfer = restTemplate.exchange(API_BASE_URL + "transfer/" + transferId, HttpMethod.GET, makeAuthEntity(token), Transfer.class).getBody();
        }catch (RestClientResponseException e){
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    public Transfer[] getPendingTransfers(int id, String token) {
        Transfer[] transfers = null;

        try {
            transfers = restTemplate.exchange(API_BASE_URL + "transfer/" + id + "/pending", HttpMethod.GET, makeAuthEntity(token), Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    // Creates an authenticated HTTP entity with the passed in token, use this in all requests sent from the client to the server.
    private HttpEntity<Void> makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

    public Transfer requestTeBucks(Integer userFrom, Integer userTo, BigDecimal amount, String token) {
        String url = API_BASE_URL + "transfer/request?userFrom=" + userFrom + "&userTo=" + userTo + "&amount=" +amount;

        try {
            return restTemplate.exchange(url, HttpMethod.POST, makeAuthEntity(token), Transfer.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
            return null;
        }
    }

    public void updateTransferStatus(Transfer transfer, int updatedTransferStatus, Account account, String token) {
        int comparison = transfer.getAmount().compareTo(account.getBalance());

        if (APPROVED_STATUS == updatedTransferStatus && comparison > 0) {
            System.out.println("\nNot enough TEbucks to approve request.");
        } else {
            try {
                restTemplate.exchange(API_BASE_URL + "transfer/" + transfer.getTransferId() + "/?transferStatus=" + updatedTransferStatus, HttpMethod.PUT, makeAuthEntity(token), Transfer.class).getBody();
                System.out.println("\nSuccess!");
            } catch (RestClientResponseException e) {
                BasicLogger.log(e.getMessage());
            }
        }
    }
}
