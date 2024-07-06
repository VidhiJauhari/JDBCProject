package JDBC;

import java.sql.Connection;
import java.util.Objects;
import java.util.Scanner;

public class CollegeService {
    public static void main(String[] args) {
        StudentController stuc = new StudentController();
        String dbUrl = "jdbc:postgresql://localhost:5432/CollegeJDBC";
        String dbUser = "postgres";
        String dbPass = "postgresql";
        Scanner sc = new Scanner(System.in);  // For string
        Scanner sc1 = new Scanner(System.in);  // For double
        Scanner enu = new Scanner(System.in);  // For Enrollment
        Scanner sc2 = new Scanner(System.in);  // For Integer

        Connection connection = stuc.connect_To_Db(dbUrl , dbUser , dbPass);
        //stuc.createTable(connection);
        String str = "y";
        int e_no , choice , bonus_marks , min_marks , max_marks;
        String stu_name , gender , division  ;
        double marks = 0 ;

        do{
            System.out.println("Enter your Choice : ");
            System.out.println("Enter 1 for Create Student : ");
            System.out.println("Enter 2 for Print Specific Student Data  : ");
            System.out.println("Enter 3 for Calculate Grade : ");
            System.out.println("Enter 4 for Search by Student Gender : ");
            System.out.println("Enter 5 for Add Bonus Marks  : ");
            System.out.println("Enter 6 for Change Division : ");
            System.out.println("Enter 7 for Find Student by Marks Range   : ");
            System.out.println("Enter 8 for Get students by specific division  : ");
            System.out.println("Enter 9 for Update student remarks  : ");

            choice = sc1.nextInt();

            switch (choice){

                case 1 -> {
                    System.out.println("Enter the name of student : ");
                    stu_name = sc.nextLine();
                    System.out.println("Enter the division of student : ");
                    division = sc.next();
                    System.out.println("Enter the gender of student : ");
                    gender = sc.next();
                    System.out.println("Enter the marks of student : ");
                    marks = sc1.nextDouble();
                    stuc.createStudent(connection , stu_name , division , gender , marks);
                    System.out.println("Student details inserted ! ! ! ");
                }

                case 2 ->{
                    System.out.println("Enter the Enrollment number of student :" );
                    Scanner e_no1 = new Scanner(System.in);
                    e_no = e_no1.nextInt();
                    stuc.printStudent(connection , e_no);
                }

                case 3 -> {
                    System.out.println("Enter the name of student : ");
                    stu_name = sc.next();
                    stuc.calculateGrade(connection , stu_name );
                }

                case 4 ->{
                    System.out.println("Enter the gender of student : ");
                    gender = sc.next();
                    stuc.gender(connection , gender );
                }

                case 5 ->{
                    System.out.println("Enter the Enrollment number of student : ");
                    e_no = enu.nextInt();
                    Scanner bon = new Scanner(System.in);
                    System.out.println("Enter bonus marks to add :");
                    bonus_marks = bon.nextInt();
                    stuc.addBonusMarks(connection , e_no , bonus_marks );
                    System.out.println("Bonus Marks Added ! ! !");
                }

                case 6 -> {
                    System.out.println("Enter the name of student :");
                    stu_name = sc.next();
                    System.out.println("Enter the new division for student : ");
                    division = sc.next();
                    stuc.changeDivision(connection , stu_name, division );
                    System.out.println("Division of Student " + stu_name + " changed successfully to " + division);
                }

                case 7-> {
                    System.out.println("Enter the minimum marks of student : ");
                    min_marks = sc2.nextInt();
                    System.out.println("Enter the maximum marks of student :");
                    max_marks = sc2.nextInt();
                    System.out.println("Students between " + min_marks + "and" + max_marks + " are :");
                    stuc.printInRange(connection , min_marks , max_marks);
                }

                case 8 ->{
                    System.out.println("Enter the division of student : ");
                    division = sc.next();
                    stuc.getStudentsWithSpecificDivision(connection , division);
                }

                case 9 ->{
//                    System.out.println("Table Altered ");
                    stuc.updateStudentRemarks(connection );
                    System.out.println("Remarks updated ! ! !");
                }

            }
            System.out.println("Do you want to continue ? ? ?");
            str = sc1.next();

        }while(Objects.equals(str ,"y") || Objects.equals(str , "Y"));

    }
}
