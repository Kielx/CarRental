import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class RentUtils {
    /**
     * Funkcja, która dodaje wypożyczenie do bazy danych
     *
     * @param car_id         id samochodu
     * @param user_id        id użytkownika
     * @param start_date     data rozpoczęcia wypożyczenia
     * @param end_date       data zakończenia wypożyczenia
     * @param payment_amount kwota do zapłaty
     */
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

        sql = "UPDATE car SET rent_status = 1 WHERE ID = ?";
        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, String.valueOf(car_id));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Funkcja, która usuwa wypożyczenie samochodu o podanym id
     *
     * @param car_id id samochodu
     */
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


    /**
     * Funkcja, która tworzy wypożyczenie samochodu o podanym numerze rejestracyjnym dla określonego użytkownika na wskazany czas
     *
     * @param registration_number numer rejestracyjny samochodu do wypożyczenia
     * @param user_name           nazwa użytkownika, dla którego ma zostać wypożyczony samochód
     * @param duration            czas wypożyczenia w dniach
     */
    public static void rentCarForUser(String registration_number, String user_name, int duration) {
        String sql = "SELECT user.ID as user_id, car.ID as car_id, car.price, car.rent_status from USER, CAR WHERE car.registration_number = ? AND user.name = ?";
        Integer user_id = null;
        Integer car_id = null;
        int price = 0;
        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(registration_number));
            pstmt.setString(2, String.valueOf(user_name));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user_id = rs.getInt("user_id");
                car_id = rs.getInt("car_id");
                price = rs.getInt("price");
                if (rs.getInt("rent_status") == 1) {
                    System.out.println("Samochód jest już wypożyczony");
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String start_date = LocalDate.now().toString();
        String end_date = LocalDate.now().plusDays(duration).toString();
        int payment_amount = duration * price;
        rentCar(String.valueOf(car_id), String.valueOf(user_id), start_date, end_date, payment_amount);
    }

    /**
     * Funkcja, która usuwa z bazy wypożyczenie samochodu o podanym numerze rejestracyjnym
     *
     * @param registration_number numer rejestracyjny samochodu
     */
    public static void returnRentedCar(String registration_number) {
        String sql = "DELETE FROM rent WHERE car_id = (SELECT ID FROM car WHERE registration_number = ?)";
        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, registration_number);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void returnRentedCar(int carId) {
        String sql = "DELETE FROM rent WHERE car_id = ?";
        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, carId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Funkcja, która usuwa z bazy wypożyczenie samochodu dla określonego użytkownika
     *
     * @param user_name nazwisko użytkownika
     */
    public static void returnRentedUser(String user_name) {
        String sql = "DELETE FROM rent WHERE client_id  = (SELECT ID FROM user WHERE name = ?)";
        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_name);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Funkcja, która wypisuje na ekran konsoli wszystkie aktualne wypożyczenia
     */
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

    /**
     * Funkcja, która pozwala na wynajęcie samochodu, dla użytkownika o podanym numerze ID, za pomocą konsoli
     *
     * @param userID numer ID użytkownika, dla którego ma zostać wypożyczony samochód
     */
    public static void rentCarFromConsole(int userID) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj numer rejestracyjny samochodu, który chcesz wypożyczyć");
        String registration_number = scanner.nextLine();
        System.out.println("Podaj ilość dni, na którą chcesz wypożyczyć samochód");
        int duration = scanner.nextInt();
        String sql = "SELECT * FROM car WHERE registration_number = ?";
        Car car = null;
        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, registration_number);
            ResultSet rs = pstmt.executeQuery();
            // Jeżeli mamy wynik to znaczy, że samochód istnieje w bazie
            if (rs.next()) {
                car = new Car(rs.getInt("ID"), rs.getString("car_brand"), rs.getString("car_model"), rs.getInt("car_year"), rs.getString("registration_number"), rs.getInt("rent_status"), rs.getString("engine_capacity"), rs.getString("engine_power"), rs.getString("type_fuel"), rs.getInt("price"));
                //W bazie danych nie znaleziono samochodu o takim numerze rejestracyjnym
            } else {
                System.out.println("Nie ma samochodu o podanym numerze rejestracyjnym");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (car != null && car.getRentStatus() == 0) {
            RentUtils.rentCar(String.valueOf(car.getId()), String.valueOf(userID), LocalDate.now().toString(), LocalDate.now().plusDays(duration).toString(), car.getPrice() * duration);
            System.out.println("Samochód został wypożyczony, Twoja kwota do zapłaty to: " + car.getPrice() * duration);
            System.out.println("Życzymy udanej podróży!");
        } //Samochód jest już wypożyczony wypisz stosowny komunikat
        else {
            System.out.println("Samochód jest już wypożyczony");
        }
    }


}



