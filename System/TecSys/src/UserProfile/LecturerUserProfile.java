package UserProfile;

import javax.swing.*;

public class LecturerUserProfile extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JComboBox comboBox1;
    private JButton updateButton;
    private JButton changePasswordButton;
    private JButton homeButton;
    private JPanel LecturerUserProfile;

    public LecturerUserProfile(){
        setContentPane(LecturerUserProfile);
        setTitle("Admin User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(476, 296);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
