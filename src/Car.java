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
    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    /**
     * Getter marki
     *
     * @return car_brand marka pojazdu
     */
    public String getCar_brand() {
        return car_brand;
    }

    /**
     * Setter modelu samochodu
     *
     * @param car_model model pojazdu
     */
    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    /**
     * Getter modelu samochodu
     *
     * @return car_model model pojazdu
     */
    public String getCar_model() {
        return car_model;
    }

    /**
     * Setter roku produkcji samochodu
     *
     * @param car_year rok produkcji
     */
    public void setCar_year(int car_year) {
        this.car_year = car_year;
    }

    /**
     * Getter roku produkcji samochodu
     *
     * @return car_year rok produkcji
     */
    public int getCar_year() {
        return car_year;
    }

    /**
     * Setter numeru rejestracyjnego
     *
     * @param registration_number numer rejestracyjny
     */
    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    /**
     * Getter numeru rejestracyjnego
     *
     * @return registration_number numer rejestracyjny
     */
    public String getRegistration_number() {
        return registration_number;
    }

    /**
     * Setter statusu wypożyczenia
     *
     * @param rent_status status wypożyczenia (0 - nie wypożyczony, 1 - wypożyczony)
     */
    public void setRent_status(int rent_status) {
        this.rent_status = rent_status;
    }

    /**
     * Getter statusu wypożyczenia
     *
     * @return rent_status status wypożyczenia (0 - nie wypożyczony, 1 - wypożyczony)
     */
    public int getRent_status() {
        return rent_status;
    }

    /**
     * Setter pojemności silnika
     *
     * @param engine_capacity pojemność silnika
     */
    public void setEngine_capacity(String engine_capacity) {
        this.engine_capacity = engine_capacity;
    }

    /**
     * Getter pojemności silnika
     *
     * @return engine_capacity pojemność silnika
     */
    public String getEngine_capacity() {
        return engine_capacity;
    }

    /**
     * Setter mocy silnika
     *
     * @param engine_power moc silnika
     */
    public void setEngine_power(String engine_power) {
        this.engine_power = engine_power;
    }

    /**
     * Getter mocy silnika
     *
     * @return engine_power moc silnika
     */
    public String getEngine_power() {
        return engine_power;
    }

    /**
     * Setter typu paliwa
     *
     * @param type_fuel typ paliwa
     */
    public void setType_fuel(String type_fuel) {
        this.type_fuel = type_fuel;
    }

    /**
     * Getter typu paliwa
     *
     * @return type_fuel typ paliwa
     */
    public String getType_fuel() {
        return type_fuel;
    }


}
