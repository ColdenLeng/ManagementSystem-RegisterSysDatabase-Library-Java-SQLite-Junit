package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class passWordHashTest {
    //test 1

    @Test
    void hashPasswordShouldNotReturnPlainPassword() {
        String plainPassword = "Password@123";

        String hashedPassword = passWordHash.hashPassword(plainPassword);

        assertNotNull(hashedPassword);
        assertNotEquals(plainPassword, hashedPassword);
    }

    //test 2
    @Test
    void checkPasswordShouldReturnTrueForCorrectPassword() {
        String plainPassword = "Password@123";

        String hashedPassword = passWordHash.hashPassword(plainPassword);

        boolean result = passWordHash.checkPassword(plainPassword, hashedPassword);

        assertTrue(result);
    }

    //test 3
    @Test
    void checkPasswordShouldReturnFalseForWrongPassword() {
        String plainPassword = "Password@123";
        String wrongPassword = "WrongPassword@123";

        String hashedPassword = passWordHash.hashPassword(plainPassword);

        boolean result = passWordHash.checkPassword(wrongPassword, hashedPassword);

        assertFalse(result);
    }

    //test 4
    @Test
    void checkPasswordShouldReturnFalseWhenPlainPasswordIsNull() {
        String hashedPassword = passWordHash.hashPassword("Password@123");

        boolean result = passWordHash.checkPassword(null, hashedPassword);

        assertFalse(result);
    }

    //test 5
    @Test
    void checkPasswordShouldReturnFalseWhenStoredHashIsNull() {
        boolean result = passWordHash.checkPassword("Password@123", null);

        assertFalse(result);
    }

}