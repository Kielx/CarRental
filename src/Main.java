import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.util.*;

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
                System.out.print(rs.getInt("id") + "\t");
                System.out.print(rs.getString("car_brand") + "\t");
                System.out.println(rs.getString("car_model") + "\t");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(String car_brand, String car_model) {
        String sql = "INSERT INTO car (car_brand, car_model) VALUES (?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, car_brand);
            pstmt.setString(2, car_model);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
	System.out.println("Hello World!");
    Scanner sc= new Scanner(System.in);
    System.out.println("Podaj markę samochodu");
    String car_brand=sc.nextLine();
    System.out.println("Podaj model samochodu");
    String car_model=sc.nextLine();
    insert(car_brand,car_model);



    selectAll();
    }


}
