import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Menu {

    public static void chooseLoginOrReg() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("Wynajem aut:");
            System.out.println("1. Zaloguj sie");
            System.out.println("2. Nie masz konta? Zarejestruj sie");
            System.out.println("0. Opuść program");

            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> Login.showLoginPanel();
                case 2 -> Login.showRegisterPanel();
                case 0 -> {
                    System.out.println("Koniec programu");
                    exit(0);
                }
                default -> System.out.println("Nie ma takiej opcji w menu");
            }
        }
    }

    public static void showMenuAdmin() {

        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("System obsługujący wypożyczalnię samochodów, wybierz opcję:");
            System.out.println("1. Pokaż wszystkie samochody");
            System.out.println("2. Pokaż wolne samochody");
            System.out.println("3. Pokaż wypożyczone samochody");
            System.out.println("4. Pokaż dane wypożyczeń");
            System.out.println("5. Dodaj samochód do bazy danych");
            System.out.println("6. Usuń samochód z bazy danych");
            System.out.println("0. Opuść program");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> CarUtils.printAllCars();
                case 2 -> CarUtils.printAvailableCars();
                case 3 -> CarUtils.printRentedCars();
                case 4 -> RentUtils.print_rents();
                case 5 -> CarUtils.insertConsole();
                case 6 -> CarUtils.deleteCarConsole();
                case 0 -> {
                    System.out.println("Koniec programu");
                    return;
                }
                default -> System.out.println("Nie ma takiej opcji w menu");
            }

            System.out.println("Naciśnij dowolny klawisz aby kontynuować");
            scanner.nextLine();
            scanner.nextLine();


        }


    }

    public static void showMenuUser(int userId) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        int choice;
        String sql = "SELECT * FROM user WHERE ID = ?";
        User currentUser = null;
        try {
            Connection connection = Main.connect();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                currentUser = new User();
                currentUser.setName(rs.getString("name"));
                currentUser.setSurname(rs.getString("surname"));
                currentUser.setId(rs.getInt("ID"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (currentUser != null) {
            while (true) {
                System.out.println("Witaj w wypożyczalni " + currentUser.getName() + " " + currentUser.getSurname() + ", wybierz opcję:");
                System.out.println("1. Pokaż wolne samochody");
                System.out.println("2. Pokaż swoje wypożyczone samochody");
                System.out.println("3. Wynajmij samochód");
                System.out.println("4. Zwróć wypożyczony samochód");
                System.out.println("0. Wyloguj się");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> CarUtils.printAvailableCars();
                    case 2 -> RentUtils.printCurrentUserRents(userId);
                    case 3 -> RentUtils.rentCarFromConsole(userId);
                    case 4 -> RentUtils.returnRentedCarFromConsole(userId);
                    case 0 -> {
                        System.out.println("Poprawnie wylogowano!");
                        return;
                    }
                    default -> System.out.println("Nie ma takiej opcji w menu");
                }

                System.out.println("Naciśnij dowolny klawisz aby kontynuować");
                scanner.nextLine();
                scanner.nextLine();
            }
        }

    }


}
