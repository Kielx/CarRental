public class User {

    private String name;
    private String surname;
    private String address;
    private int phoneNumber;
    private String email;
    private int id;

    public User(String name, String surname, String address, int phoneNumber, String email) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", phone_number='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
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


}
