package HomePage;
import DBCONNECTION.DBCONNECTION;
import HomePage.UG.*;
import Login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Scanner;

public class UndergraduateHomePage extends JFrame {

    private String cardCommand;

    public JPanel UndergraduateHomePage;
    public JPanel UGHomeCard;
    public JPanel UGProfile;
    public JPanel UGAttendance;
    public JPanel UGTimeTable;
    public JPanel UGCourses;
    public JPanel UGMedicals;
    public JPanel UGNotices;
    public JPanel UGGrades;
    public JPanel UGSettings;
    public JButton profileButton;
    public JButton attendanceButton;
    public JButton timeTableButton;
    public JButton coursesButton;
    public JButton medicalButton;
    public JButton noticesButton;
    public JButton gradesButton;
    public JButton settingsButton;
    public JButton logoutButton;
    public JTextField txtTGNO;
    public JTextField txtFNAME;
    public JTextField txtLNAME;
    public JTextField txtADDRESS;
    public JTextField txtEMAIL;
    public JTextField txtPHNO;
    public JTextField textField7;
    public JTextField textField8;
    public JTextField textField9;
    public JButton cancelButton;
    public JButton updateButton;
    public JLabel CardTittleLabel;
    public JButton uploadImageButton;
    public JLabel UGProfileImage;
    public JPanel UGProfImgPanel;
    public JLabel HomePageUserProfileLable;
    public JPanel HomePageUserProfile;
    public JComboBox noticeTitleDropDown;
    public JTextArea noticeDisplayArea;
    public JTable tableTimeTable;
    public JComboBox SemesterNoDropDown;
    public JComboBox LevelNoDropDown;
    public JTable UGGradeTable;
    public JComboBox UGLevelNoforMarksDropDown;
    public JComboBox UGSemesterNoforMarksDropDown;
    public JLabel lblCGPA;
    public JLabel lblSGPA;
    public JLabel UGClass;
    public JButton viewMedicalsButton;
    public JTable AttendanceTable;
    public JComboBox AttendanceLevelNo;
    public JComboBox AttendanceSemesterNo;
    public JComboBox AttendanceSubjectCode;
    public JComboBox AttendanceSubjectStatus;
    public JComboBox AttendanceSubjectStatusPerc;
    public JLabel AttendancePercWithoutMed;
    public JLabel AttendancePercWithMed;
    public JTable UGMedicalTable;
    public JComboBox LevelNoforMedical;
    public JComboBox SemesterNoforMedical;
    public JComboBox LevelNoforCourses;
    public JComboBox SemesterNoforCourses;
    public JComboBox CourseforCourses;
    public JTextArea DescriptionforCourseMaterial;
    public JComboBox CourseMaterialforCourses;
    public JButton SaveCourseMaterial;

    public CardLayout cardLayout;

    public String[] cardButtons = {"Profile", "Attendance", "Time Table", "Courses", "Medical", "Notices", "Grades", "Settings"};
    public String[] cardNames = {"UGProfileCard", "UGAttendanceCard", "UGTimeTableCard", "UGCoursesCard", "UGMedicalsCard", "UGNoticesCard", "UGGradesCard", "UGSettingsCard"};
    public JButton[] btnFieldNames = {profileButton,attendanceButton,timeTableButton,coursesButton,medicalButton,noticesButton,gradesButton,settingsButton};
    public String[] cardTitles = {"Welcome..!", "Attendance Details", "Undergraduate Time Table","Your Courses","Medical Information", "Notices", "Grades and GPA","Settings Configuration"};;

    public Object[] filePathValues = new Object[4];
    private Object[] GlobalVariables = new Object[4];

    DBCONNECTION _dbconn = new DBCONNECTION();
    public Connection conn = _dbconn.Conn();
    public PreparedStatement prepStatement;

    private int SemeterNumber;
    private int LevelNumber;

    TableSetMeth _TableSet = new TableSetMeth(this);
    UGPROFILE _UGProfile = new UGPROFILE(this);
    UGNOTICE _UGNotice = new UGNOTICE(this);
    TIMETABLE _TimeTable = new TIMETABLE(this);
    UGGRADES _UGGrade = new UGGRADES(this);
    UGATTENDANCE _UGAttendance = new UGATTENDANCE(this);
    UGMEDICAL _UGMedical = new UGMEDICAL(this);
    UGCOURSE _UGCourse = new UGCOURSE(this);
    UGSETTING _UGSetting = new UGSETTING(this);
    UGDATABASEQUERY _DataQuery = new UGDATABASEQUERY(this);

