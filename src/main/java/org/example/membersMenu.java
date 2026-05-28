package org.example;
import java.util.*;
import java.sql.*;
import java.io.*;
import java.util.UUID;

public class membersMenu {


    public void memberS(Scanner scan, String currentUserEmail){
        //String choice = scan.next();
        //scan.nextLine();

        while(true) {


            System.out.println("===Welcome to the Library===");
            System.out.println("Please Enter the option!");
            System.out.println("#1 My Profile");
            System.out.println("#2 My Books");
            System.out.println("#3 New Books");
            System.out.println("#4 Log out");
            String choice = scan.nextLine();
            //equalsIgnoreCase("Q")

            if (choice.equalsIgnoreCase("My PROFILE")||
                    choice.equalsIgnoreCase("MyPROFILE") ||
                    choice.equalsIgnoreCase("1") ) {

                    userProfile up = new userProfile();
                    up.profile(scan, currentUserEmail);

            } else if (choice.equalsIgnoreCase("My Books")||
                    choice.equalsIgnoreCase("MyBooks") ||
                    choice.equalsIgnoreCase("2") ){

                userBook uB = new userBook();
                uB.uBooks(scan, currentUserEmail);

            } else if (choice.equalsIgnoreCase("NEW Books")||
                    choice.equalsIgnoreCase("NEWBooks") ||
                    choice.equalsIgnoreCase("3") ){

                theLibrary tB = new theLibrary();
                tB.userLibrary(scan, currentUserEmail);


            } else if (choice.equalsIgnoreCase("LOG OUT")||
                    choice.equalsIgnoreCase("LOGOUT") ||
                    choice.equalsIgnoreCase("4") ){
                //sign out, return to pervious menu.
              System.out.println("Logging out...");
              return;


            }else {
                System.out.println("Please enter the correct command.");
            }

        }





}
}
