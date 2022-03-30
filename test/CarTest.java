import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class CarTest {

    /**
     * Test sprawdzający, czy można utworzyć nowy obiekt klasy Car z domyślnym konstruktorem
     */
    @Test
    public void basicTest() {
        Car car = new Car();
        car.setId(1);
        car.setRegistrationNumber("ABC123");
        car.setRentStatus(1);
        car.setEngineCapacity("2000");
        car.setEnginePower("200");
        car.setTypeFuel("diesel");
        car.setModel("X5");
        car.setBrand("BMW");
        car.setYear(2018);

        Assertions.assertEquals("X5", car.getModel());
        Assertions.assertEquals("BMW", car.getBrand());
        Assertions.assertEquals(2018, car.getYear());
        Assertions.assertEquals("diesel", car.getTypeFuel());
        Assertions.assertEquals("2000", car.getEngineCapacity());
        Assertions.assertEquals("200", car.getEnginePower());
        Assertions.assertEquals(1, car.getRentStatus());
        Assertions.assertEquals("ABC123", car.getRegistrationNumber());
        Assertions.assertEquals(1, car.getId());


    }

    /**
     * Test sprawdzający, czy można utworzyć nowy obiekt klasy Car z konstruktorem z parametrami
     */
    @Test
    public void basicTest2() {
        Car car = new Car(1, "BMW", "X5", 2018, "ABC123", 1, "2000", "200", "diesel", 500);

        Assertions.assertEquals("X5", car.getModel());
        Assertions.assertEquals("BMW", car.getBrand());
        Assertions.assertEquals(2018, car.getYear());
        Assertions.assertEquals("diesel", car.getTypeFuel());
        Assertions.assertEquals("2000", car.getEngineCapacity());
        Assertions.assertEquals("200", car.getEnginePower());
        Assertions.assertEquals(1, car.getRentStatus());
        Assertions.assertEquals("ABC123", car.getRegistrationNumber());
        Assertions.assertEquals(1, car.getId());
        Assertions.assertEquals(500, car.getPrice());
    }

    /**
     * Test sprawdzający, czy metoda getCarData zwraca prawidłowe dane
     */
    @Test
    public void testGetCarData() {
        Car car = new Car(1, "BMW", "X5", 2018, "ABC123", 1, "2000", "200", "diesel", 500);
        Assertions.assertEquals(car, car.getData());
        Assertions.assertEquals("X5", car.getModel());
        Assertions.assertEquals("BMW", car.getData().getBrand());
    }

    @Test
    public void testCarToString() {
        Car car = new Car(1, "BMW", "X5", 2018, "ABC123", 1, "2000", "200", "diesel", 500);
        Assertions.assertEquals("Car{id=1, car_brand='BMW', car_model='X5', car_year=2018, registration_number='ABC123', rent_status=1, engine_capacity='2000', engine_power='200', type_fuel='diesel'}", car.toString());
    }
}