    public UndergraduateHomePage(String userIdentity){

        GlobalVariables[0] = 1;
        GlobalVariables[1] = 1;
        GlobalVariables[2] = "";

        _UGProfile.dbConnection(userIdentity);
        _UGNotice.LoadNotices();

        setContentPane(UndergraduateHomePage);
        setTitle("Undergraduate User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 570);
        setLocationRelativeTo(this);
        setVisible(true);

        cardLayout = (CardLayout)(UGHomeCard.getLayout());

        profileButton.setEnabled(false);
        CardTittleLabel.setText(cardTitles[0]);
        _UGProfile.loadUGProfImage(userIdentity);
        lblSGPA.setText("SGPA Not Available");

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardCommand = e.getActionCommand();
                _UGProfile.changeBtnState(cardCommand,userIdentity);
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
                _UGSetting.UGUploadToPreviewProfileImage(userIdentity);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _UGSetting.UGUpdateCredentials(userIdentity);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int noOfButtons = cardButtons.length;

                cardLayout.show(UGHomeCard,cardNames[0]);
                btnFieldNames[0].setEnabled(false);
                btnFieldNames[noOfButtons-1].setEnabled(true);
                _UGProfile.loadUGProfImage(userIdentity);
                _UGProfile.dbConnection(userIdentity);
            }
        });
        noticeTitleDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String notice = (String) noticeTitleDropDown.getSelectedItem();
                _UGNotice.viewNotice(notice);
            }
        });

        _TableSet.TimeTableSetModelMethod();

        LevelNoDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _TableSet.TimeTableSetModelMethod();

                int[] timetablefilterdata = _TimeTable.TimeTableCollection();
                _TimeTable.valuesForTimeTable(timetablefilterdata[0],timetablefilterdata[1]);
            }
        });

        SemesterNoDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _TableSet.TimeTableSetModelMethod();

                int[] timetablefilterdata = _TimeTable.TimeTableCollection();
                _TimeTable.valuesForTimeTable(timetablefilterdata[0],timetablefilterdata[1]);
            }
        });

        _TableSet.MarksTableSetModelMethod();

        UGLevelNoforMarksDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                lblSGPA.setText("SGPA Not Available");

                _TableSet.MarksTableSetModelMethod();

                int[] marksTableFilterData = _UGGrade.MarksTableCollection();

                _UGGrade.calculateGrade(userIdentity,marksTableFilterData[0],marksTableFilterData[1]);
            }
        });
        UGSemesterNoforMarksDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                lblSGPA.setText("SGPA Not Available");

                _TableSet.MarksTableSetModelMethod();

                int[] marksTableFilterData = _UGGrade.MarksTableCollection();

                _UGGrade.calculateGrade(userIdentity,marksTableFilterData[0],marksTableFilterData[1]);
            }
        });

        _TableSet.AttendanceTableSetMethod();

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
                _TableSet.AttendanceTableSetMethod();

                String level_no = (String) AttendanceLevelNo.getSelectedItem();
                int LevelNo = Integer.parseInt(level_no);

                _UGAttendance.valuesforAttendanceTable(LevelNo,SemeterNumber,userIdentity);
                _UGAttendance.valuesforAttendanceTable(LevelNo,1,userIdentity);
                _UGAttendance.LoadAttendanceCourseNumber(userIdentity, LevelNo,1);

                GlobalVariables[0] = LevelNo;

                int semester_no_Global = (Integer) GlobalVariables[1];
                String course_id_Global = (String) GlobalVariables[2];
                String course_status_Global = (String) GlobalVariables[3];

                _UGAttendance.LoadAttendanceTable(userIdentity,LevelNo,semester_no_Global,course_id_Global,course_status_Global);
            }
        });
        AttendanceSemesterNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _TableSet.AttendanceTableSetMethod();

                String semester_no = (String) AttendanceSemesterNo.getSelectedItem();
                int SemesterNo = Integer.parseInt(semester_no);

                _UGAttendance.valuesforAttendanceTable(LevelNumber,SemesterNo,userIdentity);
                _UGAttendance.valuesforAttendanceTable(2,SemesterNo,userIdentity);
                _UGAttendance.LoadAttendanceCourseNumber(userIdentity, 2, SemesterNo);

                GlobalVariables[1] = SemesterNo;

                int level_no_Global = (Integer) GlobalVariables[0];
                String course_id_Global = (String) GlobalVariables[2];
                String course_status_Global = (String) GlobalVariables[3];

                _UGAttendance.LoadAttendanceTable(userIdentity,level_no_Global,SemesterNo,course_id_Global,course_status_Global);
            }
        });
        AttendanceSubjectCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _TableSet.AttendanceTableSetMethod();

                int level_no_Global = (Integer) GlobalVariables[0];
                int semester_no_Global = (Integer) GlobalVariables[1];
                String course_status_Global = (String) GlobalVariables[3];

                String Atten_Subject_code = (String) AttendanceSubjectCode.getSelectedItem();
                _UGAttendance.LoadAttendanceCourseStatus(userIdentity,Atten_Subject_code);

                GlobalVariables[2] = Atten_Subject_code;
                String course_id_Global = (String) GlobalVariables[2];

                _UGAttendance.LoadAttendanceTable(userIdentity,level_no_Global,semester_no_Global,course_id_Global,course_status_Global);
            }
        });
        AttendanceSubjectStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _TableSet.AttendanceTableSetMethod();

                String Atten_Subject_Status = (String) AttendanceSubjectStatus.getSelectedItem();

                GlobalVariables[3] = Atten_Subject_Status;

                int level_no_Global = (Integer) GlobalVariables[0];
                int semester_no_Global = (Integer) GlobalVariables[1];
                String course_id_Global = (String) GlobalVariables[2];

                _UGAttendance.LoadAttendanceTable(userIdentity,level_no_Global,semester_no_Global,course_id_Global,Atten_Subject_Status);
            }
        });
        AttendanceSubjectStatusPerc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Course_Status_for_Perc = "Theory";
                Course_Status_for_Perc = (String) AttendanceSubjectStatusPerc.getSelectedItem();

                String course_id_Global = (String) GlobalVariables[2];

                _UGAttendance.LoadAttendancePercentages(Course_Status_for_Perc,userIdentity,course_id_Global);
            }
        });

        _UGGrade.CalcCGPA(userIdentity);
        _TableSet.MedicalTableSetModelMethod();

        LevelNoforMedical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _TableSet.MedicalTableSetModelMethod();

                int[] medicalTableFilterData = _UGMedical.MedicalTableCollection();

                _UGMedical.LoadMedicalTable(userIdentity,medicalTableFilterData[0],medicalTableFilterData[1]);
            }
        });
        SemesterNoforMedical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _TableSet.MedicalTableSetModelMethod();

                int[] medicalTableFilterData = _UGMedical.MedicalTableCollection();

                System.out.println(medicalTableFilterData[0] + " " + medicalTableFilterData[1]);

                _UGMedical.LoadMedicalTable(userIdentity,medicalTableFilterData[0],medicalTableFilterData[1]);
            }
        });
        LevelNoforCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _UGCourse.CourseDetailsforCourses();
            }
        });
        SemesterNoforCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _UGCourse.CourseDetailsforCourses();
            }
        });
        CourseforCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String getCourse = (String) CourseforCourses.getSelectedItem();
                if (getCourse == null) {
                    CourseMaterialforCourses.removeAllItems();
                    DescriptionforCourseMaterial.setText("");
                } else {
                    _UGCourse.CourseMaterialDetailsforCourses();
                }
            }
        });
        CourseMaterialforCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _UGCourse.LoadCourseMaterialDesc();
            }
        });
        SaveCourseMaterial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveCourseMaterial();
            }
        });
    }

    private void SaveCourseMaterial(){
        String filename;
        String resourcePath;
        String resourceRegex = "/";
        try {
            String LevelforCoursesStr = (String) LevelNoforCourses.getSelectedItem();
            assert LevelforCoursesStr != null;
            if(LevelforCoursesStr.isEmpty()){
                LevelforCoursesStr = "0";
            }
            int LevelforCoursesInt = Integer.parseInt(LevelforCoursesStr);

            String SemesterforCoursesStr = (String) SemesterNoforCourses.getSelectedItem();
            assert SemesterforCoursesStr != null;
            if(SemesterforCoursesStr.isEmpty()){
                SemesterforCoursesStr = "0";
            }
            int SemesterforCoursesInt = Integer.parseInt(SemesterforCoursesStr);

            String selectedCourse = (String) CourseforCourses.getSelectedItem();

            String regex = "\\s";
            assert selectedCourse != null;
            String[] str_split = selectedCourse.split(regex);

            String course_material = (String) CourseMaterialforCourses.getSelectedItem();

            resourcePath = _UGCourse.selectCourseMaterial(LevelforCoursesInt,SemesterforCoursesInt,str_split[0],course_material);

            String[] resourceFile = resourcePath.split(resourceRegex);
            int arrLength = resourceFile.length;
            filename = resourceFile[arrLength - 1];

            _UGCourse.saveResourceToFile(resourcePath,filename);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UndergraduateHomePage("tg0001");
    }
}
