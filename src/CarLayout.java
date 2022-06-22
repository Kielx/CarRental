import java.awt.*;
import java.lang.*;
import java.lang.String;
import java.lang.System;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.*;
import javax.swing.ImageIcon;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class CarLayout  implements ListSelectionListener  {

    /**
     * Interfejs uzywany w funkcji lambda dla pol tekstowych z data (dateTextField i dateTextFieldStart)
     * Dzieki temu interfejsowi w prosty sposob mozemy sprawdzac aktualizacje pol
     * Po aktualizacji na biezaco weryfikujemy i ustalamy date, a takze wyswietlamy proponowana cene za wypozyczenie
     */
    @FunctionalInterface
    public interface SimpleDocumentListener extends DocumentListener {
        void update(DocumentEvent e);

        @Override
        default void insertUpdate(DocumentEvent e) {
            update(e);
        }
        @Override
        default void removeUpdate(DocumentEvent e) {
            update(e);
        }
        @Override
        default void changedUpdate(DocumentEvent e) {
            update(e);
        }
    }

    /**
     * DefaultListModel  implementacja ListModel
     */
    DefaultListModel<String> demoList = new DefaultListModel<>();
    DefaultListModel<String> infoListDemo = new DefaultListModel<>();
    DefaultListModel<String> listRentUser = new DefaultListModel<>();
    DefaultListModel<String> listAdminModel= new DefaultListModel<>();
    /**
     * Obszar wyświetlania
     */
    JLabel jlabel, jlicon,status,jIconCar;
    /**
     * Obszar wyświetlania z okreslonymi stylami
     */
    label_class jRentName,jRentPrince,jUserName,jUserSurname,jUserPesel,jUserData,jUserEnd,jUserPrince,jUserRent,jAdminCarW,wprowadzPesel;
    label_class_color INmarka,INmodel,INrejestracja,INstatus,INsilnik,INmoc,INrok,INpaliwo,INcena,wprowadzID;
    /**
     * Komponent wyświetlający listę obiektów
     */
    JList<String> marka;
    JList<String> infoList;
    JList<String> listRent;
    JList<String> listAdminCar;
    /**
     * Klasa Vector implementuje rosnącą tablicę obiektów
     */
    Vector<Integer> indexTab = new Vector<>();
    Vector<String> registTab = new Vector<>();
    String srcIcon;
    /**
     * Implementacja interfejsu ikon, który maluje ikony z obrazów
     */
    ImageIcon icon =new ImageIcon("img/homeimg.png");
    ImageIcon jIconImageCar;

    /**
     * Zbior pojemników dla objektów
     */
    JPanel homePage=new JPanel();
    JPanel btnPanel =new JPanel();
    JPanel home= new JPanel();
    JPanel btnRentPanel= new JPanel();
    JPanel userPanel=new JPanel();
    JPanel adminPanel=new JPanel();
    /**
     * Zbior przycisków
     */
    JButton logoutButton = new JButton("Wyloguj");

    JButton rentButton = new JButton("Wypozycz");
    JButton rentApButton = new JButton("Zatwierdz");
    JButton returnButton= new JButton("Zwroc");
    JButton addCar = new JButton("Dodaj samochod");
    JButton delCar= new JButton("Usun samochod");
    JFrame f = new JFrame("frame");
    /**
     * Format dla daty
     */
    DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);

    /**
     * Format dla daty z bazy danych
     */
    DateTimeFormatter formatUS = new DateTimeFormatterBuilder()
            // your pattern (weekday, month, day, hour/minute/second)
            .appendPattern("EE MMM dd HH:mm:ss ")
            // optional timezone short name (like "CST" or "CEST")
            .optionalStart().appendZoneText(TextStyle.SHORT, Collections.singleton(ZoneId.of("Asia/Kolkata"))).optionalEnd()
            // optional GMT offset (like "GMT+02:00")
            .optionalStart().appendPattern("OOOO").optionalEnd()
            // year
            .appendPattern(" yyyy")
            // create formatter (using English locale to make sure it parses weekday and month names correctly)
            .toFormatter(Locale.US);


    /**
     * Pola tekstowe do wprowadzania danych
     */
    JTextField dateTextField = new JFormattedTextField(format);
    JTextField dateTextFieldStart = new JFormattedTextField(format);
    JTextField returnCarRegist = new JFormattedTextField();
    JTextField idAdminCar = new JFormattedTextField();
    JTextField JTcar_brand = new JFormattedTextField();
    JTextField JTcar_model = new JFormattedTextField();
    JTextField JTregistration_number = new JFormattedTextField();
    JTextField JTrent_status = new JFormattedTextField();
    JTextField JTengine_capacity = new JFormattedTextField();
    JTextField JTengine_power = new JFormattedTextField();
    JTextField JTcar_year = new JFormattedTextField();
    JTextField JTtype_fuel = new JFormattedTextField();
    JTextField JTprince = new JFormattedTextField();

    JTextField rentByPesel = new JFormattedTextField();

    /**
     * Klasa Date reprezentuje określoną chwilę w czasie z dokładnością do milisekund
     */

    Date localData = new Date();
    Date date1;
    Date date2;
    /**
     * Definicja zmiennych
     */
    int activeIndex;
    float actPrince;
    float actIdCar;
    float prince=0;
    /**
     * Klasa z okreslonymi stylami dla Jlabel
     */
    public static class  label_class extends JLabel {
    public label_class(String text,Color fG,Color bG,float size){// constructor
// Transfer of the label text to the super constructor
        super(text);
// Set the color of the text on the label
        setForeground(fG);

// Set the background color of the label
        setBackground(bG);
        setFont(getFont().deriveFont(size));
        setAlignmentX(CENTER_ALIGNMENT);
    }
}
    /**
     * Klasa z okreslonymi stylami dla Jlabel
     */
    public static class  label_class_color extends JLabel {
        public label_class_color(String text,Color fG,Color bG){// constructor
// Transfer of the label text to the super constructor
            super(text);
// Set the background of the label to opaque
            setOpaque(true);
// Set the color of the text on the label
            setForeground(fG);

// Set the background color of the label
            setBackground(bG);
        }
    }




    CarLayout(int UserId,String name,String surname,String pesel,int typeUser){

        final boolean[] verfDate = {false};
        /**
         * Okreslenie polozenia okna aplikacji na ekranie monitora
         */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        f.setSize(width/2, height/2);
        f.setLocationRelativeTo(null);
        f.setAlwaysOnTop (true);
        JScrollPane jsp = new JScrollPane(marka);
        marka = new JList<>();
        listAdminCar=new JList<>();
        infoList=new JList<>();
        listRent=new JList<>();
        jlabel = new JLabel("");
        status = new JLabel("Niedostępny");


        int r,g,b;
        r=25;
        g=30;
        b=41;
        float jlSize=15;
        /**
         * Ustawia elementy za pomocą  Layout Managera
         */
        btnRentPanel.setLayout(new BoxLayout(btnRentPanel, BoxLayout.PAGE_AXIS));
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        adminPanel.setLayout(null);
        /**
         * Ustawiamy pola tekstowe dla jLabel
         */
        INmarka= new label_class_color("Marka: ",new Color (255,255,255),new Color(r,g,b));
        INmodel= new label_class_color("Model: ",new Color (255,255,255),new Color(r,g,b));
        INrejestracja= new label_class_color("Rejestracja",new Color (255,255,255),new Color(r,g,b));
        INstatus= new label_class_color("Rent_status",new Color (255,255,255),new Color(r,g,b));
        INsilnik= new label_class_color("Pojemnosc",new Color (255,255,255),new Color(r,g,b));
        INmoc= new label_class_color("Moc",new Color (255,255,255),new Color(r,g,b));
        INrok= new label_class_color("Rok",new Color (255,255,255),new Color(r,g,b));
        INpaliwo= new label_class_color("Paliwo",new Color (255,255,255),new Color(r,g,b));
        INcena= new label_class_color("Cena/24h",new Color (255,255,255),new Color(r,g,b));
        wprowadzID= new label_class_color("Wprowadz ID",new Color (255,255,255),new Color(r,g,b));



        jlicon=new JLabel();
        jIconCar=new JLabel("");
        jlicon.setIcon(icon);
        jRentName=new label_class("",new Color (255,255,255),new Color(r,g,b),jlSize);
        jRentPrince=new label_class("",new Color (255,255,255),new Color(r,g,b),jlSize);
        jUserName=new label_class("Imie: "+name,new Color (255,255,255),new Color(r,g,b),jlSize);
        jUserSurname=new label_class("Nazwisko: "+surname,new Color (255,255,255),new Color(r,g,b),jlSize);
        jUserPesel=new label_class("Pesel: "+pesel,new Color (255,255,255),new Color(r,g,b),jlSize);
        jUserData=new label_class("Start wypozyczenia: ",new Color (255,255,255),new Color(r,g,b),jlSize);
        jUserEnd=new label_class("Koniec wypozyczenia: ",new Color (255,255,255),new Color(r,g,b),jlSize);
        jUserRent=new label_class("Twoje wypozyczenia: ",new Color (255,255,255),new Color(r,g,b),jlSize);
        jUserPrince=new label_class("Cena: ",new Color (255,255,255),new Color(r,g,b),jlSize);
        jAdminCarW=new label_class("Samochody: ",new Color (255,255,255),new Color(r,g,b),jlSize);
        wprowadzPesel= new label_class("Wprowadz Pesel",new Color (255,255,255),new Color(r,g,b),jlSize);

        /**
         * setBounds służy do definiowania prostokąta ograniczającego rozmiar komponenta.
         */
        homePage.setBounds(0,0,600,600);
        btnPanel.setBounds(0,0,600,50);
        home.setBounds(100,50,500,550);
        btnRentPanel.setBounds(0,50,600,550);
        userPanel.setBounds(0,50,600,550);
        adminPanel.setBounds(0,50,600,550);
        home.setLayout(null);
        marka.setBounds(0,50,100,550);

        infoList.setBounds(100,0,300,150);
        status.setBounds(100,350,100,100);
        status.setFont(status.getFont().deriveFont(16.0f));
        rentButton.setBounds(100,400,100,50);
        jIconCar.setBounds(marka.getWidth(),infoList.getHeight(),300,200);





        listAdminCar.setBounds(25,50,275,300);
        idAdminCar.setBounds(25,390,100,30);
        wprowadzID.setBounds(25,360,100,20);
        delCar.setBounds(25,430,200,30);
        addCar.setBounds(310,430,200,30);


        JTcar_brand.setBounds(410,50,80,20);
        JTcar_model.setBounds(410,90,80,20);
        JTregistration_number.setBounds(410,130,80,20);
        JTrent_status.setBounds(410,170,80,20);
        JTengine_capacity.setBounds(410,210,80,20);
        JTengine_power.setBounds(410,250,80,20);
        JTcar_year.setBounds(410,290,80,20);
        JTtype_fuel.setBounds(410,330,80,20);
        JTprince.setBounds(410,370,80,20);
        INmarka.setBounds(310,50,80,30);
        INmodel.setBounds(310,90,80,20);
        INrejestracja.setBounds(310,130,80,20);
        INstatus.setBounds(310,170,80,20);
        INsilnik.setBounds(310,210,80,20);
        INmoc.setBounds(310,250,80,20);
        INrok.setBounds(310,290,80,20);
        INpaliwo.setBounds(310,330,80,20);
        INcena.setBounds(310,370,80,20);

        /**
         * Ustawiamy rozmiar pol tekstowych.
         */
        dateTextFieldStart.setPreferredSize( new Dimension( 150, 30 ) );
        dateTextFieldStart.setMaximumSize( dateTextFieldStart.getPreferredSize() );
        dateTextFieldStart.setText(format.format(
                new Date()
        ));
        dateTextField.setPreferredSize( new Dimension( 150, 30 ) );
        dateTextField.setMaximumSize( dateTextField.getPreferredSize() );
        dateTextField.setText(format.format(
                new Date(new Date().getTime() + (1000 * 60 * 60 * 24))
        ));
        returnCarRegist.setPreferredSize( new Dimension( 100, 30 ) );
        returnCarRegist.setMaximumSize( returnCarRegist.getPreferredSize() );
        rentByPesel.setPreferredSize( new Dimension( 100, 30 ) );
        rentByPesel.setMaximumSize( rentByPesel.getPreferredSize() );

        /**
         * setBackground słuzy do zmiany tła.
         */
        adminPanel.setBackground(new Color(r,g,b));
        userPanel.setBackground(new Color(r,g,b));
        marka.setBackground(new Color(r,g,b));
        home.setBackground(new Color(r,g,b));
        infoList.setBackground(new Color(r,g,b));
        listAdminCar.setBackground(new Color(r,g,b));
        btnPanel.setBackground(new Color(r,g,b));
        btnRentPanel.setBackground(new Color(r,g,b));
        listRent.setBackground(new Color(r,g,b));

        /**
         * setBackground słuzy do zmiany koloru czcionki.
         */
        marka.setForeground(new Color(255,255,255));
        listAdminCar.setForeground(new Color(255,255,255));
        status.setForeground(new Color(255,255,255));
        infoList.setForeground(new Color(255,255,255));
        listRent.setForeground(new Color(255,255,255));

        JButton firstButton = new JButton("Strona Glowna");
        JButton previousButton = new JButton("Auta");
        JButton lastButton = new JButton("Panel Uzytkowanika");
        JButton lastAdminButton =new JButton("Panel Admina");

        /**
         * Add słuzy do dodania komponentu do panelu
         */
        btnRentPanel.add(jUserName);
        btnRentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        btnRentPanel.add(jUserSurname);
        btnRentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        btnRentPanel.add(jUserPesel);
        btnRentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        btnRentPanel.add(jRentName);
        btnRentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        btnRentPanel.add(jRentPrince);
        btnRentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        btnRentPanel.add(jUserData,BorderLayout.CENTER);
        btnRentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        btnRentPanel.add(dateTextFieldStart,BorderLayout.CENTER);
        btnRentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        btnRentPanel.add(jUserEnd,BorderLayout.CENTER);
        btnRentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        btnRentPanel.add(dateTextField,BorderLayout.CENTER);
        btnRentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        btnRentPanel.add(jUserPrince,BorderLayout.CENTER);
        btnRentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        if(typeUser==1)btnRentPanel.add(wprowadzPesel);
        btnRentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        if(typeUser==1)btnRentPanel.add(rentByPesel);
        btnRentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        btnRentPanel.add(rentApButton);


        userPanel.add(jUserName);
        userPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userPanel.add(jUserSurname);
        userPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userPanel.add(jUserPesel);
        userPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userPanel.add(jUserRent);





        homePage.add(jlicon);
        home.add(jIconCar);
        home.add(infoList);

        btnPanel.add(firstButton);
        btnPanel.add(previousButton);
        if(typeUser==0)btnPanel.add(lastButton);
        else btnPanel.add(lastAdminButton);
        btnPanel.add(logoutButton);

        adminPanel.add(JTcar_brand);
        adminPanel.add(JTcar_model);
        adminPanel.add(JTregistration_number);
        adminPanel.add(JTrent_status);
        adminPanel.add(JTengine_capacity);
        adminPanel.add(JTengine_power);
        adminPanel.add(JTcar_year);
        adminPanel.add(JTtype_fuel);
        adminPanel.add(JTprince);
        adminPanel.add(listAdminCar);
        adminPanel.add(idAdminCar);
        adminPanel.add(wprowadzID);
        adminPanel.add(delCar);
        adminPanel.add(addCar);

        adminPanel.add(INmarka);
        adminPanel.add(INmodel);
        adminPanel.add(INrejestracja);
        adminPanel.add(INstatus);
        adminPanel.add(INsilnik);
        adminPanel.add(INmoc);
        adminPanel.add(INrok);
        adminPanel.add(INpaliwo);
        adminPanel.add(INcena);



        /**
         * Okreslamy co ma sie wyswietlac przy starcie aplikacji.
         */
        btnRentPanel.setVisible(false);
        marka.setVisible(false);
        home.setVisible(false);
        userPanel.setVisible(false);
        adminPanel.setVisible(false);
        /**
         * Lambda do nasłuchiwania akcji kazdego z przycisków
         */
        firstButton.addActionListener(ae -> {
            marka.setVisible(false);
            btnRentPanel.setVisible(false);
            home.setVisible(false);
            homePage.setVisible(true);
            userPanel.setVisible(false);
            adminPanel.setVisible(false);
        });

        logoutButton.addActionListener(e -> {
            f.dispose();
             new LoginLayout(null);
        });
        previousButton.addActionListener(ae -> {
            homePage.setVisible(false);
            btnRentPanel.setVisible(false);
            home.setVisible(true);
            marka.setVisible(true);
            userPanel.setVisible(false);
            adminPanel.setVisible(false);
            /**
             * Usuwamy kazdy element z listy dla poprawnego wyswietlania
             */
            demoList.removeAllElements();
            indexTab.removeAllElements();
            try {
                List<Car> allCars = CarUtils.getAllCars();
                if(!allCars.isEmpty())
                /**
                 * Dodajemy do jList dane marki i modelu samochodu  z bazy danych
                 */
                for (Car car : allCars) {

                        demoList.addElement(car.getBrand() + " " + car.getModel());
                        indexTab.add(car.getId());

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            /**
             * Jesli demoList jest pusta dodajemy odswiezona done
             */
           if(!demoList.isEmpty()) {
                marka.setModel(demoList);
                marka.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                marka.addListSelectionListener(this);

            }
        });


        final String[] info = new String[1];
        lastAdminButton.addActionListener(ae -> {
                    home.setVisible(false);
                    homePage.setVisible(false);
                    btnRentPanel.setVisible(false);
                    adminPanel.setVisible(true);
                    marka.setVisible(false);
            listAdminModel.removeAllElements();
            try {
                List<Car> allCars = CarUtils.getAllCars();
                /**
                 * Sprawdzamy status samochodu i wyswietlamy odpowiedni komunikat
                 */
                for (Car car : allCars) {
                    if(car.getRentStatus()==0) info[0] ="Dostepny";
                    else info[0] ="Niedostępny";
                    listAdminModel.addElement("ID:"+car.getId()+" "+car.getBrand() + " " + car.getModel()+" Status: "+ info[0]);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            listAdminCar.setModel(listAdminModel);
                });

        delCar.addActionListener(ae -> {
            /**
             * Pobieramy informacje o samochodzie za pomoca funkcji getCarsById i sprawdzamy status wypozyczenia
             * Jezeli samochod jest wypozyczony wyswietlamy komunikat w przeciwnym razie usuwamy go z bazy danych
             * Jezeli pole tekstowe idAdminCar jest puste wyswietlamy odpowiedni komunikat
             */
            if(!idAdminCar.getText().isEmpty()){
                try {
                    List<Car> allCars = CarUtils.getCarsById(Integer.parseInt(idAdminCar.getText()));
                    for (Car car : allCars) {
                        if (car.getRentStatus() == 0) {
                            if(CarUtils.deleteCar(car.getRegistrationNumber())) JOptionPane.showMessageDialog(f,
                                    "Sukces ",
                                    "OK",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        else     JOptionPane.showMessageDialog(f,
                                "Nie mozesz usunac tego auta! Jest wypozyczone!! ",
                                "OK",
                                JOptionPane.INFORMATION_MESSAGE);

                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            else     JOptionPane.showMessageDialog(f,
                    "Podaj Id ",
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE);

        });
        addCar.addActionListener(ae->{
            /**
             * Sprawdzamy czy wszystkie pola tekstowe sa uzupełnione
             */
            if (JTcar_brand .getText().isEmpty() || JTcar_model .getText().isEmpty() ||JTregistration_number .getText().isEmpty()||
                    JTrent_status .getText().isEmpty()|| JTengine_capacity .getText().isEmpty() || JTengine_power .getText().isEmpty()||
                    JTcar_year .getText().isEmpty()|| JTtype_fuel .getText().isEmpty()|| JTprince .getText().isEmpty())
                JOptionPane.showMessageDialog(f, "Wypełnij wszystkie pola", "Spróbuj ponownie", JOptionPane.ERROR_MESSAGE);
            /**
             *Wprowadzamy dane do bazy i wyswietlamy komunikat
             */
            else {
                String sql = "INSERT INTO car (car_brand, car_model, registration_number,rent_status , engine_capacity, engine_power,car_year, type_fuel,price) VALUES (?,?, ?, ?, ?, ?, ?,?,?)";

                try (Connection conn = Main.connect();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, JTcar_brand .getText());
                    pstmt.setString(2, JTcar_model .getText());
                    pstmt.setString(3, JTregistration_number .getText());
                    pstmt.setString(4, JTrent_status .getText());
                    pstmt.setString(5,JTengine_capacity .getText());
                    pstmt.setString(6, JTengine_power .getText());
                    pstmt.setString(7,  JTcar_year .getText());
                    pstmt.setString(8,  JTtype_fuel .getText());
                    pstmt.setString(9,  JTprince .getText());

                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(f,
                            "Sukces ",
                            "Dodano",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }


            }


        });
        final String[] date = new String[1];
        final long[] dataTimeOld = {0};
        lastButton.addActionListener(ae -> {
            home.setVisible(false);
            homePage.setVisible(false);
            btnRentPanel.setVisible(false);
            userPanel.setVisible(true);
            marka.setVisible(false);
            adminPanel.setVisible(false);
            /**
             * Zapytanie do bazy o wypozyczenia dla zalogowanego uzytkownika
             */
            String sqluser = "SELECT * FROM rent, car WHERE client_id = ? AND car_id=car.ID";
            listRentUser.removeAllElements();
            try (Connection conn = Main.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sqluser)) {
                pstmt.setInt(1, UserId);
                ResultSet rs = pstmt.executeQuery();
                /**
                 * Dodajemy informacje z bazy danych do jList
                 */
                while (rs.next()) {

                    listRentUser.addElement(  "Model i marka: " + rs.getString("car_brand")+" "+rs.getString("car_model")+" "
                            +"Numer rejestracyjny samochodu: " + rs.getString("registration_number"));
                    listRentUser.addElement("Data wypożyczenia: " +  rs.getString("rental_date"));
                    listRentUser.addElement("Data zwrotu: "+ rs.getString("return_date"));
                    listRentUser.addElement( "Cena: " + rs.getInt("payment_amount"));
                    registTab.add(rs.getString("registration_number"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            /**
             * Jesli uzytkownik posiada wypozyczenia i spozni sie ze zwrotem samochodu naliczamy dodatkowe opłayt
             */
            if(!listRentUser.isEmpty()) {

                try (Connection conn = Main.connect();
                     PreparedStatement pstmt = conn.prepareStatement(sqluser)) {
                    pstmt.setInt(1, UserId);
                    ResultSet rs = pstmt.executeQuery();
                    /**
                     * Wprowadzamy nowa wartosc do bazy
                     */
                    String sqlUpdate = "UPDATE  rent SET payment_amount=? WHERE ID=?";
                    while (rs.next()) {
                        localData = new Date();
                        date[0] = rs.getString("return_date");
                        ZonedDateTime Ldate1= ZonedDateTime.parse(date[0],formatUS);
                        if (ChronoUnit.MINUTES.between(Ldate1.toInstant(), localData.toInstant())> 0&&ChronoUnit.MINUTES.between(Ldate1.toInstant(), localData.toInstant())> dataTimeOld[0])
                        {
                            dataTimeOld[0] =ChronoUnit.MINUTES.between(Ldate1.toInstant(), localData.toInstant());
                            float mins=(ChronoUnit.MINUTES.between(Ldate1.toInstant(), localData.toInstant()));
                            PreparedStatement pstmtup = conn.prepareStatement(sqlUpdate);
                            int priceCar=rs.getInt("price");
                            float priceRent=rs.getInt("payment_amount");
                            pstmtup.setString(1, String.valueOf(priceRent+ (float)(priceCar/1440)*mins));
                            pstmtup.setString(2, rs.getString("ID"));
                            pstmtup.executeUpdate();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                }


        });
        listRent.setModel(listRentUser);
        userPanel.add(listRent);
        userPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userPanel.add(returnCarRegist,BorderLayout.CENTER);
        userPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userPanel.add(returnButton,BorderLayout.CENTER);

        returnButton.addActionListener(aee -> {
            /**
             * Zapytanie do bazy danych o samochod z okreslonym id
             */
            String checkIfCarExists = "SELECT (ID) FROM car WHERE registration_number = ?";
            try (Connection conn = Main.connect();
                 PreparedStatement pstmt = conn.prepareStatement(checkIfCarExists)) {
                pstmt.setString(1, returnCarRegist.getText());
                ResultSet rs = pstmt.executeQuery();
                /**
                 * Jesli ResultSet zwraca null oznacza to ze samochod nie istnieje
                 * Wyswietlamy odpowiedni komunikat
                 */
                if (rs!=null&&!listRentUser.isEmpty()){
                    /**
                     * Usuwamy samochod z bazy danych
                     */
                        String sql = "DELETE FROM rent WHERE car_id = " + rs.getString("ID");
                        PreparedStatement pstmt2 = conn.prepareStatement(sql);
                        pstmt2.executeUpdate();
                    /**
                     * Wprowadzamy nowy status auta
                     */
                        String sqlUpdate = "UPDATE  car SET rent_status=+0" +
                                " WHERE ID=" +  rs.getString("ID");
                        PreparedStatement pstmt3 = conn.prepareStatement(sqlUpdate);
                        pstmt3.executeUpdate();
                        JOptionPane.showMessageDialog(f,
                                "Sukces ",
                                "Zapłac",
                                JOptionPane.INFORMATION_MESSAGE);

                }
                else JOptionPane.showMessageDialog(f,
                        "Brak",
                        "OK",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(f,
                        "Wprowadz poprawna rejestracje",
                        "OK",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });


        rentButton.addActionListener(ae -> {
            homePage.setVisible(false);
            home.setVisible(false);
            marka.setVisible(false);
            btnRentPanel.setVisible(true);

            try {
                /**
                 * Pobieramy informacje o samochodzie ktory chcemy wypozyczyc
                 */
                List<Car> allCars = CarUtils.getCarsById(activeIndex);
                for (Car car : allCars) {
                    /**
                     * Ustawiamy odpowiedni tekst dla jLabel
                     */
                    jRentName.setText("Pojazd: "+car.getBrand() + " " + car.getModel());
                    jRentPrince.setText("Cena: "+car.getPrice() + " zł"+"/24h");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        });
        /**
         * Lambda do nasłuchiwania akcji z pola tekstowego
         */
        dateTextField.getDocument().addDocumentListener((SimpleDocumentListener) ae -> {
                    /**
                     * Aktualizacja daty
                     */
                    localData = new Date();
                    /**
                     * Wprawdzamy czy pola tekstowe sa wprowadzone
                     */
                    if (!dateTextField.getText().isEmpty() && !dateTextFieldStart.getText().isEmpty()) {
                        /**
                         * Konwersja danych typu string na data
                         */
                        try {
                            date1 = format.parse(dateTextFieldStart.getText());
                            date2 = format.parse(dateTextField.getText());

                            /**
                             * Kalkulacja ceny(Cena za samochod jest okreslona /24h )
                             * Wyliczmy cene minutowa
                             */

                            if (ChronoUnit.MINUTES.between(date1.toInstant(), date2.toInstant()) > 0) {
                                float noOfDaysBetween = ChronoUnit.MINUTES.between(date1.toInstant(), date2.toInstant());
                                prince = noOfDaysBetween * (actPrince / 1440);
                                jUserPrince.setText("Cena : " + prince);
                                verfDate[0] = true;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                });



        /**
         * Przypisanie odpowiedniej akcji dla typu uzytkowania (user/admin) dla przycisku wypozycz
         */
        if(typeUser==0)rentApButton.addActionListener(eb -> {
                    if (!dateTextField.getText().isEmpty() && !dateTextFieldStart.getText().isEmpty()&&verfDate[0]) {
                        /**
                         * Wprowadzenie do bazy informacji o wypożyczeniu i aktualizacja statusu
                         */
                        String sql = "INSERT INTO rent (car_id, client_id, rental_date, return_date, payment_amount) " +
                                "VALUES (?,?, ?, ?,?)";
                        String typrent = "UPDATE  car SET rent_status=?" +
                                " WHERE ID=?";

                        try (Connection conn = Main.connect();
                             PreparedStatement pstmt = conn.prepareStatement(sql)
                        ) {
                            pstmt.setString(1, String.valueOf(actIdCar));
                            pstmt.setString(2, String.valueOf(UserId));
                            pstmt.setString(3, String.valueOf(date1));
                            pstmt.setString(4, String.valueOf(date2));
                            pstmt.setString(5, String.valueOf(prince));
                            pstmt.executeUpdate();


                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                        try (Connection conn = Main.connect();
                             PreparedStatement pstmtTyperent = conn.prepareStatement(typrent)
                        ) {
                            pstmtTyperent.setString(1, String.valueOf(1));
                            pstmtTyperent.setString(2, String.valueOf(actIdCar));
                            pstmtTyperent.executeUpdate();

                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }

                        JOptionPane.showMessageDialog(f,
                                "Dodano auto ",
                                "OK",
                                JOptionPane.INFORMATION_MESSAGE);

                    } else JOptionPane.showMessageDialog(f,
                            "Bład danych ",
                            "OK",
                            JOptionPane.INFORMATION_MESSAGE);

            });
        /**
         * Przypisanie odpowiedniej akcji dla typu uzytkowania (user/admin) dla przycisku wypozycz
         */
            if(typeUser==1) {
                rentApButton.addActionListener(eb -> {
                    if (!dateTextField.getText().isEmpty() && !dateTextFieldStart.getText().isEmpty() && !rentByPesel.getText().isEmpty() && verfDate[0]) {
                        /**
                         * Zapytanie o uzytkownika o podanym nr pesel jesli ResultSet zwraca null oznacza ze dany uzytkownik nie istnieje
                         * Wprowadzenie do bazy informacji o wypożyczeniu i aktualizacja statusu
                         */
                        String userId = "SELECT (ID) FROM user WHERE pesel=?";
                        String sql = "INSERT INTO rent (car_id, client_id, rental_date, return_date, payment_amount) " +
                                "VALUES (?,?, ?, ?,?)";
                        String typrent = "UPDATE  car SET rent_status=?" +
                                " WHERE ID=?";
                        try (Connection conuser = Main.connect();
                             PreparedStatement userpsmt = conuser.prepareStatement(userId);
                             PreparedStatement pstmt = conuser.prepareStatement(sql);
                             PreparedStatement pstmtTyperent = conuser.prepareStatement(typrent)

                        ) {
                            userpsmt.setString(1, String.valueOf(rentByPesel.getText()));
                            ResultSet rs = userpsmt.executeQuery();
                            if (rs != null) {


                                try (Connection conn = Main.connect()

                                ) {
                                    pstmt.setString(1, String.valueOf(actIdCar));
                                    pstmt.setString(2, rs.getString("ID"));
                                    pstmt.setString(3, String.valueOf(date1));
                                    pstmt.setString(4, String.valueOf(date2));
                                    pstmt.setString(5, String.valueOf(prince));
                                    pstmt.executeUpdate();


                                    pstmtTyperent.setString(1, String.valueOf(1));
                                    pstmtTyperent.setString(2, String.valueOf(actIdCar));
                                    pstmtTyperent.executeUpdate();

                                    JOptionPane.showMessageDialog(f,
                                            "Dodano auto ",
                                            "OK",
                                            JOptionPane.INFORMATION_MESSAGE);
                                } catch (SQLException e) {
                                    JOptionPane.showMessageDialog(f, "Bład danych ",
                                            "OK",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }else JOptionPane.showMessageDialog(f,
                            "Wprowadz dane ",
                            "OK",
                            JOptionPane.INFORMATION_MESSAGE);

                });
            }



        /**
         * Dodajemy panele do frame
         */
        f.add(adminPanel);
        f.add(userPanel);
        f.add(btnRentPanel);
        f.add(btnPanel);
        f.add(homePage);
        f.add(home);
        f.add(marka);
        f.add(jsp);
        //set the size of frame
        f.setResizable(false);
        f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        f.setSize(600, 600);
        f.setVisible(true);

}
    /**
     * Funkcja ktora dodaje informacje o samochodzi ktory jest aktualnie wybrany z listy marka
     */
    public void valueChanged(ListSelectionEvent le){
        infoListDemo.removeAllElements();
        int index= marka.getSelectedIndex();
        if(!demoList.isEmpty()&&index>-1) {
            /**
             * ActiveIndex to index pojazdu z bazy danych
             * Index to aktualnie wybrana pozycja z listy marka
             */
            activeIndex = indexTab.get(index);
            int activeRentStatus = -1;
            try {
                List<Car> allCars = CarUtils.getCarsById(activeIndex);
                for (Car car : allCars) {
                    /**
                     * Dodajemy do listy informacje z bazy danych
                     */
                    infoListDemo.addElement("Model i marka: " + car.getBrand() + " " + car.getModel());
                    infoListDemo.addElement("Numer tablicy: " + car.getRegistrationNumber());
                    infoListDemo.addElement("Pojemność: " + car.getEngineCapacity());
                    infoListDemo.addElement("Moc: " + car.getEnginePower());
                    infoListDemo.addElement("Rok: " + car.getYear());
                    infoListDemo.addElement("Rodzaj paliwa: " + car.getTypeFuel());
                    infoListDemo.addElement("Cena za dzień: " + car.getPrice());
                    activeRentStatus = car.getRentStatus();
                    srcIcon = "img/" + car.getBrand() + "" + car.getRegistrationNumber() + ".jpg";
                    actPrince = car.getPrice();
                    actIdCar = car.getId();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            /**
             * Jesli samochod jest dostepny dodajemy przycisk mozliwosc wypozyczenia
             */
            if (activeRentStatus != 1) {
                home.add(rentButton);
                home.remove(status);
            }
            /**
             * Jesli samochod jest niedostepny dodajemy informacje o statusie
             */
            else {
                home.add(status);
                home.remove(rentButton);
            }

        jIconImageCar=new ImageIcon(srcIcon);
        jIconCar.setIcon(jIconImageCar);

        infoList.setModel(infoListDemo);
        }

        //Odswiezamy okno
        f.repaint();
    }



}










