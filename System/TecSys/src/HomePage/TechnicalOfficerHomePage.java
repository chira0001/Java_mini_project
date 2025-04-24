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
    private JTextField textField7;
    private JTextField textField9;
    private JTextField textField8;
    private JPanel TOProfImgPanel;
    private JLabel TOProfileImage;
    private JButton uploadImageButton;
    private JTextField textDEP;
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
    private JTable UGMedicalTable;
    private JPanel Notice;
    private JPanel toupdatemedical;
    private JButton ToupdateMedicalBtn;
    private JButton medicalupdatecancel;
    private JTextField TOmedTgnum;
    private JTextField TomedCid;
    private JTextField ToMedReason;


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

   // private int Atten_Level_No_Global;
  //  private int Atten_Semester_No_Global;
  //  private String Atten_Course_Number;

    public TechnicalOfficerHomePage(String userIdentity) {

        GlobalVariables[0] = 1;
        GlobalVariables[1] = 1;
        GlobalVariables[2] = "";

        dbConnection(userIdentity);
        LoadNotices();

        setContentPane(TechnicalOfficerHomePage);
        setTitle("Technical Officer User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 570);
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
                cardLayout.show(TOHomeCard, cardNames[4]);
                attendanceButton.setEnabled(true);
                medicalButton.setEnabled(false);
                CardTittleLabel.setText(cardTitles[4]);
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

                String level_no = (String) LevelNoDropDown.getSelectedItem();
                assert level_no != null;
                if (level_no.isEmpty()) {
                    level_no = "0";
                }
                int LevelNo = Integer.parseInt(level_no);

                String semester_no = (String) SemesterNoDropDown.getSelectedItem();
                assert semester_no != null;
                if (semester_no.isEmpty()) {
                    semester_no = "0";
                }
                int SemesterNo = Integer.parseInt(semester_no);

                valuesForTimeTable(LevelNo, SemesterNo);
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

                String level_no = (String) AttendenceLevelNo.getSelectedItem();
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

            }
        });
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
        UGMedicalTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Medical No","Course code","Course Name","Course Status","Week No","Medical Reason"}
        ));
    }

    private void LoadMedicalTable(String tono, int level_no, int semester_no){
        System.out.println("Lev " + level_no + " Sem - " +semester_no);

        DefaultTableModel tblmodel = (DefaultTableModel) UGMedicalTable.getModel();
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
    private void TimeTableSetModelMethod() {
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

    private void valuesForTimeTable(int levelNo, int semesterNo) {
        String semester_no = (String) SemesterNoDropDown.getSelectedItem();
        String level_no = (String) LevelNoDropDown.getSelectedItem();

        System.out.println("Level " + level_no + " Semester " + semester_no);

        String TimeTableValues = "select time_table_id, Courses.course_id, module_day, course_name, time from timeTable join Courses where timeTable.course_id = Courses.course_id and level_no = ? and semester_no = ? ORDER BY CASE module_day WHEN 'Monday' THEN 1 WHEN 'Tuesday' THEN 2 WHEN 'Wednesday' THEN 3 WHEN 'Thursday' THEN 4 WHEN 'Friday' THEN 5 WHEN 'Saturday' THEN 6 WHEN 'Sunday' THEN 7 END";
        DefaultTableModel tblmodel = (DefaultTableModel) tableTimeTable.getModel();
        try{
            prepStatement = conn.prepareStatement(TimeTableValues);
            prepStatement.setInt(1, Integer.parseInt(level_no));
            prepStatement.setInt(2, Integer.parseInt(semester_no));
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

    //Attendance
    private void AttendanceTableSetMethod() {
        AttendanceTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Week No","Course No","Course Name","Attendance Status","Medical No","Medical Reason"}
        ));
    }


    private void LoadAttendanceTable(String userIdentity, int levelNo, int semesterNoGlobal, String courseIdGlobal, String courseStatusGlobal) {
        try{
            String semester_no = (String) SemesterNoDropDown.getSelectedItem();
            String level_no = (String) LevelNoDropDown.getSelectedItem();

            AttendanceTableSetMethod();

            DefaultTableModel tblmodel = (DefaultTableModel) AttendanceTable.getModel();
            String LoadAttendanceQuery = "select attendance.week_no,courses.course_id,courses.course_name,atten_status,attendance.med_id,medical.med_reason from attendance join courses on attendance.course_id = courses.course_id left join medical on attendance.med_id = medical.medical_no where attendance.tgno = ? and level_no = ? and semester_no = ? and attendance.course_id = ? and attendance.course_status = ?";

            prepStatement = conn.prepareStatement(LoadAttendanceQuery);
            prepStatement.setString(1,tono);
            prepStatement.setInt(2, Integer.parseInt(level_no));
            prepStatement.setInt(3, Integer.parseInt(semester_no));
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

    private void LoadAttendanceCourseNumber(String tono, int level_no, int semester_no) {

        String Atten_Course_Code;

        try{
            String noticeLoadQuery = "select distinct courses.course_id from attendance join courses where attendance.course_id = courses.course_id and tgno = ? and level_no = ? and semester_no = ?";

            prepStatement = conn.prepareStatement(noticeLoadQuery);
            prepStatement.setString(1,tono);
            prepStatement.setInt(2, Integer.parseInt(level_no));
            prepStatement.setInt(3, Integer.parseInt(semester_no));

            ResultSet result = prepStatement.executeQuery();

            AttendanceSubjectCode.removeAllItems();
//            AttendanceSubjectCodePerc.removeAllItems();

            AttendanceSubjectCode.addItem("");
//            AttendanceSubjectCodePerc.addItem("");

            while(result.next()){
                Atten_Course_Code = result.getString("course_id");
                AttendanceSubjectCode.addItem(Atten_Course_Code);
//                AttendanceSubjectCodePerc.addItem(Atten_Course_Code);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void LoadAttendanceCourseStatus(String tono, int level_no, int semester_no, String course_id) {
        String Atten_Course_Status;

        try{
            String Atten_Course_StatusLoadQuery = "select distinct attendance.course_status from attendance join courses where attendance.course_id = courses.course_id and tgno = ? and level_no = ? and semester_no = ? and courses.course_id = ?";

            prepStatement = conn.prepareStatement(Atten_Course_StatusLoadQuery);
            prepStatement.setString(1,tono);
            prepStatement.setInt(2,level_no);
            prepStatement.setInt(3,semester_no);
            prepStatement.setString(4,course_id);

            ResultSet result = prepStatement.executeQuery();

            AttendanceSubjectStatus.removeAllItems();
            AttendanceSubjectStatusPerc.removeAllItems();

            AttendanceSubjectStatus.addItem("");
            AttendanceSubjectStatusPerc.addItem("");

            while(result.next()){
                Atten_Course_Status = result.getString("course_status");
                AttendanceSubjectStatus.addItem(Atten_Course_Status);
                AttendanceSubjectStatusPerc.addItem(Atten_Course_Status);
            }

            int itemCount = AttendanceSubjectStatusPerc.getItemCount();
            if(itemCount>2){
                AttendanceSubjectStatusPerc.addItem("Theory/Practical");
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

    private void valuesforAttendanceTable(int level_no , String semester_no, String tono) {

        String AttendanceTableValues = "select courses.course_id,course_name,course_status,week_no,atten_status,med_id from attendance join courses where attendance.course_id = courses.course_id and tgno = ? and level_no = ? and semester_no = ?";
        DefaultTableModel tblmodel = (DefaultTableModel) AttendanceTable.getModel();
        try{
            prepStatement = conn.prepareStatement(AttendanceTableValues);
            prepStatement.setString(1,tono);
            prepStatement.setInt(2,level_no);
            prepStatement.setInt(3, Integer.parseInt(semester_no));
            ResultSet result = prepStatement.executeQuery();
            while (result.next()){
                String courseID = result.getString("course_id");
                String courseName = result.getString("course_name");
                String course_status = result.getString("course_status");
                String week_no = result.getString("week_no");
                String atten_status = result.getString("atten_status");
                String med_id = result.getString("med_id");

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

    //setting configuration
    private void TOUpdateCredentials(String tono) {
        try{
            String TOaddress = textField7.getText();
            String TOemail = textField8.getText();
            String TOphno = textField9.getText();

            String extension = (String) filePathValues[3];

            String TOProfileImagePath = "Resources/ProfileImages/" + tono + "." + extension;
            System.out.println(TOProfileImagePath);
            String TOCredentialupdateQuery;
            if (extension == null){
                TOCredentialupdateQuery = "Update technical_officer set toaddress = '" + toAddress + "', TOemail = '"+ TOemail +"',TOphno = '"+ TOphno+"' where tgno = '" + tono + "'";
            }else {
                TOCredentialupdateQuery = "Update technical_officer set toaddress = '" + toAddress + "', toemail = '"+ toEmail +"',tophno = '"+ toPhno+"',toProfImg ='" + toProfImg + "' where tono = '" + tono + "'";
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
                textDEP =DBresult.getString("todepartment");
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
            } else {
                JOptionPane.showMessageDialog(null, "Internal Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

