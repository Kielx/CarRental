/**
 * Klasa dla obslugi uzytkownikow
 */
public class User {

    /**
     * Imie uzytkownika
     * */
    public String name;
    /**
     * Nazwisko uzytkownika
     */
    public String surname;
    /**
     * Pesel uzytkownika
     * */
    public String pesel;
    /**
     * Adres uzytkownika
     * */
    public String address;
    /**
     * Numer telefonu uzytkownika
     * */
    public String phoneNumber;
    /**
     * Email uzytkownika
     * */
    public String email;
    /**
     * Login uzytkownika
     * */
    public int id;
    /**
     * Login uzytkownika
     * */
    public String login;
    /**
     * Haslo uzytkownika
     * */
    public String password;
    /**
     * Czy uzytkownik jest adminem
     * */
    public int admin;

    /**
     * Konstruktor klasy User
     */
    public User(){}

    /**
     * Konstruktor klasy User
     * @param name - imie
     * @param surname - nazwisko
     * @param address - adres
     * @param phoneNumber - numer telefonu
     * @param email - email
     * @param login - login
     * @param password - haslo
     * @param admin - czy jest adminem
     * @param pesel - pesel
     */
    public User(String name, String surname, String address, String phoneNumber, String email, String login,
                String password, int admin, String pesel){
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.login = login;
        this.password = password;
        this.admin = admin;
        this.pesel = pesel;
    }

    /**
     * Nadpisana metoda toString
     * @return - zwraca stringa zawierajacego dane uzytkownika
     */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", phone_number='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", admin='" + admin + '\'' +
                ", pesel='" + pesel + '\'' +
                '}';
    }

    /**
     * Metoda zwracajaca referencje do biezacego obiektu uzytkownika
     * @return - referencja do obiektu uzytkownika
     */
    public User getUser() {
        return this;
    }

    /**
     * Getter id uzytkownika
     * @return - id uzytkownika
     */
    public int getId() {
        return id;
    }

    /**
     * Setter id uzytkownika
     * @param id - id uzytkownika
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter imienia uzytkownika
     * @return - imie uzytkownika
     */
    public String getName() {
        return name;
    }

    /**
     * Setter imienia uzytkownika
     * @param name - imie uzytkownika
     */
    public void setName(String name) {
        if (name.length() > 0) {
            this.name = name;
        } else {
            System.out.println("Nazwa użytkownika nie może być pusta");
        }
    }

    /**
     * Getter nazwiska uzytkownika
     * @return - nazwisko uzytkownika
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter nazwiska uzytkownika
     * @param surname - nazwisko uzytkownika
     */
    public void setSurname(String surname) {
        if (surname.length() > 0) {
            this.surname = surname;
        } else {
            System.out.println("Nazwisko użytkownika nie może być puste");
        }
    }

    /**
     * Getter adresu uzytkownika
     * @return - adres uzytkownika
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter adresu uzytkownika
     * @param address - adres uzytkownika
     */
    public void setAddress(String address) {
        if (address.length() > 0) {
            this.address = address;
        } else {
            System.out.println("Adres użytkownika nie może być pusty");
        }
    }

    /**
     * Getter numeru telefonu uzytkownika
     * @return - numer telefonu uzytkownika
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter numeru telefonu uzytkownika
     * @param phoneNumber - numer telefonu uzytkownika
     */
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.length()==9) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Numer telefonu użytkownika nie może być pusty");
        }

    }

    /**
     * Getter emailu uzytkownika
     * @return - email uzytkownika
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter emailu uzytkownika
     * @param email - email uzytkownika
     */
    public void setEmail(String email) {
        if (email.length() > 0) {
            this.email = email;
        } else {
            System.out.println("Email użytkownika nie może być pusty");
        }
    }

    /**
     * Getter loginu uzytkownika
     * @return - login uzytkownika
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter loginu uzytkownika
     * @param login - login uzytkownika
     */
    public void setLogin(String login) {
        if (login.length() > 0) {
            this.login = login;
        } else {
            System.out.println("Login użytkownika nie może być pusty");
        }
    }

    /**
     * Getter hasla uzytkownika
     * @return - haslo uzytkownika
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter hasla uzytkownika
     * @param password - haslo uzytkownika
     */
    public void setPassword(String password) {
        if (password.length() > 7) {
            this.password = password;
        } else {
            System.out.println("Hasło użytkownika musi posiadac minimum 8 znakow");
        }
    }

    /**
     * Getter czy uzytkownik jest adminem
     * @return - czy uzytkownik jest adminem
     */
    public int getAdmin() {
        return admin;
    }

    /**
     * Setter czy uzytkownik jest adminem
     * @param admin - czy uzytkownik jest adminem
     */
    public void setAdmin(int admin) {
        this.admin = admin;
    }

    /**
     * Getter peselu uzytkownika
     * @return - pesel uzytkownika
     */
    public String getPesel() {
        return pesel;
    }

    /**
     * Setter peselu uzytkownika
     * @param pesel - pesel uzytkownika
     */
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
}
