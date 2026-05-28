package org.example;
import java.util.*;
import java.sql.*;
import java.io.*;
import java.util.UUID;
public class loginService {
    membersMenu mm = new membersMenu();

    public boolean login(Scanner scan) {
        System.out.println("======== Log In ========");

        System.out.println("Please enter your email/username:");
        String email = scan.nextLine();

        System.out.println("Please enter your password:");
        String password = scan.nextLine();

        String storedPasswordHash = dataBaseManager.findPasswordHashByEmail(email);

        if (storedPasswordHash == null) {
            System.out.println("Email not found.");
            return false;
        }

        if (passWordHash.checkPassword(password, storedPasswordHash)) {
            System.out.println("Login Successful!");
            mm.memberS(scan, email);
            return true;
        } else {
            System.out.println("Wrong password.");
            return false;
        }
    }

    private void returnToMainMenu() {
        System.out.println("Returning to the Main menu");

        try {
            for (int i = 3; i >= 1; i--) {
                System.out.println("..." + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Return Interrupted.");
        }
    }
}
