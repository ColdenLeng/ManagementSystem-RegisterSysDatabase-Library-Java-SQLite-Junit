package org.example;
import java.util.*;

public class adminMainCtrl {
    public static void main(String[] args) {

        dataBaseManager.initializeDatabase();
        Scanner scan = new Scanner(System.in);
        adminMainCtrl adminCtrl = new adminMainCtrl();
        systemInfo sI = new systemInfo();

        while (true) {

            System.out.println("Welcome to the admin CTRL");
            System.out.println("#1 Admin Log in");
            System.out.println("#2 Quit");
            System.out.println("#3 System INFO");

            String choice = scan.next();
            scan.nextLine();

            if (choice.equalsIgnoreCase("login") || choice.equalsIgnoreCase("1")) {
                adminCtrl.adLogin(scan);

            } else if (choice.equalsIgnoreCase("quit") || choice.equalsIgnoreCase("2")) {
                System.out.println("GoodBye");
                break;

            } else if (choice.equalsIgnoreCase("systeminfo") || choice.equalsIgnoreCase("3")) {
                sI.inFo(scan);
                //

            } else {
                System.out.println("Please Enter the correct option.");
            }

        }
    }

    public boolean adLogin(Scanner scan) {

        System.out.println("======== Admin Login ========");

        System.out.println("Please enter admin username:");
        String username = scan.nextLine();

        System.out.println("Please enter admin password:");
        String password = scan.nextLine();

        if (username.equals("admin") && password.equals("admin")) {
            System.out.println("Admin login successful!");

            //jump to the adminMenu()
            adminMenu(scan);

            return true;
        } else {
            System.out.println("Wrong admin username or password.");
            return false;
        }
    }

    public void adminMenu(Scanner scan) {

        while (true) {
            System.out.println();
            System.out.println("======== Admin Menu ========");
            System.out.println("Select an Option:");
            System.out.println("#1 Browse Members information");
            System.out.println("#2 Manage Library");
            System.out.println("#3 Quit");

            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("1")) {
                // go to browse members information or search members
                dataBaseManager.viewAllMembersForAdmin(scan);

            } else if (choice.equalsIgnoreCase("2")) {
                manageBooks(scan);

            } else if (choice.equalsIgnoreCase("3")) {
                System.out.println("Good Bye");
                break;

            } else {
                System.out.println("Please enter a correct option.");
            }
        }
    }


    public void manageBooks(Scanner scan) {

        while (true) {
            System.out.println();
            dataBaseManager.viewAllBooksOnly();

            System.out.println();
            System.out.println("======== Manage Books ========");
            System.out.println("#1 Add new book");
            System.out.println("#2 Delete book");
            System.out.println("#3 Refresh book list");
            System.out.println("Q  Back to Admin Library Menu");

            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("1")) {
                addBookFromAdmin(scan);

            } else if (choice.equalsIgnoreCase("2")) {
                deleteBookFromAdmin(scan);

            } else if (choice.equalsIgnoreCase("3")) {
                System.out.println("Refreshing book list...");

            } else if (choice.equalsIgnoreCase("q") || choice.equalsIgnoreCase("quit")) {
                System.out.println("Back to Admin Library Menu.");
                break;

            } else {
                System.out.println("Please enter a correct option.");
            }
        }
    }

    public void deleteBookFromAdmin(Scanner scan) {
        System.out.println();
        System.out.println("======== Delete Book ========");

        System.out.println("Enter Book ID to delete:");
        String bookId = scan.nextLine();

        boolean success = dataBaseManager.deleteBook(bookId);

        if (success) {
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book not found or delete failed.");
        }
    }

    public void addBookFromAdmin(Scanner scan) {
        System.out.println();
        System.out.println("======== Add Book ========");

        System.out.println("Enter Book ID:");
        String bookId = scan.nextLine();

        int quantity;

        while (true) {
            System.out.println("Enter Quantity:");

            try {
                quantity = Integer.parseInt(scan.nextLine());

                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        boolean success = dataBaseManager.addBook(bookId, quantity);

        if (success) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Failed to add book.");
        }
    }

}


