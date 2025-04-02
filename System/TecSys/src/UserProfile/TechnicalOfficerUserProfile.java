package UserProfile;

import javax.swing.*;

public class TechnicalOfficerUserProfile extends JFrame {
    private JPanel TechnicalOfficerUserProfile;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox comboBox1;
    private JButton updateButton;
    private JButton changePasswordButton;
    private JButton homeButton;



    public TechnicalOfficerUserProfile(){
        setContentPane(TechnicalOfficerUserProfile);
        setTitle("Admin User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(623, 257);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
