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
    private JComboBox CourseforCourses;
    private JTextArea DescriptionforCourseMaterial;
    private JComboBox CourseMaterialforCourses;
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
//        uploadImageButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ADMINUploadToPreviewProfileImage(userIdentity);
//            }
//        });
        ADViewUserTableSetModelMethod();

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

                System.out.println(user_id);

                FillAdViewUser(user_id,userType);
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
            }
        });

        DefaultListModel<String> courseList = new DefaultListModel<>();
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
                String lec_no;
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
                            TGNumberLabel.setText(lec_no);

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
        });
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

    private void ADViewUserTableSetModelMethod(){
        AdminViewUserTable.setModel(new DefaultTableModel(
                null,
                new String[]{"User ID", "First Name", "Last Name", "Phone Number"}
        ));
    }

    private void FillAdViewUser(String userNo, String userType){
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

            comboBox2.removeAllItems();
            comboBox2.addItem("");

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

                System.out.println(ADMINDestinationFile);

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
            System.out.println(ADMINProfileImagePath);
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
        new AdminHomePage("ad0001");
    }
}
