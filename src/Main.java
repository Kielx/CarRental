import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    /**
     * Laczymy sie z baza danych
     * @return Connection object
     */
    private static Connection connect() {
        String url = "jdbc:sqlite:./data.sqlite";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Wyswietla wszystkie rekordy z tabeli
     */
    public static void selectAll(){
        String sql = "SELECT * FROM car";

        try (Connection conn = connect();
             //
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString("car_brand"));
                System.out.println(rs.getString("car_model"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
	System.out.println("Hello World!");
    selectAll();
    }


}
