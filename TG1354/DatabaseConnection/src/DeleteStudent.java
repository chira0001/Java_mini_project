import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class DeleteStudent {

    Scanner scanner = new Scanner(System.in);

    public void delete(){
        System.out.print("Enter Student ID: ");
        String tgno = scanner.nextLine();

        String deleteStudentbyIDQuery = "Delete from basicdata where stu_id = '" + tgno + "'";
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","1234");
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(deleteStudentbyIDQuery);

            if (result > 0){
                System.out.println("Student deleted successfully");
            }else {
                System.out.println("Student not found");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
