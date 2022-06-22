import javax.swing.*;
import java.awt.*;

import java.sql.*;



public class LoginLayout<override> extends JDialog{
    private JPanel loginPanel;
    private JTextField login;
    private JPasswordField passwd;
    private JButton loginButton;
    private JButton newRegisButton;

    public LoginLayout(JFrame parent){
        super(parent);
        setTitle("Rent Car Kielce - Panel logowania");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(800, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loginButton.addActionListener(e -> {
            String login_l = login.getText();
            String password_l = String.valueOf(passwd.getPassword());

            user = loginUser(login_l, password_l);

            if (user != null) {
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(LoginLayout.this,
                        "Login lub hasło są nieprawidłowe",
                        "Spróbuj ponownie",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        newRegisButton.addActionListener(e -> new RegistrationLayout());

        setVisible(true);
    }



    public User user;



    private User loginUser(String login, String password){

        User user = null;
        String hash_passwd = HashPass.md5(password);

        String sql = "SELECT count(*) as qnt FROM user WHERE user.login='" + login + "'";
        String sql1 = "SELECT count(*) as qnt1,* FROM user WHERE user.login='" + login + "' AND user.password='" + hash_passwd + "'";
        try (Connection conn = Main.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int qnt = (rs.getInt("qnt"));

            if (qnt == 1) {
                ResultSet rsUser = stmt.executeQuery(sql1);
                int qnt1 = (rsUser.getInt("qnt1"));
                if (qnt1 == 1) {
                    sql = "SELECT admin, ID FROM user WHERE  user.login='" + login + "' AND user.password='" + hash_passwd + "'";
                    Statement stmt1 = conn.createStatement();
                    ResultSet rsAdmin = stmt1.executeQuery(sql);
                    int admin = rsAdmin.getInt("admin");
                    System.out.println("Zalogowano");

                    user=new User();
                    user.name = rsUser.getString("name");
                    user.surname = rsUser.getString("surname");
                    user.pesel = rsUser.getString("pesel");
                    user.address = rsUser.getString("address");
                    user.email = rsUser.getString("email");
                    user.phoneNumber = rsUser.getString("phone_number");
                    user.id = rsUser.getInt("id");
                    user.login = rsUser.getString("login");
                    user.password = rsUser.getString("password");
                    user.admin = rsUser.getInt("admin");
                    System.out.println(""+user.name);

                }

            }
           if(user!=null) new CarLayout(user.id,user.name,user.surname,user.pesel,user.admin);
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        return user;
    }

    public static void main(String[] args) {
        LoginLayout loginLayout = new LoginLayout(null);
        User user = loginLayout.user;

        if (user != null) {
            System.out.println("Poprawne logowanie: ");
            System.out.println("          Imię: " + user.name);
            System.out.println("          Email: " + user.email);
            System.out.println("          Phone: " + user.phoneNumber);
            System.out.println("          Address: " + user.address);
            System.out.println("          admin: " + user.admin);


            }

        else {
            System.out.println("Authentication canceled");
        }
    }
}
