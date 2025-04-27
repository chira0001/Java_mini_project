package DBCONNECTION;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBCONNECTION {
    private String connUrl = "jdbc:mysql://localhost:3306/tecsys";
    private String connUsername = "root";
    private String connPassword = "1234";

    private Connection conn = null;

    private void registerConn(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Successfully registered...");
        } catch (ClassNotFoundException e) {
            System.out.println("Error in registering the drive class..."+ e.getMessage());
        }
    }
    public Connection Conn(){
        registerConn();
        try{
            conn = DriverManager.getConnection(connUrl,connUsername,connPassword);
            System.out.println("Connection successfully established");
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return conn;
    }
}
