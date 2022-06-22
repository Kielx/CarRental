import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                car.setRegistrationNumber(rs.getString("registration_number"));
                car.setBrand(rs.getString("car_brand"));
                car.setModel(rs.getString("car_model"));
                car.setRentStatus(rs.getInt("rent_status"));
                car.setEngineCapacity(String.valueOf(rs.getInt("engine_capacity")));
                car.setEnginePower(String.valueOf(rs.getInt("engine_power")));
                car.setYear(rs.getInt("car_year"));
                car.setTypeFuel(rs.getString("type_fuel"));
                car.setPrice(rs.getInt("price"));
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
        String sql = "SELECT * FROM car ";

        return prepareCarsList(sql);
    }
    /**
     * Zwraca listę  samochodu o podanym ID zawierającą id, markę, model i status wypożyczenia
     *
     * @return List<Car> - lista  samochodu zawierającą id, markę, model i status wypożyczenia
     * @throws SQLException-wyjątek z bazy danych wypisany na konsolę
     */
    public static List<Car> getCarsById(int id) throws SQLException {
        String sql = "SELECT * FROM car WHERE  ID="+id;

        return prepareCarsList(sql);
    }
    /**
     * Wypisuje na konsolę listę wszystkich samochodów (zawierającą id, markę, model i status wypożyczenia)
     */
    public static void printAllCars() {
        try {
            List<Car> allCars = getAllCars();
            for (Car car : allCars) {
                System.out.println(car.getId() + " " + car.getBrand() + " " + car.getModel());
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
                System.out.println(car.getId() + " " + car.getBrand() + " " + car.getModel() + " " + car.getRegistrationNumber() + ", Pojemność: " + car.getEngineCapacity() + ", Moc: " + car.getEnginePower() + ", Rok: " + car.getYear() + ", Rodzaj paliwa: " + car.getTypeFuel() + ", Cena za dzień: " + car.getPrice());
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
                System.out.println(car.getId() + " " + car.getBrand() + " " + car.getModel());
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
     * @return true-jeśli dodano samochód do bazy danych
     * false-jeśli nie dodano samochód do bazy danych
     */
    public static boolean insert(String car_brand, String car_model, String car_year, String registration_number, String engine_capacity, String engine_power, String type_fuel) {
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
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Funkcja, która dodaje samochód z poziomu konsoli
     */
    public static boolean insertConsole() {
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

        return (insert(car_brand, car_model, car_year, registration_number, engine_capacity, engine_power, type_fuel));


    }


    /**
     * Usuwa samochód z bazy danych
     *
     * @param registration_number - numer rejestracyjny samochodu do usunięcia
     */
    public static boolean deleteCar(String registration_number) {
        String checkIfCarExists = "SELECT * FROM car WHERE registration_number = ?";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(checkIfCarExists)) {
            pstmt.setString(1, registration_number);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String sql = "DELETE FROM car WHERE registration_number = ?";
                PreparedStatement pstmt2 = conn.prepareStatement(sql);
                pstmt2.setString(1, registration_number);
                pstmt2.executeUpdate();
                return true;

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;


    }

    /**
     * Funkcja, która usuwa samochód z poziomu konsoli
     * @return true-jeśli usunięto samochód z bazy danych
     * false-jeśli nie usunięto samochód z bazy danych
     */
    public static boolean deleteCarConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj numer rejestracyjny samochodu do usunięcia: ");
        String registration_number = scanner.nextLine();
        return deleteCar(registration_number);
    }


}
