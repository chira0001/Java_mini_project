package HomePage;
import DBCONNECTION.DBCONNECTION;
import Login.Login;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Scanner;

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
    private JLabel HomePageUserProfileLable;
    private JPanel HomePageUserProfile;
    private JComboBox noticeTitleDropDown;
    private JTextArea noticeDisplayArea;
    private JTable tableTimeTable;
    private JComboBox comboBox1;
    private JButton viewMaterialsButton;

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

    private Object[] filePathValues = new Object[4];
//    private Object[][] data;

    DBCONNECTION _dbconn = new DBCONNECTION();
    Connection conn = _dbconn.Conn();
    private PreparedStatement prepStatement;

    private Scanner input;

    public UndergraduateHomePage(String userIdentity){

        dbConnection(userIdentity);
        LoadNotices();

        setContentPane(UndergraduateHomePage);
        setTitle("Undergraduate User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 570);
        setLocationRelativeTo(this);
        setVisible(true);

        cardLayout = (CardLayout)(UGHomeCard.getLayout());

        profileButton.setEnabled(false);
        CardTittleLabel.setText(cardTitles[0]);
        loadUGProfImage(userIdentity);

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
        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UGUploadToPreviewProfileImage(userIdentity);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UGUpdateCredentials(userIdentity);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int noOfButtons = cardButtons.length;

                cardLayout.show(UGHomeCard,cardNames[0]);
                btnFieldNames[0].setEnabled(false);
                btnFieldNames[noOfButtons-1].setEnabled(true);
                loadUGProfImage(userIdentity);
                dbConnection(userIdentity);
            }
        });
        noticeTitleDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String notice = (String) noticeTitleDropDown.getSelectedItem();
                System.out.println(notice);
                viewNotice(notice);
            }
        });

        tableTimeTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Day", "Course Module"}
        ));
    }

    private void valuesForCourseTable(){
        Object[][] data = {
                {"hi"},
                {"hi"}
        };
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
            String selectQuery = "select * from undergraduate where tgno = ?";

            prepStatement = conn.prepareStatement(selectQuery);
            prepStatement.setString(1,tgno);
            ResultSet DBresult = prepStatement.executeQuery();

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

                loadUGProfImage(tgno);
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

            String extension = (String) filePathValues[3];

            String UGProfileImagePath = "Resources/ProfileImages/" + tgno + "." + extension;
            System.out.println(UGProfileImagePath);
            String UGCredentialupdateQuery;
            if (extension == null){
                 UGCredentialupdateQuery = "Update undergraduate set ugaddress = '" + UGaddress + "', ugemail = '"+ UGemail +"',ugphno = '"+ UGphno+"' where tgno = '" + tgno + "'";
            }else {
                 UGCredentialupdateQuery = "Update undergraduate set ugaddress = '" + UGaddress + "', ugemail = '"+ UGemail +"',ugphno = '"+ UGphno+"',ugProfImg ='" + UGProfileImagePath + "' where tgno = '" + tgno + "'";
            }

            Statement statement = conn.createStatement();
            int resultSet = statement.executeUpdate(UGCredentialupdateQuery);

            if(resultSet > 0){
                UGSaveProfileImage(tgno);
                JOptionPane.showMessageDialog(null,"Credentials updated successfully");
            }else {
                JOptionPane.showMessageDialog(null,"Error in credential updation");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadUGProfImage(String tgno){
        try{
            String UGProfImageSearchQuery = "select * from undergraduate where tgno = ?";

            prepStatement = conn.prepareStatement(UGProfImageSearchQuery);
            prepStatement.setString(1,tgno);
            ResultSet result = prepStatement.executeQuery();

            while (result.next()){
                Path UGSaveImagePath = Path.of(result.getString("ugProfImg"));
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

    private void UGUploadToPreviewProfileImage(String tgno) {
        try {
            JFileChooser UGFileChooser = new JFileChooser();
            UGFileChooser.setDialogTitle("Select Profile Picture");
            UGFileChooser.setAcceptAllFileFilterUsed(false);
            UGFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg"));

            if (UGFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                ImageIcon icon = new ImageIcon(UGFileChooser.getSelectedFile().getPath());
                Image scaled = icon.getImage().getScaledInstance(
                        UGProfImgPanel.getWidth() - 50,
                        UGProfImgPanel.getHeight() - 50,
                        Image.SCALE_SMOOTH
                );
                UGProfileImage.setIcon(new ImageIcon(scaled));
                UGProfileImage.setText("");

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

    private void UGSaveProfileImage(String tgno){

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

    private void viewNotice(String selected_notice_title){
        String view_notice_Query_details = "Select * from notice where noticeTitle = ?";
        try{
            prepStatement = conn.prepareStatement(view_notice_Query_details);
            prepStatement.setString(1,selected_notice_title);

            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                String notice_FilePath = resultSet.getString("noticeFilePath");

                System.out.println(notice_FilePath);

                File notice = new File(notice_FilePath);
                input = new Scanner(notice);

                StringBuilder noticeContent = new StringBuilder();

                while (input.hasNextLine()){
                    noticeContent.append(input.nextLine()).append("\n");
                }
                noticeDisplayArea.setText(noticeContent.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
