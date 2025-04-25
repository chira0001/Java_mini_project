import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class InsertStudent {
    Scanner scanner = new Scanner(System.in);

    public void Insert(){

        System.out.print("Enter New Student ID: ");
        String tgno = scanner.nextLine();

        System.out.print("Enter New Student Name: ");
        String newName = scanner.nextLine();

        System.out.print("Enter New Student Address: ");
        String newAddress = scanner.nextLine();

        String insertQuery = "INSERT INTO basicdata VALUES ('" + tgno + "','" + newName + "','" + newAddress + "')";
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","1234");
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(insertQuery);

            if (result > 0){
                System.out.println("Student Successfully Inserted");
            }else {
                System.out.println("Student Not Inserted");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
