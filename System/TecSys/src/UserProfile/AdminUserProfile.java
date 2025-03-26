package UserProfile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserProfile extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton updateButton;
    private JButton changePasswordButton;
    private JButton homeButton;
    private JPanel AdminUserProfile;

    public static void main(String[] args){
        new AdminUserProfile();
    }

    public AdminUserProfile(){
        setContentPane(AdminUserProfile);
        setTitle("Admin User Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
