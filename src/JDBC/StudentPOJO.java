package JDBC;

public class StudentPOJO {
    private int e_no;

    private String stu_name;

    private String division;

    private String gender;

    private double marks;

    public StudentPOJO(int e_no, String stu_name, String division, String gender, double marks ) {
        this.e_no = e_no;
        this.stu_name = stu_name;
        this.division = division;
        this.gender = gender;
        this.marks = marks;
    }

    public int getE_no() {
        return e_no;
    }

    public void setE_no(int e_no) {
        this.e_no = e_no;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }



    @Override
    public String toString() {
        return "StudentPOJO{" +
                "e_no=" + e_no +
                ", stu_name='" + stu_name + '\'' +
                ", division='" + division + '\'' +
                ", gender='" + gender + '\'' +
                ", marks=" + marks +
                '}';
    }
}
