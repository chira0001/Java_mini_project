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
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField7;
    private JTextField textField9;
    private JTextField textField8;
    private JPanel TOProfImgPanel;
    private JLabel TOProfileImage;
    private JButton uploadImageButton;
    private JButton cancelButton;
    private JButton updateButton;
    private JPanel ProfileButton;
    private JPanel TechnicalOfficerHomePage;
    private JPanel HomePageUserProfile;
    private JLabel HomePageUserProfileLable;
    private JPanel attendenceButton;
    private JPanel technicalofficerTimeTable;
    private JTable tableTimeTable;
    private JComboBox SemesterNoDropDown;
    private JComboBox LevelNoDropDown;
    private JComboBox noticeTitleDropDown;
    private JTextArea noticeDisplayArea;
    private JComboBox AttendanceSemesterNo;
    private JComboBox AttendanceSubjectCode;
    private JComboBox AttendanceSubjectStatus;
    private JButton viewMedicalsButton;
    private JLabel AttendancePercWithoutMed;
    private JLabel AttendancePercWithMed;
    private JComboBox AttendanceSubjectStatusPerc;
    private JTable AttendanceTable;
    private JComboBox AttendenceLevelNo;
    private JButton attendenceUpdateBtn;
    private JComboBox SemesterNoforMedical;
    private JComboBox LevelNoforMedical;
    private JTable TOMedicalTable;
    private JPanel Notice;
    private JPanel toupdatemedical;
    private JButton ToupdateMedicalBtn;
    private JButton medicalupdatecancel;
    private JTextField textFieldTG;
    private JTextField textFieldreason;
    private JTextField textDEP;
    private JTextField textFieldweek;
    private JButton ToaddmedBtn;
    private JTextField textFieldCid;
    private JTextField AttenUpdateTgNo;
    private JTextField AttenUpdateCid;
    private JTextField AttenUpdateWno;
    private JTextField AttenUpdateCstatus;
    private JTextField AttenUpdateAttSt;
    private JButton addButton;


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
    private String[] cardTitles = {"Welcome..!", "Attendance Details", "View Technical Officer Time Table","Medical Information", "Notices","Settings Configuration"};

    private Object[] filePathValues = new Object[4];
    private Object[] GlobalVariables = new Object[4];

    DBCONNECTION _dbconn = new DBCONNECTION();
    Connection conn = _dbconn.Conn();
    private PreparedStatement prepStatement;

    private Scanner input;

    private int SemeterNumber;
    private int LevelNumber;


    public TechnicalOfficerHomePage(String userIdentity) {

        GlobalVariables[0] = 1;
        GlobalVariables[1] = 1;
        GlobalVariables[2] = "";

        dbConnection(userIdentity);
        LoadNotices();

        setContentPane(TechnicalOfficerHomePage);
        setTitle("Technical Officer User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1150, 570);
        setLocationRelativeTo(this);
        setVisible(true);

        cardLayout = (CardLayout) (TOHomeCard.getLayout());

        profileButton.setEnabled(false);
        CardTittleLabel.setText(cardTitles[0]);
        loadTOProfImage(userIdentity);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardCommand = e.getActionCommand();
                changeBtnState(cardCommand, userIdentity);
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

                cardLayout.show(TOHomeCard, cardNames[0]);
                btnFieldNames[0].setEnabled(false);
                btnFieldNames[noOfButtons - 1].setEnabled(true);
                loadTOProfImage(userIdentity);
                dbConnection(userIdentity);
            }
        });
        viewMedicalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(TOHomeCard, cardNames[3]);
                attendanceButton.setEnabled(true);
                medicalButton.setEnabled(false);
                CardTittleLabel.setText(cardTitles[3]);
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

        TimeTableSetModelMethod();

        LevelNoDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeTableSetModelMethod();

                String levelSelected = (String) LevelNoDropDown.getSelectedItem();
                if (levelSelected == null || levelSelected.isEmpty()) {
                    levelSelected = "0";
                }
                int level_no = Integer.parseInt(levelSelected);

                String semesterSelected = (String) SemesterNoDropDown.getSelectedItem();
                if (semesterSelected == null || semesterSelected.isEmpty()) {
                    semesterSelected = "0";
                }
                int semester_no = Integer.parseInt(semesterSelected);

                valuesForTimeTable(level_no, semester_no);
            }
        });


        SemesterNoDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeTableSetModelMethod();

                String semester_no = (String) SemesterNoDropDown.getSelectedItem();
                assert semester_no != null;
                if(semester_no.isEmpty()){
                    semester_no = "0";
                }
                int SemesterNo = Integer.parseInt(semester_no);

                String level_no = (String) LevelNoDropDown.getSelectedItem();
                assert level_no != null;
                if(level_no.isEmpty()){
                    level_no = "0";
                }
                int LevelNo = Integer.parseInt(level_no);

                valuesForTimeTable(LevelNo,SemesterNo);
            }
        });

        AttendanceTableSetMethod();

        AttendenceLevelNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AttendanceTableSetMethod();
                LoadAttendanceCourseNumber();
            }
        });

        AttendanceSemesterNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AttendanceTableSetMethod();
                LoadAttendanceCourseNumber();

            }
        });
        AttendanceSubjectCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LoadAttendanceCourseStatus();
            }
        });

        AttendanceSubjectStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AttendanceTableSetMethod();
                valuesforAttendanceTable(userIdentity);

            }
        });

        AttendanceSubjectStatusPerc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Course_Status_for_Perc = "Theory";
                Course_Status_for_Perc = (String) AttendanceSubjectStatusPerc.getSelectedItem();

                String course_id_Global = (String) GlobalVariables[2];

                LoadAttendancePercentages(Course_Status_for_Perc,userIdentity,course_id_Global);
            }
        });

        //Technical officer can update attendance
        attendenceUpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TOUpdateAttendance();
                valuesforAttendanceTable(tono);

            }
        });

        LevelNoforMedical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedicalTableSetModelMethod();

                String SemesterforMedicalStr = (String) SemesterNoforMedical.getSelectedItem();
                assert SemesterforMedicalStr != null;
                if(SemesterforMedicalStr.isEmpty()){
                    SemesterforMedicalStr = "0";
                }
                int SemesterforMedicalInt = Integer.parseInt(SemesterforMedicalStr);


                String LevelforMedicalStr = (String) LevelNoforMedical.getSelectedItem();
                assert LevelforMedicalStr != null;
                if(LevelforMedicalStr.isEmpty()){
                    LevelforMedicalStr = "0";
                }
                int LevelforMedicalInt = Integer.parseInt(LevelforMedicalStr);

                LoadMedicalTable(userIdentity,LevelforMedicalInt,SemesterforMedicalInt);
            }
        });

        SemesterNoforMedical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedicalTableSetModelMethod();

                String LevelforMedicalStr = (String) LevelNoforMedical.getSelectedItem();
                assert LevelforMedicalStr != null;
                if(LevelforMedicalStr.isEmpty()){
                    LevelforMedicalStr = "0";
                }
                int LevelforMedicalInt = Integer.parseInt(LevelforMedicalStr);

                String SemesterforMedicalStr = (String) SemesterNoforMedical.getSelectedItem();
                assert SemesterforMedicalStr != null;
                if(SemesterforMedicalStr.isEmpty()){
                    SemesterforMedicalStr = "0";
                }
                int SemesterforMedicalInt = Integer.parseInt(SemesterforMedicalStr);

                LoadMedicalTable(userIdentity,LevelforMedicalInt,SemesterforMedicalInt);
            }
        });
        medicalupdatecancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int noOfButtons = cardButtons.length;

                cardLayout.show(TOHomeCard, cardNames[0]);
                btnFieldNames[0].setEnabled(false);
                btnFieldNames[noOfButtons - 1].setEnabled(true);
                loadTOProfImage(userIdentity);
                dbConnection(userIdentity);
            }
        });

        //Technical officer can update medical
        ToupdateMedicalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ToUpdatemedical();
            }
        });

        //To add medical
        ToaddmedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Toaddmedical(userIdentity);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TOAddAttendance();
            }
        });
    }

    private void TOAddAttendance() {
        try {
            String AtUpdateTG = AttenUpdateTgNo.getText();
            String AtUpdateCid = AttenUpdateCid.getText();
            String AtUpdateweekno = AttenUpdateWno.getText();
            String AtUpdateCstatus = AttenUpdateCstatus.getText();
            String AtUpdateAttstatus = AttenUpdateAttSt.getText();

            String TOAddattendanceQuery = "INSERT INTO attendance(course_status,atten_status) VALUES ( " +
                    "course_status = '" + AtUpdateCstatus + "', " +
                    "atten_status = '" + AtUpdateAttstatus + "' " +
                    "WHERE tgno = '" + AtUpdateTG + "' AND " +
                    "course_id = '" + AtUpdateCid + "' AND " +
                    "week_no = " + AtUpdateweekno + ");";

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest", "root", "1234");
            PreparedStatement preparedStatement = connection.prepareStatement(TOAddattendanceQuery);

            preparedStatement.setString(1, AtUpdateTG);
            preparedStatement.setString(2, AtUpdateCid);
            preparedStatement.setInt(3, Integer.parseInt(AtUpdateweekno));
            preparedStatement.setString(4, AtUpdateCstatus);
            preparedStatement.setString(5, AtUpdateAttstatus);

            int resultSet = preparedStatement.executeUpdate();

            if (resultSet > 0) {
                JOptionPane.showMessageDialog(null, "Attendance record added successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Error in adding Attendance record");
            }

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    private void TOUpdateAttendance() {
        try{
            String AtUpdateTG = AttenUpdateTgNo.getText();
            String AtUpdateCid = AttenUpdateCid.getText();
            String AtUpdateweekno = AttenUpdateWno.getText();
            String AtUpdateCstatus = AttenUpdateCstatus.getText();
            String AtUpdateAttstatus = AttenUpdateAttSt.getText();

            String extension = (String) filePathValues[3];

            String TOupdateattendanceQuery = "UPDATE attendance SET " +
                    "course_status = '" + AtUpdateCstatus + "', " +
                    "atten_status = '" + AtUpdateAttstatus + "' " +
                    "WHERE tgno = '" + AtUpdateTG + "' AND " +
                    "course_id = '" + AtUpdateCid + "' AND " +
                    "week_no = " + AtUpdateweekno;

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest", "root", "1234");
            Statement statement = connection.createStatement();
            int resultSet = statement.executeUpdate(TOupdateattendanceQuery);

            if (resultSet > 0) {
                //  TOsaveupdatedmedical();
                JOptionPane.showMessageDialog(null, "Attendance record updated successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Error in updating Attendance record");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void Toaddmedical(String useridentity) {
        try {
            String Medical_Tgnumber = textFieldTG.getText();
            String Medical_Cid = textFieldCid.getText();
            Integer Medical_week = Integer.valueOf(textFieldweek.getText());
            String Medical_reason = textFieldreason.getText();

            String extension = (String) filePathValues[3];

            String TOaddMedicalQuery = "INSERT INTO medical (tono, tgno, course_id, week_no, med_reason) VALUES ("
                    + "'" + useridentity + "', "
                    + "'" + Medical_Tgnumber + "', "
                    + "'" + Medical_Cid + "', "
                    + Medical_week + ", "
                    + "'" + Medical_reason + "')";



            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest", "root", "1234");
            Statement statement = connection.createStatement();
            int resultSet = statement.executeUpdate(TOaddMedicalQuery);

            if (resultSet > 0) {
                //  TOsaveupdatedmedical();
                JOptionPane.showMessageDialog(null, "Medical record add successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Error in adding medical record");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            ex.getMessage();
        }
    }

    private void ToUpdatemedical() {
        try {
            String Medical_Tgnumber = textFieldTG.getText();
            String Medical_Cid = textFieldCid.getText();
            Integer Medical_week = Integer.valueOf(textFieldweek.getText());
            String Medical_reason = textFieldreason.getText();

            String extension = (String) filePathValues[3];

            String TOupdateMedicalQuery = "UPDATE medical SET "
                    + "tgno = '" + Medical_Tgnumber + "', "
                    + "course_id = '" + Medical_Cid + "', "
                    + "week_no = " + Medical_week + ", "
                    + "med_reason = '" + Medical_reason + "' "
                    + "WHERE tgno = '" + Medical_Tgnumber + "'";


            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest", "root", "1234");
            Statement statement = connection.createStatement();
            int resultSet = statement.executeUpdate(TOupdateMedicalQuery);

            if (resultSet > 0) {
              //  TOsaveupdatedmedical();
                JOptionPane.showMessageDialog(null, "Medical record updated successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Error in updating medical record");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    //Notices
private void LoadNotices() {
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

    private void viewNotice(String selected_notice_title) {
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

    //Medical
    private void MedicalTableSetModelMethod(){
        TOMedicalTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Medical No","Course code","Course Name","Course Status","Week No","Medical Reason"}
        ));
    }

    private void LoadMedicalTable(String tono, int level_no, int semester_no){
        System.out.println("Lev " + level_no + " Sem - " +semester_no);

        DefaultTableModel tblmodel = (DefaultTableModel) TOMedicalTable.getModel();
//        MedicalTableSetModelMethod();

        try{
            String medLoadQuery = "select medical_no,courses.course_id,courses.course_name,course_status,medical.week_no,med_reason from attendance join medical on attendance.med_id = medical.medical_no join courses on attendance.course_id = courses.course_id where medical.tgno = ? and level_no = ? and semester_no = ?";
            prepStatement = conn.prepareStatement(medLoadQuery);
            prepStatement.setString(1,tono);
            prepStatement.setString(2, String.valueOf(level_no));
            prepStatement.setString(3, String.valueOf(semester_no));

            ResultSet resultSet = prepStatement.executeQuery();
            while(resultSet.next()){
                String med_no = resultSet.getString("medical_no");
                String course_id = resultSet.getString("course_id");
                String course_name = resultSet.getString("course_name");
                String course_status = resultSet.getString("course_status");
                String week_no = resultSet.getString("week_no");
                String med_reason = resultSet.getString("med_reason");

                Object[] med_det = new Object[6];

                med_det[0] = med_no;
                med_det[1] = course_id;
                med_det[2] = course_name;
                med_det[3] = course_status;
                med_det[4] = week_no;
                med_det[5] = med_reason;

                tblmodel.addRow(med_det);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //Timetable
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


    private void valuesForTimeTable(int level_no, int semester_no) {


        System.out.println("Level " + level_no + " Semester " + semester_no);

        String TimeTableValues = "select time_table_id, Courses.course_id, module_day, course_name, time from timeTable join Courses where timeTable.course_id = Courses.course_id and level_no = ? and semester_no = ? ORDER BY CASE module_day WHEN 'Monday' THEN 1 WHEN 'Tuesday' THEN 2 WHEN 'Wednesday' THEN 3 WHEN 'Thursday' THEN 4 WHEN 'Friday' THEN 5 WHEN 'Saturday' THEN 6 WHEN 'Sunday' THEN 7 END";
        DefaultTableModel tblmodel = (DefaultTableModel) tableTimeTable.getModel();
        try{
            prepStatement = conn.prepareStatement(TimeTableValues);
            prepStatement.setInt(1, Integer.parseInt(String.valueOf(level_no)));
            prepStatement.setInt(2, Integer.parseInt(String.valueOf(semester_no)));
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
            System.out.println(e.getMessage());
        }
    }

    //Attendance
    private void AttendanceTableSetMethod() {
        AttendanceTable.setModel(new DefaultTableModel(
                null,
                new String[]{"TG No","Course ID","Course Name","Week No","Course Status","Attendance Status","Medical Reason"}
        ));
    }

    private void LoadAttendanceCourseNumber() {
        String levelNO = (String) AttendenceLevelNo.getSelectedItem();
        String semesterNO = (String) AttendanceSemesterNo.getSelectedItem();
        AttendanceSubjectCode.removeAllItems();
        try{
            String loadQuery = "Select course_id from courses where level_no = ? and semester_no = ?";

            prepStatement = conn.prepareStatement(loadQuery);
            prepStatement.setString(1, levelNO);
            prepStatement.setString(2, semesterNO);

            ResultSet result = prepStatement.executeQuery();
            while (result.next()){
                String courseID = result.getString("course_id");

                AttendanceSubjectCode.addItem(courseID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LoadAttendanceCourseStatus() {
        String course_ID = (String) AttendanceSubjectCode.getSelectedItem();
        try{
            String Atten_Course_StatusLoadQuery = "select status from courses where course_id = ?";

            prepStatement = conn.prepareStatement(Atten_Course_StatusLoadQuery);

            prepStatement.setString(1,course_ID);

            ResultSet result = prepStatement.executeQuery();

            while(result.next()){
                String Atten_Course_Status = result.getString("status");
                System.out.println(Atten_Course_Status);

                if(Atten_Course_Status.equals("theory/practical")){
                    AttendanceSubjectStatus.addItem("Theory");
                    AttendanceSubjectStatus.addItem("Practical");
                    AttendanceSubjectStatus.addItem("Theory/Practical");
                }else{
                    AttendanceSubjectStatus.addItem(Atten_Course_Status);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void LoadAttendancePercentages(String atten_status, String tgno,String course_id){
        try{
            String Atten_Perc_Query_without_Med = "", Atten_Perc_Query_with_Med = "";

            if(atten_status != null && !atten_status.isEmpty()){
                if(atten_status.equals("theory")||atten_status.equals("Theory")){
                    Atten_Perc_Query_without_Med = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('theory') AND atten_status IN ('present'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('theory')) * 100) AS percentage"; //4
                    Atten_Perc_Query_with_Med = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('theory') AND atten_status IN ('present','medical'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('theory')) * 100) AS percentage";//4
                }
                else if(atten_status.equals("practical")||atten_status.equals("Practical")) {
                    Atten_Perc_Query_without_Med = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical') AND atten_status IN ('present'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical')) * 100) AS percentage";//4
                    Atten_Perc_Query_with_Med = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical') AND atten_status IN ('present','medical'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical')) * 100) AS percentage";//4
                }
                else if(atten_status.equals("theory/practical")||atten_status.equals("Theory/Practical")){
                    Atten_Perc_Query_without_Med = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical', 'theory') AND atten_status IN ('present'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical', 'theory')) * 100) AS percentage";//4
                    Atten_Perc_Query_with_Med = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical', 'theory') AND atten_status IN ('present','medical'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical', 'theory')) * 100) AS percentage";//4
                }
            }

            if(!Atten_Perc_Query_without_Med.isEmpty()){
                prepStatement = conn.prepareStatement(Atten_Perc_Query_without_Med);
                prepStatement.setString(1,tgno);
                prepStatement.setString(2,course_id);
                prepStatement.setString(3,tgno);
                prepStatement.setString(4,course_id);

                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()){
                    String perc_without_med_Str = resultSet.getString("percentage");
                    if(perc_without_med_Str.isEmpty()){
                        AttendancePercWithoutMed.setText("-%");
                    }else{
                        float perc_without_med_Int = Float.parseFloat(perc_without_med_Str);
                        AttendancePercWithoutMed.setText(perc_without_med_Int + "%");
                    }
                }
            }

            if(!Atten_Perc_Query_with_Med.isEmpty()){
                prepStatement = conn.prepareStatement(Atten_Perc_Query_with_Med);
                prepStatement.setString(1,tgno);
                prepStatement.setString(2,course_id);
                prepStatement.setString(3,tgno);
                prepStatement.setString(4,course_id);

                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()){
                    String perc_with_med_Str = resultSet.getString("percentage");
                    if(perc_with_med_Str.isEmpty()){
                        AttendancePercWithMed.setText("-%");
                    }else{
                        float perc_with_med_Int = Float.parseFloat(perc_with_med_Str);
                        AttendancePercWithMed.setText(perc_with_med_Int + "%");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void valuesforAttendanceTable(String tono) {

        String AttendanceTableValues;
        DefaultTableModel tblmodel = (DefaultTableModel) AttendanceTable.getModel();

        String courseID = (String) AttendanceSubjectCode.getSelectedItem();
        String CourseStat = (String) AttendanceSubjectStatus.getSelectedItem();

        try{
            if(CourseStat.equals("theory/practical")){

                System.out.println("HI");
                AttendanceTableValues = "select attendance.tgno,courses.course_id,courses.course_name,attendance.week_no,course_status,atten_status,med_reason from attendance join courses on attendance.course_id = courses.course_id left join medical on attendance.med_id = medical.medical_no where attendance.tono = ? and attendance.course_id = ?";
                prepStatement = conn.prepareStatement(AttendanceTableValues);
                prepStatement.setString(1,tono);
                prepStatement.setString(2,courseID);

            }else{
                AttendanceTableValues = "select attendance.tgno,courses.course_id,courses.course_name,attendance.week_no,course_status,atten_status,med_reason from attendance join courses on attendance.course_id = courses.course_id left join medical on attendance.med_id = medical.medical_no where attendance.tono = ? and attendance.course_id = ? and course_status = ?";
                prepStatement = conn.prepareStatement(AttendanceTableValues);

                prepStatement.setString(1,tono);
                prepStatement.setString(2,courseID);
                prepStatement.setString(3,CourseStat);
            }

            ResultSet result = prepStatement.executeQuery();

            while (result.next()){
                String tg = result.getString("tgno");
                String c_id = result.getString("course_id");
                String c_Name = result.getString("course_name");
                String week_no = result.getString("week_no");
                String course_status = result.getString("course_status");
                String atten_status = result.getString("atten_status");
                String med_reason = result.getString("med_reason");

                Object[] AttendanceTableData = new Object[7];

                AttendanceTableData[0] = tg;
                AttendanceTableData[1] = c_id;
                AttendanceTableData[2] = c_Name;
                AttendanceTableData[3] = week_no;
                AttendanceTableData[4] = course_status;
                AttendanceTableData[5] = atten_status;
                AttendanceTableData[6] = med_reason;

                tblmodel.addRow(AttendanceTableData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //setting configuration
    private void TOUpdateCredentials(String tono) {
        try{
            String TOaddress = textField7.getText();
            String TOemail = textField8.getText();
            String TOphno = textField9.getText();
            String TOFname =textField2.getText();
            String TOLname =textField1.getText();

            String extension = (String) filePathValues[3];

            String TOProfileImagePath = "Resources/ProfileImages/" + tono + "." + extension;
            System.out.println(TOProfileImagePath);
            String TOCredentialupdateQuery;
            if (extension == null){
                TOCredentialupdateQuery = "UPDATE technical_officer SET "
                        + "toaddress = '" + TOaddress + "', "
                        + "TOemail = '" + TOemail + "', "
                        + "TOphno = '" + TOphno + "', "
                        + "TOFname = '" + TOFname + "', "
                        + "TOLname = '" + TOLname + "' "
                        + "WHERE tono = '" + tono + "'";
            }else {
                TOCredentialupdateQuery = "UPDATE technical_officer SET "
                        + "toaddress = '" + toAddress + "', "
                        + "toemail = '" + toEmail + "', "
                        + "tophno = '" + toPhno + "', "
                        + "toProfImg = '" + toProfImg + "', "
                        + "toFname = '" + toFname + "', "
                        + "toLname = '" + toLname + "' "
                        + "WHERE tono = '" + tono + "'";

            }

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest","root","1234");
            Statement statement = connection.createStatement();
            int resultSet = statement.executeUpdate(TOCredentialupdateQuery);

            if(resultSet > 0){
                   TOSaveProfileImage(tono);
                JOptionPane.showMessageDialog(null,"Credentials updated successfully");
            }else {
                JOptionPane.showMessageDialog(null,"Error in credential updation");
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
    }

    private void loadTOProfImage(String userIdentity) {
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

    private void TOUploadToPreviewProfileImage(String tono) {
        try {
            JFileChooser TOFileChooser = new JFileChooser();
            TOFileChooser.setDialogTitle("Select Profile Picture");
            TOFileChooser.setAcceptAllFileFilterUsed(false);
            TOFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg"));

            if (TOFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                ImageIcon icon = new ImageIcon(TOFileChooser.getSelectedFile().getPath());
                Image scaled = icon.getImage().getScaledInstance(
                        TOProfImgPanel.getWidth() - 50,
                        TOProfImgPanel.getHeight() - 50,
                        Image.SCALE_SMOOTH
                );
                TOProfileImage.setIcon(new ImageIcon(scaled));
                TOProfileImage.setText("");

                String filename = TOFileChooser.getSelectedFile().getAbsolutePath();

                String UGSaveImagePath = "Resources/ProfileImages/";
                File UGSaveImageDirectory = new File(UGSaveImagePath);
                if (!UGSaveImageDirectory.exists()) {
                    UGSaveImageDirectory.mkdirs();
                }

                File UGSourceFile = null;

                String extension = filename.substring(filename.lastIndexOf('.') + 1);
                UGSourceFile = new File(tono + "." + extension);

                File UGDestinationFile = new File(UGSaveImagePath + UGSourceFile);

                System.out.println(UGDestinationFile);

                Path fromFile = TOFileChooser.getSelectedFile().toPath();
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
    private void TOSaveProfileImage(String tono){

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

    private void changeBtnState(String btn, String tono) {
        int noOfButtons = cardButtons.length;
        for (int i = 0; i < noOfButtons; i++){
            if (cardButtons[i].equals(btn)){
                dbConnection(tono);
                cardLayout.show(TOHomeCard,cardNames[i]);
                CardTittleLabel.setText(cardTitles[i]);
                btnFieldNames[i].setEnabled(false);
            }else {
                btnFieldNames[i].setEnabled(true);
            }
        }
    }

    private void dbConnection(String tono) {
        try {
            String selectQuery = "select * from technical_officer where tono = ?";

            prepStatement = conn.prepareStatement(selectQuery);
            prepStatement.setString(1, tono);
            ResultSet DBresult = prepStatement.executeQuery();

            if (DBresult.next()) {
                tono = DBresult.getString("tono");
                toFname = DBresult.getString("tofname");
                toLname = DBresult.getString("tolname");
                toAddress = DBresult.getString("toaddress");
                toEmail = DBresult.getString("toemail");
                toPhno = DBresult.getString("tophno");
//                textDEP =DBresult.getString("todepartment");
                toProfImg = DBresult.getString("toProfImg");


                txtTGNO.setText(tono);
                txtFNAME.setText(toFname);
                txtLNAME.setText(toLname);
                txtADDRESS.setText(toAddress);
                txtEMAIL.setText(toEmail);
                txtPHNO.setText(toPhno);
                textDEP.setText(DBresult.getString("todepartment"));

                textField7.setText(toAddress);
                textField8.setText(toEmail);
                textField9.setText(toPhno);
                textField2.setText(toFname);
                textField1.setText(toLname);


                loadTOProfImage(tono);
            } else {
                JOptionPane.showMessageDialog(null, "Internal Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

