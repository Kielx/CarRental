import java.sql.*;
import java.util.*;

public class CarUtils {

    /**
     * Funkcja pomocnicza, która zwraca listę samochodów (zawierającą id, markę, model oraz status wypożyczenia), zgodnie z zapytaniem SQL
     *
     * @param sql - zapytanie SQL
     * @return Lista samochodów zgodna z zapytaniem SQL
     * @throws SQLException — wyjątek z bazy danych, wypisany na konsolę
     */
    private static List<Car> prepareCarsList(String sql) throws SQLException {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = Main.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setCar_brand(rs.getString("car_brand"));
                car.setCar_model(rs.getString("car_model"));
                car.setRent_status(rs.getInt("rent_status"));
                cars.add(car);
            }
        }
        return cars;
    }

    /**
     * Zwraca listę wszystkich samochodów zawierającą id, markę, model i status wypożyczenia
     *
     * @return List<Car> - lista wszystkich samochodów zawierającą id, markę, model i status wypożyczenia
     * @throws SQLException-wyjątek z bazy danych wypisany na konsolę
     */
    public static List<Car> getAllCars() throws SQLException {
        String sql = "SELECT * FROM car";

        return prepareCarsList(sql);
    }

    /**
     * Wypisuje na konsolę listę wszystkich samochodów (zawierającą id, markę, model i status wypożyczenia)
     */
    public static void printAllCars() {
        try {
            List<Car> allCars = getAllCars();
            for (Car car : allCars) {
                System.out.println(car.getId() + " " + car.getCar_brand() + " " + car.getCar_model());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Zwraca listę wszystkich samochodów zawierającą id, markę i model pojazdów, które nie są wypożyczone (rent_status = 0)
     *
     * @return Lista wszystkich samochodów, które nie są wypożyczone
     * @throws SQLException-wyjątek z bazy danych
     */
    public static List<Car> getAvailableCars() throws SQLException {
        String sql = "SELECT * FROM car WHERE rent_status = 0";

        return prepareCarsList(sql);
    }


    /**
     * Wypisuje na konsolę listę wszystkich samochodów, które nie są wypożyczone (rent_status = 0)
     */
    public static void printAvailableCars() {
        try {
            List<Car> availableCars = getAvailableCars();
            for (Car car : availableCars) {
                System.out.println(car.getId() + " " + car.getCar_brand() + " " + car.getCar_model());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Zwraca listę wszystkich samochodów, które są wypożyczone (rent_status = 1)
     *
     * @return Lista wszystkich samochodów, które są wypożyczone
     * @throws SQLException-wyjątek z bazy danych
     */
    public static List<Car> getRentedCars() throws SQLException {
        String sql = "SELECT * FROM car WHERE rent_status = 1";

        return prepareCarsList(sql);
    }

    /**
     * Wypisuje na konsolę listę wszystkich samochodów, które są wypożyczone (rent_status = 1)
     */
    public static void printRentedCars() {
        try {
            List<Car> rentedCars = getRentedCars();
            for (Car car : rentedCars) {
                System.out.println(car.getId() + " " + car.getCar_brand() + " " + car.getCar_model());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Dodaje samochód do bazy danych
     *
     * @param car_brand - marka samochodu
     * @param car_model - model samochodu
     * @param car_year  - rok produkcji samochodu
     */
    public static void insert(String car_brand, String car_model, String car_year, String registration_number, String engine_capacity, String engine_power, String type_fuel) {
        String sql = "INSERT INTO car (car_brand, car_model, car_year, registration_number, engine_capacity, engine_power, type_fuel) VALUES (?,?, ?, ?, ?, ?, ?)";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, car_brand);
            pstmt.setString(2, car_model);
            pstmt.setString(3, car_year);
            pstmt.setString(4, registration_number);
            pstmt.setString(5, engine_capacity);
            pstmt.setString(6, engine_power);
            pstmt.setString(7, type_fuel);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Funkcja, która dodaje samochód z poziomu konsoli
     */
    public static void insertConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj markę samochodu: ");
        String car_brand = scanner.nextLine();
        System.out.println("Podaj model samochodu: ");
        String car_model = scanner.nextLine();
        System.out.println("Podaj rok produkcji samochodu: ");
        String car_year = scanner.nextLine();
        System.out.println("Podaj numer rejestracyjny samochodu: ");
        String registration_number = scanner.nextLine();
        System.out.println("Podaj pojemność silnika samochodu: ");
        String engine_capacity = scanner.nextLine();
        System.out.println("Podaj moc silnika samochodu: ");
        String engine_power = scanner.nextLine();
        System.out.println("Podaj rodzaj paliwa samochodu: ");
        String type_fuel = scanner.nextLine();
        insert(car_brand, car_model, car_year, registration_number, engine_capacity, engine_power, type_fuel);
    }


}
