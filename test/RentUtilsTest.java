import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

import java.time.LocalDate;

public class RentUtilsTest {
    @Test
    public void testRentCar() {
        RentUtils.rentCar("5", "2", LocalDate.now().toString(), LocalDate.now().plusDays(2).toString(), 1200);

        String sql = "SELECT * FROM rent WHERE car_id = '5' AND client_id = '2' AND rental_date = '" + LocalDate.now() + "' AND return_date = '" + LocalDate.now().plusDays(2) + "' AND payment_amount = '1200'";
        try (Connection conn = Main.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Assertions.assertEquals("5", rs.getString("car_id"));
                Assertions.assertEquals("2", rs.getString("client_id"));
                Assertions.assertEquals(LocalDate.now().toString(), rs.getString("rental_date"));
                Assertions.assertEquals(LocalDate.now().plusDays(2).toString(), rs.getString("return"));
                Assertions.assertEquals(1200, rs.getInt("payment_amount"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testDeleteRentCar() {
        RentUtils.deleteRentCar("5");

        String sql = "SELECT * FROM rent WHERE car_id = '5' AND client_id = '2' AND rental_date = '" + LocalDate.now() + "' AND return_date = '" + LocalDate.now().plusDays(2) + "'";
        try (Connection conn = Main.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Assertions.assertFalse(rs.next());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testRentCarForUser() {
        CarUtils.insert("BMW", "X5", "2005", "TJE11111", "500", "50", "benzyna");
        UserUtils.insertUser("Jan", "Kowalski", "Wrzosowa 10", "111 222 333", "test@test.pl");

        RentUtils.rentCarForUser("TJE11111", "Jan", 10);
        String sql = "SELECT car.ID as carID, user.ID as userID FROM car, user WHERE car.registration_number = ? AND user.name = ?";
        Connection conn = Main.connect();
        String rentCarID = null;
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "TJE11111");
            pstmt.setString(2, "Jan");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                sql = "SELECT * FROM rent WHERE car_id = '" + rs.getString("carID") + "' AND client_id = '" + rs.getString("userID") + "'";
                try (Connection conn2 = Main.connect();
                     Statement stmt = conn2.createStatement();
                     ResultSet rs2 = stmt.executeQuery(sql)) {

                    while (rs2.next()) {
                        rentCarID = rs2.getString("car_id");
                        Assertions.assertNotNull(rs2.getString("car_id"));
                        Assertions.assertNotNull(rs2.getString("client_id"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        RentUtils.deleteRentCar(rentCarID);
        UserUtils.deleteUser("Jan");
        CarUtils.deleteCar("TJE11111");
    }

    //TODO: Add missing tests

}

