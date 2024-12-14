package FileSaving;
/*
 * class semData is where examData is saved while the program is running
 * SemData(int i) --> constructor
 * getAverage(int subjectNumber, int semNumber) --> get average of a subject 
 * getMathAverage(ArrayList<Exam> mathExamArrayList) --> is used to change math average if matheI or II is activated
 * updateAllAverage() --> updates all the total averages ofter a change
 * getTotalAverage() --> returns total average of all subjects and semester
 * getAverage() --> gets the total average of current semester
 * functions with String at the end convert double to String and format it 
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import Main.MainFrame;

public class SemData {
    public ArrayList<ArrayList<Exam>> examArrayList = new ArrayList<>();
    public ArrayList<Integer> bonusPoints = new ArrayList<>();

    public ArrayList<Double> averageArrayList = new ArrayList<>();
    public ArrayList<Double> totalAverageArrayList = new ArrayList<>();

    public SemData(int i){
        for (@SuppressWarnings("unused") Subject subjectArrayList : MainFrame.data.subjectArrayList) {
            examArrayList.add(new ArrayList<>());
            averageArrayList.add(255.0);
            totalAverageArrayList.add(null);
            bonusPoints.add(0);
        }
    }
    public double getAverage(int subjectNumber, int semNumber){//good
        //if empty return 255
        if(MainFrame.examData.get(semNumber).examArrayList.get(subjectNumber).isEmpty()){
            if(MainFrame.examData.get(semNumber).bonusPoints.get(subjectNumber)!=0)
                return MainFrame.examData.get(semNumber).bonusPoints.get(subjectNumber);
            return 255;
        }
        //make maxGrade 60
        for(Exam currentExam : MainFrame.examData.get(semNumber).examArrayList.get(subjectNumber)){
            if(currentExam.getMaxGrade()!=60){
                currentExam.setGrade(currentExam.getGrade()*(60.0/currentExam.getMaxGrade()));
                currentExam.setMaxGrade(60);
            }
        }
        //if math -> returns math average
        if(subjectNumber == MainFrame.data.getMathNumber()&&MainFrame.data.isSplitMath()){
           return getMathAverage(MainFrame.examData.get(semNumber).examArrayList.get(subjectNumber)); 
        }
        //calculates the rest
        double average=0;
        int i=0;
        for(Exam currentExam : MainFrame.examData.get(semNumber).examArrayList.get(subjectNumber)){
            average+=currentExam.getGrade(); 
            i++;
        }
        int bonusPoint = MainFrame.examData.get(semNumber).bonusPoints.get(subjectNumber);
        return (average/i)+bonusPoint;
    }
    private double getMathAverage(ArrayList<Exam> mathExamArrayList){
        final int MATH_I_COEFF = MainFrame.data.getMathICoeff();
        final int MATH_II_COEFF = MainFrame.data.getMathIICoeff();
        int subjectNumber = MainFrame.data.getMathNumber();
        ArrayList<Double> math1 = new ArrayList<>();
        ArrayList<Double> math2 = new ArrayList<>();
        for(int i=0; i<mathExamArrayList.size(); i++){
            if(mathExamArrayList.get(i).getName().contains(" - Mathe II")){
                math2.add(mathExamArrayList.get(i).getGrade());
            }else if(mathExamArrayList.get(i).getName().contains(" - Mathe I")){
                math1.add(mathExamArrayList.get(i).getGrade());
            }
        }

        if(math1.isEmpty() && math2.isEmpty())
            return 255;
        double average1=0;
        double average2=0;
        if(!math1.isEmpty()){
            for(double sum:math1){
                average1+=sum;
            }
            average1/=math1.size();
        }
        if(!math2.isEmpty()){
            for(double sum:math2){
                average2+=sum;
            }
            average2/=math2.size();
        }
        if(math1.isEmpty())
            return round(average2+bonusPoints.get(subjectNumber),2);
        if(math2.isEmpty()) 
            return round(average1+bonusPoints.get(subjectNumber),2);
        double allAv = MATH_II_COEFF*average2+MATH_I_COEFF*average1;
        allAv/=MATH_I_COEFF+MATH_II_COEFF;   
        return  round(allAv+bonusPoints.get(subjectNumber),2);
    }

    public void updateAllAverage(){
        //calculate average arrayList for all sems
        for(int x=1; x<=MainFrame.data.getSemCounter();x++){
            for(int i=0; i<MainFrame.examData.get(x).examArrayList.size(); i++){
                MainFrame.examData.get(x).averageArrayList.set(i, getAverage(i, x));
            }
        }
        //changes totalAverageArrayList
        for(int x=0; x<MainFrame.data.getSubjectCount();x++){
            int i=0;
            double sum=0;
            for(int y=1; y<=MainFrame.data.getSemCounter(); y++){
                if(MainFrame.examData.get(y).averageArrayList.get(x)!=255){
                    sum+=MainFrame.examData.get(y).averageArrayList.get(x);
                    i++;
                }
            }
            if(i!=0)
                MainFrame.examData.get(0).totalAverageArrayList.set(x,sum/i);
            else{
                MainFrame.examData.get(0).totalAverageArrayList.set(x,null);
            }   
        }
    }

    public double getTotalAverage(){
        int counter=0;
        double sum =0;
        for(int i=1;i<=MainFrame.data.getSemCounter();i++){
            if(MainFrame.examData.get(i).getAverage()!=0.0){
                sum+=MainFrame.examData.get(i).getAverage();
                counter++;
            }
        }
        if(counter!=0)
            return round(sum/counter, 2);
        return 0.0;
    }

    public String getTotalAverageString(){
        String temp = " ";
        if(String.valueOf(getTotalAverage()).contains(".") && getTotalAverage()!=0.0&& getTotalAverage()!=255){
            temp = String.valueOf(getTotalAverage()); 
            if(Double.parseDouble(temp)%1==0){
                int tempInt = (int)Double.parseDouble(temp);
                temp = String.valueOf(tempInt);
            }
        }
        return temp;
    }
    public double getAverage(){
        ArrayList<Double> tempList = new ArrayList<>();
        for(int i=0; i<averageArrayList.size();i++){
            if(String.valueOf(averageArrayList.get(i)).contains(".")&&averageArrayList.get(i)!=255){
                for(int j=0; j<MainFrame.data.subjectArrayList.get(i).getCoefficient(); j++){
                    tempList.add(averageArrayList.get(i));
                }
            }
        }
        double sum =0.0;
        for(double tempAverage: tempList){
            sum+=tempAverage;
        }
        if(!tempList.isEmpty()){
            return round(sum/tempList.size(),2);
        }
        return 0.0;
    }
    public String getTotalSemAverageString(){
        String temp = " ";
        if(String.valueOf(getAverage()).contains(".") && getAverage()!=0&& getAverage()!=255){
            temp = String.valueOf(getAverage()); 
            if(Double.parseDouble(temp)%1==0){
                int tempInt = (int)Double.parseDouble(temp);
                temp = String.valueOf(tempInt);
            }
        }
        return temp;
    }
    public String getAverageString(int subjectNumber){
        String temp = " ";
        if(String.valueOf(averageArrayList.get(subjectNumber)).contains(".")&&averageArrayList.get(subjectNumber)!=255){
            temp = String.valueOf(round(averageArrayList.get(subjectNumber),2)); 
            if(Double.parseDouble(temp)%1==0){
                int tempInt = (int)Double.parseDouble(temp);
                temp = String.valueOf(tempInt);
            }
        }
        return temp;
    }
    public String getTotalAverageString(int subjectNumber){
        String temp = " ";
        MainFrame.examData.get(0).totalAverageArrayList.get(subjectNumber);
        if(String.valueOf(MainFrame.examData.get(0).totalAverageArrayList.get(subjectNumber)).contains(".")){
            temp = String.valueOf(round(MainFrame.examData.get(0).totalAverageArrayList.get(subjectNumber),2)); 
            if(Double.parseDouble(temp)%1==0){
                int tempInt = (int)Double.parseDouble(temp);
                temp = String.valueOf(tempInt);
            }
        }
        return temp;
    }
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.UP);
        return bd.doubleValue();
    }
}
