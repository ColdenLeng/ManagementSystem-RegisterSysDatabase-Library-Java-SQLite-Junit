package org.example;
import java.util.*;
import java.sql.*;
import java.io.*;

public class systemInfo{
    String choice;

    public void inFo(Scanner scan) {

        System.out.println("=====System=====");
        System.out.println("");

        System.out.println("Author: Yu Leng");
        System.out.println("Version: 0.1");
        System.out.println("Type: Missing Project");
        System.out.println("");

        System.out.println("Press Q return to the main menu.");

        while(true) {
            choice = scan.nextLine();

            if (choice.equalsIgnoreCase("Q")) {
                System.out.println("Returning to the Main menu");
                countDownReturn();

                return;
            } else {
                System.out.println("Please enter the correct key.");
            }
        }
    }

    public void countDownReturn(){
        try{
            System.out.println("Returning to the main menu in 3...");
            Thread.sleep(1000);

            System.out.println("Returning to the main menu in 2...");
            Thread.sleep(1000);

            System.out.println("Returning to the main menu in 1...");
            Thread.sleep(1000);


        }catch(InterruptedException e){
            System.out.println("Wrong Time Error!");

        }

    }
}
