import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SearchStudent {
    public void searchStudentById() {

        try{
            Scanner input = new Scanner(System.in);
            System.out.print("Enter student id : ");
            String studentId = input.nextLine();

            String searchQuery = "select * from basicdata where stu_id = '" + studentId + "'";

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","1234");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(searchQuery);

            while (result.next()){
                String stu_id = result.getString("stu_id");
                String stu_name = result.getString("stu_name");
                String stu_address = result.getString("stu_address");

                System.out.println("Student Id : " + studentId + ", Name : " + stu_name + ", Address : " + stu_address);
            }

        }catch (Exception exp){
            exp.printStackTrace();
        }


    }
}
