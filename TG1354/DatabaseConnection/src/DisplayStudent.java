import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayStudent {
    public void displayStudent(){
        try{
            String selectQuery = "select basicdata.stu_id, stu_name, stu_address, chemistry, physics, maths from basicdata inner join marks where marks.stu_id = basicdata.stu_id";

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","1234");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(selectQuery);

            System.out.println("student_ID\tName\tChemistry_Grade\tPhysics_Grade\tMaths_Grade\tOverall_Grade\t");
            while (result.next()){
                String stu_id = result.getString("stu_id");
                String stu_name = result.getString("stu_name");
                String stu_address = result.getString("stu_address");

                double chemistry = Double.parseDouble(result.getString("chemistry"));
                double physics = Double.parseDouble(result.getString("physics"));
                double maths = Double.parseDouble(result.getString("maths"));

                double overall = (chemistry + physics + maths) / 3;

                System.out.println(stu_id + "\t\t" + stu_name + "\t\t\t" + detectGrade(chemistry) + "\t\t\t\t" + detectGrade(physics) + "\t\t\t " + detectGrade(maths) + "\t\t\t\t" + detectGrade(overall));
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public char detectGrade(double marks){
        char grade = 'R';
        if(marks <= 100 && marks >=85){
            grade = 'A';
        } else if (marks<85 && marks >=65) {
            grade = 'B';
        } else if (marks<65 && marks >=35) {
            grade = 'C';
        } else if (marks<35 && marks >=0) {
            grade = 'F';
        }
        return grade;
    }
}
