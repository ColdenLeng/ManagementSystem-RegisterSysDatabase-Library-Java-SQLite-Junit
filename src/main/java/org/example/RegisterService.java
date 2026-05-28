package org.example;
import java.util.*;
import java.util.UUID;

public class RegisterService {

    public void signUp(Scanner scan){
        System.out.println("Please enter your email");
        String email = scan.nextLine().trim();

        if (email.isEmpty()) {
            System.out.println("Email cannot be empty.");
            return;
        }

        if (dataBaseManager.emailExists(email)) {
            System.out.println("This username/email has been registered.");
            return;
        }

        String password1;
        String password2;

        // password while check
        while(true) {

            //check if the password 1 matched all requirements, then jump to the password 2.

            System.out.println("Please enter your password");
            password1 = scan.nextLine();

            if(password1.length() < 8){
                System.out.println("The password should at least 8 characters");
                continue;
            }

            if(!hasSpecialCharacter(password1)){
                System.out.println("The password should contain at least one special character");
                continue;
            }



            System.out.println("Please enter your password again");
            password2 = scan.nextLine();

            if(!password1.equals(password2)){
                System.out.println("The password is not matched, please enter again");
                continue;
            }

            System.out.println("Password accepted.");
            break;
        }

        String fName;
        String lName;

            System.out.println("Please enter your First Name");
            fName = scan.nextLine();

            System.out.println("Please enter your Last Name");
            lName = scan.nextLine();


        System.out.println("Birthday");
        String birthDay = scan.nextLine();

        String uid = UUID.randomUUID().toString();
        String passwordHashValue = passWordHash.hashPassword(password1);


        boolean success = dataBaseManager.insertMember(
                uid,
                email,
                passwordHashValue,
                fName,
                lName,
                birthDay
        );

        if (success) {
            System.out.println("Sign up successful. Member saved to database.");
        } else {
            System.out.println("Sign up failed.");
        }
    }


    private boolean hasSpecialCharacter(String input) {
        return input.matches(".*[^a-zA-Z0-9].*");
    }

    private boolean forbiddenWord(String input) {
        return true;
    }

    //============CSV IO ENTER-IN

    //============SQLlite



}

