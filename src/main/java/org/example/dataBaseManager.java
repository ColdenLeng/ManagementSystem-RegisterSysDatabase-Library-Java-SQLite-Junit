package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class dataBaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:library.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void initializeDatabase() {
        createMembersTable();
        createBooksTable();
        createBorrowRecordsTable();
    }

    private static void createMembersTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS members (
                uid TEXT PRIMARY KEY,
                email TEXT UNIQUE NOT NULL,
                password_hash TEXT NOT NULL,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                birthday TEXT NOT NULL,
                role TEXT NOT NULL DEFAULT 'USER'
            );
            """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Members table is ready.");

        } catch (SQLException e) {
            System.out.println("Error creating members table.");
            System.out.println(e.getMessage());
        }
    }

    private static void createBooksTable() {
        String sql = """
    CREATE TABLE IF NOT EXISTS books (
        book_id TEXT PRIMARY KEY,
        quantity INTEGER NOT NULL DEFAULT 1,
        status TEXT NOT NULL DEFAULT 'available'
    );
    """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Books table is ready.");

        } catch (SQLException e) {
            System.out.println("Error creating books table.");
            System.out.println(e.getMessage());
        }
    }

    private static void createBorrowRecordsTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS borrow_records (
                record_id TEXT PRIMARY KEY,
                user_email TEXT NOT NULL,
                book_id TEXT NOT NULL,
                borrow_date TEXT NOT NULL,
                return_date TEXT,
                status TEXT NOT NULL
            );
            """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Borrow records table is ready.");

        } catch (SQLException e) {
            System.out.println("Error creating borrow records table.");
            System.out.println(e.getMessage());
        }
    }

    public static boolean returnBook(String bookId, String userEmail) {
        String checkRecordSql = """
            SELECT record_id
            FROM borrow_records
            WHERE book_id = ?
            AND user_email = ?
            AND status = 'borrowed';
            """;

        String updateBookSql = """
            UPDATE books
            SET quantity = quantity + 1,
            status = 'available'
             WHERE book_id = ?;
             """;

        String updateRecordSql = """
            UPDATE borrow_records
            SET status = 'returned',
                return_date = ?
            WHERE record_id = ?;
            """;

        try (Connection conn = getConnection()) {

            String recordId;

            // 1. Check whether this user borrowed this book
            try (PreparedStatement checkStmt = conn.prepareStatement(checkRecordSql)) {
                checkStmt.setString(1, bookId);
                checkStmt.setString(2, userEmail);

                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {
                    System.out.println("No active borrow record found for this book.");
                    return false;
                }

                recordId = rs.getString("record_id");
            }

            // 2. Update book status back to available
            try (PreparedStatement updateBookStmt = conn.prepareStatement(updateBookSql)) {
                updateBookStmt.setString(1, bookId);
                updateBookStmt.executeUpdate();
            }

            // 3. Update borrow record
            try (PreparedStatement updateRecordStmt = conn.prepareStatement(updateRecordSql)) {
                String returnDate = LocalDate.now().toString();

                updateRecordStmt.setString(1, returnDate);
                updateRecordStmt.setString(2, recordId);

                updateRecordStmt.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error returning book.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void viewMyCurrentBooks(String userEmail) {
        String sql = """
            SELECT b.book_id, r.borrow_date, r.status
            FROM borrow_records r
            JOIN books b ON r.book_id = b.book_id
            WHERE r.user_email = ?
            AND r.status = 'borrowed';
            """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userEmail);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println();
                System.out.println("======== My Current Borrowed Books ========");

                boolean hasBook = false;

                while (rs.next()) {
                    hasBook = true;

                    System.out.println("Book ID: " + rs.getString("book_id"));
                    //System.out.println("Title: " + rs.getString("title"));
                    //System.out.println("Author: " + rs.getString("author"));
                    //System.out.println("Category: " + rs.getString("category"));
                    System.out.println("Borrow Date: " + rs.getString("borrow_date"));
                    System.out.println("Status: " + rs.getString("status"));
                    System.out.println("------------------------------------------");
                }

                if (!hasBook) {
                    System.out.println("You have no currently borrowed books.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error viewing current borrowed books.");
            System.out.println(e.getMessage());
        }
    }

    public static void viewMyBorrowHistory(String userEmail) {
        String sql = """
            SELECT b.book_id,
                   r.borrow_date, r.return_date, r.status
            FROM borrow_records r
            JOIN books b ON r.book_id = b.book_id
            WHERE r.user_email = ?
            ORDER BY r.borrow_date DESC;
            """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userEmail);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println();
                System.out.println("======== My Borrow History ========");

                boolean hasRecord = false;

                while (rs.next()) {
                    hasRecord = true;

                    System.out.println("Book ID: " + rs.getString("book_id"));
                    //System.out.println("Title: " + rs.getString("title"));
                    //System.out.println("Author: " + rs.getString("author"));
                    //System.out.println("Category: " + rs.getString("category"));
                    System.out.println("Borrow Date: " + rs.getString("borrow_date"));
                    System.out.println("Return Date: " + rs.getString("return_date"));
                    System.out.println("Status: " + rs.getString("status"));
                    System.out.println("----------------------------------");
                }

                if (!hasRecord) {
                    System.out.println("No borrow history found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error viewing borrow history.");
            System.out.println(e.getMessage());
        }
    }

    public static boolean insertMember(String uid, String email, String passwordHash,
                                       String firstName, String lastName, String birthday) {
        String sql = """
            INSERT INTO members (uid, email, password_hash, first_name, last_name, birthday)
            VALUES (?, ?, ?, ?, ?, ?);
            """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, uid);
            pstmt.setString(2, email);
            pstmt.setString(3, passwordHash);
            pstmt.setString(4, firstName);
            pstmt.setString(5, lastName);
            pstmt.setString(6, birthday);

            pstmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error inserting member.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String findPasswordHashByEmail(String email) {
        String sql = "SELECT password_hash FROM members WHERE email = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("password_hash");
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Error finding member.");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String[] findUserProfileByEmail(String email){
        String sql = """
                SELECT email, first_name, last_name, birthday
                FROM members
                WHERE email = ?
                """;

        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                String userEmail = rs.getString("email");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String birthday = rs.getString("birthday");

                return new String[]{userEmail, firstName, lastName, birthday};
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Error finding user profile.");
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static boolean updatePasswordByEmail(String email, String newPasswordHash) {
        String sql = "UPDATE members SET password_hash = ? WHERE email = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPasswordHash);
            pstmt.setString(2, email);

            int rowsUpdated = pstmt.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating password.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void viewAllMembersForAdmin(Scanner scan) {
        while (true) {
            String sql = """
            SELECT uid, email, first_name, last_name, birthday, role
            FROM members
            """;

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                System.out.println();
                System.out.println("======== Member List ========");

                boolean hasMember = false;

                while (rs.next()) {
                    hasMember = true;

                    System.out.println("UID: " + rs.getString("uid"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                    System.out.println("Birthday: " + rs.getString("birthday"));
                    System.out.println("Role: " + rs.getString("role"));
                    System.out.println("-----------------------------");
                }

                if (!hasMember) {
                    System.out.println("No members found.");
                }

            } catch (SQLException e) {
                System.out.println("Error loading members.");
                System.out.println(e.getMessage());
            }

            System.out.println("Enter Q or Quit to return to Admin Menu:");
            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("q") || choice.equalsIgnoreCase("quit")) {
                System.out.println("Back to Admin Menu.");
                break;
            } else {
                System.out.println("Please enter Q or Quit.");
            }
        }
    }

    public static void viewAllBooks(Scanner scan) {
        while (true) {
            String sql = """
            SELECT book_id, quantity, status
            FROM books
            """;

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                System.out.println();
                System.out.println("======== Book List ========");

                boolean hasBook = false;

                while (rs.next()) {
                    hasBook = true;

                    System.out.println("Book ID: " + rs.getString("book_id"));
                    //System.out.println("Title: " + rs.getString("title"));
                    //System.out.println("Author: " + rs.getString("author"));
                    //System.out.println("Category: " + rs.getString("category"));
                    System.out.println("Status: " + rs.getString("status"));
                    System.out.println("Quantity: " + rs.getInt("quantity"));
                    System.out.println("---------------------------");
                }

                if (!hasBook) {
                    System.out.println("No books found.");
                }

            } catch (SQLException e) {
                System.out.println("Error viewing books.");
                System.out.println(e.getMessage());
            }

            System.out.println("Enter Q or Quit to return to Admin Library Menu:");
            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("q") || choice.equalsIgnoreCase("quit")) {
                System.out.println("Back to Admin Library Menu.");
                break;
            } else {
                System.out.println("Please enter Q or Quit.");
            }
        }
    }

    public static boolean addBook(String bookId, int quantity) {
        String sql = """
    INSERT INTO books (book_id, quantity, status)
    VALUES (?, ?, ?)
    ON CONFLICT(book_id)
    DO UPDATE SET
        quantity = quantity + excluded.quantity,
        status = 'available';
    """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String status = quantity > 0 ? "available" : "unavailable";

            pstmt.setString(1, bookId);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, status);

            pstmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error adding book.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void viewAllBooksOnly() {
        String sql = """
    SELECT book_id, quantity, status
    FROM books
    """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println();
            System.out.println("======== Book List ========");

            boolean hasBook = false;

            while (rs.next()) {
                hasBook = true;

                System.out.println("Book ID: " + rs.getString("book_id"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("---------------------------");
            }

            if (!hasBook) {
                System.out.println("No books found.");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing books.");
            System.out.println(e.getMessage());
        }
    }

    public static boolean increaseBookQuantity(String bookId, int quantityToAdd) {
        String sql = """
        UPDATE books
        SET quantity = quantity + ?,
            status = 'available'
        WHERE book_id = ?;
        """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantityToAdd);
            pstmt.setString(2, bookId);

            int rowsUpdated = pstmt.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error increasing book quantity.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean emailExists(String email) {
        String sql = "SELECT email FROM members WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // 如果查到一行，说明 email 已存在
            }

        } catch (SQLException e) {
            System.out.println("Error checking email.");
            System.out.println(e.getMessage());
            return true;
        }
    }

    public static boolean deleteBook(String bookId) {
        String sql = """
            DELETE FROM books
            WHERE book_id = ?;
            """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bookId);

            int rowsDeleted = pstmt.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting book.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean borrowBook(String bookId, String userEmail) {
        String checkSql = """
        SELECT quantity, status
        FROM books
        WHERE book_id = ?;
        """;

        String updateBookSql = """
        UPDATE books
        SET quantity = quantity - 1,
            status = CASE
                WHEN quantity - 1 <= 0 THEN 'unavailable'
                ELSE 'available'
            END
        WHERE book_id = ?;
        """;

        String insertRecordSql = """
        INSERT INTO borrow_records 
        (record_id, user_email, book_id, borrow_date, return_date, status)
        VALUES (?, ?, ?, ?, NULL, 'borrowed');
        """;

        try (Connection conn = getConnection()) {

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, bookId);

                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {
                    System.out.println("Book not found.");
                    return false;
                }

                int quantity = rs.getInt("quantity");

                if (quantity <= 0) {
                    System.out.println("This book is not available.");
                    return false;
                }
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(updateBookSql)) {
                updateStmt.setString(1, bookId);
                updateStmt.executeUpdate();
            }

            try (PreparedStatement recordStmt = conn.prepareStatement(insertRecordSql)) {
                String recordId = UUID.randomUUID().toString();
                String borrowDate = LocalDate.now().toString();

                recordStmt.setString(1, recordId);
                recordStmt.setString(2, userEmail);
                recordStmt.setString(3, bookId);
                recordStmt.setString(4, borrowDate);

                recordStmt.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error borrowing book.");
            System.out.println(e.getMessage());
            return false;
        }
    }
}