package HomePage;
import Login.Login;



import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TechnicalOfficerHomePage extends JFrame {

    private String cardCommand;

    private JButton profileButton;
    private JButton settingsButton;
    private JButton noticesButton;
    private JButton medicalButton;
    private JButton timeTableButton;
    private JButton attendanceButton;
    private JLabel CardTittleLabel;
    private JButton logoutButton;
    private JPanel TOHomeCard;
    private JPanel TOProfile;
    private JTextField txtTGNO;
    private JTextField txtPHNO;
    private JTextField txtEMAIL;
    private JTextField txtADDRESS;
    private JTextField txtLNAME;
    private JTextField txtFNAME;
    private JPanel TOAttendance;
    private JPanel TOTimeTable;
    private JPanel TOMedicals;
    private JPanel TONotices;
    private JPanel TOSettings;
    private JTextField textField7;
    private JTextField textField9;
    private JTextField textField8;
    private JPanel TOProfImgPanel;
    private JLabel TOProfileImage;
    private JButton uploadImageButton;
    private JButton cancelButton;
    private JButton updateButton;
    private JTextField textField1;
    private JPanel ProfileButton;
    private JPanel TechnicalOfficerHomePage;
    private JPanel HomePageUserProfile;
    private JLabel HomePageUserProfileLable;

    private CardLayout cardLayout;

    private String tono;
    private String toFname;
    private String toLname;
    private String toAddress;
    private String toEmail;
    private String toPhno;
    private String toProfImg;


    private String[] cardButtons = {"Profile", "Attendance", "Time Table", "Medical", "Notices", "Settings"};
    private String[] cardNames = {"TOProfileCard", "TOAttendanceCard", "TOTimeTableCard", "TOMedicalsCard", "TONoticesCard", "TOSettingsCard"};
    JButton[] btnFieldNames = {profileButton,attendanceButton,timeTableButton,medicalButton,noticesButton,settingsButton};
    private String[] cardTitles = {"Welcome..!", "Attendance Details", "View Undergraduate Time Table","Medical Information", "Notices","Settings Configuration"};

    private Object[] filePathValues = new Object[4];



    public TechnicalOfficerHomePage(String userIdentity){

        dbConnection(userIdentity);

        setContentPane(TechnicalOfficerHomePage);
        setTitle("Technical Officer User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 570);
        setLocationRelativeTo(this);
        setVisible(true);

        cardLayout = (CardLayout)(TOHomeCard.getLayout());
        profileButton.setEnabled(false);
        CardTittleLabel.setText(cardTitles[0]);
        loadTOProfImage(userIdentity);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardCommand = e.getActionCommand();
                changeBtnState(cardCommand,userIdentity);
            }
        };
        profileButton.addActionListener(listener);
        settingsButton.addActionListener(listener);
        noticesButton.addActionListener(listener);
        medicalButton.addActionListener(listener);
        timeTableButton.addActionListener(listener);
        attendanceButton.addActionListener(listener);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });
         uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TOUploadToPreviewProfileImage(userIdentity);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TOUpdateCredentials(userIdentity);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int noOfButtons = cardButtons.length;

                cardLayout.show(TOHomeCard,cardNames[0]);
                btnFieldNames[0].setEnabled(false);
                btnFieldNames[noOfButtons-1].setEnabled(true);
            }
        });
    }

    public void changeBtnState(String btn, String tgno){

        int noOfButtons = cardButtons.length;
        for (int i = 0; i < noOfButtons; i++){
            if (cardButtons[i].equals(btn)){
                dbConnection(tgno);
                cardLayout.show(TOHomeCard,cardNames[i]);
                CardTittleLabel.setText(cardTitles[i]);
                btnFieldNames[i].setEnabled(false);
            }else {
                btnFieldNames[i].setEnabled(true);
            }
        }
    }

    private void dbConnection(String tgno){
        try{
            String selectQuery = "select * from technical_officer where tono = '" + tono + "'";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest","root","1234");
            Statement statement = connection.createStatement();
            ResultSet DBresult = statement.executeQuery(selectQuery);

            if(DBresult.next()){
                tono = DBresult.getString("tono");
                toFname = DBresult.getString("tofname");
                toLname = DBresult.getString("tolname");
                toAddress = DBresult.getString("toaddress");
                toEmail = DBresult.getString("toemail");
                toPhno = DBresult.getString("tophno");
                toProfImg = DBresult.getString("toProfImg");

                txtTGNO.setText(tono);
                txtFNAME.setText(toFname);
                txtLNAME.setText(toLname);
                txtADDRESS.setText(toAddress);
                txtEMAIL.setText(toEmail);
                txtPHNO.setText(toPhno);

                textField7.setText(toAddress);
                textField8.setText(toEmail);
                textField9.setText(toPhno);

                loadTOProfImage(tono);
            }else{
                JOptionPane.showMessageDialog(null,"Internal Error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void TOUpdateCredentials(String tgno){
        try{
            String TOaddress = textField7.getText();
            String TOemail = textField8.getText();
            String TOphno = textField9.getText();

            String extension = (String) filePathValues[3];

            String TOProfileImagePath = "Resources/ProfileImages/" + tono + "." + extension;
            System.out.println(TOProfileImagePath);
            String TOCredentialupdateQuery;
            if (extension == null){
                TOCredentialupdateQuery = "Update technical_officer set TOaddress = '" + TOaddress + "', TOemail = '"+ TOemail +"',TOphno = '"+ TOphno+"' where tgno = '" + tgno + "'";
            }else {
                TOCredentialupdateQuery = "Update technical_officer set toaddress = '" + toAddress + "', toemail = '"+ toEmail +"',tophno = '"+ toPhno+"',toProfImg ='" + toProfImg + "' where tono = '" + tono + "'";
            }

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest","root","1234");
            Statement statement = connection.createStatement();
            int resultSet = statement.executeUpdate(TOCredentialupdateQuery);

            if(resultSet > 0){
                TOSaveProfileImage(tgno);
                JOptionPane.showMessageDialog(null,"Credentials updated successfully");
            }else {
                JOptionPane.showMessageDialog(null,"Error in credential updation");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadTOProfImage(String tgno){
        try{
            String UGProfImageSearchQuery = "select * from technical_officer where tono = '" + tono + "'";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest","root","1234");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(UGProfImageSearchQuery);

            while (result.next()){
                Path toSaveImagePath = Path.of(result.getString("toProfImg"));
                ImageIcon icon = new ImageIcon(toSaveImagePath.toString());
                Image scaled = icon.getImage().getScaledInstance(
                        HomePageUserProfile.getWidth() - 50,
                        HomePageUserProfile.getHeight() - 50,
                        Image.SCALE_SMOOTH
                );
                HomePageUserProfileLable.setIcon(new ImageIcon(scaled));
                HomePageUserProfileLable.setText("");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void TOUploadToPreviewProfileImage(String tgno) {
        try {
            JFileChooser UGFileChooser = new JFileChooser();
            UGFileChooser.setDialogTitle("Select Profile Picture");
            UGFileChooser.setAcceptAllFileFilterUsed(false);
            UGFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg"));

            if (UGFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                ImageIcon icon = new ImageIcon(UGFileChooser.getSelectedFile().getPath());
                Image scaled = icon.getImage().getScaledInstance(
                        TOProfImgPanel.getWidth() - 50,
                        TOProfImgPanel.getHeight() - 50,
                        Image.SCALE_SMOOTH
                );
                TOProfileImage.setIcon(new ImageIcon(scaled));
                TOProfileImage.setText("");

                String filename = UGFileChooser.getSelectedFile().getAbsolutePath();

                String UGSaveImagePath = "Resources/ProfileImages/";
                File UGSaveImageDirectory = new File(UGSaveImagePath);
                if (!UGSaveImageDirectory.exists()) {
                    UGSaveImageDirectory.mkdirs();
                }

                File UGSourceFile = null;

                String extension = filename.substring(filename.lastIndexOf('.') + 1);
                UGSourceFile = new File(tgno + "." + extension);

                File UGDestinationFile = new File(UGSaveImagePath + UGSourceFile);

                System.out.println(UGDestinationFile);

                Path fromFile = UGFileChooser.getSelectedFile().toPath();
                Path toFile = UGDestinationFile.toPath();

                filePathValues[0] = fromFile;
                filePathValues[1] = toFile;
                filePathValues[2] = UGDestinationFile;
                filePathValues[3] = extension;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void TOSaveProfileImage(String tgno){

        try{
            Path fromFile = (Path) filePathValues[0];
            Path toFile = (Path) filePathValues[1];
            File UGDestinationFile = (File) filePathValues[2];

            if (UGDestinationFile.exists()){
                UGDestinationFile.delete();
                Files.copy(fromFile,toFile);
            }else{
                Files.copy(fromFile,toFile);
            }

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

}
