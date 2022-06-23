import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Główna klasa programu
 * @author - Krzysztof Pańtak, Krystian Jagiełło, Paweł Kasprzak
 * @version - 1.0
 *
 */
public class Main {

    /**
     * Konstruktor domyślny
     */
    private Main() {}

    /**
     * Funkcja, która łączy się z bazą danych i zwraca połączenie
     *
     * @return Connection object
     */
    public static Connection connect() {
        String url = "jdbc:sqlite:./data.sqlite?foreign_keys=on";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Główna funkcja programu
     *
     * @param args - argumenty wywołania programu
     */
    public static void main(String[] args) {
        LoginLayout loginLayout = new LoginLayout(null);
        //Menu.chooseLoginOrReg();if(Login.showLoginPanel()) Menu.showMenu();
    }


}
