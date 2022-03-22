import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    /**
     * łączymy się z baza danych
     *
     * @return Connection object
     */
    public static Connection connect() {
        String url = "jdbc:sqlite:./data.sqlite";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public static void main(String[] args) {
        //Menu.showMenu();
        RentUtils.rent_car_for_user("TPI12KE", "Krystian", 5);
        RentUtils.print_rents();
    }


}
