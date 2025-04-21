package HomePage;

import DBCONNECTION.DBCONNECTION;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminHomePage extends JFrame {

    private String cardCommand;

    private JButton profileButton;
    private JButton settingsButton;
    private JButton noticesButton;
    private JButton timeTableButton;
    private JButton userProfilesButton;
    private JButton coursesButton;
    private JLabel CardTittleLabel;
    private JButton logoutButton;
    private JPanel AdminHomeCard;
    private JPanel AdminProfile;
    private JTextField txtADNO;
    private JTextField txtPHNO;
    private JTextField txtEMAIL;
    private JTextField txtADDRESS;
    private JTextField txtLNAME;
    private JTextField txtFNAME;
    private JPanel HomePageUserProfile;
    private JLabel HomePageUserProfileLable;
    private JPanel AdminUserProfiles;
    private JComboBox AttendanceSemesterNo;
    private JComboBox AttendanceLevelNo;
    private JComboBox AttendanceSubjectCode;
    private JComboBox AttendanceSubjectStatus;
    private JButton viewMedicalsButton;
    private JLabel AttendancePercWithoutMed;
    private JLabel AttendancePercWithMed;
    private JComboBox AttendanceSubjectStatusPerc;
    private JTable AttendanceTable;
    private JPanel AdminTimeTable;
    private JTable tableTimeTable;
    private JComboBox SemesterNoDropDown;
    private JComboBox LevelNoDropDown;
    private JPanel AdminCourses;
    private JComboBox LevelNoforCourses;
    private JComboBox SemesterNoforCourses;
    private JComboBox CourseforCourses;
    private JTextArea DescriptionforCourseMaterial;
    private JComboBox CourseMaterialforCourses;
    private JButton SaveCourseMaterial;
    private JPanel AdminNotices;
    private JComboBox noticeTitleDropDown;
    private JTextArea noticeDisplayArea;
    private JPanel AdminSettings;
    private JTextField textField7;
    private JTextField textField9;
    private JTextField textField8;
    private JPanel ADMINProfImgPanel;
    private JLabel ADMINProfileImage;
    private JButton uploadImageButton;
    private JButton cancelButton;
    private JButton updateButton;
    private JPanel AdminHomePage;
    private JTextField textField1;
    private JTextField textField2;
    private JButton changePasswordButton;

    private String ADnumber;
    private String ADFname;
    private String ADLname;
    private String ADAddress;
    private String ADEmail;
    private String ADPhno;
    private String ADProfImg;

    private String[] cardButtons = {"Profile", "User Profiles", "Time Table", "Courses", "Notices", "Settings"};
    private String[] cardNames = {"AdminProfileCard","AdminUserProfileCard","AdminTimeTableCard","AdminCoursesCard","AdminNoticesCard","AdminSettingsCard"};
    JButton[] btnFieldNames = {profileButton,userProfilesButton,timeTableButton,coursesButton,noticesButton,settingsButton};
    private String[] cardTitles = {"Welcome..!","User Profile Details","Time Table","Courses","Notices","Settings Configuration"};;

    DBCONNECTION _dbconn = new DBCONNECTION();
    Connection conn = _dbconn.Conn();
    private PreparedStatement prepStatement;

    private CardLayout cardLayout;

    private Object[] filePathValues = new Object[4];

    public AdminHomePage(String userIdentity){

        dbConnection(userIdentity);
        LoadNotices();

        setContentPane(AdminHomePage);
        setTitle("Admin User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 570);
        setLocationRelativeTo(this);
        setVisible(true);

        cardLayout = (CardLayout)(AdminHomeCard.getLayout());

        profileButton.setEnabled(false);
        CardTittleLabel.setText(cardTitles[0]);
        loadUGProfImage(userIdentity);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardCommand = e.getActionCommand();
                changeBtnState(cardCommand,userIdentity);
            }
        };
        profileButton.addActionListener(listener);
        userProfilesButton.addActionListener(listener);
        settingsButton.addActionListener(listener);
        noticesButton.addActionListener(listener);
        timeTableButton.addActionListener(listener);
        coursesButton.addActionListener(listener);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int noOfButtons = cardButtons.length;

                cardLayout.show(AdminHomeCard,cardNames[0]);
                btnFieldNames[0].setEnabled(false);
                btnFieldNames[noOfButtons-1].setEnabled(true);
                loadUGProfImage(userIdentity);
                dbConnection(userIdentity);
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ADMINUpdateCredentials(userIdentity);
            }
        });
        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ADMINUploadToPreviewProfileImage(userIdentity);
            }
        });
        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ADMINUploadToPreviewProfileImage(userIdentity);
            }
        });
    }
    private boolean isFileChooserOpen = false;

    private void ADMINUploadToPreviewProfileImage(String adno) {
        if (isFileChooserOpen) return;
        isFileChooserOpen = true;

        try {
            JFileChooser UGFileChooser = new JFileChooser();
            UGFileChooser.setDialogTitle("Select Profile Picture");
            UGFileChooser.setAcceptAllFileFilterUsed(false);
            UGFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg"));

            if (UGFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                ImageIcon icon = new ImageIcon(UGFileChooser.getSelectedFile().getPath());
                Image scaled = icon.getImage().getScaledInstance(
                        ADMINProfImgPanel.getWidth() - 50,
                        ADMINProfImgPanel.getHeight() - 50,
                        Image.SCALE_SMOOTH
                );
                ADMINProfileImage.setIcon(new ImageIcon(scaled));
                ADMINProfileImage.setText("");

                String filename = UGFileChooser.getSelectedFile().getAbsolutePath();

                String UGSaveImagePath = "Resources/ProfileImages/";
                File UGSaveImageDirectory = new File(UGSaveImagePath);
                if (!UGSaveImageDirectory.exists()) {
                    UGSaveImageDirectory.mkdirs();
                }

                File UGSourceFile = null;

                String extension = filename.substring(filename.lastIndexOf('.') + 1);

                UGSourceFile = new File(adno + "." + extension);

                File ADMINDestinationFile = new File(UGSaveImagePath + UGSourceFile);

                System.out.println(ADMINDestinationFile);

                Path fromFile = UGFileChooser.getSelectedFile().toPath();
                Path toFile = ADMINDestinationFile.toPath();

                filePathValues[0] = fromFile;
                filePathValues[1] = toFile;
                filePathValues[2] = ADMINDestinationFile;
                filePathValues[3] = extension;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void changeBtnState(String btn, String adno){

        int noOfButtons = cardButtons.length;
        for (int i = 0; i < noOfButtons; i++){
            if (cardButtons[i].equals(btn)){
                dbConnection(adno);
                cardLayout.show(AdminHomeCard,cardNames[i]);
                CardTittleLabel.setText(cardTitles[i]);
                btnFieldNames[i].setEnabled(false);
            }else {
                btnFieldNames[i].setEnabled(true);
            }
        }
    }

    private void dbConnection(String adno){
        try{
            String selectQuery = "select * from admin where adno = ?";

            prepStatement = conn.prepareStatement(selectQuery);
            prepStatement.setString(1,adno);
            ResultSet DBresult = prepStatement.executeQuery();

            if(DBresult.next()){
                ADnumber = DBresult.getString("adno");
                ADFname = DBresult.getString("adfname");
                ADLname = DBresult.getString("adlname");
                ADAddress = DBresult.getString("adaddress");
                ADEmail = DBresult.getString("ademail");
                ADPhno = DBresult.getString("adphno");
                ADProfImg = DBresult.getString("adProfImg");

                txtADNO.setText(ADnumber);
                txtFNAME.setText(ADFname);
                txtLNAME.setText(ADLname);
                txtADDRESS.setText(ADAddress);
                txtEMAIL.setText(ADEmail);
                txtPHNO.setText(ADPhno);

                textField7.setText(ADFname);
                textField8.setText(ADLname);
                textField9.setText(ADAddress);
                textField1.setText(ADEmail);
                textField2.setText(ADPhno);

                loadUGProfImage(adno);
            }else{
                JOptionPane.showMessageDialog(null,"Internal Error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadUGProfImage(String adno){
        try{
            String UGProfImageSearchQuery = "select * from admin where adno = ?";

            prepStatement = conn.prepareStatement(UGProfImageSearchQuery);
            prepStatement.setString(1,adno);
            ResultSet result = prepStatement.executeQuery();

            while (result.next()){
                Path UGSaveImagePath = Path.of(result.getString("adProfImg"));
                ImageIcon icon = new ImageIcon(UGSaveImagePath.toString());
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

    private void LoadNotices(){

        String notice_Title;

        try{
            String noticeLoadQuery = "select * from notice";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(noticeLoadQuery);

            while(result.next()){
                notice_Title = result.getString("noticeTitle");

                noticeTitleDropDown.addItem(notice_Title);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ADMINUpdateCredentials(String adno){
        try{
//            String ADaddress = textField7.getText();
//            String UGemail = textField8.getText();
//            String UGphno = textField9.getText();

            String AdFname = textField7.getText();
            String AdLname = textField8.getText();
            String AdAddress = textField9.getText();
            String AdEmail = textField1.getText();
            String AdPhno = textField2.getText();

            String extension = (String) filePathValues[3];

            String ADMINProfileImagePath = "Resources/ProfileImages/" + adno + "." + extension;
            System.out.println(ADMINProfileImagePath);
            String UGCredentialupdateQuery;

            if (extension == null){
                UGCredentialupdateQuery = "Update admin set adfname = '" + AdFname + "', adlname = '"+ AdLname +"',adaddress = '"+ AdAddress+"', ademail = '"+ AdEmail +"', adphno = '"+ AdPhno +"' where adno = '" + adno + "'";
            }else {
                UGCredentialupdateQuery = "Update admin set adfname = '" + AdFname + "', adlname = '"+ AdLname +"',adaddress = '"+ AdAddress+"', ademail = '"+ AdEmail +"', adphno = '"+ AdPhno +"', adProfImg ='" + ADMINProfileImagePath + "' where adno = '" + adno + "'";
            }

            Statement statement = conn.createStatement();
            int resultSet = statement.executeUpdate(UGCredentialupdateQuery);

            if(resultSet > 0){
                ADMINSaveProfileImage(adno);
                JOptionPane.showMessageDialog(null,"Credentials updated successfully");
            }else {
                JOptionPane.showMessageDialog(null,"Error in credential updation");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void ADMINSaveProfileImage(String adno){

        try{
            Path fromFile = (Path) filePathValues[0];
            Path toFile = (Path) filePathValues[1];
            File ADMINDestinationFile = (File) filePathValues[2];

            if (ADMINDestinationFile.exists()){
                ADMINDestinationFile.delete();
                Files.copy(fromFile,toFile);
            }else{
                Files.copy(fromFile,toFile);
            }

        }catch(Exception exc){

        }
    }
}
