import javax.sql.RowSet;
import java.sql.*;

public class DBConnection {

    public DBConnection(){

            int count = 0;

            String query = "create table if not exists users(username varchar(10) primary key,password varchar(50))";
            String selectQuery = "Select * from users";
            String admin = "admin";
            String pass = "123";

            try{
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaGUI","root","1234");
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                ResultSet result = statement.executeQuery(selectQuery);

                while (result.next()){
                    count++;
                }
                if (!(count > 0)){
                    String admin_data = "insert into users(username,password) values ('" + admin + "','" + pass + "')";
                    statement.executeUpdate(admin_data);
                    //System.out.println("data entered");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
    }

//    public static void main(String[] args) {
//        new DBConnection();
//
//    }

}