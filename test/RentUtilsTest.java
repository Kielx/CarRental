import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

import java.time.LocalDate;

/**
 * Testy dla klasy CarUtils.
 */
public class RentUtilsTest {

    /**
     * Konstruktor domyslny
     */
    public RentUtilsTest() {}

    /**
     * Test sprawdzający, czy poprawnie dziala wypozyczanie samochodow
     */
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

    /**
     * Test sprawdzajacy, czy poprawnie dziala usuwanie wypozyczen samochodow
     */
    @Test
    public void testDeleteRentCar() {
        RentUtils.returnRentedCar(5);

        String sql = "SELECT * FROM rent WHERE car_id = '5' AND client_id = '2' AND rental_date = '" + LocalDate.now() + "' AND return_date = '" + LocalDate.now().plusDays(2) + "'";
        try (Connection conn = Main.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Assertions.assertFalse(rs.next());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test sprawdzajacy, czy poprawnie dziala wypozyczanie samochodow dla konkretnego uzytkownika
     */
    @Test
    public void testRentCarForUser() {
        CarUtils.insert("BMW", "X5", "2005", "TJE11111", "500", "50", "benzyna");
        UserUtils.insertUser("Jan", "Kowalski", "Wrzosowa 10", "111 222 333", "test@test.pl");

        RentUtils.rentCarForUser("TJE11111", "Jan", 10);
        String sql = "SELECT car.ID as carID, user.ID as userID FROM car, user WHERE car.registration_number = ? AND user.name = ?";
        Connection conn = Main.connect();
        Integer rentCarID = null;
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
                        rentCarID = rs2.getInt("car_id");
                        Assertions.assertNotNull(rs2.getString("car_id"));
                        Assertions.assertNotNull(rs2.getString("client_id"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (rentCarID != null) {
            RentUtils.returnRentedCar(rentCarID);
        }
        UserUtils.deleteUser("Jan");
        CarUtils.deleteCar("TJE11111");
    }

    /**
     * Test sprawdzajacy, czy poprawnie dziala wyswietlanie wypozyczen w konsoli
     */
    @Test
    public void testPrintRents() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        RentUtils.print_rents();

        String expected = "Lista wypożyczeń:";
        Assertions.assertEquals(expected, outContent.toString().substring(0, expected.length()));

    }
}

