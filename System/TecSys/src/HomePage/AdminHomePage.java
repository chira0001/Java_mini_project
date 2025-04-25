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
    private JPanel AdminProfImgSelectedPanel;
    private JPanel AdminTimeTable;
    private JTable tableTimeTable;
    private JComboBox SemesterNoDropDown;
    private JComboBox LevelNoDropDown;
    private JPanel AdminCourses;
    private JComboBox LevelNoforCourses;
    private JComboBox SemesterNoforCourses;
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
    private JTabbedPane tabbedPane1;
    private JComboBox comboBox1;
    private JTable AdminViewUserTable;
    private JComboBox comboBox2;
    private JTextField AD_Phno;
    private JTextField AD_Email;
    private JTextField AD_Address;
    private JTextField AD_Lname;
    private JTextField AD_Fname;
    private JPanel ADProfImgPanel;
    private JLabel ADProfImgLabel;
    private JTabbedPane tabbedPane2;
    private JButton UndergraduateAddUserBtn;
    private JTextField UserUndergraduateFname;
    private JTextField UserUndergraduateLname;
    private JTextField UserUndergraduateAddress;
    private JTextField UserUndergraduateEmail;
    private JTextField UserUndergraduatePhNo;
    private JComboBox comboBox3;
    private JComboBox UserUndergraduateSemesterNumber;
    private JButton UserUndergraduateProfImgBtn;
    private JButton AdminAddUserBtn;
    private JButton LecturerAddUserBtn;
    private JButton TechnicalOfficerAddUserBtn;
    private JTextField UserAdminFname;
    private JTextField UserAdminLname;
    private JTextField UserAdminAddress;
    private JTextField UserAdminEmail;
    private JTextField UserAdminPhNo;
    private JButton UserAdminProfImgBtn;
    private JTextField ADAddFname;
    private JTextField ADAddLname;
    private JTextField ADAddAddress;
    private JTextField ADAddEmail;
    private JTextField ADAddPhno;
    private JButton ADAddUserBtn;
    private JComboBox UserUndergraduateLevelNumber;
    private JTextField UserLecturerFname;
    private JTextField UserLecturerLname;
    private JTextField UserLecturerAddress;
    private JTextField UserLecturerEmail;
    private JTextField UserLecturerPhNo;
    private JTextField UserTechnicalOfficerFname;
    private JTextField UserTechnicalOfficerLname;
    private JTextField UserTechnicalOfficerAddress;
    private JTextField UserTechnicalOfficerEmail;
    private JTextField UserTechnicalOfficerPhNo;
    private JButton UserTechnicalOfficerProfImgBtn;
    private JLabel TONumberLabel;
    private JLabel LECNumberLabel;
    private JLabel TGNumberLabel;
    private JLabel ADNumberLabel;
    private JButton ADaddLecCourseButton;
    private JComboBox CourseIDforADLecturer;
    private JList SelectedCourseList;
    private JButton removeCourseButton;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JComboBox comboBox6;
    private JButton deleteUserButton;
    private JTextField adminfname;
    private JTextField adminlname;
    private JTextField adminaddress;
    private JTextField adminemail;
    private JTextField adminphno;
    private JTextField undergradfname;
    private JTextField undergradlname;
    private JTextField undergradaddress;
    private JTextField undergrademail;
    private JTextField undergradphno;
    private JComboBox uglevel;
    private JComboBox ugsem;
    private JTextField lfname;
    private JTextField llname;
    private JTextField laddress;
    private JTextField lemail;
    private JTextField lphno;
    private JList lcourses;
    private JComboBox l_Acourses;
    private JButton upLecCourses;
    private JButton removeupdatecoursesbtn;
    private JTextField tfname;
    private JTextField tlname;
    private JTextField taddress;
    private JTextField temail;
    private JTextField tphno;
    private JButton aUpdateBtn;
    private JButton uUpdateBtn;
    private JButton lUpdateBtn;
    private JButton tUpdateBtn;
    private JTable ADDeleteTable;
    private JComboBox ADDeleteUserType;
    private JComboBox ADDeleteUserID;
    private JComboBox comboBox7;
    private JComboBox comboBox8;
    private JTextField textField3;
    private JButton addButton;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox comboBox9;
    private JTextField textField6;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JTextField textField13;
    private JTextField textField14;
    private JButton addNoticeButton;
    private JLabel AdminProfImgSelectedLabel;
    private JPanel UndergraduateProfImgSelectedPanel;
    private JLabel UndergraduateProfImgSelectedLabel;
    private JPanel Admin;


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

    DefaultListModel<String> courseList = new DefaultListModel<>();
    DefaultListModel<String> courseList1 = new DefaultListModel<>();
