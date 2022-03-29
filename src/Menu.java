import java.util.Scanner;

import static java.lang.System.exit;

public class Menu {

    public static void chooseLoginOrReg(){
        Scanner scanner = new Scanner(System.in);
        int choice;

        while(true){
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
            System.out.println("4. Dodaj samochód do bazy danych");
            System.out.println("5. Usuń samochód z bazy danych");
            System.out.println("0. Opuść program");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> CarUtils.printAllCars();
                case 2 -> CarUtils.printAvailableCars();
                case 3 -> CarUtils.printRentedCars();
                case 4 -> CarUtils.insertConsole();
                case 5 -> CarUtils.deleteCarConsole();
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

    public static void showMenuUser() {

        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("Witaj w wypożyczalni użytkowniku, wybierz opcję:");
            System.out.println("1. Pokaż wolne samochody");
            System.out.println("0. Wyloguj się");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> CarUtils.printAvailableCars();
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
