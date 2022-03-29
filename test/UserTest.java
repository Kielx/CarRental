import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class UserTest {

    /**
     * Test sprawdzający, czy można utworzyć nowy obiekt klasy Car z konstruktorem z parametrami
     */
    @Test
    public void basicTest() {
        User user = new User("Henryk", "Mikulski", "Bebelno 10, 28-300 Jędrzejów", 123456789, "henryk@henryk.pl", "Heniek", "Heniek123", 1, 111111111);

        Assertions.assertEquals("Henryk", user.getName());
        Assertions.assertEquals("Mikulski", user.getSurname());
        Assertions.assertEquals("Bebelno 10, 28-300 Jędrzejów", user.getAddress());
        Assertions.assertEquals(123456789, user.getPhoneNumber());
        Assertions.assertEquals("henryk@henryk.pl", user.getEmail());

        user.setId(1);
        user.setName("Jan");
        user.setSurname("Kowalski");
        user.setAddress("Warszawa, 05-100 Warszawa");
        user.setPhoneNumber(111111111);
        user.setEmail("test@test.pl");

        Assertions.assertEquals("Jan", user.getName());
        Assertions.assertEquals("Kowalski", user.getSurname());
        Assertions.assertEquals("Warszawa, 05-100 Warszawa", user.getAddress());
        Assertions.assertEquals(111111111, user.getPhoneNumber());
        Assertions.assertEquals("test@test.pl", user.getEmail());
        Assertions.assertEquals(1, user.getId());

    }

    /**
     * Test sprawdzający, czy metoda getCarData zwraca prawidłowe dane
     */
    @Test
    public void testGetUser() {
        User user = new User("Henryk", "Mikulski", "Bebelno 10, 28-300 Jędrzejów", 123456789, "henryk@henryk.pl", "Heniek", "Heniek123", 1, 111111111);
        Assertions.assertEquals("Henryk", user.getUser().getName());
        Assertions.assertEquals("Mikulski", user.getUser().getSurname());
    }

    @Test
    public void testUserToString() {
        User user = new User("Henryk", "Mikulski", "Bebelno 10, 28-300 Jędrzejów", 123456789, "henryk@henryk.pl", "Heniek", "Heniek123", 1, 111111111);
        Assertions.assertEquals(user.toString(), "User{name='Henryk', surname='Mikulski', address='Bebelno 10, 28-300 Jędrzejów', phone_number='123456789', email='henryk@henryk.pl', login='Heniek', password='Heniek123', admin='1', pesel='111111111'}");
    }

    @Test
    public void testValidations() {
        User user = new User("Henryk", "Mikulski", "Bebelno 10, 28-300 Jędrzejów", 123456789, "henryk@henryk.pl", "Heniek", "Heniek123", 1, 111111111);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        user.setName("");
        String expected = "Nazwa użytkownika nie może być pusta";
        Assertions.assertEquals(expected, outContent.toString().substring(0, expected.length()));
        outContent.reset();
        user.setEmail("");
        expected = "Email użytkownika nie może być pusty";
        Assertions.assertEquals(expected, outContent.toString().substring(0, expected.length()));
        outContent.reset();
        user.setPhoneNumber(0);
        expected = "Numer telefonu użytkownika nie może być pusty";
        Assertions.assertEquals(expected, outContent.toString().substring(0, expected.length()));
        outContent.reset();
        user.setAddress("");
        expected = "Adres użytkownika nie może być pusty";
        Assertions.assertEquals(expected, outContent.toString().substring(0, expected.length()));
        outContent.reset();
        user.setSurname("");
        expected = "Nazwisko użytkownika nie może być puste";
        Assertions.assertEquals(expected, outContent.toString().substring(0, expected.length()));
    }


}
