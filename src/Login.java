import java.sql.*;
import java.util.*;

public class Login {
    /**
     * Panel logowania uzytkownika
     */

    public static boolean showLoginPanel(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Panel logowania");
        System.out.println("Login:");
        String login = scanner.nextLine();
        System.out.println("Haslo:");
        String passwd = scanner.nextLine();

        String hash_passwd = HashPass.md5(passwd);
        System.out.println("MD5: "+hash_passwd+"\nHaslo: "+passwd);

        String sql = "SELECT count(*) as qnt FROM user WHERE user.login='"+login+"'";
        String sql1 = "SELECT count(*) as qnt1,* FROM user WHERE user.login='"+login+"' AND user.password='"+hash_passwd+"'";

        try (Connection conn = Main.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int qnt=(rs.getInt("qnt"));
            //System.out.println(qnt);

            if(qnt==1){
                ResultSet rsUser = stmt.executeQuery(sql1);
                int qnt1=(rsUser.getInt("qnt1"));
                if(qnt1==1) {
                    sql = "SELECT admin FROM user WHERE user.login='" + login + "' AND user.password='" + hash_passwd + "'";
                    Statement stmt1 = conn.createStatement();
                    ResultSet rsAdmin = stmt1.executeQuery(sql);
                    int admin = rsAdmin.getInt("admin");
                    System.out.println("Zalogowano");
                    if (admin == 1) {
                        Menu.showMenuAdmin();
                    }
                    else {
                        Menu.showMenuUser();
                    }
                    return true;
                }else System.out.println("Bledne haslo");
            }
            else if(qnt > 1){
                System.out.println("Blad w bazie uzytkownikow");
            }
            else {
                System.out.println("Brak uzytkownika o takim loginie");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return (showLoginPanel());
    }

    public static void showRegisterPanel(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Rejestracja");
        UserMan.insertRegUser();
        Menu.chooseLoginOrReg();
    }
}
