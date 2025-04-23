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
import java.sql.*;
import java.util.Scanner;

public class LecturerHomePage extends JFrame {
    private String cardCommand;

    private JPanel Lecturer;
    private JButton profileButton;
    private JButton settingsButton;
    private JButton marksButton;
    private JButton noticesButton;
    private JButton medicalButton;
    private JButton coursesButton;
    private JButton timeTableButton;
    private JButton attendanceButton;
    private JLabel CardTittleLabel;
    private JButton logoutButton;
    private JPanel LECHomeCard;
    private JPanel LECProfile;
    private JTextField txtLECNO;
    private JTextField txtPHNO;
    private JTextField txtEMAIL;
    private JTextField txtADDRESS;
    private JTextField txtLNAME;
    private JTextField txtFNAME;
    private JPanel LECAttendance;
    private JPanel LECTimeTable;
    private JPanel LECCourses;
    private JPanel LECMedicals;
    private JPanel LECNotices;
    private JPanel LECMarks;
    private JPanel LECSettings;
    private JTextField textField7;
    private JTextField textField9;
    private JTextField textField8;
    private JButton uploadImageButton;
    private JButton cancelButton;
    private JButton updateButton;
    private JTextField textField1;
    private JTextField textField2;
    private JList leccourselist;
    private JList list2;
    private JButton addFilesButton;
    private JButton renameFilesButton;
    private JButton removeFilesButton;
    private JComboBox noticeTitleDropDown;
    private JTextArea noticeDisplayArea;
    private JPanel LecturerHomePageProfile;
    private JLabel LecturerHomePageProfileLable;
    private JPanel LecProfileImagePanel;
    private JLabel LecProfileImage;
    private JComboBox comboBox1;
    private JComboBox comboBox2;

    private CardLayout cardLayout;

    private String Lecno;
    private String LecFname;
    private String LecLname;
    private String LecAddress;
    private String LecEmail;
    private String LecPhno;
    private String LecProfImg;

    private String[] cardButtons = {"Profile", "Attendance", "Time Table", "Courses", "Medical", "Notices", "Marks", "Settings"};
    private String[] cardNames = {"LECProfileCard", "LECAttendanceCard", "LECTimeTableCard", "LECCoursesCard", "LECMedicalsCard", "LECNoticesCard", "LECMarksCard", "LECSettingsCard"};
    JButton[] btnFieldNames = {profileButton,attendanceButton,timeTableButton,coursesButton,medicalButton,noticesButton,marksButton,settingsButton};
    private String[] cardTitles = {"Welcome..!", "Attendance Details", "Undergraduate Time Table","Your Courses","Medical Information", "Notices", "Marks","Settings Configuration"};;


    private Object[] filePathValues = new Object[4];

    DBCONNECTION dbconn = new DBCONNECTION();
    Connection conn = dbconn.Conn();
    private PreparedStatement prepStatement;

    private Scanner input;

