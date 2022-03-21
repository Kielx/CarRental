import java.util.Scanner;

public class Menu {
    public static void showMenu() {

        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("System obsługujący wypożyczalnię samochodów, wybierz opcję:");
            System.out.println("1. Pokaż wszystkie samochody");
            System.out.println("2. Pokaż wolne samochody");
            System.out.println("3. Pokaż wypożyczone samochody");
            System.out.println("4. Dodaj samochód");
            System.out.println("0. Opuść program");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> CarUtils.printAllCars();
                case 2 -> CarUtils.printAvailableCars();
                case 3 -> CarUtils.printRentedCars();
                case 4 -> CarUtils.insertConsole();
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


}
