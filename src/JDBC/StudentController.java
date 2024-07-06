package JDBC;
import java.sql.*;
import java.util.Scanner;

public class StudentController {

    PreparedStatement prepState;

    public Connection connect_To_Db(String dbUrl , String dbUser , String dbPass ){
        Connection con = null ;
        try{
            con = DriverManager.getConnection(dbUrl , dbUser , dbPass);
            if(con != null ){
                System.out.println("Connection to Database established. ");
            }
            else {
                System.out.println("Connection to Database failed ! ! !");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return con;
    }

    public void createTable(Connection conn){
        try{
            prepState = conn.prepareStatement("create table college (e_no SERIAL , stu_name varchar(25) NOT NULL , " +
                    "division varchar(1) NOT NULL , gender varchar(10) NOT NULL , marks double precision NOT NULL, primary key (e_no))");
            prepState.executeUpdate();
            System.out.println("Table created.");
        }catch (Exception e){
            System.out.println("Error" + e.getMessage());
        }
    }

    //1.Create Student

    public void createStudent(Connection conn, String stu_name , String division , String gender , double marks){
        try{
            prepState = conn.prepareStatement("insert into college(stu_name, division, gender , marks ) values (? , ? , ?, ?)");
            prepState.setString(1,stu_name);
            prepState.setString(2,division);
            prepState.setString(3,gender);
            prepState.setDouble(4,marks);
            prepState.executeUpdate();
            System.out.println("Student information inserted into college table successfully.");

        }catch(Exception e) {
            System.out.println("Error" + e.getMessage());
        }

    }

    // 2. Print Student

    public void printStudent(Connection conn , int e_no){
        try {
            Scanner sc2 = new Scanner(System.in);
            prepState = conn.prepareStatement("Select * from college where e_no = ? ");
            e_no = sc2.nextInt();
            prepState.setInt(1, e_no);
            ResultSet resultSet = prepState.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getInt("e_no") + " " + resultSet.getString("stu_name") + " " +
                        resultSet.getString("division") + " " + resultSet.getString("gender") + " "+
                        resultSet.getDouble("marks"));
            }
            System.out.println("Row selected successfully.");
        }catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }

    }

    public void calculateGrade(Connection conn , String stu_name){
        try{
            prepState = conn.prepareStatement("select marks from college where stu_name = ? ");
            prepState.setString(1,stu_name);
            ResultSet resultSet = prepState.executeQuery();
            if(resultSet.next()){
                int marks = resultSet.getInt("marks");
                String grade = getGrade(marks);
                System.out.println("Grade for " + stu_name + " : " + grade);
            }else {
                System.out.println("Student not found! ! !");
            }
        }catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }
    }

    private String getGrade(int marks) {
        if (marks >= 75 && marks <= 100){
            return "A";
        } else if (marks >= 60 && marks <= 74) {
            return "B";
        }else if (marks >= 40 && marks <= 59){
            return "C";
        } else if (marks >= 0 && marks <= 39) {
            return "D";
        }else{
            return "Invalid Marks ! ! !";
        }
    }

    public void gender (Connection conn , String gender){
        try{
            Scanner gen = new Scanner(System.in);
            prepState = conn.prepareStatement("select * from college where gender = ? ");
            gender = gen.next();
            prepState.setString(1,gender);
            ResultSet resultSet = prepState.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getInt("e_no") + "   " + resultSet.getString("stu_name") + "   " +
                        resultSet.getString("division") + "   "+
                        resultSet.getDouble("marks"));
            }
            System.out.println("Row Selected Successfully ! ! !");
        }catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }
    }

    int bonus_marks = 0;
    public void addBonusMarks(Connection conn , int e_no , int bonus_marks){
        try{
            Scanner bm = new Scanner(System.in);
            prepState = conn.prepareStatement("update college set marks = marks + ? where e_no = ?" );
            prepState.setInt(1,bonus_marks);
            prepState.setInt(2,e_no);
            prepState.executeUpdate();
        }catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }
    }

    String new_Div ;
    public void changeDivision(Connection conn , String stu_name , String new_Div){
        try{
            prepState = conn.prepareStatement("update college set division = ? where stu_name = ? returning *");
            prepState.setString(1,new_Div);
            prepState.setString(2,stu_name);
            ResultSet resultSet = prepState.executeQuery();
            if(resultSet.next()){
                System.out.println("Division updated successfully for " + stu_name );
            }else{
                System.out.println("No student found with the name -  " + stu_name);
            }
        }catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }
    }

    int min_marks = 0 , max_marks = 0;

    public void printInRange(Connection conn ,int min_marks , int max_marks ){
        try{
            prepState = conn.prepareStatement("select * from college where marks between ? and ? ");
            prepState.setInt(1,min_marks);
            prepState.setInt(2,max_marks);
            ResultSet resultSet = prepState.executeQuery();

            while(resultSet.next()){
                System.out.println("Student enrollment number " + resultSet.getInt("e_no") +
                        "Student name " + resultSet.getString("stu_name")
                        + " Student marks" + resultSet.getDouble("marks"));
            }
        }catch (Exception e){
            System.out.println("Error" + e.getMessage());
        }
    }

    public void getStudentsWithSpecificDivision(Connection conn , String division ){
        try{
            prepState = conn.prepareStatement(" select * from college where division = ? ");
            prepState.setString(1,division);
            ResultSet resultSet = prepState.executeQuery();
            while(resultSet.next()){
                System.out.println("Student with Division " + resultSet.getString("division") + " : " + resultSet.getString("stu_name"));
            }
        }catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }
    }

    String remarks;
    public void updateStudentRemarks (Connection conn ){
        try{
//            prepState = conn.prepareStatement("alter table college add column remarks varchar (100) ");
//            prepState.executeUpdate();
            prepState = conn.prepareStatement("update college set remarks  = " +
                    " case " +
                    " when marks between 75 and 100 then 'Excellent'" +
                    " when marks between 60 and 74 then 'Good'" +
                    " when marks between 40 and 59 then 'Average'" +
                    " when marks between 0 and 39 then 'Needs Improvement'" +
                    " else 'Invalid Marks'" +
                    " end ");
            int rowsAffected = prepState.executeUpdate();
            System.out.println(rowsAffected + "Row updated with remarks. ");
        }catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }
    }
}