    public LecturerHomePage(String userIdentity){
        dbConnection(userIdentity);
        LoadNotices();

        setContentPane(Lecturer);
        setTitle("Lecturer User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 570);
        setLocationRelativeTo(this);
        setVisible(true);

        cardLayout = (CardLayout)(LECHomeCard.getLayout());
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
        marksButton.addActionListener(listener);
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
                LECUpdateCredentials(userIdentity);
            }
        });
        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LECUploadToPreviewProfileImage(userIdentity);
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
    }

    public void changeBtnState(String btn, String lecno){

        int noOfButtons = cardButtons.length;
        for (int i = 0; i < noOfButtons; i++){
            if (cardButtons[i].equals(btn)){
                dbConnection(lecno);
                cardLayout.show(LECHomeCard,cardNames[i]);
                CardTittleLabel.setText(cardTitles[i]);
                btnFieldNames[i].setEnabled(false);
            }
            if (btn.equals("Courses")) {
                LECCourse(lecno);
            }
            else {
                btnFieldNames[i].setEnabled(true);
            }
        }
    }

    private void dbConnection(String lecno){
        try{
            String selectQuery = "select * from lecturer where lecno = '" + lecno + "'";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest","root","1234");
            Statement statement = connection.createStatement();
            ResultSet DBresult = statement.executeQuery(selectQuery);

            if(DBresult.next()){
                Lecno = DBresult.getString("lecno");
                LecFname = DBresult.getString("lecfname");
                LecLname = DBresult.getString("leclname");
                LecAddress = DBresult.getString("lecaddress");
                LecEmail = DBresult.getString("lecemail");
                LecPhno = DBresult.getString("lecphno");
                LecProfImg = DBresult.getString("lecProfImg");

                txtLECNO.setText(Lecno);
                txtFNAME.setText(LecFname);
                txtLNAME.setText(LecLname);
                txtADDRESS.setText(LecAddress);
                txtEMAIL.setText(LecEmail);
                txtPHNO.setText(LecPhno);

                textField1.setText(LecFname);
                textField2.setText(LecLname);
                textField7.setText(LecAddress);
                textField8.setText(LecEmail);
                textField9.setText(LecPhno);

                loadLECProfImage(lecno);

            }else{
                JOptionPane.showMessageDialog(null,"Internal Error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadLECProfImage(String lecno){
        try{
            String LECProfImageSearchQuery = "select * from lecturer where lecno = ?";

            prepStatement = conn.prepareStatement(LECProfImageSearchQuery);
            prepStatement.setString(1,lecno);
            ResultSet result = prepStatement.executeQuery();

            while (result.next()){
                Path LECSaveImagePath = Path.of(result.getString("lecProfImg"));
                ImageIcon icon = new ImageIcon(LECSaveImagePath.toString());
                Image scaled = icon.getImage().getScaledInstance(
                        LecturerHomePageProfile.getWidth() - 50,
                        LecturerHomePageProfile.getHeight() - 50,
                        Image.SCALE_SMOOTH
                );
                LecturerHomePageProfileLable.setIcon(new ImageIcon(scaled));
                LecturerHomePageProfileLable.setText("");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void LECUploadToPreviewProfileImage(String lecno) {
        try {
            JFileChooser LECFileChooser = new JFileChooser();
            LECFileChooser.setDialogTitle("Select Profile Picture");
            LECFileChooser.setAcceptAllFileFilterUsed(false);
            LECFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg"));

            if (LECFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                ImageIcon icon = new ImageIcon(LECFileChooser.getSelectedFile().getPath());
                Image scaled = icon.getImage().getScaledInstance(
                        LecProfileImagePanel.getWidth() - 50,
                        LecProfileImagePanel.getHeight() - 50,
                        Image.SCALE_SMOOTH
                );
                LecProfileImage.setIcon(new ImageIcon(scaled));
                LecProfileImage.setText("");

                String filename = LECFileChooser.getSelectedFile().getAbsolutePath();

                String LECSaveImagePath = "Resources/ProfileImages/";
                File LECSaveImageDirectory = new File(LECSaveImagePath);
                if (!LECSaveImageDirectory.exists()) {
                    LECSaveImageDirectory.mkdirs();
                }

                File LECSourceFile = null;

                String extension = filename.substring(filename.lastIndexOf('.') + 1);

                LECSourceFile = new File(lecno + "." + extension);

                File LECDestinationFile = new File(LECSaveImagePath + LECSourceFile);

                System.out.println(LECDestinationFile);

                Path fromFile = LECFileChooser.getSelectedFile().toPath();
                Path toFile = LECDestinationFile.toPath();

                filePathValues[0] = fromFile;
                filePathValues[1] = toFile;
                filePathValues[2] = LECDestinationFile;
                filePathValues[3] = extension;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void LECSaveProfileImage(String tgno){

        try{
            Path fromFile = (Path) filePathValues[0];
            Path toFile = (Path) filePathValues[1];
            File LECDestinationFile = (File) filePathValues[2];

            if (LECDestinationFile.exists()){
                LECDestinationFile.delete();
                Files.copy(fromFile,toFile);
            }else{
                Files.copy(fromFile,toFile);
            }

        }catch(Exception exc){

        }
    }

    private void LECUpdateCredentials(String lecno){
        try{
            String LecFname=textField1.getText();
            String LecLname=textField2.getText();
            String Lecaddress = textField7.getText();
            String Lecemail = textField8.getText();
            String Lecphno = textField9.getText();

            String extension = (String) filePathValues[3];

            String LECProfileImagePath = "Resources/ProfileImages/" + lecno + "." + extension;
            System.out.println(LECProfileImagePath);
            String LECCredentialupdateQuery;
            if(extension==null) {
                LECCredentialupdateQuery = "Update lecturer set lecfname = '" + LecFname + "',leclname = '" + LecLname + "',lecaddress = '" + Lecaddress + "', lecemail = '" + Lecemail + "',lecphno = '" + Lecphno + "' where lecno = '" + lecno + "'";
            }
            else{
                LECCredentialupdateQuery = "Update lecturer set lecfname = '" + LecFname + "',leclname = '" + LecLname + "',lecaddress = '" + Lecaddress + "', lecemail = '" + Lecemail + "',lecphno = '" + Lecphno + "',lecProfImg ='" + LECProfileImagePath + "' where lecno = '"+ lecno + "'";
            }

            Statement statement = conn.createStatement();
            int resultSet = statement.executeUpdate(LECCredentialupdateQuery);

            if(resultSet > 0){
                LECSaveProfileImage(lecno);
                JOptionPane.showMessageDialog(null,"Credentials updated successfully");
                loadLECProfImage(lecno);
            }else {
                JOptionPane.showMessageDialog(null,"Error in credential updation");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void LECCourse(String lecno){
        try {
            DefaultListModel<String> model = new DefaultListModel<>();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest","root","1234");
            Statement statement = connection.createStatement();
            String query = "SELECT c.course_name FROM courses c " +
                    "JOIN lecture_course lc ON c.course_id = lc.course_id " +
                    "WHERE lc.lecno = '" + lecno + "'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                model.addElement(courseName);
            }

            leccourselist.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
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

//    private void UGFillUpdateFields(String tgno){
//        String UpdateFillQuery = "select ";
//    }
}