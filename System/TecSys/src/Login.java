import HomePage.UndergraduateHomePage;
import UserProfile.AdminUserProfile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class Login extends JFrame {
    private JPanel Login;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton registerButton;
    private JButton loginButton;
    private JButton cancelButton;
    private JTextField textField2;

    public Login() {
        new DBConnection();

        setContentPane(Login);
        setTitle("Login Form");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(this);
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

                String dbUname,dbPassword;
                String uname = null, pass = null;
                String user_identity;

                uname = textField1.getText();
                pass = passwordField1.getText();

                try{
                    char[] arr = uname.toCharArray();

                    if(arr.length > 0){
                        user_identity = (arr[0] + "" + arr[1]);

                        String loginQuery = "select * from users where username ='" + uname + "'";

                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaGUI","root","1234");
                        Statement statement = connection.createStatement();
                        ResultSet result = statement.executeQuery(loginQuery);

                        if (result.next()){
                            dbUname = result.getString("username");
                            dbPassword = result.getString("password");

                            if(Objects.equals(uname, dbUname) && Objects.equals(pass, dbPassword)){
                                switch (user_identity) {
                                    case "tg" :
                                        System.out.println("Undergraduate");
                                        new UndergraduateHomePage();
                                        dispose();
                                        break;
                                    case "le" :
                                        System.out.println("Lecturer");
                                        dispose();
                                        break;
                                    case "to" :
                                        System.out.println("Technical Officer");
                                        dispose();
                                        break;
                                    case "ad" :
                                        System.out.println("Admin");
                                        new AdminUserProfile();
                                        dispose();
                                        break;
                                }
                            } else {
                                JOptionPane.showMessageDialog(null,"Incorrect user credentials");
                            }
                        }else {
                            JOptionPane.showMessageDialog(null,"User profile not found");
                        }
                    }else {
                        JOptionPane.showMessageDialog(null,"Please enter user credentials");
                    }
                }catch (Exception excep){
                    excep.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new Login();
    }
}
