package HomePage;
import Login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
    private JList list1;
    private JList list2;
    private JButton addFilesButton;
    private JButton renameFilesButton;
    private JButton removeFilesButton;

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

    public LecturerHomePage(String userIdentity){
        dbConnection(userIdentity);

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
    }

    public void changeBtnState(String btn, String tgno){

        int noOfButtons = cardButtons.length;
        for (int i = 0; i < noOfButtons; i++){
            if (cardButtons[i].equals(btn)){
                dbConnection(tgno);
                cardLayout.show(LECHomeCard,cardNames[i]);
                CardTittleLabel.setText(cardTitles[i]);
                btnFieldNames[i].setEnabled(false);
            }else {
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

            }else{
                JOptionPane.showMessageDialog(null,"Internal Error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void LECUpdateCredentials(String lecno){
        try{
            String LecFname=textField1.getText();
            String LecLname=textField2.getText();
            String Lecaddress = textField7.getText();
            String Lecemail = textField8.getText();
            String Lecphno = textField9.getText();

            String LECCredentialupdateQuery = "Update lecturer set lecfname = '"+LecFname + "',leclname = '" + LecLname +"',lecaddress = '" + Lecaddress + "', lecemail = '"+ Lecemail +"',lecphno = '"+ Lecphno+"' where lecno = '" + lecno + "'";

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javatest","root","1234");
            Statement statement = connection.createStatement();
            int resultSet = statement.executeUpdate(LECCredentialupdateQuery);

            if(resultSet > 0){
                JOptionPane.showMessageDialog(null,"Credentials updated successfully");
            }else {
                JOptionPane.showMessageDialog(null,"Error in credential updation");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    private void UGFillUpdateFields(String tgno){
//        String UpdateFillQuery = "select ";
//    }
}
