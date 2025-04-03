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

public class UndergraduateHomePage extends JFrame {

    private String cardCommand;

    private JPanel UndergraduateHomePage;
    private JPanel UGHomeCard;
    private JPanel UGProfile;
    private JPanel UGAttendance;
    private JPanel UGTimeTable;
    private JPanel UGCourses;
    private JPanel UGMedicals;
    private JPanel UGNotices;
    private JPanel UGGrades;
    private JPanel UGSettings;
    private JButton profileButton;
    private JButton attendanceButton;
    private JButton timeTableButton;
    private JButton coursesButton;
    private JButton medicalButton;
    private JButton noticesButton;
    private JButton gradesButton;
    private JButton settingsButton;
    private JButton logoutButton;
    private JTextField txtTGNO;
    private JTextField txtFNAME;
    private JTextField txtLNAME;
    private JTextField txtADDRESS;
    private JTextField txtEMAIL;
    private JTextField txtPHNO;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JButton cancelButton;
    private JButton updateButton;
    private JLabel CardTittleLabel;
    private JButton uploadImageButton;
    private JLabel UGProfileImage;
    private JPanel UGProfImgPanel;

    private CardLayout cardLayout;

    private String UGTgno;
    private String UGFname;
    private String UGLname;
    private String UGAddress;
    private String UGEmail;
    private String UGPhno;
    private String UGProfImg;


    private String[] cardButtons = {"Profile", "Attendance", "Time Table", "Courses", "Medical", "Notices", "Grades", "Settings"};
    private String[] cardNames = {"UGProfileCard", "UGAttendanceCard", "UGTimeTableCard", "UGCoursesCard", "UGMedicalsCard", "UGNoticesCard", "UGGradesCard", "UGSettingsCard"};
    JButton[] btnFieldNames = {profileButton,attendanceButton,timeTableButton,coursesButton,medicalButton,noticesButton,gradesButton,settingsButton};
    private String[] cardTitles = {"Welcome..!", "Attendance Details", "Undergraduate Time Table","Your Courses","Medical Information", "Notices", "Grades and GPA","Settings Configuration"};;

    public UndergraduateHomePage(String userIdentity){
        dbConnection(userIdentity);

//        UGsaveProfileImage();

        setContentPane(UndergraduateHomePage);
        setTitle("Undergraduate User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 570);
        setLocationRelativeTo(this);
        setVisible(true);

        cardLayout = (CardLayout)(UGHomeCard.getLayout());
        profileButton.setEnabled(false);
        CardTittleLabel.setText(cardTitles[0]);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardCommand = e.getActionCommand();
                changeBtnState(cardCommand,userIdentity);
            }
        };
        profileButton.addActionListener(listener);
        settingsButton.addActionListener(listener);
        gradesButton.addActionListener(listener);
        noticesButton.addActionListener(listener);
        medicalButton.addActionListener(listener);
        coursesButton.addActionListener(listener);
        timeTableButton.addActionListener(listener);
        attendanceButton.addActionListener(listener);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UGUpdateCredentials(userIdentity);
            }
        });
        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UGsaveProfileImage(userIdentity);
            }
        });
    }

    public void changeBtnState(String btn, String tgno){

        int noOfButtons = cardButtons.length;
        for (int i = 0; i < noOfButtons; i++){
            if (cardButtons[i].equals(btn)){
                dbConnection(tgno);
                cardLayout.show(UGHomeCard,cardNames[i]);
                CardTittleLabel.setText(cardTitles[i]);
                btnFieldNames[i].setEnabled(false);
            }else {
                btnFieldNames[i].setEnabled(true);
            }
        }
    }

    private void dbConnection(String tgno){
        try{
            String selectQuery = "select * from undergraduate where tgno = '" + tgno + "'";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest","root","1234");
            Statement statement = connection.createStatement();
            ResultSet DBresult = statement.executeQuery(selectQuery);

            if(DBresult.next()){
                UGTgno = DBresult.getString("tgno");
                UGFname = DBresult.getString("ugfname");
                UGLname = DBresult.getString("uglname");
                UGAddress = DBresult.getString("ugaddress");
                UGEmail = DBresult.getString("ugemail");
                UGPhno = DBresult.getString("ugphno");
                UGProfImg = DBresult.getString("ugProfImg");

                txtTGNO.setText(UGTgno);
                txtFNAME.setText(UGFname);
                txtLNAME.setText(UGLname);
                txtADDRESS.setText(UGAddress);
                txtEMAIL.setText(UGEmail);
                txtPHNO.setText(UGPhno);

                textField7.setText(UGAddress);
                textField8.setText(UGEmail);
                textField9.setText(UGPhno);

            }else{
                JOptionPane.showMessageDialog(null,"Internal Error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void UGUpdateCredentials(String tgno){
        try{
            String UGaddress = textField7.getText();
            String UGemail = textField8.getText();
            String UGphno = textField9.getText();
            String UGProfileImagePath = "";

            String UGCredentialupdateQuery = "Update undergraduate set ugaddress = '" + UGaddress + "', ugemail = '"+ UGemail +"',ugphno = '"+ UGphno+"',ugProfImg ='" + UGProfileImagePath + "' where tgno = '" + tgno + "'";

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest","root","1234");
            Statement statement = connection.createStatement();
            int resultSet = statement.executeUpdate(UGCredentialupdateQuery);

            if(resultSet > 0){
                JOptionPane.showMessageDialog(null,"Credentials updated successfully");
            }else {
                JOptionPane.showMessageDialog(null,"Error in credential updation");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void UGsaveProfileImage(String tgno){
        try{
            JFileChooser UGFileChooser = new JFileChooser();
            UGFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images","jpg","png","jpeg","gif"));
//            UGFileChooser.showOpenDialog(null);

//                ############################################################

            if(UGFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){

                ImageIcon icon = new ImageIcon(UGFileChooser.getSelectedFile().getPath());
                Image scaled = icon.getImage().getScaledInstance(
                        UGProfImgPanel.getWidth() - 50,
                        UGProfImgPanel.getHeight() - 50,
                        Image.SCALE_SMOOTH
                );
                UGProfileImage.setIcon(new ImageIcon(scaled));
                UGProfileImage.setText("");

//                ############################################################

//                File f = UGFileChooser.getSelectedFile();
//                UGProfileImage.setIcon(new ImageIcon(f.toString()));

                String filename = UGFileChooser.getSelectedFile().getAbsolutePath();

// Pass the filename for the next method as parameter, Instantiate JFilechooser as UGFileChooser in UGSAveProfile
                
                String UGSaveImagePath = "Resources/ProfileImages/";
                File UGSaveImageDirectory = new File(UGSaveImagePath);
                if (!UGSaveImageDirectory.exists()){
                    UGSaveImageDirectory.mkdirs();
                }

                File UGSourceFile = null;

                String extension = filename.substring(filename.lastIndexOf('.') + 1 );
                UGSourceFile = new File(tgno +"."+ extension);

                File UGDestinationFile = new File(UGSaveImagePath + UGSourceFile);

                System.out.println(UGDestinationFile);

                Path fromFile = UGFileChooser.getSelectedFile().toPath();
                Path toFile = UGDestinationFile.toPath();

                if (UGDestinationFile.exists()){
//                System.out.println("File exists");
                    UGDestinationFile.delete();
                    Files.copy(fromFile,toFile);
                }else{
                    Files.copy(fromFile,toFile);
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

//    private void UGFillUpdateFields(String tgno){
//        String UpdateFillQuery = "select ";
//    }
}
