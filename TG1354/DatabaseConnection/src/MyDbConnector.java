import java.sql.*;

public class MyDbConnector {
    public void DBConn(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","1234");
            Statement statement = connection.createStatement();
            System.out.println(statement);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
