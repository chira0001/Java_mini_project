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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.spec.ECField;
import java.sql.*;
import java.text.DecimalFormat;
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
    private JComboBox AttendanceSubjectStatusPerc;
    private JLabel AttendancePercWithoutMed;
    private JLabel AttendancePercWithMed;
    private JTable UGMedicalTable;
    private JComboBox LevelNoforMedical;
    private JComboBox SemesterNoforMedical;
    private JComboBox LevelNoforCourses;
    private JComboBox SemesterNoforCourses;
    private JComboBox CourseforCourses;
    private JTextArea DescriptionforCourseMaterial;
    private JComboBox CourseMaterialforCourses;
    private JButton SaveCourseMaterial;

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

    double CGPA;

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
        lblSGPA.setText("SGPA Not Available");

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
                assert level_no != null;
                if(level_no.isEmpty()){
                    level_no = "0";
                }
                int LevelNo = Integer.parseInt(level_no);

                String semester_no = (String) SemesterNoDropDown.getSelectedItem();
                assert semester_no != null;
                if(semester_no.isEmpty()){
                    semester_no = "0";
                }

                int SemesterNo = Integer.parseInt(semester_no);

                valuesForTimeTable(LevelNo,SemesterNo);
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

        MarksTableSetModelMethod();

        UGLevelNoforMarksDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblSGPA.setText("SGPA Not Available");
                MarksTableSetModelMethod();

                String level_no = (String) UGLevelNoforMarksDropDown.getSelectedItem();
                assert level_no != null;
                if(level_no.isEmpty()){
                    level_no = "0";
                }
                int LevelNo = Integer.parseInt(level_no);

                String semester_no = (String) UGSemesterNoforMarksDropDown.getSelectedItem();
                assert semester_no != null;
                if(semester_no.isEmpty()){
                    semester_no = "0";
                }
                int SemesterNo = Integer.parseInt(semester_no);

                calculateGrade(userIdentity,LevelNo,SemesterNo);
            }
        });
        UGSemesterNoforMarksDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblSGPA.setText("SGPA Not Available");
                MarksTableSetModelMethod();

                String semester_no = (String) UGSemesterNoforMarksDropDown.getSelectedItem();
                assert semester_no != null;
                if(semester_no.isEmpty()){
                    semester_no = "0";
                }
                int SemesterNo = Integer.parseInt(semester_no);

                String level_no = (String) UGLevelNoforMarksDropDown.getSelectedItem();
                assert level_no != null;
                if(level_no.isEmpty()){
                    level_no = "0";
                }
                int LevelNo = Integer.parseInt(level_no);

                calculateGrade(userIdentity,LevelNo,SemesterNo);
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
        AttendanceSubjectStatusPerc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Course_Status_for_Perc = "Theory";
                Course_Status_for_Perc = (String) AttendanceSubjectStatusPerc.getSelectedItem();

                String course_id_Global = (String) GlobalVariables[2];

                LoadAttendancePercentages(Course_Status_for_Perc,userIdentity,course_id_Global);
            }
        });

        CalcCGPA(userIdentity);

        MedicalTableSetModelMethod();


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
        LevelNoforCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SemesterforCoursesStr = (String) SemesterNoforCourses.getSelectedItem();
                assert SemesterforCoursesStr != null;
                if(SemesterforCoursesStr.isEmpty()){
                    SemesterforCoursesStr = "0";
                }
                int SemesterforCoursesInt = Integer.parseInt(SemesterforCoursesStr);

                String LevelforCoursesStr = (String) LevelNoforCourses.getSelectedItem();
                assert LevelforCoursesStr != null;
                if(LevelforCoursesStr.isEmpty()){
                    LevelforCoursesStr = "0";
                }
                int LevelforCoursesInt = Integer.parseInt(LevelforCoursesStr);

                CourseDetailsforCourses(LevelforCoursesInt,SemesterforCoursesInt);
            }
        });
        SemesterNoforCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                CourseDetailsforCourses(LevelforCoursesInt,SemesterforCoursesInt);
            }
        });
        CourseforCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                String regex = "[\\s]";
                String[] str_split = selectedCourse.split(regex);

                CourseMaterialDetailsforCourses(LevelforCoursesInt,SemesterforCoursesInt,str_split[0]);
            }
        });
        CourseMaterialforCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                LoadCourseMaterialDesc(LevelforCoursesInt,SemesterforCoursesInt,str_split[0],course_material);
            }
        });
        SaveCourseMaterial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                    resourcePath = selectCourseMaterial(LevelforCoursesInt,SemesterforCoursesInt,str_split[0],course_material);

                    String[] resourceFile = resourcePath.split(resourceRegex);
                    int arrLength = resourceFile.length;
                    filename = resourceFile[arrLength - 1];

                    saveResourceToFile(resourcePath,filename);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

