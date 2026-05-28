package org.example;
import java.util.*;

public class userBook {

    public void uBooks(Scanner scan, String currentUserEmail) {

        while (true) {
            System.out.println();
            System.out.println("======== My Books ========");
            System.out.println("#1 View my current borrowed books");
            System.out.println("#2 View my borrow history");
            System.out.println("#3 Back");

            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("1")) {
                dataBaseManager.viewMyCurrentBooks(currentUserEmail);

            } else if (choice.equalsIgnoreCase("2")) {
                dataBaseManager.viewMyBorrowHistory(currentUserEmail);

            } else if (choice.equalsIgnoreCase("3")) {
                System.out.println("Back to Member Menu.");
                break;

            } else {
                System.out.println("Please enter the correct command.");
            }
        }
    }
}
