package Login;

import DBCONNECTION.DBCONNECTION;
import HomePage.AdminHomePage;
import HomePage.LecturerHomePage;
import HomePage.TechnicalOfficerHomePage;
import HomePage.UndergraduateHomePage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

public class Login extends JFrame {
    private JPanel Login;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton cancelButton;
    private JTextField textField2;

    DBCONNECTION _dbconn = new DBCONNECTION();
    Connection conn = _dbconn.Conn();
    private PreparedStatement prepStatement;

    public Login() {
        DBConnection();

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

                if(uname.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Please enter username");
                    return;
                }
                if(pass.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Please enter password");
                    return;
                }

                try{
                    char[] arr = uname.toCharArray();

                    if(arr.length > 0){
                        user_identity = (arr[0] + "" + arr[1]);

                        String loginQuery = "select * from users where id ='" + uname + "'";

                        Statement statement = conn.createStatement();
                        ResultSet result = statement.executeQuery(loginQuery);

                        if (result.next()){
                            dbUname = result.getString("id");
                            dbPassword = result.getString("password");

                            if(Objects.equals(uname, dbUname) && Objects.equals(pass, dbPassword)){
                                switch (user_identity) {
                                    case "tg" :
                                        System.out.println("Undergraduate");
                                        System.out.println("uname:"+uname+", pass:"+pass+", dbname:"+dbUname+",dbpass:"+dbPassword);
                                        dispose();
                                        new UndergraduateHomePage(uname);
                                        break;

                                    case "le" :
                                        System.out.println("Lecturer");
                                        dispose();
                                        new LecturerHomePage(uname);
                                        break;

                                    case "to" :
                                        System.out.println("Technical Officer");
                                        System.out.println("uname:"+uname+", pass:"+pass+", dbname:"+dbUname+",dbpass:"+dbPassword);
                                        dispose();
                                        new TechnicalOfficerHomePage(uname);
                                        break;

                                    case "ad" :
                                        System.out.println("Admin");
                                        dispose();
                                        new AdminHomePage(uname);
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

    void DBConnection(){
        int count = 0;

        String query = "create table if not exists users(username varchar(10) primary key,password varchar(50))";
        String selectQuery = "Select * from users";
        String admin = "admin";
        String pass = "123";

        try{
            Statement statement = conn.createStatement();

            statement.executeUpdate(query);
            ResultSet result = statement.executeQuery(selectQuery);

            while (result.next()){
                count++;
            }
            if (!(count > 0)){
                String admin_data = "insert into users(username,password) values ('" + admin + "','" + pass + "')";
                statement.executeUpdate(admin_data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
