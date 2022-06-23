import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Testy dla klasy CarUtils.
 */
public class CarUtilsTest {

    /**
     * Konstruktor domyslny
     */
    public CarUtilsTest() {}

    /**
     * Test sprawdzający, czy baza zwraca prawidłową listę samochodów
     */
    @Test
    public void testGetAllCars() {
        List<Car> cars = new ArrayList<>();
        try {
            cars = CarUtils.getAllCars();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(cars.size() > 0);
        Assertions.assertEquals(1, cars.get(0).getId());
        Assertions.assertEquals("Toyota", cars.get(0).getBrand());
        Assertions.assertEquals("GT86", cars.get(0).getModel());
    }

    /**
     * Test sprawdzajacy wypisywanie danych wszystkich pojazdow
     */
    @Test
    public void testPrintAllCars() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CarUtils.printAllCars();

        String expected = "1 Toyota GT86";
        Assertions.assertEquals(expected, outContent.toString().substring(0, expected.length()));
    }

    /**
     * Test sprawdzajacy poprawnosc zwracania dostepnych samochodow
     */
    @Test
    public void testGetAvailableCars() {
        List<Car> cars = new ArrayList<>();
        try {
            cars = CarUtils.getAvailableCars();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(cars.size() > 0);
        if (cars.get(0).getRentStatus() == 0) {
            Assertions.assertTrue(cars.size() > 0);
        }
    }

    /**
     * Test sprawdzajacy poprawnosc wypisywania wszystkich dostepnych samochodow
     */
    @Test
    public void testPrintAvailableCars() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CarUtils.printAvailableCars();

        Assertions.assertTrue(outContent.toString().length() > 0);
    }

    /**
     * Test sprawdzajacy poprawnosc wyswietlania wszystkich wypozyczonych samochodow
     */
    @Test
    public void testGetRentedCars() {
        List<Car> cars = new ArrayList<>();
        try {
            cars = CarUtils.getRentedCars();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(cars.size() > 0);
        if (cars.get(0).getRentStatus() == 1) {
            Assertions.assertTrue(cars.size() > 0);
        }
    }

    /**
     * Test sprawdzający poprawnosc wypisywania samochodow na konsolę
     */
    @Test
    public void testPrintRentedCars() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CarUtils.printRentedCars();
        Assertions.assertTrue(outContent.toString().length() > 0);
    }

    /**
     * Testuje wstawianie nowego samochodu do bazy danych i listy samochodow
     */
    @Test
    public void testInsertCar() {
        try {
            CarUtils.insert("Fiat", "Panda", "2002", "TPI12345", "900", "40", "benzyna");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Car> cars = new ArrayList<>();
        try {
            cars = CarUtils.getAllCars();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(cars.size() > 0);
        Assertions.assertEquals("Fiat", cars.get(cars.size() - 1).getBrand());
        Assertions.assertEquals("Panda", cars.get(cars.size() - 1).getModel());
        Assertions.assertEquals(2002, cars.get(cars.size() - 1).getYear());
        Assertions.assertEquals("TPI12345", cars.get(cars.size() - 1).getRegistrationNumber());
        Assertions.assertEquals("900", cars.get(cars.size() - 1).getEngineCapacity());
        Assertions.assertEquals("40", cars.get(cars.size() - 1).getEnginePower());
        Assertions.assertEquals("benzyna", cars.get(cars.size() - 1).getTypeFuel());
        Assertions.assertEquals(0, cars.get(cars.size() - 1).getRentStatus());
    }

    /**
     * Testuje poprawnosc dzialania usuwania samochodow
     */
    @Test
    public void testDeleteCar() {
        try {
            CarUtils.deleteCar("TPI12345");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Car> cars = new ArrayList<>();
        try {
            cars = CarUtils.getAllCars();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertFalse(
                cars.stream().anyMatch(car -> car.getRegistrationNumber().equals("TPI12345")));
    }

    /**
     * Testuje poprawnosc dzialania dodawania samochodow z poziomu konsoli
     */
    @Test
    public void testInsertConsole() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(("1" + System.lineSeparator() + "2" + System.lineSeparator() + "3" + System.lineSeparator() + "4" + System.lineSeparator() + "5" + System.lineSeparator() + "6" + System.lineSeparator() + "7").getBytes());
        System.setIn(in);
        Assertions.assertTrue(CarUtils.insertConsole());
        CarUtils.deleteCar("4");
        System.setIn(sysInBackup);
    }

    /**
     * Testuje poprawnosc dzialania usuwania samochodow z poziomu konsoli
     */
    @Test
    public void testDeleteConsole() {
        InputStream sysInBackup = System.in;
        CarUtils.insert("Fiat", "Panda", "2002", "TPI12345", "900", "40", "benzyna");
        ByteArrayInputStream in = new ByteArrayInputStream("TPI12345".getBytes());
        System.setIn(in);
        Assertions.assertTrue(CarUtils.deleteCarConsole());
        System.setIn(sysInBackup);
    }
}