//    DefaultListModel<String> courseList3 = new DefaultListModel<>();

    private String DefualtImage = "Resources/ProfileImages/DefaultUser.png";

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

        loadCoursesForAddLecturer();
        loadADnoForUpdateAdmin();
        loadTGnoForUpdateUndergraduate();
        loadLECnoForUpdateLecturer();
        loadTOnoForUpdateTechOfficer();
        loadCredentialsTO();
        ADDeleteUserTableSetModelMethod();
        loadCredentialsLecturer();
        loadCredentialsUndergraduate();
        loadCredentialsAdmin();
        ADViewUserTableSetModelMethod();

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

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ADViewUserTableSetModelMethod();

                String userType = (String) comboBox1.getSelectedItem();
                ADUserFilter(userType);
                FillViewUserTable(userType);
            }
        });
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userType = (String) comboBox1.getSelectedItem();
                String user_id = (String) comboBox2.getSelectedItem();
                FillAdViewUser(user_id,userType);
//                FillAdViewUser();

            }
        });

        AdminAddUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddAdminUser();
            }
        });
        UndergraduateAddUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddUndergraduateUser();
            }
        });

        TechnicalOfficerAddUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddTechnicalOfficerUser();

                UserTechnicalOfficerFname.setText("");
                UserTechnicalOfficerLname.setText("");
                UserTechnicalOfficerAddress.setText("");
                UserTechnicalOfficerEmail.setText("");
                UserTechnicalOfficerPhNo.setText("");
            }
        });


        ADaddLecCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) CourseIDforADLecturer.getSelectedItem();
                courseList.addElement(selectedCourse);

                SelectedCourseList.setModel(courseList);
            }
        });
        LecturerAddUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddLecturerUser();

                UserLecturerFname.setText("");
                UserLecturerLname.setText("");
                UserLecturerAddress.setText("");
                UserLecturerEmail.setText("");
                UserLecturerPhNo.setText("");
            }
        });
        removeCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int courseListLength = courseList.getSize();
                if(courseListLength != 0){
                    courseList.removeElementAt(courseListLength - 1);
                    courseListLength--;
                }
            }
        });

        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCredentialsAdmin();
            }
        });

        comboBox4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCredentialsUndergraduate();
            }
        });

        comboBox5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int courseListLength = courseList1.getSize();
                while (courseListLength > 0){
                    courseList1.removeElementAt(courseListLength - 1);
                    courseListLength--;
                }

                loadCredentialsLecturer();
            }
        });
        upLecCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) l_Acourses.getSelectedItem();
                courseList1.addElement(selectedCourse);

                lcourses.setModel(courseList1);
            }
        });
        removeupdatecoursesbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int courseListLength = courseList1.getSize();
                if(courseListLength != 0){
                    courseList1.removeElementAt(courseListLength - 1);
                    courseListLength--;
                }
            }
        });

        comboBox6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCredentialsTO();
            }
        });
        aUpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCredentialsAdmin();
            }
        });
        uUpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCredentialsundergraduate();
            }
        });
        tUpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCredentialsTO();
            }
        });
        lUpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCredentialsLEC();
            }
        });


        ADDeleteUserType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ADDeleteUserTableSetModelMethod();
                String deleteUserType = (String) ADDeleteUserType.getSelectedItem();

                ADDeleteUserFilter(deleteUserType);
                FillDeleteViewUserTable(deleteUserType);
            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String deleteUserType = (String) ADDeleteUserType.getSelectedItem();
               String deleteUserId = (String) ADDeleteUserID.getSelectedItem();

                deleteUser(deleteUserType,deleteUserId);

                loadCredentialsAdmin();
                loadCredentialsTO();
                loadCredentialsUndergraduate();
                loadCredentialsLecturer();
            }
        });
    }

    private void deleteUser(String DeleteuserType, String DeleteuserId){

        System.out.println("Type = " + DeleteuserType + " ID = " + DeleteuserId);

        String deleteQuery = "";
        try{
            if(DeleteuserType.equals("Admin")){
                deleteQuery = "delete from admin where adno = ?";
            } else if (DeleteuserType.equals("Undergraduate")) {
                deleteQuery = "delete from undergraduate where tgno = ?";
            } else if (DeleteuserType.equals("Lecturer")) {
                deleteQuery = "delete from lecturer where lecno = ?";
            } else if (DeleteuserType.equals("Technical Officer")) {
                deleteQuery = "delete from technical_officer where tono = ?";
            }

            prepStatement = conn.prepareStatement(deleteQuery);
            prepStatement.setString(1,DeleteuserId);

            int resultDelete = prepStatement.executeUpdate();
            if(resultDelete > 0){
                JOptionPane.showMessageDialog(null,"User Deleted Successfully");
            }else {
                JOptionPane.showMessageDialog(null,"User Delete failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCredentialsLEC(){
        String l_num = (String) comboBox5.getSelectedItem();
        try{
            String updateQuery = "update lecturer set lecfname = ?, leclname = ?, lecaddress = ?, lecemail = ?, lecphno = ? where lecno = ?";
            prepStatement = conn.prepareStatement(updateQuery);

            prepStatement.setString(1,lfname.getText());
            prepStatement.setString(2,llname.getText());
            prepStatement.setString(3,laddress.getText());
            prepStatement.setString(4,lemail.getText());
            prepStatement.setString(5,lphno.getText());
            prepStatement.setString(6,l_num);

            int result = prepStatement.executeUpdate();
            if(result > 0){
                String deleteQuery = "delete from lecture_course where lecno = ?";
                prepStatement = conn.prepareStatement(deleteQuery);
                prepStatement.setString(1,l_num);
                int deleteResult = prepStatement.executeUpdate();
                if(deleteResult > 0){
                    int i;
                    int courseListLength = courseList1.getSize();

                    String addQueryLectureCourse = "INSERT INTO lecture_course (lecno,course_id) values(?,?)";
                    prepStatement = conn.prepareStatement(addQueryLectureCourse);

                    for (i = 0; i < courseListLength; i++){
                        prepStatement.setString(1,l_num);
                        prepStatement.setString(2,courseList1.getElementAt(i));

                        prepStatement.executeUpdate();
                    }
                    JOptionPane.showMessageDialog(null,"Technical Officer user updated successfully");
                }
            }else {
                JOptionPane.showMessageDialog(null,"Technical Officer user update failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCredentialsTO(){
        String t_num = (String) comboBox6.getSelectedItem();
        try{
            String updateQuery = "update technical_officer set tofname = ?, tolname = ?, toaddress = ?, toemail = ?, tophno = ? where tono = ?";
            prepStatement = conn.prepareStatement(updateQuery);

            prepStatement.setString(1,tfname.getText());
            prepStatement.setString(2,tlname.getText());
            prepStatement.setString(3,taddress.getText());
            prepStatement.setString(4,temail.getText());
            prepStatement.setString(5,tphno.getText());
            prepStatement.setString(6,t_num);

            int result = prepStatement.executeUpdate();
            if(result > 0){
                JOptionPane.showMessageDialog(null,"Technical Officer user updated successfully");
            }else {
                JOptionPane.showMessageDialog(null,"Technical Officer user update failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateCredentialsundergraduate(){
        String tg_num = (String) comboBox4.getSelectedItem();
        try{
            String updateQuery = "update undergraduate set ugfname = ?, uglname = ?, ugaddress = ?, ugemail = ?, ugphno = ?, study_year = ?, study_semester = ? where tgno = ?";
            prepStatement = conn.prepareStatement(updateQuery);

            prepStatement.setString(1,undergradfname.getText());
            prepStatement.setString(2,undergradlname.getText());
            prepStatement.setString(3,undergradaddress.getText());
            prepStatement.setString(4,undergrademail.getText());
            prepStatement.setString(5,undergradphno.getText());

            String lNumstr = (String) uglevel.getSelectedItem();
            String sNumstr = (String) ugsem.getSelectedItem();

            assert lNumstr != null;
            prepStatement.setInt(6,Integer.parseInt(lNumstr));
            assert sNumstr != null;
            prepStatement.setInt(7,Integer.parseInt(sNumstr));

            prepStatement.setString(8,tg_num);

            int result = prepStatement.executeUpdate();
            if(result > 0){
                JOptionPane.showMessageDialog(null,"Undergraduate user updated successfully");
            }else {
                JOptionPane.showMessageDialog(null,"Undergraduate user update failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateCredentialsAdmin(){
        String a_num = (String) comboBox3.getSelectedItem();
        try{
            String updateQuery = "update admin set adfname = ?, adlname = ?, adaddress = ?, ademail = ?, adphno = ? where adno = ?";
            prepStatement = conn.prepareStatement(updateQuery);

            prepStatement.setString(1,adminfname.getText());
            prepStatement.setString(2,adminlname.getText());
            prepStatement.setString(3,adminaddress.getText());
            prepStatement.setString(4,adminemail.getText());
            prepStatement.setString(5,adminphno.getText());
            prepStatement.setString(6,a_num);

            int result = prepStatement.executeUpdate();
            if(result > 0){
                JOptionPane.showMessageDialog(null,"Admin user updated successfully");
            }else {
                JOptionPane.showMessageDialog(null,"Admin user update failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCredentialsAdmin(){
        try{
            String admin_id = (String) comboBox3.getSelectedItem();

            String getInfo = "select * from admin where adno = ?";
            prepStatement = conn.prepareStatement(getInfo);
            prepStatement.setString(1,admin_id);

            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                adminfname.setText(resultSet.getString("adfname"));
                adminlname.setText(resultSet.getString("adlname"));
                adminaddress.setText(resultSet.getString("adaddress"));
                adminemail.setText(resultSet.getString("ademail"));
                adminphno.setText(resultSet.getString("adphno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadCredentialsUndergraduate(){
        try{
            String underg_id = (String) comboBox4.getSelectedItem();

            String getInfo = "select * from undergraduate where tgno = ?";
            prepStatement = conn.prepareStatement(getInfo);
            prepStatement.setString(1,underg_id);

            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                undergradfname.setText(resultSet.getString("ugfname"));
                undergradlname.setText(resultSet.getString("uglname"));
                undergradaddress.setText(resultSet.getString("ugaddress"));
                undergrademail.setText(resultSet.getString("ugemail"));
                undergradphno.setText(resultSet.getString("ugphno"));
                uglevel.setSelectedItem(resultSet.getString("study_year"));
                ugsem.setSelectedItem(resultSet.getString("study_semester"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCredentialsLecturer(){
        try{
            String l_id = (String) comboBox5.getSelectedItem();

            String getInfo = "select * from lecturer where lecno = ?";
            prepStatement = conn.prepareStatement(getInfo);
            prepStatement.setString(1,l_id);

            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                lfname.setText(resultSet.getString("lecfname"));
                llname.setText(resultSet.getString("leclname"));
                laddress.setText(resultSet.getString("lecaddress"));
                lemail.setText(resultSet.getString("lecemail"));
                lphno.setText(resultSet.getString("lecphno"));
            }

            String getCourses = "select course_id from courses";

            l_Acourses.removeAllItems();
            prepStatement = conn.prepareStatement(getCourses);

            ResultSet resultSet1 = prepStatement.executeQuery();
            while (resultSet1.next()){
                String Cu = resultSet1.getString("course_id");
                l_Acourses.addItem(Cu);
            }
            SelectedCourseList.setModel(courseList);


            String getInfoCourse = "select * from lecture_course where lecno = ?";

            prepStatement = conn.prepareStatement(getInfoCourse);
            prepStatement.setString(1,l_id);

            ResultSet resultSet2 = prepStatement.executeQuery();
            while (resultSet2.next()){
                String selectedCourse = resultSet2.getString("course_id");
                courseList1.addElement(selectedCourse);
            }
            lcourses.setModel(courseList1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   private void loadCredentialsTO(){
       try{
            String t_id = (String) comboBox6.getSelectedItem();

            String getInfo = "select * from technical_officer where tono = ?";
            prepStatement = conn.prepareStatement(getInfo);
            prepStatement.setString(1,t_id);

            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                tfname.setText(resultSet.getString("tofname"));
                tlname.setText(resultSet.getString("tolname"));
                taddress.setText(resultSet.getString("toaddress"));
                temail.setText(resultSet.getString("toemail"));
                tphno.setText(resultSet.getString("tophno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
   }

    private void AddLecturerUser(){
        String lec_no = "";
        try{
            String Fname = UserLecturerFname.getText();
            String Lname = UserLecturerLname.getText();
            String Address = UserLecturerAddress.getText();
            String Email = UserLecturerEmail.getText();
            String Phno = UserLecturerPhNo.getText();

            String addQuery = "INSERT INTO lecturer (lecfname,leclname,lecaddress,lecemail,lecphno,lecProfImg) VALUES (?,?,?,?,?,?)";

            prepStatement = conn.prepareStatement(addQuery);
            prepStatement.setString(1,Fname);
            prepStatement.setString(2,Lname);
            prepStatement.setString(3,Address);
            prepStatement.setString(4,Email);
            prepStatement.setString(5,Phno);
            prepStatement.setString(6,DefualtImage);

            int Result = prepStatement.executeUpdate();
            if(Result > 0){

                String getLecId = "select lecno from lecturer where lecfname = ? and leclname = ? and lecemail = ?";

                prepStatement = conn.prepareStatement(getLecId);
                prepStatement.setString(1,Fname);
                prepStatement.setString(2,Lname);
                prepStatement.setString(3,Email);

                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()){
                    int i;
                    int courseListLength = courseList.getSize();
                    lec_no = resultSet.getString("lecno");
                    LECNumberLabel.setText(lec_no);

                    String addQueryLectureCourse = "INSERT INTO lecture_course (lecno,course_id) values(?,?)";
                    prepStatement = conn.prepareStatement(addQueryLectureCourse);

                    for (i = 0; i < courseListLength; i++){
                        prepStatement.setString(1,lec_no);
                        prepStatement.setString(2,courseList.getElementAt(i));

                        prepStatement.executeUpdate();
                    }
                }
                JOptionPane.showMessageDialog(null,"Lecturer User Successfully added");
            }else {
                JOptionPane.showMessageDialog(null,"Lecturer User Entry Failed");
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }
    }

    private void loadCoursesForAddLecturer(){
        try{
            String getQuery = "select distinct(course_id) from courses";
            prepStatement = conn.prepareStatement(getQuery);
            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                String course_id = resultSet.getString("course_id");
                CourseIDforADLecturer.addItem(course_id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadADnoForUpdateAdmin(){
        try{
            String getQuery = "select distinct(adno) from admin";
            prepStatement = conn.prepareStatement(getQuery);
            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                String ad_id = resultSet.getString("adno");
                comboBox3.addItem(ad_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadTGnoForUpdateUndergraduate(){
        try{
            String getQuery = "select distinct(tgno) from undergraduate";
            prepStatement = conn.prepareStatement(getQuery);
            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                String tg_id = resultSet.getString("tgno");
                comboBox4.addItem(tg_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadLECnoForUpdateLecturer(){
        try{
            String getQuery = "select distinct(lecno) from lecturer";
            prepStatement = conn.prepareStatement(getQuery);
            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                String l_id = resultSet.getString("lecno");
                comboBox5.addItem(l_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadTOnoForUpdateTechOfficer(){
        try{
            String getQuery = "select distinct(tono) from technical_officer";
            prepStatement = conn.prepareStatement(getQuery);
            ResultSet resultSet = prepStatement.executeQuery();
            while (resultSet.next()){
                String t_id = resultSet.getString("tono");
                comboBox6.addItem(t_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AddTechnicalOfficerUser(){
        String to_no;
        try{
            String Fname = UserTechnicalOfficerFname.getText();
            String Lname = UserTechnicalOfficerLname.getText();
            String Address = UserTechnicalOfficerAddress.getText();
            String Email = UserTechnicalOfficerEmail.getText();
            String Phno = UserTechnicalOfficerPhNo.getText();

            String addQuery = "INSERT INTO technical_officer (tofname,tolname,toaddress,toemail,tophno,toProfImg) VALUES (?,?,?,?,?,?)";

            prepStatement = conn.prepareStatement(addQuery);
            prepStatement.setString(1,Fname);
            prepStatement.setString(2,Lname);
            prepStatement.setString(3,Address);
            prepStatement.setString(4,Email);
            prepStatement.setString(5,Phno);
            prepStatement.setString(6,DefualtImage);

            int Result = prepStatement.executeUpdate();
            if(Result > 0){

                String getToId = "select tono from technical_officer where tofname = ? and tolname = ? and toemail = ?";

                prepStatement = conn.prepareStatement(getToId);
                prepStatement.setString(1,Fname);
                prepStatement.setString(2,Lname);
                prepStatement.setString(3,Email);

                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()){
                    to_no = resultSet.getString("tono");

                    JOptionPane.showMessageDialog(null,"Technical Officer User Successfully added");
                    TONumberLabel.setText(to_no);
                }
            }else {
                JOptionPane.showMessageDialog(null,"Technical Officer User Entry Failed");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void AddUndergraduateUser(){
        String tg_no;
        try{
            String Fname = UserUndergraduateFname.getText();
            String Lname = UserUndergraduateLname.getText();
            String Address = UserUndergraduateAddress.getText();
            String Email = UserUndergraduateEmail.getText();
            String Phno = UserUndergraduatePhNo.getText();

            int Undergraduate_LevelNo = Integer.parseInt((String) UserUndergraduateLevelNumber.getSelectedItem());
            int Undergraduate_SemesterNo = Integer.parseInt((String) UserUndergraduateSemesterNumber.getSelectedItem());

            String addQuery = "INSERT INTO undergraduate (ugfname,uglname,ugaddress,ugemail,ugphno,ugProfImg,study_year,study_semester) VALUES (?,?,?,?,?,?,?,?)";

            prepStatement = conn.prepareStatement(addQuery);
            prepStatement.setString(1,Fname);
            prepStatement.setString(2,Lname);
            prepStatement.setString(3,Address);
            prepStatement.setString(4,Email);
            prepStatement.setString(5,Phno);
            prepStatement.setString(6,DefualtImage);
            prepStatement.setInt(7,Undergraduate_LevelNo);
            prepStatement.setInt(8,Undergraduate_SemesterNo);

            int Result = prepStatement.executeUpdate();
            if(Result > 0){

                String getAdminId = "select tgno from undergraduate where ugfname = ? and uglname = ? and ugemail = ?";

                prepStatement = conn.prepareStatement(getAdminId);
                prepStatement.setString(1,Fname);
                prepStatement.setString(2,Lname);
                prepStatement.setString(3,Email);

                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()){
                    tg_no = resultSet.getString("tgno");

                    JOptionPane.showMessageDialog(null,"Undergraduate User Successfully added");
                    TGNumberLabel.setText(tg_no);
                }
            }else {
                JOptionPane.showMessageDialog(null,"Undergraduate User Entry Failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void AddAdminUser(){
        String admin_no;
        try{
            String Fname = UserAdminFname.getText();
            String Lname = UserAdminLname.getText();
            String Address = UserAdminAddress.getText();
            String Email = UserAdminEmail.getText();
            String Phno = UserAdminPhNo.getText();

            String addQuery = "INSERT INTO admin (adfname,adlname,adaddress,ademail,adphno,adProfImg) VALUES (?,?,?,?,?,?)";

            prepStatement = conn.prepareStatement(addQuery);
            prepStatement.setString(1,Fname);
            prepStatement.setString(2,Lname);
            prepStatement.setString(3,Address);
            prepStatement.setString(4,Email);
            prepStatement.setString(5,Phno);
            prepStatement.setString(6,DefualtImage);

            int Result = prepStatement.executeUpdate();
            if(Result > 0){

                String getAdminId = "select adno from admin where adfname = ? and adlname = ? and ademail = ?";

                prepStatement = conn.prepareStatement(getAdminId);
                prepStatement.setString(1,Fname);
                prepStatement.setString(2,Lname);
                prepStatement.setString(3,Email);

                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()){
                    admin_no = resultSet.getString("adno");

                    JOptionPane.showMessageDialog(null,"Admin User Successfully added");
                    ADNumberLabel.setText(admin_no);
                }
            }else {
                JOptionPane.showMessageDialog(null,"Admin User Entry Failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void FillDeleteViewUserTable(String userType){
        DefaultTableModel tblmodel = (DefaultTableModel) ADDeleteTable.getModel();

        try{
            String userTableFillQuery = "";
            if(userType.equals("Undergraduate")){
                userTableFillQuery = "select tgno as user_id, ugfname as fname, uglname as lname, ugphno as phno from undergraduate";
            }
            else if(userType.equals("Lecturer")){
                userTableFillQuery = "select lecno as user_id, lecfname as fname, leclname as lname, lecphno as phno from lecturer";
            }
            else if(userType.equals("Technical Officer")){
                userTableFillQuery = "select tono as user_id, tofname as fname, tolname as lname, tophno as phno from technical_officer";
            }
            else if(userType.equals("Admin")){
                userTableFillQuery = "select adno as user_id, adfname as fname, adlname as lname, adphno as phno from admin";
            }

            prepStatement = conn.prepareStatement(userTableFillQuery);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                String user_id = resultSet.getString("user_id");
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");
                String phno = resultSet.getString("phno");

                Object[] user_det = new Object[4];

                user_det[0] = user_id;
                user_det[1] = fname;
                user_det[2] = lname;
                user_det[3] = phno;

                tblmodel.addRow(user_det);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void FillViewUserTable(String userType){

        DefaultTableModel tblmodel = (DefaultTableModel) AdminViewUserTable.getModel();

        try{
            String userTableFillQuery = "";
            if(userType.equals("Undergraduate")){
                userTableFillQuery = "select tgno as user_id, ugfname as fname, uglname as lname, ugphno as phno from undergraduate";
            }
            else if(userType.equals("Lecturer")){
                userTableFillQuery = "select lecno as user_id, lecfname as fname, leclname as lname, lecphno as phno from lecturer";
            }
            else if(userType.equals("Technical Officer")){
                userTableFillQuery = "select tono as user_id, tofname as fname, tolname as lname, tophno as phno from technical_officer";
            }
            else if(userType.equals("Admin")){
                userTableFillQuery = "select adno as user_id, adfname as fname, adlname as lname, adphno as phno from admin";
            }

            prepStatement = conn.prepareStatement(userTableFillQuery);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                String user_id = resultSet.getString("user_id");
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");
                String phno = resultSet.getString("phno");

                Object[] user_det = new Object[4];

                user_det[0] = user_id;
                user_det[1] = fname;
                user_det[2] = lname;
                user_det[3] = phno;

                tblmodel.addRow(user_det);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ADDeleteUserTableSetModelMethod(){
        ADDeleteTable.setModel(new DefaultTableModel(
                null,
                new String[]{"User ID", "First Name", "Last Name", "Phone Number"}
        ));
    }

    private void ADViewUserTableSetModelMethod(){
        AdminViewUserTable.setModel(new DefaultTableModel(
                null,
                new String[]{"User ID", "First Name", "Last Name", "Phone Number"}
        ));
    }

    private void FillAdViewUser(String userNo, String userType){
//        System.out.println("User No - " + userNo + ", userType - " + userType);
        try{
            String userFillQuery = "";
            if(userType.equals("Undergraduate")){
                userFillQuery = "select ugfname as fname, uglname as lname, ugaddress as address, ugemail as email, ugphno as phno, ugprofimg as profimg from undergraduate where tgno = ?";
            }
            else if(userType.equals("Lecturer")){
                userFillQuery = "select lecfname as fname, leclname as lname, lecaddress as address, lecemail as email, lecphno as phno, lecprofimg as profimg from lecturer where lecno = ?";
            }
            else if(userType.equals("Technical Officer")){
                userFillQuery = "select tofname as fname, tolname as lname, toaddress as address, toemail as email, tophno as phno, toprofimg as profimg from technical_officer where tono = ?";
            }
            else if(userType.equals("Admin")){
                userFillQuery = "select adfname as fname, adlname as lname, adaddress as address, ademail as email, adphno as phno, adProfImg as profimg from admin where adno = ?";
            }

            prepStatement = conn.prepareStatement(userFillQuery);
            prepStatement.setString(1,userNo);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String phno = resultSet.getString("phno");
                Path SaveImagePath = Path.of(resultSet.getString("profimg"));

                ImageIcon icon = new ImageIcon(SaveImagePath.toString());
                Image scaled = icon.getImage().getScaledInstance(
                        ADProfImgPanel.getWidth() - 50,
                        ADProfImgPanel.getHeight() - 50,
                        Image.SCALE_SMOOTH
                );

                AD_Fname.setText(fname);
                AD_Lname.setText(lname);
                AD_Address.setText(address);
                AD_Email.setText(email);
                AD_Phno.setText(phno);
                ADProfImgLabel.setIcon(new ImageIcon(scaled));
                ADProfImgLabel.setText("");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ADDeleteUserFilter(String userType){
        try{
            String usersQuery = "";

            if(userType.equals("Undergraduate")){
                usersQuery = "select tgno as userNo from undergraduate";
            }
            else if(userType.equals("Lecturer")){
                usersQuery = "select lecno as userNo from Lecturer";
            }
            else if(userType.equals("Technical Officer")){
                usersQuery = "select tono as userNo from technical_officer";
            }
            else if(userType.equals("Admin")){
                usersQuery = "select adno as userNo from admin";
            }

            ADDeleteUserID.removeAllItems();
            ADDeleteUserID.addItem("");

            prepStatement = conn.prepareStatement(usersQuery);
            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                String user = resultSet.getString("userNo");
                ADDeleteUserID.addItem(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ADUserFilter(String userType){

        try{
            String usersQuery = "";

            if(userType.equals("Undergraduate")){
                usersQuery = "select tgno as userNo from undergraduate";
            }
            else if(userType.equals("Lecturer")){
                usersQuery = "select lecno as userNo from Lecturer";
            }
            else if(userType.equals("Technical Officer")){
                usersQuery = "select tono as userNo from technical_officer";
            }
            else if(userType.equals("Admin")){
                usersQuery = "select adno as userNo from admin";
            }

            comboBox2.removeAllItems();
//            comboBox2.addItem("");

            prepStatement = conn.prepareStatement(usersQuery);
            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                String user = resultSet.getString("userNo");
                comboBox2.addItem(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
                JOptionPane.showMessageDialog(null,"No Records found - Internal Error");
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

    public static void main(String[] args) {
        new AdminHomePage("ad0002");
    }
}
