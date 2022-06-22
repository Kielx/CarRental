import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistrationLayout extends JDialog {
    private JTextField login;
    private JTextField email;
    private JTextField name;
    private JTextField address;
    private JTextField phone;
    private JTextField surname;
    private JTextField pesel;
    private JButton registration;
    private JPasswordField passwd;
    private JPanel RegistrationPanel;


    public RegistrationLayout() {
        setTitle("Rent Car Kielce - Rejestracja");
        setContentPane(RegistrationPanel);
        setMinimumSize(new Dimension(850, 600));
        setModal(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        registration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        setVisible(true);
    }

    private void registerUser() {
        String name_r = name.getText();
        String surname_r = surname.getText();
        String pesel_r = pesel.getText();
        String address_r = address.getText();
        String phone_number_r = phone.getText();
        String email_r = email.getText();
        String login_r = login.getText();
        String password_r = String.valueOf(passwd.getPassword());
        String hash_passwd = HashPass.md5(password_r);

        String sql = "SELECT count(*) as qnt FROM user WHERE user.login='" + login_r + "'";
        try (Connection conn = Main.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

             int qnt = (rs.getInt("qnt"));
             if(qnt>0){
                 JOptionPane.showMessageDialog(this,
                         "Istnieje użtkownik o takim loginie, użyj innej nazwy użytkownika",
                         "OK",
                         JOptionPane.ERROR_MESSAGE);
                 return;
             }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        int lengthPesel = String.valueOf(pesel_r).length();
        int lengthPhoneNum = String.valueOf(phone_number_r).length();

        if (name_r.isEmpty() || surname_r.isEmpty() || address_r.isEmpty() ||  email_r.isEmpty() || login_r.isEmpty() || password_r.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Wypełnij wszystkie pola", "Spróbuj ponownie", JOptionPane.ERROR_MESSAGE);
            return;
        }else if(lengthPesel!=11){
            JOptionPane.showMessageDialog(this, "Pesel powinien zawierać 11 cyfr", "Spróbuj ponownie",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }else if(lengthPhoneNum!=9){
            JOptionPane.showMessageDialog(this, "Numer telefonu powinien zawierać 9 cyfr", "Spróbuj ponownie",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = insertUser(name_r,surname_r,pesel_r,address_r,phone_number_r,email_r,login_r,hash_passwd,0);
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "Rejestracja przebiegła poprawnie. Dziękujemy :D",
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Błąd podczas rejestracji",
                    "Spróbuj ponownie",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public User user;
    private User insertUser(String name, String surname, String pesel, String address, String phone_number,
                            String email, String login, String password, int admin){
        String sql = "INSERT INTO user (name, surname, pesel, address, phone_number, email, login, password, admin) " +
                "VALUES (?,?, ?, ?, ?, ?, ?,?,?)";

        try (Connection conn = Main.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, pesel);
            pstmt.setString(4, address);
            pstmt.setString(5, phone_number);
            pstmt.setString(6, email);
            pstmt.setString(7, login);
            pstmt.setString(8, password);
            pstmt.setInt(9, admin);

            int addedRows = pstmt.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.name = name;
                user.surname = surname;
                user.pesel = pesel;
                user.address = address;
                user.email = email;
                user.phoneNumber = phone_number;
                user.login = login;
                user.password = password;
                user.admin = admin;
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static void main(String[] args) {
        RegistrationLayout registrationLayout = new RegistrationLayout();
        User user = registrationLayout.user;
        if(user!=null){
            System.out.println("Uzytkownik: " + user.name + " poprawnie zarejestrowany");
        }else{
            System.out.println("BłĄD");
        }
    }
}
