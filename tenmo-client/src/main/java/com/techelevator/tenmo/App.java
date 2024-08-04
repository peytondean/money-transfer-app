package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * client side Application
 *
 * */


public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private static final int TRANSFER_REQUEST = 1;
    private static final int TRANSFER_SEND = 2;
    private static final int TRANSFER_PENDING = 1;
    private static final int TRANSFER_APPROVED = 2;
    private static final int TRANSFER_REJECTED = 3;

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService();
    private final UserService userService = new UserService(API_BASE_URL);
    private final TransferService transferService = new TransferService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    /**
     * Prints out current user's balance
     */
    private void viewCurrentBalance() {
		Account account = accountService.getAccount(currentUser.getUser().getId(), currentUser.getToken());
        System.out.println(account.toString());
	}

	private void viewTransferHistory() {
		int userId = currentUser.getUser().getId();
        String token = currentUser.getToken();
        List<Transfer> transfers = transferService.getTransfersByUser(userId, token);

        if(transfers.isEmpty()){
            System.out.println("Sorry, you have no previous transfers to view at this time. ");
        }else {
            consoleService.printTransfers(transfers);
            int transferId = consoleService.promptForTransferId();
            Transfer transfer = validTransfer(transferId, transfers);

            if(transferId != 0 ){
                if(transfer == null){
                    System.out.println("You entered a transfer that doesn't exist. Please try again. ");
                } else {
                    viewTransferInfo(transferId);
                }
            }

        }
	}
    // Helper method to make sure transfer to request info about exists
    private Transfer validTransfer(int transferId, List<Transfer> transfers) {
        for (Transfer transfer : transfers) {
            if (transfer.getTransferId() == transferId) {
                return transfer;
            }
        }
        return null;
    }




    private void viewTransferInfo(int transferId){
        String token = currentUser.getToken();
        Transfer transfer = transferService.getTransferById(transferId, token);
        consoleService.printTransferInfo(transfer);
    }

    /**
     * Prints the current user's pending requests and lets them approve or reject them.
     */
	private void viewPendingRequests() {
        Account account = accountService.getAccount(currentUser.getUser().getId(), currentUser.getToken());
        Transfer[] transfers = consoleService.printPendingTransfers(transferService.getPendingTransfers(account.getId(), currentUser.getToken()));
        //if there is no pending transfers leave this method
        if (transfers != null) {
            // prompt to see which transfer to approve or reject
            int transferId = consoleService.promptForApproveOrReject();
            if (transferId != 0) {
                // Goes through helper method
                Transfer transfer = validPendingTransfer(transferId, transfers);
                if (transfer != null) {
                    int updatedTransferStatus = consoleService.promptForDecision();
                    if (updatedTransferStatus != 0) {
                        transferService.updateTransferStatus(transfer, updatedTransferStatus,
                                accountService.getAccount(currentUser.getUser().getId(), currentUser.getToken()), currentUser.getToken());
                    }
                } else {
                    System.out.println("\nInvalid Transfer Id.");
                }
            }
        }
	}
    // Just a helper method to make sure the typed in transfer exists
    private Transfer validPendingTransfer(int transferId, Transfer[] transfers) {
        for (Transfer transfer : transfers) {
            if (transfer.getTransferId() == transferId) {
                return transfer;
            }
        }
        return null;
    }

	private void sendBucks() {
        List<User> users = userService.getAllUsers(currentUser.getToken());
        consoleService.printOutUsers(users);
        Integer userId = consoleService.promptForInt("Please enter the users id that you wish to send TE bucks to (side-note: enter 0 to cancel send TE Bucks): ");


        if(userId == 0){
            return;
        }

        if(userId == currentUser.getUser().getId()){
            System.out.println("Our Apologies, you cannot send money from yourself. please try again later. ");
        }else {
            BigDecimal amount = consoleService.promptForBigDecimal("Please enter the amount of TE Bucks you wish to send: ");
            try{
                BigDecimal one = new BigDecimal(1.00);
                if(amount.compareTo(one) >= 0){
                    Transfer transfer = transferService.sendTeBucks(currentUser.getUser().getId(), userId, amount, currentUser.getToken());
                    System.out.println("Congrats! Your transfer was successful! Here is the Info: " + transfer);
                }else if(amount.compareTo(one) <= 0){
                    System.out.println("Our Apologies, the value cannot be less than one");
                }
            }catch (Exception e){
                System.out.println("Our apologies! Something went wrong!!! here is the info: " + e.getMessage());
                System.out.println("The above error likely indicates that you tried to send more money you had, ");
                System.out.println("or you tried to send zero TE bucks or negative TE bucks.");
                System.out.println(" It is also possible you tried to send money to a user that doesn't yet exist.");
            }
        }

		
	}

    // Helper method to make sure the typed in user exists for Request TE Bucks
    private User validUser(int userId, List<User> users) {
        String token = currentUser.getToken();
        for (User user: users){
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

	private void requestBucks() {
        List<User> users = userService.getAllUsers(currentUser.getToken());
        consoleService.printOutUsers(users);
        int userId = consoleService.promptForInt("Please enter the users id that you wish to request TE bucks from (side-note: enter 0 to cancel request TE Bucks): ");
        if(userId == 0){
            return;
        }
        User user = validUser(userId, users);
        if(user == null){
            System.out.println("User Does not exist, please try again. ");
        }else{
            if(userId == currentUser.getUser().getId()){
                System.out.println("Our Apologies, you cannot request money from yourself. please try again later. ");
            }else {
                BigDecimal amount = consoleService.promptForBigDecimal("Please enter the amount of TE Bucks you wish to request: ");
                try{
                    BigDecimal one = new BigDecimal(1.00);
                    if(amount.compareTo(one) >= 0){
                        Transfer transfer = transferService.requestTeBucks(userId, currentUser.getUser().getId(), amount, currentUser.getToken());
                        System.out.println();
                        System.out.println();
                        System.out.println("Congrats! Your transfer request was successfully saved!" );
                        System.out.println(" We are waiting for the user to approve the request on their end.  Here is the Info: " + transfer );
                    }else if(amount.compareTo(one) <= 0){
                        System.out.println("Our apologies, the requested amount must be greater than or equal to 1. Please try again");
                    }


                } catch (Exception e){
                    System.out.println("Our apologies! Something went wrong!!! here is the info: " + e.getMessage());
                }
        }

        }

	}

}
