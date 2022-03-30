/**
 * Klasa opisująca pojazd
 */
public class Car {
    private int id;
    private String car_brand;
    private String car_model;
    private int car_year;
    private String registration_number;
    private int rent_status;
    private String engine_capacity;
    private String engine_power;
    private String type_fuel;
    private int price;


    /**
     * Override toString, który wyświetla dane pojazdu
     *
     * @return dane pojazdu
     */
    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", car_brand='" + car_brand + '\'' +
                ", car_model='" + car_model + '\'' +
                ", car_year=" + car_year +
                ", registration_number='" + registration_number + '\'' +
                ", rent_status=" + rent_status +
                ", engine_capacity='" + engine_capacity + '\'' +
                ", engine_power='" + engine_power + '\'' +
                ", type_fuel='" + type_fuel + '\'' +
                '}';
    }

    /**
     * Domyślny konstruktor
     */
    public Car() {
    }

    /**
     * Konstruktor klasy Car z parametrami
     *
     * @param id                  id pojazdu
     * @param car_brand           marka pojazdu
     * @param car_model           model pojazdu
     * @param car_year            rok produkcji
     * @param registration_number numer rejestracyjny
     * @param rent_status         status wypożyczenia (0 - nie wypożyczony, 1 - wypożyczony)
     * @param engine_capacity     pojemność silnika
     * @param engine_power        moc silnika
     * @param type_fuel           rodzaj paliwa
     * @param price               cena wypożyczenia pojazdu (dzień)
     */
    public Car(int id, String car_brand, String car_model, int car_year, String registration_number, int rent_status, String engine_capacity, String engine_power, String type_fuel, int price) {
        this.id = id;
        this.car_brand = car_brand;
        this.car_model = car_model;
        this.car_year = car_year;
        this.registration_number = registration_number;
        this.rent_status = rent_status;
        this.engine_capacity = engine_capacity;
        this.engine_power = engine_power;
        this.type_fuel = type_fuel;
        this.price = price;
    }

    public Car getData() {
        return this;
    }

    /**
     * Setter id
     *
     * @param id id pojazdu
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter id
     *
     * @return id pojazdu
     */
    public int getId() {
        return id;
    }

    /**
     * Setter marki
     *
     * @param car_brand marka pojazdu
     */
    public void setBrand(String car_brand) {
        this.car_brand = car_brand;
    }

    /**
     * Getter marki
     *
     * @return car_brand marka pojazdu
     */
    public String getBrand() {
        return car_brand;
    }

    /**
     * Setter modelu samochodu
     *
     * @param car_model model pojazdu
     */
    public void setModel(String car_model) {
        this.car_model = car_model;
    }

    /**
     * Getter modelu samochodu
     *
     * @return car_model model pojazdu
     */
    public String getModel() {
        return car_model;
    }

    /**
     * Setter roku produkcji samochodu
     *
     * @param car_year rok produkcji
     */
    public void setYear(int car_year) {
        this.car_year = car_year;
    }

    /**
     * Getter roku produkcji samochodu
     *
     * @return car_year rok produkcji
     */
    public int getYear() {
        return car_year;
    }

    /**
     * Setter numeru rejestracyjnego
     *
     * @param registration_number numer rejestracyjny
     */
    public void setRegistrationNumber(String registration_number) {
        this.registration_number = registration_number;
    }

    /**
     * Getter numeru rejestracyjnego
     *
     * @return registration_number numer rejestracyjny
     */
    public String getRegistrationNumber() {
        return registration_number;
    }

    /**
     * Setter statusu wypożyczenia
     *
     * @param rent_status status wypożyczenia (0 - nie wypożyczony, 1 - wypożyczony)
     */
    public void setRentStatus(int rent_status) {
        this.rent_status = rent_status;
    }

    /**
     * Getter statusu wypożyczenia
     *
     * @return rent_status status wypożyczenia (0 - nie wypożyczony, 1 - wypożyczony)
     */
    public int getRentStatus() {
        return rent_status;
    }

    /**
     * Setter pojemności silnika
     *
     * @param engine_capacity pojemność silnika
     */
    public void setEngineCapacity(String engine_capacity) {
        this.engine_capacity = engine_capacity;
    }

    /**
     * Getter pojemności silnika
     *
     * @return engine_capacity pojemność silnika
     */
    public String getEngineCapacity() {
        return engine_capacity;
    }

    /**
     * Setter mocy silnika
     *
     * @param engine_power moc silnika
     */
    public void setEnginePower(String engine_power) {
        this.engine_power = engine_power;
    }

    /**
     * Getter mocy silnika
     *
     * @return engine_power moc silnika
     */
    public String getEnginePower() {
        return engine_power;
    }

    /**
     * Setter typu paliwa
     *
     * @param type_fuel typ paliwa
     */
    public void setTypeFuel(String type_fuel) {
        this.type_fuel = type_fuel;
    }

    /**
     * Getter typu paliwa
     *
     * @return type_fuel typ paliwa
     */
    public String getTypeFuel() {
        return type_fuel;
    }

    /**
     * Getter ceny wynajmu pojazdu
     *
     * @return cena wynajmu pojazdu
     */
    public int getPrice() {
        return price;
    }

    /**
     * Setter ceny wynajmu pojazdu
     *
     * @param price cena wynajmu pojazdu
     */

    public void setPrice(int price) {
        this.price = price;
    }
}
