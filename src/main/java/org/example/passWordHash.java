package org.example;
import org.mindrot.jbcrypt.BCrypt;

public class passWordHash {

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String plainPassword, String storedHash) {
        if (plainPassword == null || storedHash == null) {
            return false;
        }

        return BCrypt.checkpw(plainPassword, storedHash);
    }
}
