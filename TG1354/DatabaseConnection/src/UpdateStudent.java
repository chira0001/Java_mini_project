import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateStudent {
    Scanner scanner = new Scanner(System.in);

    public void updateNamebyID(){

        System.out.print("Enter Student ID: ");

        String tgno = scanner.nextLine();
        System.out.print("Enter New Student Name: ");
        String newName = scanner.nextLine();

        String updateNamebyIDQuery = "UPDATE basicdata SET stu_name='"+newName+"' where stu_id = '" + tgno + "'";
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","1234");
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(updateNamebyIDQuery);

            if (result > 0){
                System.out.println("Student Name Updated");
            }else {
                System.out.println("Student Name Not Updated");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateAddressbyID(){
        System.out.print("Enter Student ID: ");
        String tgno = scanner.nextLine();
        System.out.print("Enter New Address: ");
        String newAddress = scanner.nextLine();

        String updateAddressbyIDQuery = "UPDATE basicdata SET stu_address='"+newAddress+"' where stu_id = '" + tgno + "'";
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","1234");
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(updateAddressbyIDQuery);

            if (result > 0){
                System.out.println("Student Address Updated");
            }else {
                System.out.println("Student Address Not Updated");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
