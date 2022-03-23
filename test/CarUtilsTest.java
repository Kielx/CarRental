import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Testy dla klasy CarUtils.
 */
public class CarUtilsTest {

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

    @Test
    public void testPrintAllCars() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CarUtils.printAllCars();

        String expected = "1 Toyota GT86";
        Assertions.assertEquals(expected, outContent.toString().substring(0, expected.length()));
    }

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

    @Test
    public void testPrintAvailableCars() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CarUtils.printAvailableCars();

        Assertions.assertTrue(outContent.toString().length() > 0);
    }

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

    @Test
    public void testPrintRentedCars() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CarUtils.printRentedCars();
        Assertions.assertTrue(outContent.toString().length() > 0);
    }

    //TODO: testy dla metody insert i delete
}
