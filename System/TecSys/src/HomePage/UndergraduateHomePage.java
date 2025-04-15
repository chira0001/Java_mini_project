package HomePage;
import DBCONNECTION.DBCONNECTION;
import Login.Login;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Scanner;

import static java.util.Arrays.sort;

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
    private JComboBox SemesterNoDropDown;
    private JComboBox LevelNoDropDown;
    private JTable UGGradeTable;
    private JComboBox UGLevelNoforMarksDropDown;
    private JComboBox UGSemesterNoforMarksDropDown;
    private JLabel lblCGPA;
    private JLabel lblSGPA;
    private JLabel UGClass;
    private JButton viewMedicalsButton;
    private JTable AttendanceTable;
    private JComboBox AttendanceLevelNo;
    private JComboBox AttendanceSemesterNo;
    private JComboBox AttendanceSubjectCode;
    private JComboBox AttendanceSubjectStatus;

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
    private Object[] GlobalVariables = new Object[4];

    DBCONNECTION _dbconn = new DBCONNECTION();
    Connection conn = _dbconn.Conn();
    private PreparedStatement prepStatement;

    private Scanner input;

    private int SemeterNumber;
    private int LevelNumber;

    private int Atten_Level_No_Global;
    private int Atten_Semester_No_Global;
    private String Atten_Course_Number;

    public UndergraduateHomePage(String userIdentity){

        GlobalVariables[0] = 1;
        GlobalVariables[1] = 1;
        GlobalVariables[2] = "";

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
//                System.out.println(notice);
                viewNotice(notice);
            }
        });

        TimeTableSetModelMethod();

        LevelNoDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeTableSetModelMethod();

                String level_no = (String) LevelNoDropDown.getSelectedItem();
                int LevelNo = Integer.parseInt(level_no);
                valuesForCourseTable(LevelNo,SemeterNumber);
                System.out.println(LevelNo);
                valuesForCourseTable(LevelNo,1);
            }
        });

        SemesterNoDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeTableSetModelMethod();

                String semester_no = (String) SemesterNoDropDown.getSelectedItem();
                int SemesterNo = Integer.parseInt(semester_no);
                valuesForCourseTable(LevelNumber,SemesterNo);
                valuesForCourseTable(2,SemesterNo);
                System.out.println(SemesterNo);
            }
        });

        MarksTableSetModelMethod();

        UGLevelNoforMarksDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeTableSetModelMethod();

                String level_no = (String) LevelNoDropDown.getSelectedItem();
                int LevelNo = Integer.parseInt(level_no);
                valuesForMarksTable(LevelNo,SemeterNumber);
                valuesForMarksTable(LevelNo,1);
            }
        });
        UGSemesterNoforMarksDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeTableSetModelMethod();

                String semester_no = (String) SemesterNoDropDown.getSelectedItem();
                int SemesterNo = Integer.parseInt(semester_no);
                valuesForMarksTable(LevelNumber,SemesterNo);
                valuesForMarksTable(2,SemesterNo);
            }
        });

        AttendanceTableSetMethod();

        viewMedicalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(UGHomeCard,cardNames[4]);
                attendanceButton.setEnabled(true);
                medicalButton.setEnabled(false);
                CardTittleLabel.setText(cardTitles[4]);
            }
        });
        AttendanceLevelNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AttendanceTableSetMethod();

                String level_no = (String) AttendanceLevelNo.getSelectedItem();
                int LevelNo = Integer.parseInt(level_no);
                valuesforAttendanceTable(LevelNo,SemeterNumber,userIdentity);

                valuesforAttendanceTable(LevelNo,1,userIdentity);
                LoadAttendanceCourseNumber(userIdentity, LevelNo,1);

                GlobalVariables[0] = LevelNo;

                int semester_no_Global = (Integer) GlobalVariables[1];
                String course_id_Global = (String) GlobalVariables[2];
                String course_status_Global = (String) GlobalVariables[3];

                LoadAttendanceTable(userIdentity,LevelNo,semester_no_Global,course_id_Global,course_status_Global);
            }
        });
        AttendanceSemesterNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AttendanceTableSetMethod();

                String semester_no = (String) AttendanceSemesterNo.getSelectedItem();
                int SemesterNo = Integer.parseInt(semester_no);

                valuesforAttendanceTable(LevelNumber,SemesterNo,userIdentity);
                valuesforAttendanceTable(2,SemesterNo,userIdentity);

                LoadAttendanceCourseNumber(userIdentity, 2, SemesterNo);

                GlobalVariables[1] = SemesterNo;

                int level_no_Global = (Integer) GlobalVariables[0];
                String course_id_Global = (String) GlobalVariables[2];
                String course_status_Global = (String) GlobalVariables[3];

                LoadAttendanceTable(userIdentity,level_no_Global,SemesterNo,course_id_Global,course_status_Global);
            }
        });
        AttendanceSubjectCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AttendanceTableSetMethod();

                int level_no_Global = (Integer) GlobalVariables[0];
                int semester_no_Global = (Integer) GlobalVariables[1];
                String course_status_Global = (String) GlobalVariables[3];

                String Atten_Subject_code = (String) AttendanceSubjectCode.getSelectedItem();
                LoadAttendanceCourseStatus(userIdentity,level_no_Global,semester_no_Global,Atten_Subject_code);

                GlobalVariables[2] = Atten_Subject_code;
                String course_id_Global = (String) GlobalVariables[2];

                LoadAttendanceTable(userIdentity,level_no_Global,semester_no_Global,course_id_Global,course_status_Global);
            }
        });
        AttendanceSubjectStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AttendanceTableSetMethod();

                String Atten_Subject_Status = (String) AttendanceSubjectStatus.getSelectedItem();

                GlobalVariables[3] = Atten_Subject_Status;

                int level_no_Global = (Integer) GlobalVariables[0];
                int semester_no_Global = (Integer) GlobalVariables[1];
                String course_id_Global = (String) GlobalVariables[2];

                LoadAttendanceTable(userIdentity,level_no_Global,semester_no_Global,course_id_Global,Atten_Subject_Status);
            }
        });
    }

    private void LoadAttendanceTable(String tgno, int level_no, int semester_no, String course_id, String course_status) {
        try{
            AttendanceTableSetMethod();

            DefaultTableModel tblmodel = (DefaultTableModel) AttendanceTable.getModel();
            String LoadAttendanceQuery = "select attendance.week_no,courses.course_id,courses.course_name,atten_status,attendance.med_id,medical.med_reason from attendance join courses on attendance.course_id = courses.course_id left join medical on attendance.med_id = medical.medical_no where attendance.tgno = ? and level_no = ? and semester_no = ? and attendance.course_id = ? and attendance.course_status = ?";

            prepStatement = conn.prepareStatement(LoadAttendanceQuery);
            prepStatement.setString(1,tgno);
            prepStatement.setInt(2,level_no);
            prepStatement.setInt(3,semester_no);
            prepStatement.setString(4,course_id);
            prepStatement.setString(5,course_status);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                String table_week_no = resultSet.getString("week_no");
                String table_course_id = resultSet.getString("course_id");
                String table_course_name = resultSet.getString("course_name");
                String table_atten_status = resultSet.getString("atten_status");
                String table_med_id = resultSet.getString("med_id");
                String med_reason = resultSet.getString("med_reason");

                if(med_reason == "NULL"){
                    med_reason = "";
                }

                Object[] attendanceTableData = new Object[6];

                attendanceTableData[0] = table_week_no;
                attendanceTableData[1] = table_course_id;
                attendanceTableData[2] = table_course_name;
                attendanceTableData[3] = table_atten_status;
                attendanceTableData[4] = table_med_id;
                attendanceTableData[5] = med_reason;

                tblmodel.addRow(attendanceTableData);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void LoadAttendanceCourseStatus(String tgno, int level_no, int semester_no, String course_id){

        String Atten_Course_Status;

        try{
            String Atten_Course_StatusLoadQuery = "select distinct attendance.course_status from attendance join courses where attendance.course_id = courses.course_id and tgno = ? and level_no = ? and semester_no = ? and courses.course_id = ?";

            prepStatement = conn.prepareStatement(Atten_Course_StatusLoadQuery);
            prepStatement.setString(1,tgno);
            prepStatement.setInt(2,level_no);
            prepStatement.setInt(3,semester_no);
            prepStatement.setString(4,course_id);

            ResultSet result = prepStatement.executeQuery();

            AttendanceSubjectStatus.removeAllItems();
            AttendanceSubjectStatus.addItem("");

            while(result.next()){
                Atten_Course_Status = result.getString("course_status");
                AttendanceSubjectStatus.addItem(Atten_Course_Status);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void LoadAttendanceCourseNumber(String tgno, int level_no, int semester_no){

        String Atten_Course_Code;

        try{
            String noticeLoadQuery = "select distinct courses.course_id from attendance join courses where attendance.course_id = courses.course_id and tgno = ? and level_no = ? and semester_no = ?";

            prepStatement = conn.prepareStatement(noticeLoadQuery);
            prepStatement.setString(1,tgno);
            prepStatement.setInt(2,level_no);
            prepStatement.setInt(3,semester_no);

            ResultSet result = prepStatement.executeQuery();

            AttendanceSubjectCode.removeAllItems();
            AttendanceSubjectCode.addItem("");

            while(result.next()){
                Atten_Course_Code = result.getString("course_id");
                AttendanceSubjectCode.addItem(Atten_Course_Code);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void MarksTableSetModelMethod(){
        UGGradeTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Course Code","Course Module","CA Marks","Eligibility for final exam","Final Marks","Grade"}
        ));
    }

    private void AttendanceTableSetMethod(){
        AttendanceTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Week No","Course No","Course Name","Attendance Status","Medical No","Medical Reason"}
        ));
    }

    private void TimeTableSetModelMethod(){
        tableTimeTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Day", "Course Code", "Course Module","Time"}
        ));

        TableColumnModel timeTableColumns = tableTimeTable.getColumnModel();
        timeTableColumns.getColumn(2).setMinWidth(100);
        timeTableColumns.getColumn(3).setMinWidth(20);

        DefaultTableCellRenderer timeTableCells = new DefaultTableCellRenderer();
        timeTableCells.setHorizontalAlignment(JLabel.CENTER);

        timeTableColumns.getColumn(0).setCellRenderer(timeTableCells);
        timeTableColumns.getColumn(1).setCellRenderer(timeTableCells);
        timeTableColumns.getColumn(3).setCellRenderer(timeTableCells);
    }

    private void valuesForMarksTable(int level_no, int semester_no){

    }
    private void calculateGrade(){
        int quiz1 = 75;
        int quiz2 = 65;
        int quiz3 = 55;
        int assessment = 78;
        int mid_term = 70;
        int finalTheory = 80;
        int finalPractical = 85;

    }

    private void valuesForCourseTable(int level_no, int semester_no){
        String TimeTableValues = "select time_table_id, Courses.course_id, module_day, course_name, time from timeTable join Courses where timeTable.course_id = Courses.course_id and level_no = ? and semester_no = ? ORDER BY CASE module_day WHEN 'Monday' THEN 1 WHEN 'Tuesday' THEN 2 WHEN 'Wednesday' THEN 3 WHEN 'Thursday' THEN 4 WHEN 'Friday' THEN 5 WHEN 'Saturday' THEN 6 WHEN 'Sunday' THEN 7 END";
        DefaultTableModel tblmodel = (DefaultTableModel) tableTimeTable.getModel();
        try{
            prepStatement = conn.prepareStatement(TimeTableValues);
            prepStatement.setInt(1,level_no);
            prepStatement.setInt(2,semester_no);
            ResultSet result = prepStatement.executeQuery();
            while (result.next()){
                String courseID = result.getString("course_id");
                String moduleDay = result.getString("module_day");
                String courseName = result.getString("course_name");
                String courseTime = result.getString("time");

                Object[] timeTableData = new Object[4];

                timeTableData[0] = moduleDay;
                timeTableData[1] = courseID;
                timeTableData[2] = courseName;
                timeTableData[3] = courseTime;

                tblmodel.addRow(timeTableData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void valuesforAttendanceTable(int level_no, int semester_no, String tgno){
//        String AttendanceTableValues = "select courses.course_id,course_name,course_status,week_no,atten_status,med_id from attendance join courses where attendance.course_id = courses.course_id and tgno = 'tg1234' and level_no = 2 and semester_no = 1";
        String AttendanceTableValues = "select courses.course_id,course_name,course_status,week_no,atten_status,med_id from attendance join courses where attendance.course_id = courses.course_id and tgno = ? and level_no = ? and semester_no = ?";
        DefaultTableModel tblmodel = (DefaultTableModel) AttendanceTable.getModel();
        try{
            prepStatement = conn.prepareStatement(AttendanceTableValues);
            prepStatement.setString(1,tgno);
            prepStatement.setInt(2,level_no);
            prepStatement.setInt(3,semester_no);
            ResultSet result = prepStatement.executeQuery();
            while (result.next()){
                String courseID = result.getString("course_id");
                String courseName = result.getString("course_name");
                String course_status = result.getString("course_status");
                String week_no = result.getString("week_no");
                String atten_status = result.getString("atten_status");
                String med_id = result.getString("med_id");

//                Object[] timeTableData = new Object[4];
                Object[] AttendanceTableData = new Object[6];

                AttendanceTableData[0] = week_no;
                AttendanceTableData[1] = courseID;
                AttendanceTableData[2] = courseName;
                AttendanceTableData[3] = course_status;
                AttendanceTableData[4] = atten_status;
                AttendanceTableData[5] = med_id;

                tblmodel.addRow(AttendanceTableData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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

    public void calculateGrade(String tgno){
        int quiz_percentage,assessment_percentage,mid_term_percentage,final_theory_percentage,final_practical_percentage,
                quiz1,quiz2,quiz3,quiz4,assessment1,assessment2,mid_term,finalTheory,finalPractical,ca_perc_max,final_mark_perc_max;

        double quizSum, quizPercentage, assessmentPercentage, midPercentage, FinalTheoryPercentage,FinalPracticalPercentage, ca_mark_perc, final_mark_perc;

        String c_id,c_grade = "";

        try{
            String selectMarkPercentage = "select tgno,courses.course_id,quiz_one,quiz_second,quiz_third,quiz_fourth,assessment_one,assessment_second,mid_term,final_theory,final_practical,quizzes_perc,assessment_perc,mid_term_perc,final_theory_perc,final_practical_perc from marks join courses where marks.course_id = courses.course_id and tgno = '" + tgno + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectMarkPercentage);

            while (resultSet.next()){
                c_id = resultSet.getString("course_id");

                quiz1 = resultSet.getInt("quiz_one");
                quiz2 = resultSet.getInt("quiz_second");
                quiz3 = resultSet.getInt("quiz_third");
                quiz4 = resultSet.getInt("quiz_fourth");
                assessment1 = resultSet.getInt("assessment_one");
                assessment2 = resultSet.getInt("assessment_second");
                mid_term = resultSet.getInt("mid_term");
                finalTheory = resultSet.getInt("final_theory");
                finalPractical = resultSet.getInt("final_practical");

                quiz_percentage = resultSet.getInt("quizzes_perc");
                assessment_percentage = resultSet.getInt("assessment_perc");
                mid_term_percentage = resultSet.getInt("mid_term_perc");
                final_theory_percentage = resultSet.getInt("final_theory_perc");
                final_practical_percentage = resultSet.getInt("final_practical_perc");

                ca_perc_max = ((quiz_percentage + assessment_percentage + mid_term_percentage) * 50) / 100;
                final_mark_perc_max = final_theory_percentage + final_practical_percentage;

                int[] quizzes = {quiz1,quiz2,quiz3,quiz4};
                sort(quizzes);
                int arrLen = quizzes.length;

                if(quizzes[0] == 0){
                    quizPercentage = ((quizzes[arrLen - 1] * (quiz_percentage/2.0)) / 100) + ((quizzes[arrLen - 2] * (10/2.0)) / 100);
                }else {
                    quizPercentage = ((quizzes[arrLen - 1] * (quiz_percentage/3.0)) / 100) + ((quizzes[arrLen - 2] * (10/3.0)) / 100) + ((quizzes[arrLen - 3] * (10/3.0)) / 100);
                }

                if(assessment1 == 0 || assessment2 == 0){
                    assessmentPercentage = (((assessment1 + assessment2) * assessment_percentage) / 100.0);
                }else{
                    assessmentPercentage = (((assessment1 + assessment2) * (assessment_percentage / 2.0) ) / 100.0);
                }

                midPercentage = ((mid_term * mid_term_percentage) / 100.0);

                FinalTheoryPercentage = ((finalTheory * final_theory_percentage) / 100.0);
                FinalPracticalPercentage = ((finalPractical * final_practical_percentage) / 100.0);

                ca_mark_perc = quizPercentage + assessmentPercentage + midPercentage;
                final_mark_perc = FinalTheoryPercentage + FinalPracticalPercentage;

                if (ca_mark_perc > ca_perc_max){
                    System.out.println("Eligible");
                    if((ca_mark_perc + final_mark_perc) >= 90){
                        c_grade = "A+";
                    }else if((ca_mark_perc + final_mark_perc) >= 85){
                        c_grade = "A";
                    }else if((ca_mark_perc + final_mark_perc) >= 80){
                        c_grade = "A-";
                    }else if((ca_mark_perc + final_mark_perc) >= 75){
                        c_grade = "B+";
                    }else if((ca_mark_perc + final_mark_perc) >= 70){
                        c_grade = "B";
                    }else if((ca_mark_perc + final_mark_perc) >= 65){
                        c_grade = "B-";
                    }else if((ca_mark_perc + final_mark_perc) >= 60){
                        c_grade = "C+";
                    }else if((ca_mark_perc + final_mark_perc) >= 40){
                        c_grade = "C";
                    }else if((ca_mark_perc + final_mark_perc) >= 35){
                        c_grade = "C-";
                    }else if((ca_mark_perc + final_mark_perc) >= 30){
                        c_grade = "D+";
                    }else if((ca_mark_perc + final_mark_perc) >= 25){
                        c_grade = "D";
                    }else if((ca_mark_perc + final_mark_perc) >= 20){
                        c_grade = "E";
                    }else if((ca_mark_perc + final_mark_perc) >= 0){
                        c_grade = "F";
                    }
                }else{
                    System.out.println("Not Eligible");
                }
                System.out.println("Grade " + c_grade);
                System.out.println("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
