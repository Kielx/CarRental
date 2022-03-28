import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserUtils {
    /**
     * Dodaje użytkownika do bazy danych.
     *
     * @param name         Nazwa użytkownika.
     * @param surname      Nazwisko użytkownika.
     * @param address      Adres użytkownika.
     * @param phone_number Numer telefonu użytkownika.
     * @param email        Adres e-mail użytkownika.
     */
    public static void insertUser(String name, String surname, String address, String phone_number, String email) {
        String sql = "INSERT INTO user (name, surname, address, phone_number, email) VALUES (?,?, ?, ?, ?)";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, address);
            pstmt.setString(4, phone_number);
            pstmt.setString(5, email);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Usuwa użytkownika z bazy danych.
     *
     * @param name Nazwa użytkownika.
     */
    public static void deleteUser(String name) {
        String sql = "DELETE FROM user WHERE name = ?";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Aktualizuje dane użytkownika.
     *
     * @param name         Nazwa użytkownika.
     * @param surname      Nazwisko użytkownika.
     * @param address      Adres użytkownika.
     * @param phone_number Numer telefonu użytkownika.
     * @param email        Adres e-mail użytkownika.
     */
    public static void updateUser(String name, String surname, String address, String phone_number, String email) {
        String sql = "UPDATE user SET name = ?, surname = ?, address = ?, phone_number = ?, email = ? WHERE name = ?";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, address);
            pstmt.setString(4, phone_number);
            pstmt.setString(5, email);
            pstmt.setString(6, name);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

