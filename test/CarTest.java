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
        car.setRegistration_number("ABC123");
        car.setRent_status(1);
        car.setEngine_capacity("2000");
        car.setEngine_power("200");
        car.setType_fuel("diesel");
        car.setCar_model("X5");
        car.setCar_brand("BMW");
        car.setCar_year(2018);

        Assertions.assertEquals("X5", car.getCar_model());
        Assertions.assertEquals("BMW", car.getCar_brand());
        Assertions.assertEquals(2018, car.getCar_year());
        Assertions.assertEquals("diesel", car.getType_fuel());
        Assertions.assertEquals("2000", car.getEngine_capacity());
        Assertions.assertEquals("200", car.getEngine_power());
        Assertions.assertEquals(1, car.getRent_status());
        Assertions.assertEquals("ABC123", car.getRegistration_number());
        Assertions.assertEquals(1, car.getId());


    }

    /**
     * Test sprawdzający, czy można utworzyć nowy obiekt klasy Car z konstruktorem z parametrami
     */
    @Test
    public void basicTest2() {
        Car car = new Car(1, "BMW", "X5", 2018, "ABC123", 1, "2000", "200", "diesel");

        Assertions.assertEquals("X5", car.getCar_model());
        Assertions.assertEquals("BMW", car.getCar_brand());
        Assertions.assertEquals(2018, car.getCar_year());
        Assertions.assertEquals("diesel", car.getType_fuel());
        Assertions.assertEquals("2000", car.getEngine_capacity());
        Assertions.assertEquals("200", car.getEngine_power());
        Assertions.assertEquals(1, car.getRent_status());
        Assertions.assertEquals("ABC123", car.getRegistration_number());
        Assertions.assertEquals(1, car.getId());
    }

    /**
     * Test sprawdzający, czy metoda getCarData zwraca prawidłowe dane
     */
    @Test
    public void testGetCarData() {
        Car car = new Car(1, "BMW", "X5", 2018, "ABC123", 1, "2000", "200", "diesel");
        Assertions.assertEquals(car, car.getCarData());
        Assertions.assertEquals("X5", car.getCar_model());
        Assertions.assertEquals("BMW", car.getCarData().getCar_brand());
    }
}
