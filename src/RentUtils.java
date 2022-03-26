import java.sql.*;
import java.time.LocalDate;

public class RentUtils {
    public static void rentCar(String car_id, String user_id, String start_date, String end_date, int payment_amount) {
        String sql = "INSERT INTO rent (car_id, client_id, rental_date, return_date, payment_amount) VALUES (?,?, ?, ?, ?)";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, car_id);
            pstmt.setString(2, user_id);
            pstmt.setString(3, start_date);
            pstmt.setString(4, end_date);
            pstmt.setString(5, String.valueOf(payment_amount));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteRentCar(String car_id) {
        String sql = "DELETE FROM rent WHERE car_id = ?";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, car_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static int get_user_id(String user_name) {
        String sql = "SELECT id FROM user WHERE name = ?";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static int get_car_id(String registration_number) {
        String sql = "SELECT id FROM car WHERE registration_number = ?";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, registration_number);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static int get_car_price(String registration_number) {
        String sql = "SELECT price FROM car WHERE registration_number = ?";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, registration_number);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("price");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static void rent_car_for_user(String registration_number, String user_name, int duration) {
        int user_id = get_user_id(user_name);
        int car_id = get_car_id(registration_number);
        String start_date = LocalDate.now().toString();
        String end_date = LocalDate.now().plusDays(duration).toString();
        int payment_amount = duration * get_car_price(registration_number);
        rentCar(String.valueOf(car_id), String.valueOf(user_id), start_date, end_date, payment_amount);

        String sql = "UPDATE car SET rent_status = 1 WHERE ID = ?";
        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, String.valueOf(car_id));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void print_rents() {
        String sql = "SELECT * FROM user, car, rent WHERE user.id = rent.client_id AND car.id = rent.car_id";

        try (Connection conn = Main.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String user_name = rs.getString("name");
                String user_surname = rs.getString("surname");
                String registration_number = rs.getString("registration_number");
                String car_id = rs.getString("car_id");
                String client_id = rs.getString("client_id");
                String rental_date = rs.getString("rental_date");
                String return_date = rs.getString("return_date");
                String payment_amount = rs.getString("payment_amount");
                System.out.println(user_name + " " + user_surname + " " + registration_number + " " + car_id + " " + client_id + " " + rental_date + " " + return_date + " " + payment_amount);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}



