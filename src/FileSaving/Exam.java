package FileSaving;
/*
 * Instance of class Exam represents one Exam 
 */


public class Exam{
    //Attributes
    private String name;
    private double grade;
    private int maxGrade;
    //Constructors
    public Exam( String name, double grade, int maxGrade) {
        this.name = name;
        this.grade = grade;
        this.maxGrade = maxGrade; 
    }
    public Exam(Exam pExam){
        this.name = pExam.name;
        this.grade = pExam.grade;
        this.maxGrade = pExam.maxGrade;
    }
    //getters and setters
    public String getName() {
        return name;
    }
    public double getGrade() {
        return grade;
    }
    public int getMaxGrade() {
        return maxGrade;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGrade(double grade) {
        this.grade = grade;
    }
    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }
    @Override
    public String toString(){
        return "Name: "+this.getName()+" Grade: "+this.getGrade()+" / "+this.getMaxGrade();
    }
}