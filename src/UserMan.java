import java.sql.*;
import java.util.*;

public class UserMan {
    private static List<User> displayUsers(String sql) throws SQLException{
        List<User> users = new ArrayList<>();
        try (Connection conn = Main.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setAddress(rs.getString("address"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getInt("admin"));
                user.setPesel(rs.getString("pesel"));

                users.add(user);
            }
        }
        return users;
    }

    public static void insertUser(String name, String surname, String pesel, String address, String phone_number,
                                  String email, String login, String password, int admin){
        String sql = "INSERT INTO user (name, surname, pesel, address, phone_number, email, login, password, admin) " +
                "VALUES (?,?, ?, ?, ?, ?, ?,?,?)";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, pesel);
            pstmt.setString(4, address);
            pstmt.setString(5, phone_number);
            pstmt.setString(6, email);
            pstmt.setString(7, login);
            pstmt.setString(8, password);
            pstmt.setInt(9, admin);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //Funkcja do rejestracji uzytkownika
    public static void insertRegUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj imie: ");
        String name = scanner.nextLine();
        System.out.println("Podaj nazwisko: ");
        String surname = scanner.nextLine();
        System.out.println("Podaj numer Pesel: ");
        String pesel = scanner.nextLine();
        System.out.println("Podaj adres: ");
        String address = scanner.nextLine();
        System.out.println("Podaj numer telefonu: ");
        String phone_number = scanner.nextLine();
        System.out.println("Podaj adres e-mail: ");
        String email = scanner.nextLine();
        System.out.println("Podaj login, ktorym chcesz sie logowac: ");
        String login = scanner.nextLine();
        System.out.println("Podaj haslo: ");
        String password = scanner.nextLine();
        String hash_passwd = HashPass.md5(password);
        insertUser(name,surname,pesel,address,phone_number,email,login,hash_passwd,0);
    }
}
