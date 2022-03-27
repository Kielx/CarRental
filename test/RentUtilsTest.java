import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    //TODO: Add missing tests

}

