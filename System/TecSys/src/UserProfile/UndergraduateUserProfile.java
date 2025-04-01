package UserProfile;

import javax.swing.*;

public class UndergraduateUserProfile extends JFrame{
    private JPanel UndergraduateUserProfile;
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

    public UndergraduateUserProfile(){
        setContentPane(UndergraduateUserProfile);
        setTitle("Undergraduate User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(472, 296);
        setLocationRelativeTo(this);
        setVisible(true);
    }
}
