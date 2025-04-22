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
    private JButton cancelButton;
    private JButton updateButton;
    private JPanel ProfileButton;
    private JPanel TechnicalOfficerHomePage;
    private JPanel HomePageUserProfile;
    private JLabel HomePageUserProfileLable;
    private JComboBox TODepartment;
    private JComboBox TOupdateDepartment;
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
}

