# A Java-based console book management system built with SQLite, JDBC, and JUnit.

This project allows users to register, log in, view their profile, browse books, borrow books, return books, and check borrowing history. It also includes an admin panel for viewing members and managing book inventory.

Features
User registration and login
Password hashing with BCrypt
SQLite database storage
Admin book management
Book borrowing and returning
Borrow history tracking
Basic JUnit testing support
Tech Stack
Java
SQLite
JDBC
JUnit
Maven
Git / GitHub
Project Purpose

This project was built to practice Java object-oriented programming, database integration, authentication logic, CRUD operations, and software testing.

How to Run
Clone the repository.
Open the project in IntelliJ IDEA.
Make sure Maven dependencies are loaded.
Run Main.java for the user system.
Run adminMainCtrl.java for the admin system.


# Password Hash Test

This test file uses JUnit to verify the password hashing logic in the Library Management System. It checks that hashed passwords are not stored as plain text, correct passwords can be verified successfully, wrong passwords fail verification, and null password inputs are handled safely.

