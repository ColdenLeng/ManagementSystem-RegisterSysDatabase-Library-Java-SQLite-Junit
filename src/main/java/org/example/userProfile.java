package org.example;
import java.util.*;
import java.sql.*;
import java.io.*;
import java.util.UUID;

public class userProfile {

    //membersMenu mM = new membersMenu();

    public void profile(Scanner scan, String currentUserEmail){

        while(true){

            String[] profileInfo = dataBaseManager.findUserProfileByEmail(currentUserEmail);

            //MemberProfile profile = dataBaseManager.findUserProfileByEmail(currentUserEmail);
            System.out.println("");
            System.out.println("Enter Change to change the password.");
            System.out.println("");

            //lS.login(scan);

            if(profileInfo != null){
                System.out.println("Your email address: " + profileInfo[0]);
                System.out.println("Your Registered name: "+ profileInfo[1] + " " + profileInfo[2]);
                System.out.println("Your date of birth:" + profileInfo[3]);

            } else {

                System.out.println("Profile not found.");
            }
                System.out.println("");
                System.out.println("Enter Q or Quit to go back to the previous page.");

                String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("Change")) {
                changePassword(scan, currentUserEmail);

            } else if(choice.equalsIgnoreCase("Quit") || choice.equalsIgnoreCase("Q")){
                return;
            } else {
                System.out.println("Please enter the correct command or enter Q to go back..");
            }
//changePassword(scan, currentUserEmail);
        }

    }

    private void changePassword(Scanner scan, String currentUserEmail){
        System.out.println("Change the password.");
        System.out.println("Enter your pervious password:");
        String oldPassword = scan.nextLine();

        String oldPasswordHash = String.valueOf(oldPassword.hashCode());

        String storedPasswordHash = dataBaseManager.findPasswordHashByEmail(currentUserEmail);

        if(storedPasswordHash == null) {
            System.out.println("Account not found.");
            return;
        }

        if(!oldPasswordHash.equals(storedPasswordHash)) {
            System.out.println("Current password is incorrect.");
            return;

        }

        System.out.println("Please enter your new password:");
        String newPassword = scan.nextLine();

        System.out.println("Please confirm your new password.");
        String confirmPassword = scan.nextLine();

        if(!newPassword.equals(confirmPassword)){
            System.out.println("New password do not match.");
            return;
        }

        String newPasswordHash = String.valueOf(newPassword.hashCode());

        boolean updated = dataBaseManager.updatePasswordByEmail(currentUserEmail, newPasswordHash);

        if(updated){
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Password change failed.");
        }
    }
}
