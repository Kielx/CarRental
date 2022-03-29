public class User {

    private String name;
    private String surname;
    private int pesel;
    private String address;
    private int phoneNumber;
    private String email;
    private int id;
    private String login;
    private String password;
    private int admin;

    public User(){};

    public User(String name, String surname, String address, int phoneNumber, String email, String login,
                String password, int admin, int pesel){
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

    public User getUser() {
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 0) {
            this.name = name;
        } else {
            System.out.println("Nazwa użytkownika nie może być pusta");
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname.length() > 0) {
            this.surname = surname;
        } else {
            System.out.println("Nazwisko użytkownika nie może być puste");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address.length() > 0) {
            this.address = address;
        } else {
            System.out.println("Adres użytkownika nie może być pusty");
        }
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        if (phoneNumber > 0 && phoneNumber < 999999999) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Numer telefonu użytkownika nie może być pusty");
        }

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.length() > 0) {
            this.email = email;
        } else {
            System.out.println("Email użytkownika nie może być pusty");
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login.length() > 0) {
            this.login = login;
        } else {
            System.out.println("Login użytkownika nie może być pusty");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() > 7) {
            this.password = password;
        } else {
            System.out.println("Hasło użytkownika musi posiadac minimum 8 znakow");
        }
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getPesel() {
        return pesel;
    }

    public void setPesel(int pesel) {
        this.pesel = pesel;
    }
}
