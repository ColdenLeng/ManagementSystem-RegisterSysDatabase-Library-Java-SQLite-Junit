package org.example;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        dataBaseManager.initializeDatabase();
        Scanner scan = new Scanner(System.in);
        RegisterService aS = new RegisterService();
        loginService lS = new loginService();
        systemInfo sI = new systemInfo();

        while (true) {
            System.out.println("====Main Menu====");
            System.out.println("#1 LogIn");
            System.out.println("#2 SignUp");
            System.out.println("#3 Quit");
            System.out.println("#4 System info");

            String choice = scan.next();
            scan.nextLine();

            if (choice.equalsIgnoreCase("LogIn") || choice.equals("1")) {
                lS.login(scan);

            } else if (choice.equalsIgnoreCase("SignUp") || choice.equals("2")) {
                aS.signUp(scan);

            } else if (choice.equalsIgnoreCase("Quit") || choice.equals("3")) {
                System.out.println("Good Bye");
                break;

            } else if (choice.equalsIgnoreCase("System Info") || choice.equals("4")) {
                sI.inFo(scan);

            } else {
                System.out.println("Please enter or select the correct options");
            }


        }
    }

}
