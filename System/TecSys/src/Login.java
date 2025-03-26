import UserProfile.AdminUserProfile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Login extends JFrame {
    private JPanel Login;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton cancelButton;
    private JTextField textField2;

    public static void main(String[] args) {
        new Login();
    }
    public Login() {

        setContentPane(Login);
        setTitle("Login Form");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,200);
        setLocationRelativeTo(null);
        setVisible(true);



        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String uname = textField1.getText();
                String pass = textField2.getText();

                if(Objects.equals(uname, "admin") && Objects.equals(pass, "123")){
                    new AdminUserProfile();
                    Login.setVisible(false);
                }
            }
        });
    }
}
