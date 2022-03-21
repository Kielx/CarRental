import java.util.Scanner;

public class Menu {
    public static void showMenu() {
        System.out.println("System obsługujący wypożyczalnię samochodów, wybierz opcję:");
        System.out.println("1. Pokaż wszystkie samochody");
        System.out.println("2. Pokaż wolne samochody");
        System.out.println("3. Pokaż wypożyczone samochody");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> CarUtils.printAllCars();
            case 2 -> CarUtils.printAvailableCars();
            case 3 -> CarUtils.printRentedCars();
            default -> System.out.println("Nie ma takiej opcji w menu");
        }
    }


}
