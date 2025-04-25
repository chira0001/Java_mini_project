import java.util.Scanner;

public class MyDatabaseDemo {
    public static void main(String[] args) {

        InsertStudent insert_stu = new InsertStudent();
        UpdateStudent update_stu = new UpdateStudent();
        DeleteStudent delete_stu = new DeleteStudent();
        SearchStudent search_stu = new SearchStudent();
        DisplayStudent display_stu = new DisplayStudent();

        do {
            Scanner input = new Scanner(System.in);
            System.out.println("________Menu List_________ ");
            System.out.println("1 : Insert a Student ");
            System.out.println("2 : Update a Student Name by Id");
            System.out.println("3 : Update a Student Address by Id");
            System.out.println("4 : Delete a Student by Id");
            System.out.println("5 : Search a Student by Id");
            System.out.println("6 : Display all Students");
            System.out.println("0 : Exit");

            System.out.println("");
            System.out.print("Enter your choice: ");
            int choice = input.nextInt();

            switch (choice){
                case 1:
                    insert_stu.Insert();
                    break;
                case 2:
                    update_stu.updateNamebyID();
                    break;
                case 3:
                    update_stu.updateAddressbyID();
                    break;
                case 4:
                    delete_stu.delete();
                    break;
                case 5:
                    search_stu.searchStudentById();
                    break;
                case 6:
                    display_stu.displayStudent();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }while (true);


    }

}
