package org.example;
import java.util.*;

public class theLibrary {

    public void adminLibrary(Scanner scan){

            while (true) {
                System.out.println();
                System.out.println("======== Admin Library Menu ========");
                System.out.println("#1 View all books");
                System.out.println("#2 Add new book");
                System.out.println("#3 Delete book");
                System.out.println("#4 Back to Admin Menu");

                String choice = scan.nextLine();

                if (choice.equalsIgnoreCase("1")) {
                    System.out.println("View all books selected.");
                    viewAllBooks(scan);

                } else if (choice.equalsIgnoreCase("2")) {
                    System.out.println("Add new book selected.");
                    addBook(scan);

                } else if (choice.equalsIgnoreCase("3")) {
                    System.out.println("Delete book selected.");
                    deleteBook(scan);

                } else if (choice.equalsIgnoreCase("4")) {
                    System.out.println("Back to Admin Menu.");
                    break;

                } else {
                    System.out.println("Please enter a correct option.");
                }
            }
        }

    public void viewAllBooks(Scanner scan){
        System.out.println("This is view ALL Books");
        dataBaseManager.viewAllBooks(scan);

        }


    public void addBook(Scanner scan) {
        System.out.println();
        System.out.println("======== Add New Book ========");

        System.out.println("Enter book title:");
        String title = scan.nextLine();

        System.out.println("Enter book author:");
        String author = scan.nextLine();

        System.out.println("Enter book category:");
        String category = scan.nextLine();

        int quantity;

        while (true) {
            System.out.println("Enter book quantity:");

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

        String bookId = UUID.randomUUID().toString();
        String status = "available";

        boolean success = dataBaseManager.addBook(
                bookId,
                //title,
                //author,
                //category,
                //status,
                quantity
        );

        if (success) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Failed to add book.");
        }
    }

    public void deleteBook(Scanner scan) {
        System.out.println();
        System.out.println("======== Delete Book ========");

        System.out.println("Enter book ID you want to delete:");
        String bookId = scan.nextLine();

        boolean success = dataBaseManager.deleteBook(bookId);

        if (success) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book not found or delete failed.");
        }
    }

    public void userLibrary(Scanner scan, String currentUserEmail) {

        while (true) {
            System.out.println();
            System.out.println("======== Library ========");
            System.out.println("#1 View all books");
            System.out.println("#2 Search book");
            System.out.println("#3 Borrow book");
            System.out.println("#4 Return book");
            System.out.println("#5 Back");

            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("1")) {
                viewAllBooks(scan);

            } else if (choice.equalsIgnoreCase("2")) {
                System.out.println("Search book selected.");

            } else if (choice.equalsIgnoreCase("3")) {
                borrowBook(scan, currentUserEmail);

            } else if (choice.equalsIgnoreCase("4")) {
                returnBook(scan, currentUserEmail);

            } else if (choice.equalsIgnoreCase("5")) {
                System.out.println("Back to Member Menu.");
                break;

            } else {
                System.out.println("Please enter the correct command.");
            }
        }
    }

    public void borrowBook(Scanner scan, String currentUserEmail) {
        System.out.println();
        System.out.println("==== Borrow Book =====");

        System.out.println("Enter book ID you want to borrow:");
        String bookId = scan.nextLine();

        boolean success = dataBaseManager.borrowBook(bookId, currentUserEmail);

        if (success) {
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Failed to borrow book.");
        }
    }

    public void returnBook(Scanner scan, String currentUserEmail) {
        System.out.println();
        System.out.println("======== Return Book ========");

        System.out.println("Enter book ID you want to return:");
        String bookId = scan.nextLine();

        boolean success = dataBaseManager.returnBook(bookId, currentUserEmail);

        if (success) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Failed to return book.");
        }
    }

    }

