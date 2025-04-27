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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Scanner;

import static java.util.Arrays.sort;

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
    private JComboBox noticeTitleDropDown;
    private JTextArea noticeDisplayArea;
    private JPanel LecturerHomePageProfile;
    private JLabel LecturerHomePageProfileLable;
    private JPanel LecProfileImagePanel;
    private JLabel LecProfileImage;
    private JComboBox LECSemesterCombobox;
    private JComboBox LECLevelComboBox;
    private JTable tableTimeTable;
    private JComboBox SemesterNoDropDown;
    private JComboBox LevelNoDropDown;
    private JComboBox AttendanceSemesterNo;
    private JComboBox AttendanceLevelNo;
    private JComboBox AttendanceSubjectCode;
    private JComboBox AttendanceSubjectStatus;
    private JLabel Percentage;
    private JLabel AttendancePercWithMedi;
    private JComboBox StuTGnoAttendance;
    private JTable AttendanceTable;
    private JComboBox SemesterNoforMedical;
    private JComboBox LevelNoforMedical;
    private JTable LECMedicalTable;
    private JComboBox MedicalCourseCode;
    private JPanel LECGrades;
    private JComboBox SemesterNoGrade;
    private JComboBox LevelNoGrade;
    private JLabel lblCGPA;
    private JLabel lblSGPA;
    private JLabel UGClass;
    private JTable UGGradeTable;
    private JButton GradesButton;
    private JComboBox MarkSubCodeCombo;
    private JTextField LECMarkTGno;
    private JTextField LECMarkQuiz1;
    private JTextField LECMarkQuiz2;
    private JTextField LECMarkQuiz3;
    private JTextField LECMarkQuiz4;
    private JTextField LECMarkAssess1;
    private JTextField LECMarkAssess2;
    private JTextField LECMarkMid;
    private JTextField theory_field;
    private JTextField LECMarkFTheory;
    private JTextField LECMarkFPractical;
    private JButton uploadMarksButton;
    private JComboBox MarkLevelCombo;
    private JComboBox MarkSemesterCombo;
    private JComboBox MarkSubStatusCombo;
    private JComboBox GradeSubCodeCombo;
    private JButton enterButton;
    private JTextField studentTGno;
    private JButton a;
    private JComboBox LevelNoforCourses;
    private JComboBox SemesterNoforCourses;
    private JComboBox CourseforCourses;
    private JTextArea DescriptionforCourseMaterial;
    private JButton AddMaterials;
    private JButton renameButton;
    private JButton removeButton;
    private JButton saveButton;
    private JTextField Course_MaterialField;
    private JButton StudentDetailsButtons;
    private JPanel LECStudnetDetails;
    private JComboBox STLevel;
    private JComboBox STSem;
    private JComboBox STCource;
    private JTable StudentTable;

    private CardLayout cardLayout;

    private String Lecno;
    private String LecFname;
    private String LecLname;
    private String LecAddress;
    private String LecEmail;
    private String LecPhno;
    private String LecProfImg;

    private String[] cardButtons = {"Profile","Student Details","Attendance", "Time Table", "Courses", "Medical", "Notices", "Grades" ,"Marks", "Settings"};
    private String[] cardNames = {"LECProfileCard","LECStudentCard", "LECAttendanceCard", "LECTimeTableCard", "LECCoursesCard", "LECMedicalsCard", "LECNoticesCard","LECGradesCards", "LECMarksCard", "LECSettingsCard"};
    JButton[] btnFieldNames = {profileButton,StudentDetailsButtons,attendanceButton,timeTableButton,coursesButton,medicalButton,noticesButton,GradesButton,marksButton,settingsButton};
    private String[] cardTitles = {"Welcome Lecturer..!","Students Details","Student Attendance Details", "Lecturer Time Table","Your Courses","Student Medical Information", "Notices", "Student Grades","Students Marks","Your Settings Configuration"};;


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
        GradesButton.addActionListener(listener);
        StudentDetailsButtons.addActionListener(listener);


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

                valuesForTimeTable(userIdentity,LevelNo,SemesterNo);
            }
        });


        SemesterNoDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeTableSetModelMethod();

                String semester_no = (String) SemesterNoDropDown.getSelectedItem();
                assert semester_no != null;
                if (semester_no.isEmpty()) {
                    semester_no = "0";
                }
                int SemesterNo = Integer.parseInt(semester_no);

                String level_no = (String) LevelNoDropDown.getSelectedItem();
                assert level_no != null;
                if (level_no.isEmpty()) {
                    level_no = "0";
                }
                int LevelNo = Integer.parseInt(level_no);

                valuesForTimeTable(userIdentity,LevelNo,SemesterNo);
            }
        });
        AttendanceLevelNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String levelnoattend=  (String) AttendanceLevelNo.getSelectedItem();
                assert levelnoattend != null;
                if(levelnoattend.isEmpty()){
                  levelnoattend = "0";
                }
              int levelnoattendInt= Integer.parseInt(levelnoattend);


              String semesternoattend=  (String) AttendanceSemesterNo.getSelectedItem();
                assert semesternoattend != null;
                if(semesternoattend.isEmpty()){
                  semesternoattend = "0";
                }
              int semesternoattendInt= Integer.parseInt(semesternoattend);

              lecattendanceMethod(userIdentity,levelnoattendInt,semesternoattendInt);
            }

        });
        AttendanceSemesterNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String semesternoattend=  (String) AttendanceSemesterNo.getSelectedItem();
                assert semesternoattend != null;
                if(semesternoattend.isEmpty()){
                    semesternoattend = "0";
                }
                int semesternoattendInt= Integer.parseInt(semesternoattend);

                String levelnoattend=  (String) AttendanceLevelNo.getSelectedItem();
                assert levelnoattend != null;
                if(levelnoattend.isEmpty()){
                    levelnoattend = "0";
                }
                int levelnoattendInt= Integer.parseInt(levelnoattend);

                lecattendanceMethod(userIdentity,levelnoattendInt,semesternoattendInt);

                String subjectstatus = (String) AttendanceSubjectStatus.getSelectedItem();
                String subjectcode = (String) AttendanceSubjectCode.getSelectedItem();
                showtgnumberAttendance(subjectcode,subjectstatus);
            }
        });

        AttendanceSubjectCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subjectstatus = (String) AttendanceSubjectCode.getSelectedItem();
                lecattendancesubjectstatus(subjectstatus);
            }
        });
        AttendanceSubjectStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AllAttendanceTableMethod();
                String subjectstatus = (String) AttendanceSubjectStatus.getSelectedItem();
                String subjectcode = (String) AttendanceSubjectCode.getSelectedItem();

                attendancepercentagemedi(subjectcode,subjectstatus);
            }
        });
        StuTGnoAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String tgno = (String) StuTGnoAttendance.getSelectedItem();
                loadattendance(tgno);
                attendancePersentage(tgno);
                AttendancePerengeMediLabel(tgno);

            }
        });
        LevelNoforMedical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String medicalLevel=(String)LevelNoforMedical.getSelectedItem();
                assert medicalLevel != null;
                if(medicalLevel.isEmpty()){
                    medicalLevel = "0";
                }
                int medicalLevelInt= Integer.parseInt(medicalLevel);

                String medicalSemester= (String)SemesterNoforMedical.getSelectedItem();
                assert medicalSemester != null;
                if(medicalSemester.isEmpty()){
                    medicalSemester = "0";
                }
                int medicalSemesterInt= Integer.parseInt(medicalSemester);
                System.out.println(medicalLevel+" "+medicalSemester);

                SelectMediLevelCourse(userIdentity,medicalLevelInt,medicalSemesterInt);
            }
        });
        SemesterNoforMedical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String medicalSemester=(String)SemesterNoforMedical.getSelectedItem();
                assert medicalSemester != null;
                if(medicalSemester.isEmpty()){
                    medicalSemester = "0";

                }
                int medicalSemesterInt= Integer.parseInt(medicalSemester);

                String medicalLevel=(String)LevelNoforMedical.getSelectedItem();
                assert medicalLevel != null;
                if(medicalLevel.isEmpty()){
                    medicalLevel = "0";
                }
                int medicalLevelInt= Integer.parseInt(medicalLevel);
                System.out.println(medicalSemester+" "+medicalLevel);

                SelectMediLevelCourse(userIdentity,medicalLevelInt,medicalSemesterInt);
                LoadMedicalTable(userIdentity,medicalLevelInt,medicalSemesterInt);
            }
        });
        MarkLevelCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String marklevel=(String)MarkLevelCombo.getSelectedItem();
                assert marklevel != null;
                if(marklevel.isEmpty()){
                    marklevel = "0";

                }
                String marksemester=(String)MarkSemesterCombo.getSelectedItem();
                assert marksemester != null;
                if(marksemester.isEmpty()){
                    marksemester = "0";
                }
                int marksemesterInt= Integer.parseInt(marksemester);
                int marklevelInt= Integer.parseInt(marklevel);



            }
        });
        MarkSemesterCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String marksemester=(String)MarkSemesterCombo.getSelectedItem();
                assert marksemester != null;
                if(marksemester.isEmpty()){
                    marksemester = "0";
                }
                String marklevel=(String)MarkLevelCombo.getSelectedItem();
                assert marklevel != null;
                if(marklevel.isEmpty()){
                    marklevel = "0";
                }
                int marksemesterInt= Integer.parseInt(marksemester);
                int marklevelInt= Integer.parseInt(marklevel);
                markgetcourseid(userIdentity,marklevelInt,marksemesterInt);
            }
        });
        MarkSubStatusCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String status = (String) MarkSubStatusCombo.getSelectedItem();
                if (status != null) {
                    if (status.equals("theory")) {
                        LECMarkFPractical.setEnabled(false);
                        LECMarkFTheory.setEnabled(true);
                    } else if (status.equals("practical")) {
                        LECMarkFTheory.setEnabled(false);
                        LECMarkFPractical.setEnabled(true);
                    } else {
                        LECMarkFTheory.setEnabled(true);
                        LECMarkFPractical.setEnabled(true);
                    }
                }
            }
        });

        MarkSubCodeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subcode=(String)MarkSubCodeCombo.getSelectedItem();
                if(subcode.isEmpty()&& subcode.equals("")){
                    JOptionPane.showMessageDialog(null,"Please select a subcode");
                }
                getMarkStatus(subcode);

            }
        });


        LevelNoGrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        SemesterNoGrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String semester = (String) SemesterNoGrade.getSelectedItem();
                assert semester != null;
                if(semester.isEmpty()){
                    semester = "0";
                }
                int semesterInt= Integer.parseInt(semester);
                String level=(String)LevelNoGrade.getSelectedItem();
                assert level != null;
                if(level.isEmpty()){
                    level = "0";
                }
                int levelInt= Integer.parseInt(level);
                loadCourseIDforGrade(userIdentity,levelInt,semesterInt);
            }

        });

        uploadMarksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMark();
            }
        });
        LevelNoforCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CoursesforLecCourse(userIdentity);
            }
        });
        SemesterNoforCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CoursesforLecCourse(userIdentity);
            }
        });
        AddMaterials.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LecSelectMaterial();
                LecSaveMaterial();
                CourseMaterialUpdate();
            }
        });
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GradesTableSetModelMethod();
                calCGPA();

            }
        });

        StudentTableMethod();

        STLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentTableMethod();

                String level = (String) STLevel.getSelectedItem();
                assert level != null;
                if(level.isEmpty()){
                    level = "0";

                }
                int levelInt= Integer.parseInt(level);
                String semester=(String)STSem.getSelectedItem();
                assert semester != null;
                if(semester.isEmpty()){
                    semester = "0";
                }
                int semesterInt= Integer.parseInt(semester);
                LoaStudentDetails(levelInt,semesterInt);
            }
        });
        STSem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentTableMethod();

                String semester = (String) STSem.getSelectedItem();
                assert semester != null;
                if(semester.isEmpty()){
                    semester = "0";
                }
                int semesterInt= Integer.parseInt(semester);

                String level=(String)STLevel.getSelectedItem();
                assert level != null;
                if(level.isEmpty()){
                    level = "0";
                }
                int levelInt= Integer.parseInt(level);

                LoaStudentDetails(levelInt,semesterInt);
            }
        });
        GradesTableSetModelMethod();
        a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradesTableSetModelMethod();
                calCGPA();

            }
        });
    }


    public void GradesTableSetModelMethod(){
        UGGradeTable.setModel(new DefaultTableModel(
                null,
                new String[]{"TGNO","Grade","GPA"}
        ));
    }

    private void calCGPA() {
        String courseID = GradeSubCodeCombo.getSelectedItem().toString();
        DefaultTableModel defaultTableModel = (DefaultTableModel) UGGradeTable.getModel();

        try {

            defaultTableModel.setRowCount(0);

            Connection con = new DBCONNECTION().Conn();

            String sql = "SELECT tgno, quiz_one, quiz_second, quiz_third, quiz_fourth, " +
                    "assessment_one, assessment_second, mid_term, final_theory, final_practical " +
                    "FROM marks WHERE course_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, courseID);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String tgno = rs.getString("tgno");

                double totalMarks = 0;
                totalMarks += rs.getDouble("quiz_one");
                totalMarks += rs.getDouble("quiz_second");
                totalMarks += rs.getDouble("quiz_third");
                totalMarks += rs.getDouble("quiz_fourth");
                totalMarks += rs.getDouble("assessment_one");
                totalMarks += rs.getDouble("assessment_second");
                totalMarks += rs.getDouble("mid_term");
                totalMarks += rs.getDouble("final_theory");
                totalMarks += rs.getDouble("final_practical");

                double percentage = (totalMarks / 700.0) * 100.0;  // adjust if needed

                double gpa = 0.0;
                String grade = "";

                if (percentage >= 85) {
                    gpa = 4.00;
                    grade = "A+";
                } else if (percentage >= 75) {
                    gpa = 3.70;
                    grade = "A";
                } else if (percentage >= 65) {
                    gpa = 3.30;
                    grade = "B+";
                } else if (percentage >= 55) {
                    gpa = 3.00;
                    grade = "B";
                } else if (percentage >= 45) {
                    gpa = 2.70;
                    grade = "C+";
                } else if (percentage >= 35) {
                    gpa = 2.30;
                    grade = "C";
                } else {
                    gpa = 0.00;
                    grade = "F";
                }

                String[] GradeTableData = {tgno, grade, String.format("%.2f", gpa)};
                defaultTableModel.addRow(GradeTableData);
            }

            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }


    private void calCGPA1() {

        DefaultTableModel defaultTableModel = (DefaultTableModel) UGGradeTable.getModel();

        String tgno = studentTGno.getText();
        String courseID = GradeSubCodeCombo.getSelectedItem().toString();

        try {

            defaultTableModel.setRowCount(0);

            Connection con = new DBCONNECTION().Conn();

            String sql = "SELECT quiz_one, quiz_second, quiz_third, quiz_fourth, " +
                    "assessment_one, assessment_second, mid_term, final_theory, final_practical " +
                    "FROM marks WHERE tgno = ? AND course_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, tgno);
            pst.setString(2, courseID);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double totalMarks = 0;
                totalMarks += rs.getDouble("quiz_one");
                totalMarks += rs.getDouble("quiz_second");
                totalMarks += rs.getDouble("quiz_third");
                totalMarks += rs.getDouble("quiz_fourth");
                totalMarks += rs.getDouble("assessment_one");
                totalMarks += rs.getDouble("assessment_second");
                totalMarks += rs.getDouble("mid_term");
                totalMarks += rs.getDouble("final_theory");
                totalMarks += rs.getDouble("final_practical");

                double percentage = (totalMarks / 700.0) * 100.0;

                double gpa = 0.0;
                String grade = "";

                if (percentage >= 85) {
                    gpa = 4.00;
                    grade = "A+";
                } else if (percentage >= 75) {
                    gpa = 3.70;
                    grade = "A";
                } else if (percentage >= 65) {
                    gpa = 3.30;
                    grade = "B+";
                } else if (percentage >= 55) {
                    gpa = 3.00;
                    grade = "B";
                } else if (percentage >= 45) {
                    gpa = 2.70;
                    grade = "C+";
                } else if (percentage >= 35) {
                    gpa = 2.30;
                    grade = "C";
                } else {
                    gpa = 0.00;
                    grade = "F";
                }

                lblCGPA.setText(String.format("%.2f", gpa));
                lblSGPA.setText(grade);
                UGClass.setText(calculateClass(gpa)); // optional: if you have class ranking

                String[] GradeTableData = {tgno, grade, String.format("%.2f", gpa)};
                defaultTableModel.addRow(GradeTableData);

            } else {
                JOptionPane.showMessageDialog(this, "No marks found for this student and subject!");
            }

            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }


    private String calculateClass(double gpa) {
        if (gpa >= 3.70) {
            return "First Class";
        } else if (gpa >= 3.00) {
            return "Second Upper";
        } else if (gpa >= 2.00) {
            return "Second Lower";
        } else {
            return "Fail";
        }
    }


    private void LoaStudentDetails(int level, int semester){
        try{
            DefaultTableModel model = (DefaultTableModel) StudentTable.getModel();

            String loadQry = "select * from undergraduate where study_year = ? and study_semester = ?";

            prepStatement = conn.prepareStatement(loadQry);
            prepStatement.setInt(1,level);
            prepStatement.setInt(2,semester);

            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()) {
                String tgno = resultSet.getString("tgno");
                String ugfname = resultSet.getString("ugfname");
                String uglname = resultSet.getString("uglname");
                String ugaddress = resultSet.getString("ugaddress");
                String ugemail = resultSet.getString("ugemail");
                String ugphno = resultSet.getString("ugphno");


                Object[] ob = new Object[6];
                ob[0] = tgno;
                ob[1] = ugfname;
                ob[2] = uglname;
                ob[3] = ugaddress;
                ob[4] = ugemail;
                ob[5] = ugphno;

                model.addRow(ob);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void CourseMaterialUpdate(){
        try{

            String course_id = (String) CourseforCourses.getSelectedItem();
            String material_name = Course_MaterialField.getText();
            String course_material_description = DescriptionforCourseMaterial.getText();
            String courseMaterialDescFilePath = "Resources/CourseMaterialDesc/"+course_id+"_"+material_name+".txt";
            String extension = (String) filePathValues[3];

            String CourseMaterialPath = "Resources/CourseMaterial/" + course_id+"_"+material_name + "." + extension;

            String InsertMaterialQuery = "INSERT into course_materials(c_id,c_material,c_material_desc,c_material_location) values(?,?,?,?)";

            prepStatement = conn.prepareStatement(InsertMaterialQuery);
            prepStatement.setString(1, course_id);
            prepStatement.setString(2, material_name);
            prepStatement.setString(3, courseMaterialDescFilePath);
            prepStatement.setString(4, CourseMaterialPath);

            int insertResult = prepStatement.executeUpdate();
            if(insertResult>0){
                JOptionPane.showMessageDialog(null, "Successfully Added");
            }else {
                JOptionPane.showMessageDialog(null, "Failed to Add");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void LecSaveMaterial(){
        try{
            Path fromFile = (Path) filePathValues[0];
            Path toFile = (Path) filePathValues[1];
            File UGDestinationFile = (File)filePathValues[2];

            String course_id = (String) CourseforCourses.getSelectedItem();
            String material_name = Course_MaterialField.getText();
            String course_material_description = DescriptionforCourseMaterial.getText();

            String courseMaterialDescFilePath = "Resources/CourseMaterialDesc/"+course_id+"_"+material_name+".txt";

            if (UGDestinationFile.exists()){
                UGDestinationFile.delete();
                Files.copy(fromFile,toFile);
            }else{
                Files.copy(fromFile,toFile);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(courseMaterialDescFilePath));
            writer.write(course_material_description);
            writer.close();
        }catch(Exception exc){
        }
    }

    public void LecSelectMaterial() {
        try {
            JFileChooser UGFileChooser = new JFileChooser();
            UGFileChooser.setDialogTitle("Select a file");

            if (UGFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                String filename = UGFileChooser.getSelectedFile().getAbsolutePath();

                String MaterialSavePath = "Resources/CourseMaterial/";

                File LecSaveMaterialDirectory = new File(MaterialSavePath);
                if (!LecSaveMaterialDirectory.exists()) {
                    LecSaveMaterialDirectory.mkdirs();
                }

            String course_id = (String) CourseforCourses.getSelectedItem();
            String material_name = Course_MaterialField.getText();

                File LecSourceFile = null;

                String extension = filename.substring(filename.lastIndexOf('.') + 1);

                String newFile = course_id + "_" + material_name;
                LecSourceFile = new File(newFile + "." + extension);

                File UGDestinationFile = new File(MaterialSavePath + LecSourceFile);

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

    private void CoursesforLecCourse(String lecno){
       try{
           String LevelNO = (String) LevelNoforCourses.getSelectedItem();
           if(LevelNO.isEmpty()){
               LevelNO = "0";
           }
           String SemesterNO = (String) SemesterNoforCourses.getSelectedItem();
            if(SemesterNO.isEmpty()){
                SemesterNO = "0";
            }
           CourseforCourses.removeAllItems();

           String getCourseQry = "select courses.course_id from courses join lecture_course on courses.course_id = lecture_course.course_id where lecno = ? and level_no = ? and semester_no = ?";
           prepStatement = conn.prepareStatement(getCourseQry);

           prepStatement.setString(1,lecno);
           prepStatement.setInt(2,Integer.parseInt(LevelNO));
           prepStatement.setInt(3,Integer.parseInt(SemesterNO));

           ResultSet resultSet = prepStatement.executeQuery();
           while (resultSet.next()) {
               String courseID = resultSet.getString("course_id");

               CourseforCourses.addItem(courseID);
           }

       } catch (Exception e) {
           e.printStackTrace();
       }
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

    private void LECCourse(String lecno) {
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

    private void valuesForTimeTable(String lecno,int level_no, int semester_no){

        System.out.println("Level " + level_no + " Semester " + semester_no);

        String TimeTableValues = "SELECT t.module_day,c.course_id,c.course_name,t.time FROM lecturer lec JOIN lecture_course lc ON lec.lecno = lc.lecno JOIN courses c ON lc.course_id = c.course_id JOIN timeTable t ON c.course_id = t.course_id WHERE lec.lecno = ? AND c.level_no = ? AND c.semester_no = ?;";
        DefaultTableModel tblmodel = (DefaultTableModel) tableTimeTable.getModel();
        try{
            prepStatement = conn.prepareStatement(TimeTableValues);
            prepStatement.setString(1,lecno);
            prepStatement.setInt(2,level_no);
            prepStatement.setInt(3,semester_no);
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

    private void updateCoursesIfReady(String lecno) {
        String LecLevel = (String) LECLevelComboBox.getSelectedItem();
        String LecSemester = (String) LECSemesterCombobox.getSelectedItem();


        if (LecLevel != null && !LecLevel.isEmpty() && LecSemester != null && !LecSemester.isEmpty()) {
            try {

                int level = Integer.parseInt(LecLevel);
                int semester = Integer.parseInt(LecSemester);

                LECCourse(lecno,level, semester);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void LECCourse(String lecno,int level, int semester) {
        try {
            DefaultListModel<String> model = new DefaultListModel<>();
            String query = "select courses.course_name from courses join lecture_course on courses.course_id = lecture_course.course_id where lecture_course.lecno = ? and courses.level_no = ? and courses.semester_no = ?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, lecno);
            prepStatement.setInt(2, level);
            prepStatement.setInt(3, semester);

            ResultSet resultSet = prepStatement.executeQuery();


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

    private void lecattendanceMethod(String lecno,int level,int semester){
        System.out.println(lecno+" "+level + " " + semester);

        try{
            String attendanceQuery = "select courses.course_id from courses join lecture_course on courses.course_id = lecture_course.course_id where lecno=? AND level_no=? AND semester_no=?;";
            prepStatement=conn.prepareStatement(attendanceQuery);
            prepStatement.setString(1,lecno);
            prepStatement.setInt(2,level);
            prepStatement.setInt(3,semester);

            ResultSet resultSet = prepStatement.executeQuery();
            AttendanceSubjectCode.removeAllItems();
            while (resultSet.next()){
                String CourseID = resultSet.getString("course_id");
                AttendanceSubjectCode.addItem(CourseID);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void lecattendancesubjectstatus(String subject_code){
        try{
            String subjectStatusQuery="select status from courses where course_id=?;";
            prepStatement=conn.prepareStatement(subjectStatusQuery);
            prepStatement.setString(1,subject_code);

            ResultSet resultSet = prepStatement.executeQuery();
            AttendanceSubjectStatus.removeAllItems();
            while (resultSet.next()){
                String status = resultSet.getString("status");
                System.out.println(status);

                if(status.equals("theory/practical")){
                    AttendanceSubjectStatus.addItem("theory");
                    AttendanceSubjectStatus.addItem("practical");
                }
                AttendanceSubjectStatus.addItem(status);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadattendance(String tgno){
        try{
            AttendencetableMethod();
            DefaultTableModel tablemodel = (DefaultTableModel) AttendanceTable.getModel();

            String loadAttendanceQuery = "select tgno,week_no,atten_status from attendance where tgno=?;";
            prepStatement=conn.prepareStatement(loadAttendanceQuery);
            prepStatement.setString(1,tgno);

            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                String tg_no = resultSet.getString("tgno");
                String week_no = resultSet.getString("week_no");
                String atten_status = resultSet.getString("atten_status");

                Object[] attendanceData = new Object[3];
                attendanceData[0] = tg_no;
                attendanceData[1] = week_no;
                attendanceData[2] = atten_status;

                tablemodel.addRow(attendanceData);
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void attendanncepercentage(String subject_code,String subject_status){
        try{
            AllAttendanceTableMethod();
            DefaultTableModel tablemodel = (DefaultTableModel) AttendanceTable.getModel();

            String AttendancepercentageQuery="select tgno,COUNT(*) AS total_sessions,SUM(CASE WHEN atten_status='present' THEN 1 ELSE 0 END)AS present_sessions,ROUND(SUM(CASE WHEN atten_status='present' THEN 1 ELSE 0 END)*100.0/COUNT(*),2)AS attendance_percentage FROM attendance WHERE course_id=? AND course_status=? GROUP BY tgno;";
            prepStatement=conn.prepareStatement(AttendancepercentageQuery);
            prepStatement.setString(1,subject_code);
            prepStatement.setString(2,subject_status);
            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                String tgno = resultSet.getString("tgno");
                int total_sessions = resultSet.getInt("total_sessions");
                int present_sessions = resultSet.getInt("present_sessions");
                int attendance_percentage = resultSet.getInt("attendance_percentage");
                Object[] attendanceData = new Object[4];
                attendanceData[0] = tgno;
                attendanceData[1] = total_sessions;
                attendanceData[2] = present_sessions;
                attendanceData[3] = attendance_percentage;
                tablemodel.addRow(attendanceData);
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void attendancepercentagemedi(String subject_code,String subject_status){
        try{
            AllAttendanceTableMethod();
            DefaultTableModel tablemodel = (DefaultTableModel) AttendanceTable.getModel();

            String AttendancepercentageMedicalQuery="select tgno,COUNT(*) AS total_sessions,SUM(CASE WHEN atten_status IN('present','medical') THEN 1 ELSE 0 END)AS present_sessions,ROUND(SUM(CASE WHEN atten_status in('present','medical') THEN 1 ELSE 0 END)*100.0/COUNT(*),2)AS attendance_percentage FROM attendance WHERE course_id=? AND course_status=? GROUP BY tgno;";
            prepStatement=conn.prepareStatement(AttendancepercentageMedicalQuery);
            prepStatement.setString(1,subject_code);
            prepStatement.setString(2,subject_status);

            ResultSet resultSetMedi = prepStatement.executeQuery();

            while (resultSetMedi.next()){
                String tgno = resultSetMedi.getString("tgno");
                int total_sessions = resultSetMedi.getInt("total_sessions");
                int present_sessions = resultSetMedi.getInt("present_sessions");
                int attendance_percentage = resultSetMedi.getInt("attendance_percentage");
                Object[] attendanceData = new Object[4];
                attendanceData[0] = tgno;
                attendanceData[1] = total_sessions;
                attendanceData[2] = present_sessions;
                attendanceData[3] = attendance_percentage;
                tablemodel.addRow(attendanceData);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showtgnumberAttendance(String subject_code,String subject_status){
        try{
            StuTGnoAttendance.removeAllItems();

            String ShowAttendanceTGnumberQuery="select distinct(tgno)from attendance WHERE course_id=? AND course_status=?;";
            prepStatement=conn.prepareStatement(ShowAttendanceTGnumberQuery);
            prepStatement.setString(1,subject_code);
            prepStatement.setString(2,subject_status);
            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                String tgno = resultSet.getString("tgno");
                StuTGnoAttendance.addItem(tgno);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void attendancePersentage(String tgno){
        try{
            String percentageQuery="select ROUND(SUM(CASE WHEN atten_status='present' THEN 1 ELSE 0 END)*100.0/COUNT(*),2)AS attendance_percentage FROM attendance WHERE tgno=?;";
            prepStatement=conn.prepareStatement(percentageQuery);
            prepStatement.setString(1,tgno);
            ResultSet resultSet = prepStatement.executeQuery();

            if(resultSet.next()){
                int attendance_percentage = resultSet.getInt("attendance_percentage");
                Percentage.setText(attendance_percentage+"%");
            }



        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void AttendancePerengeMediLabel(String tgno){
        try{
            String percentageQuery="select ROUND(SUM(CASE WHEN atten_status in('present','medical') THEN 1 ELSE 0 END)*100.0/COUNT(*),2)AS attendance_percentage FROM attendance WHERE tgno=?;";
            prepStatement=conn.prepareStatement(percentageQuery);
            prepStatement.setString(1,tgno);
            ResultSet resultSet = prepStatement.executeQuery();

            if(resultSet.next()){
                int attendance_percentage = resultSet.getInt("attendance_percentage");
                AttendancePercWithMedi.setText(attendance_percentage+"%");
            }



        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void AttendencetableMethod(){
        AttendanceTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Student_tgno","week_no","atten_status"} ));
    }

    private void AllAttendanceTableMethod(){
        AttendanceTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Student_tgno","Total_Sessions","Present_Sessions","Attendance_Percentage(%)"}
        ));
    }

    private void SelectMediLevelCourse(String lecno,int level,int semester){
        try{
            MedicalCourseCode.removeAllItems();
            String SelectMediLevelCourseQuery="select courses.course_id from courses join lecture_course on courses.course_id = lecture_course.course_id where lecno=? AND level_no=? AND semester_no=?;";
            prepStatement=conn.prepareStatement(SelectMediLevelCourseQuery);
            prepStatement.setString( 1, lecno);
            prepStatement.setInt(2, level);
            prepStatement.setInt(3, semester);
            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                String course_id = resultSet.getString("course_id");
                MedicalCourseCode.addItem(course_id);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void MedicalTableMethod(){
        LECMedicalTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Medical_No","Student_tgno","Week_No","Course_Status","Medical_Reason"}
        ));
    }

    private void LoadMedicalTable(String lecno,int level,int semester){
        try {
            MedicalTableMethod();
            DefaultTableModel tableModel = (DefaultTableModel) LECMedicalTable.getModel();

            String LoadMediQuery="SELECT att.med_id, att.tgno, att.week_no,att.course_status, med.med_reason FROM attendance att JOIN medical med ON att.med_id = med.medical_no JOIN lecture_course lc ON att.course_id = lc.course_id JOIN courses c ON att.course_id = c.course_id WHERE lc.lecno = ? AND c.level_no = ? AND c.semester_no = ?;";
            prepStatement=conn.prepareStatement(LoadMediQuery);
            prepStatement.setString(1, lecno);
            prepStatement.setInt(2, level);
            prepStatement.setInt(3, semester);
            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                String med_id = resultSet.getString("med_id");
                String tgno = resultSet.getString("tgno");
                String week_no = resultSet.getString("week_no");
                String course_status = resultSet.getString("course_status");
                String med_reason = resultSet.getString("med_reason");

                Object[]medical=new Object[5];
                medical[0]=med_id;
                medical[1]=tgno;
                medical[2]=week_no;
                medical[3]=course_status;
                medical[4]=med_reason;
                tableModel.addRow(medical);

            }

        } catch (Exception e) {
           e.printStackTrace();
        }

    }
    private void markgetcourseid(String lecno,int level,int semester){
            try{
                MarkSubCodeCombo.removeAllItems();
                String Query="SELECT c.course_id FROM courses c INNER JOIN lecture_course lc ON c.course_id = lc.course_id WHERE lc.lecno = ? AND c.level_no = ? AND c.semester_no = ?;";
                prepStatement=conn.prepareStatement(Query);
                prepStatement.setString(1, lecno);
                prepStatement.setInt(2, level);
                prepStatement.setInt(3, semester);
                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()){
                    String course_id = resultSet.getString("course_id");
                    MarkSubCodeCombo.addItem(course_id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
        private void getMarkStatus(String course_code){
                try{
                    MarkSubStatusCombo.removeAllItems();
                    String CourseStatusQuery="select status from courses where course_id=?;";
                    prepStatement=conn.prepareStatement(CourseStatusQuery);
                    prepStatement.setString(1, course_code);

                    ResultSet resultSet1 = prepStatement.executeQuery();
                    while (resultSet1.next()){
                        String status = resultSet1.getString("status");
                        MarkSubStatusCombo.addItem(status);

                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
        }

        private void loadCourseIDforGrade(String lecno,int level,int semester){
            try{
                
                GradeSubCodeCombo.removeAllItems();
                String loadCourceID="SELECT c.course_id FROM courses c INNER JOIN lecture_course lc ON c.course_id = lc.course_id WHERE lc.lecno = ? AND c.level_no = ? AND c.semester_no = ?;";
                prepStatement=conn.prepareStatement(loadCourceID);
                prepStatement.setString(1, lecno);
                prepStatement.setInt(2, level);
                prepStatement.setInt(3, semester);
                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()){
                    String course_id = resultSet.getString("course_id");
                    GradeSubCodeCombo.addItem(course_id);
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }


    private void StudentTableMethod(){
        StudentTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Student TG Number","Student FName","StudentLName","Student Address","Student Email","Phone Number"}
        ));
    }



    private void addMark(){
        try{
            String tg_no ="";

            String course_id = (String) MarkSubCodeCombo.getSelectedItem();

            String LECmarkTGno =  LECMarkTGno.getText();
            if(LECmarkTGno.isEmpty() || LECmarkTGno.equals("")){
                JOptionPane.showMessageDialog(null, "Please select a tg number");
                return;
            }

            JTextField[] markfields = {LECMarkQuiz1,LECMarkQuiz2,LECMarkQuiz3,LECMarkQuiz4,LECMarkAssess1,LECMarkAssess2,LECMarkMid,LECMarkFTheory,LECMarkFPractical};
            for (JTextField field : markfields){
             if(field.getText().isEmpty() || field.getText() == null || field.getText().equals("")){
                 field.setText("0");
                 return;

             }
             if(Integer.parseInt(field.getText()) < 0){
                 JOptionPane.showMessageDialog(null, "Mark Should greater than 0");
                 return;
             }
                if(Integer.parseInt(field.getText()) >100){
                    JOptionPane.showMessageDialog(null, "Mark Should not exceed 100");
                    return;
                }

            }

            int LECmarkQuiz1 = Integer.parseInt(LECMarkQuiz1.getText());



            int LECmarkQuiz2 = Integer.parseInt(LECMarkQuiz2.getText());
            int LECmarkQuiz3 = Integer.parseInt(LECMarkQuiz3.getText());
            int LECmarkQuiz4 = Integer.parseInt(LECMarkQuiz4.getText());
            int LECmarkAssess1 = Integer.parseInt(LECMarkAssess1.getText());
            int LECmarkAssess2 = Integer.parseInt(LECMarkAssess2.getText());
            int LECmarkMid = Integer.parseInt(LECMarkMid.getText());
            int LECmarkFTh = Integer.parseInt(LECMarkFTheory.getText());
            int LECmarkFPr = Integer.parseInt(LECMarkFPractical.getText());

            String checkQry = "select *from marks where tgno = ? and course_id = ?";
            prepStatement = conn.prepareStatement(checkQry);
            prepStatement.setString(1,LECmarkTGno);
            prepStatement.setString(2,course_id);

            ResultSet resultSet = prepStatement.executeQuery();
            if (resultSet.next()){
                tg_no = resultSet.getString("tgno");
                String insertMark = "update marks set quiz_one = ?,quiz_second = ?,quiz_third = ?,quiz_fourth = ?,assessment_one = ?,assessment_second = ?,mid_term = ?,final_theory = ?,final_practical = ? where tgno = ? and course_id = ? ";
                prepStatement = conn.prepareStatement(insertMark);

                prepStatement.setInt(1,LECmarkQuiz1);
                prepStatement.setInt(2,LECmarkQuiz2);
                prepStatement.setInt(3,LECmarkQuiz3);
                prepStatement.setInt(4,LECmarkQuiz4);
                prepStatement.setInt(5,LECmarkAssess1);
                prepStatement.setInt(6,LECmarkAssess2);
                prepStatement.setInt(7,LECmarkMid);
                prepStatement.setInt(8,LECmarkFTh);
                prepStatement.setInt(9,LECmarkFPr);
                prepStatement.setString(10,tg_no);
                prepStatement.setString(11,course_id);

                int result = prepStatement.executeUpdate();
                if(result>0){
                    JOptionPane.showMessageDialog(null,"Mark added successfully");
                }else {
                    JOptionPane.showMessageDialog(null,"Mark not added successfully");
                }
            }else {
                String insertMark = "insert into marks(tgno,course_id,quiz_one,quiz_second,quiz_third,quiz_fourth,assessment_one,assessment_second,mid_term,final_theory,final_practical) values (?,?,?,?,?,?,?,?,?,?,?)";
                prepStatement = conn.prepareStatement(insertMark);
                prepStatement.setString(1, LECmarkTGno);
                prepStatement.setString(2, course_id);
                prepStatement.setInt(3, LECmarkQuiz1);
                prepStatement.setInt(4, LECmarkQuiz2);
                prepStatement.setInt(5, LECmarkQuiz3);
                prepStatement.setInt(6, LECmarkQuiz4);
                prepStatement.setInt(7, LECmarkAssess1);
                prepStatement.setInt(8, LECmarkAssess2);
                prepStatement.setInt(9, LECmarkMid);
                prepStatement.setInt(10, LECmarkFTh);
                prepStatement.setInt(11, LECmarkFPr);
                int result = prepStatement.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Mark added successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Mark not added successfully");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
        new LecturerHomePage("lec1234");
    }

  private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}