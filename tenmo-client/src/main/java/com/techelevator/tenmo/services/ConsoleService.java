package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

/*
 * client side Console service to organize console inputs and outputs to use in App.java
 *
 * */

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printOutUsers(List<User> users) {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("                                   Users                                         ");
        System.out.println("=================================================================================");
        System.out.println("User Id                 User Name                                                ");
        System.out.println("---------------------------------------------------------------------------------");
        for (User user: users){
            System.out.println(user.getId() + "                    " + user.getUsername());
        }
        System.out.println("===========================================================================================");
        System.out.println("_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_");
        System.out.println();
    }

    public void printTransfers(List<Transfer> transfers){
        if(transfers == null || transfers.isEmpty()){
            System.out.println("There was no transfer history to view for the current user.");
        }
        System.out.println("============================================================================================");
        System.out.println("********************************Transfers***************************************************");
        System.out.println("=============================================================================================");
        System.out.println("Transfer id                From                    To                       Amount           ");
        System.out.println("=============================================================================================");
        for (Transfer transfer: transfers){
            System.out.println(transfer.getTransferId() + "                 " + transfer.getAccountFrom() + "                      " + transfer.getAccountTo() + "                       $" + transfer.getAmount());
        }
        System.out.println("=============================================================================================");
        System.out.println("*********************************************************************************************");
        System.out.println("=============================================================================================");
        System.out.println();

    }

    public int promptForTransferId(){
        return promptForInt("Enter the id of the transfer you would like info(side note: use 0 to cancel and go back to the main menu): ");
    }

    public void printTransferInfo(Transfer transfer){
        System.out.println("=============================================================================================");
        System.out.println("********************************Transfer Information:****************************************");
        System.out.println("=============================================================================================");
        System.out.println("Id: " + transfer.getTransferId());
        System.out.println("From: " + transfer.getAccountFrom());
        System.out.println("To: " + transfer.getAccountTo());
        System.out.println("Amount: $" + transfer.getAmount());
        System.out.println("Type: " + (transfer.getTransferType() != null ? transfer.getTransferType().getTransferTypeDescription() : "N/A"));
        System.out.println("Status: " + (transfer.getTransferStatus() != null ? transfer.getTransferStatus().getTransferStatusDescription() : "N/A"));
        System.out.println("=============================================================================================");
        System.out.println("*********************************************************************************************");
        System.out.println("=============================================================================================");
        System.out.println();
    }

    /**
     * Prints out a list of the current users pending transfers to approve or reject
     * @param transfers A list of pending transfers
     * @return The list of transfers to select to approve or reject
     */
    public Transfer[] printPendingTransfers(Transfer[] transfers) {
        if (transfers == null) {
            System.out.println("\nNo pending transfers.");
            return null;
        } else {
            System.out.println("-------------------------------------------");
            System.out.println("Pending Transfers");
            System.out.println("ID          To                     Amount");
            System.out.println("-------------------------------------------");

            for (Transfer transfer : transfers) {
                System.out.printf("%-12d %-22s $ %7.2f%n", transfer.getTransferId(), transfer.getAccountTo(), transfer.getAmount());
            }

            System.out.println("---------");
            return transfers;
        }
    }

    public int promptForApproveOrReject() {
        return promptForInt("Please enter transfer ID to approve/reject (0 to cancel): ");
    }

    public int promptForDecision() {
        System.out.println("1: Approve\n2: Reject\n0: Don't approve or reject");
        System.out.println("---------");
        return promptForInt("Please choose an option: ");
    }

}