private String selectCourseMaterial(int level_no, int semester_no, String course_id, String course_material){
    String materialPath = "";
        try{
            String SelectCourseMaterialQuery = "select c_material_location from course_materials join courses on course_materials.c_id = courses.course_id where courses.level_no = ? and courses.semester_no = ? and c_id = ? and c_material = ?";
            prepStatement = conn.prepareStatement(SelectCourseMaterialQuery);

            prepStatement.setInt(1,level_no);
            prepStatement.setInt(2,semester_no);
            prepStatement.setString(3,course_id);
            prepStatement.setString(4,course_material);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                materialPath = resultSet.getString("c_material_location");
            }
            return materialPath;

        }catch(Exception e){
            e.printStackTrace();
        }
    return materialPath;
}

    private void saveResourceToFile(String resourcePath, String filename) throws IOException {
        JFileChooser directoryChooser = new JFileChooser();
        directoryChooser.setDialogTitle("Select Folder to Save File");
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        directoryChooser.setAcceptAllFileFilterUsed(false);

        int userSelection = directoryChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedDir = directoryChooser.getSelectedFile();
            File destinationFile = new File(selectedDir,filename);

            System.out.println(resourcePath);
            System.out.println(destinationFile);

            Path currentPath = Path.of(resourcePath);

            if (resourcePath == null || resourcePath.equals("")) {
                JOptionPane.showMessageDialog(null, "Resource not found: " + resourcePath);
                return;
            }else{
                Files.copy(currentPath, destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(null, "File saved to: " + destinationFile.getAbsolutePath());
            }
        }
    }

    private void LoadCourseMaterialDesc(int level_no, int semester_no, String course_id, String c_material){
        try{
            String c_material_desc;

            String selectCourseMatDescQuery = "select c_material_desc from course_materials join courses on course_materials.c_id = courses.course_id where courses.level_no = ? and courses.semester_no = ? and c_id = ? and c_material = ?";
            prepStatement = conn.prepareStatement(selectCourseMatDescQuery);
            prepStatement.setInt(1,level_no);
            prepStatement.setInt(2,semester_no);
            prepStatement.setString(3,course_id);
            prepStatement.setString(4,c_material);

            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                c_material_desc = resultSet.getString("c_material_desc");

                File CourseMatDesc = new File(c_material_desc);
                input = new Scanner(CourseMatDesc);

                StringBuilder MaterialDescContent = new StringBuilder();

                while (input.hasNextLine()){
                    MaterialDescContent.append(input.nextLine()).append("\n");
                }
                DescriptionforCourseMaterial.setText(MaterialDescContent.toString());
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

    private void CourseMaterialDetailsforCourses(int level_no, int semester_no, String course_id){
        try{
            String selectCourseMatQuery = "select c_material from course_materials join courses on course_materials.c_id = courses.course_id where courses.level_no = ? and courses.semester_no = ? and c_id = ?";
            prepStatement = conn.prepareStatement(selectCourseMatQuery);
            prepStatement.setInt(1,level_no);
            prepStatement.setInt(2,semester_no);
            prepStatement.setString(3,course_id);

            ResultSet resultSet = prepStatement.executeQuery();
            CourseMaterialforCourses.removeAllItems();

            while(resultSet.next()){
                String c_material = resultSet.getString("c_material");

                CourseMaterialforCourses.addItem(c_material);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void CourseDetailsforCourses(int level_no, int semester_no){
        try{
            String c_id,course_name,c_material,Course;

            String selectCourseQuery = "select distinct(c_id),course_name from course_materials join courses on course_materials.c_id = courses.course_id where courses.level_no = ? and courses.semester_no = ?";
            prepStatement = conn.prepareStatement(selectCourseQuery);
            prepStatement.setInt(1,level_no);
            prepStatement.setInt(2,semester_no);

            ResultSet resultSet = prepStatement.executeQuery();
            CourseforCourses.removeAllItems();

            while(resultSet.next()){
                c_id = resultSet.getString("c_id");
                course_name = resultSet.getString("course_name");

                Course = c_id + " - " + course_name;
                CourseforCourses.addItem(Course);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void MedicalTableSetModelMethod(){
        UGMedicalTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Medical No","Course code","Course Name","Course Status","Week No","Medical Reason"}
        ));
    }

    private void LoadMedicalTable(String tgno, int level_no, int semester_no){
        System.out.println("Lev " + level_no + " Sem - " +semester_no);

        DefaultTableModel tblmodel = (DefaultTableModel) UGMedicalTable.getModel();
//        MedicalTableSetModelMethod();

        try{
            String medLoadQuery = "select medical_no,courses.course_id,courses.course_name,course_status,medical.week_no,med_reason from attendance join medical on attendance.med_id = medical.medical_no join courses on attendance.course_id = courses.course_id where medical.tgno = ? and level_no = ? and semester_no = ?";
            prepStatement = conn.prepareStatement(medLoadQuery);
            prepStatement.setString(1,tgno);
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

    private void MarksTableSetModelMethod(){
        UGGradeTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Course Code","Course Name","CA Marks","Eligibility for Final Exam","Final Marks","Attendance Eligibility","Grade"}
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

    private void valuesForTimeTable(int level_no, int semester_no){

        System.out.println("Level " + level_no + " Semester " + semester_no);

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

    public void calculateGrade(String tgno, int level_no, int semester_no){
        DefaultTableModel defaultTableModel = (DefaultTableModel) UGGradeTable.getModel();
        DecimalFormat df = new DecimalFormat("0.00");

        int quiz_percentage,assessment_percentage,mid_term_percentage,final_theory_percentage,final_practical_percentage,
                quiz1,quiz2,quiz3,quiz4,assessment1,assessment2,mid_term,finalTheory,finalPractical,ca_perc_max,final_mark_perc_max;

        double quizSum, quizPercentage, assessmentPercentage, midPercentage, FinalTheoryPercentage,FinalPracticalPercentage, ca_mark_perc, final_mark_perc;

        String c_id,c_name,c_grade = "";
        float atten_perc_float = 0;

        double c_sum = 0;
        double SGPA = 0;

        int credit_sum;
        double credit_point_mul = 0,credit_point_mul_sum = 0;

        try{
            String selectMarkPercentage = "select tgno,courses.course_id,courses.course_name,quiz_one,quiz_second,quiz_third,quiz_fourth,assessment_one,assessment_second,mid_term,final_theory,final_practical,quizzes_perc,assessment_perc,mid_term_perc,final_theory_perc,final_practical_perc from marks join courses where marks.course_id = courses.course_id and tgno = ? and level_no = ? and semester_no = ?";

            prepStatement = conn.prepareStatement(selectMarkPercentage);
            prepStatement.setString(1,tgno);
            prepStatement.setInt(2,level_no);
            prepStatement.setInt(3,semester_no);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                c_id = resultSet.getString("course_id");
                c_name = resultSet.getString("course_name");

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

                String atten_eligibility_query = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical', 'theory') AND atten_status IN ('present','medical'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical', 'theory')) * 100) AS percentage";
                prepStatement = conn.prepareStatement(atten_eligibility_query);
                prepStatement.setString(1,tgno);
                prepStatement.setString(2,c_id);
                prepStatement.setString(3,tgno);
                prepStatement.setString(4,c_id);

                ResultSet resultSet1 = prepStatement.executeQuery();

                while (resultSet1.next()){
                    String atten_perc_Str = resultSet1.getString("percentage");
                    atten_perc_float = Float.parseFloat(atten_perc_Str);
                }

                ca_perc_max = ((quiz_percentage + assessment_percentage + mid_term_percentage) * 50) / 100;

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

                String ca_eligibility = "Not Eligible";
                if (ca_mark_perc > ca_perc_max){
                    ca_eligibility = "Eligible";
                    if(atten_perc_float >= 80){
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
                        c_grade = "Not Released";
                    }
                }else{
                    ca_eligibility = "Not Eligible";
                    c_grade = "Not Released";
                }

                Object[] GradeTableData = new Object[7];

                GradeTableData[0] = c_id;
                GradeTableData[1] = c_name;
                GradeTableData[2] = ca_mark_perc;
                GradeTableData[3] = ca_eligibility;
                GradeTableData[4] = final_mark_perc;
                GradeTableData[5] = atten_perc_float;
                GradeTableData[6] = c_grade;

                defaultTableModel.addRow(GradeTableData);

                char[] C_id_arr = c_id.toCharArray();
                int c_id_arr_len = C_id_arr.length;
                int i, c_credit;

                c_credit =Integer.parseInt(String.valueOf(C_id_arr[c_id_arr_len - 1]));

                if(c_grade.equals("Not Released")){
                    c_grade = "0";
                }

                double credit_points = 0.00;

                c_sum = final_mark_perc + ca_mark_perc;
                if(c_grade.equals("A+") || c_grade.equals("A")){
                    credit_points = 4.00;
                } else if (c_grade.equals("A-")) {
                    credit_points = 3.70;
                } else if (c_grade.equals("B+")) {
                    credit_points = 3.30;
                } else if (c_grade.equals("B")) {
                    credit_points = 3.00;
                } else if (c_grade.equals("B-")) {
                    credit_points = 2.70;
                } else if (c_grade.equals("C+")) {
                    credit_points = 2.30;
                } else if (c_grade.equals("C")) {
                    credit_points = 2.00;
                } else if (c_grade.equals("C-")) {
                    credit_points = 1.70;
                } else if (c_grade.equals("D+")) {
                    credit_points = 1.30;
                } else if (c_grade.equals("D")) {
                    credit_points = 1.00;
                } else if (c_grade.equals("E") || c_grade.equals("Not Released") || c_grade.equals("F")) {
                    credit_points = 0.00;
                }

                c_sum = c_sum + c_credit;
                credit_point_mul = c_credit * credit_points;
                credit_point_mul_sum = credit_point_mul_sum + credit_point_mul;

//                SGPA = credit_point_mul_sum / c_sum;

                SGPA = Double.parseDouble(df.format(credit_point_mul_sum / c_sum));

                lblSGPA.setText(String.valueOf(SGPA));

//                System.out.println("C_id - " + c_id + " cr_poi_mul " + credit_point_mul + " sum - " + credit_point_mul_sum + " SGPA - " + SGPA);
//                System.out.println("C_id " + c_id + " CM - " + c_sum + " Grade - " + c_grade +" credit - " + c_credit + " Cr point - " + credit_points);


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private double CalcCGPA(String tgno){
        DecimalFormat df = new DecimalFormat("0.00");

        int quiz_percentage,assessment_percentage,mid_term_percentage,final_theory_percentage,final_practical_percentage,
                quiz1,quiz2,quiz3,quiz4,assessment1,assessment2,mid_term,finalTheory,finalPractical,ca_perc_max,final_mark_perc_max;

        double quizPercentage, assessmentPercentage, midPercentage, FinalTheoryPercentage,FinalPracticalPercentage, ca_mark_perc, final_mark_perc;

        String c_id,c_name,c_grade = "";
        float atten_perc_float = 0;

        double c_sum = 0;
        double CGPA = 0;

        int credit_sum;
        double credit_point_mul = 0,credit_point_mul_sum = 0;

        try{
            String selectMarkPercentage = "select tgno,courses.course_id,courses.course_name,quiz_one,quiz_second,quiz_third,quiz_fourth,assessment_one,assessment_second,mid_term,final_theory,final_practical,quizzes_perc,assessment_perc,mid_term_perc,final_theory_perc,final_practical_perc from marks join courses where marks.course_id = courses.course_id and tgno = ?";

            prepStatement = conn.prepareStatement(selectMarkPercentage);
            prepStatement.setString(1,tgno);
//            prepStatement.setInt(2,level_no);
//            prepStatement.setInt(3,semester_no);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                c_id = resultSet.getString("course_id");
                c_name = resultSet.getString("course_name");

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
//                final_mark_perc_max = final_theory_percentage + final_practical_percentage;

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

                String ca_eligibility = "Not Eligible";
                if (ca_mark_perc > ca_perc_max){
                    ca_eligibility = "Eligible";
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
                    ca_eligibility = "Not Eligible";
                    c_grade = "Not Released";
                }

                char[] C_id_arr = c_id.toCharArray();
                int c_id_arr_len = C_id_arr.length;
                int i, c_credit;

                c_credit = Integer.parseInt(String.valueOf(C_id_arr[c_id_arr_len - 1]));

                if(c_grade.equals("Not Released")){
                    c_grade = "0";
                }

                double credit_points = 0.00;

                c_sum = final_mark_perc + ca_mark_perc;

                if(c_grade.equals("A+") || c_grade.equals("A")){
                    credit_points = 4.00;
                } else if (c_grade.equals("A-")) {
                    credit_points = 3.70;
                } else if (c_grade.equals("B+")) {
                    credit_points = 3.30;
                } else if (c_grade.equals("B")) {
                    credit_points = 3.00;
                } else if (c_grade.equals("B-")) {
                    credit_points = 2.70;
                } else if (c_grade.equals("C+")) {
                    credit_points = 2.30;
                } else if (c_grade.equals("C")) {
                    credit_points = 2.00;
                } else if (c_grade.equals("C-")) {
                    credit_points = 1.70;
                } else if (c_grade.equals("D+")) {
                    credit_points = 1.30;
                } else if (c_grade.equals("D")) {
                    credit_points = 1.00;
                } else if (c_grade.equals("E") || c_grade.equals("Not Released") || c_grade.equals("F")) {
                    credit_points = 0.00;
                }

                c_sum = c_sum + c_credit;
                credit_point_mul = c_credit * credit_points;
                credit_point_mul_sum = credit_point_mul_sum + credit_point_mul;

                CGPA = Double.parseDouble(df.format(credit_point_mul_sum / c_sum));

                if(CGPA >= 3.7){
                    UGClass.setText("First Class");
                }else if(CGPA >= 3.3){
                    UGClass.setText("Second Upper Class");
                }else if(CGPA >= 3.0){
                    UGClass.setText("Second Lower Class");
                }else if(CGPA >= 2.0){
                    UGClass.setText("General Class");
                }else{
                    UGClass.setText("Class Unavailable");
                }
                lblCGPA.setText(String.valueOf(CGPA));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CGPA;
    }

    private void valuesforAttendanceTable(int level_no, int semester_no, String tgno){
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
}
