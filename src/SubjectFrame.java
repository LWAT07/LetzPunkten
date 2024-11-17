/*
 * frame that opens when you click on a subject 
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;

public final class SubjectFrame extends JDialog{
    private int examCount = MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.size();
    public int subjectNumber;

    public JLabel bonusLabel = new JLabel();
    public JLabel titleLabel = new JLabel();
    public ArrayList<ExamLabel> examArrayList = new ArrayList<>();

    //Constructor 
    public SubjectFrame(int subjectNumber, JFrame parent){
        //GUI ELements
        super(parent);
        this.setResizable(false);
        this.setModal(true);   
        this.setSize(new Dimension(500, 500));
        this.setLocation(MainFrame.data.getX()+MainFrame.data.getWidth()/2-this.getWidth()/2,MainFrame.data.getY()+MainFrame.data.getHeight()/2-this.getHeight()/2);  
        this.setLayout(null);
        this.setTitle(MainFrame.data.subjectArrayList.get(subjectNumber).getName());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setLayout(null);
        this.subjectNumber = subjectNumber;

        if(!MainFrame.examData.get(MainFrame.data.getCurrentSem()).getAverageString(subjectNumber).equals(" "))
            titleLabel.setText(MainFrame.data.subjectArrayList.get(subjectNumber).getName() + ": " + MainFrame.examData.get(MainFrame.data.getCurrentSem()).getAverageString(subjectNumber));
        else
            titleLabel.setText(MainFrame.data.subjectArrayList.get(subjectNumber).getName());
        titleLabel.setFont( new Font("Arial", Font.BOLD, 36));
        titleLabel.setSize(titleLabel.getPreferredSize());
        titleLabel.setLocation(this.getWidth()/2-titleLabel.getWidth()/2, 34);
        this.add(titleLabel);

        JLabel coefLabel = new JLabel("Coefficient: " + MainFrame.data.subjectArrayList.get(subjectNumber).getCoefficient());
        coefLabel.setFont(new Font("Arial", Font.BOLD, 20));
        coefLabel.setSize(coefLabel.getPreferredSize());
        coefLabel.setLocation(this.getWidth()/2 - coefLabel.getWidth()/2, 80);
        this.add(coefLabel);

        bonusLabel.setText("Bonus:          " + MainFrame.examData.get(MainFrame.data.getCurrentSem()).bonusPoints.get(subjectNumber));
        bonusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bonusLabel.setSize(bonusLabel.getPreferredSize());
        bonusLabel.setLocation(100, 140);
        this.add(bonusLabel);

        JButton addBonus = new JButton("+");
        addBonus.setSize(40,25);
        addBonus.setLocation(310,140);
        addBonus.setBackground(new Color(255,255,255));
        addBonus.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addBonus.addActionListener(e -> {
            increaseBonus();
        });
        this.add(addBonus);

        JButton removeBonus = new JButton("-");
        removeBonus.setSize(40,25);
        removeBonus.setLocation(260,140);
        removeBonus.setBackground(new Color(255,255,255));
        removeBonus.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        removeBonus.addActionListener(e -> {
            decreaseBonus();
        });
        this.add(removeBonus);

        JButton addTestButton = new JButton(" + new Exam");
        addTestButton.setFont(new Font("Arial", Font.BOLD, 20));
        addTestButton.setBackground(new Color(255, 255, 255));
        addTestButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addTestButton.setSize(200,50);
        addTestButton.setLocation(this.getWidth()/2- addTestButton.getWidth()/2, 180);
        addTestButton.addActionListener(e -> {
           @SuppressWarnings("unused")
            NewExam n = new NewExam(this, subjectNumber);
        });
        this.add(addTestButton);

        updateExamLabel();
        //if total tab is selected, nothing happens
        if(MainFrame.data.getCurrentSem()==0){
            this.setVisible(false);
        }else{
            this.setVisible(true);
        }
        
    }
    //getters and setters
    public int getExamCount() {
        return examCount;
    }

    public void setExamCount(int pEexamCount) {
        examCount = pEexamCount;
    }
    //is called when + button is pressed
    private void increaseBonus(){
        MainFrame.examData.get(MainFrame.data.getCurrentSem()).bonusPoints.set(subjectNumber, MainFrame.examData.get(MainFrame.data.getCurrentSem()).bonusPoints.get(subjectNumber)+1);
        bonusLabel.setText("Bonus:          " + MainFrame.examData.get(MainFrame.data.getCurrentSem()).bonusPoints.get(subjectNumber));
        bonusLabel.setSize(bonusLabel.getPreferredSize());
        MainFrame.examData.get(MainFrame.data.getCurrentSem()).updateAllAverage();
        updateTitle();
    }
    //is called when - button is pressed
    private void decreaseBonus(){
        MainFrame.examData.get(MainFrame.data.getCurrentSem()).bonusPoints.set(subjectNumber, MainFrame.examData.get(MainFrame.data.getCurrentSem()).bonusPoints.get(subjectNumber)-1);
        bonusLabel.setText("Bonus:          " + MainFrame.examData.get(MainFrame.data.getCurrentSem()).bonusPoints.get(subjectNumber));
        bonusLabel.setSize(bonusLabel.getPreferredSize());
        MainFrame.examData.get(MainFrame.data.getCurrentSem()).updateAllAverage();
        updateTitle();
    }
    //updates the count of the labels with information of current exam
    public void updateExamLabel(){

        examCount = MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.get(subjectNumber).size();
        
        if(examCount != examArrayList.size()){
            for(int j=0; j<examCount; j++){
                examArrayList.add(new ExamLabel(0, "", 0, 0, this));
                this.add(examArrayList.get(j));
            } 
        }

        for(int j=0; j<examArrayList.size(); j++){
            int y = 240+50*j;
            String name = MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.get(subjectNumber).get(j).getName();
            double grade = MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.get(subjectNumber).get(j).getGrade();
            int maxGrade = MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.get(subjectNumber).get(j).getMaxGrade();
            int examNumber = j;
            examArrayList.get(j).changeValues(y, name, grade, maxGrade, examNumber);
        }

        if(examCount<=4){
            this.setSize(this.getWidth(), 280 + 4*50);
        }else{
            this.setSize(this.getWidth(), 280 + examCount*50);
        }
    }
    //called when an exam is deleted 
    public void deleteExam(int examNumber){
        MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.get(subjectNumber).remove(examNumber);
        examArrayList.get(examNumber).setVisible(false);
        examArrayList.remove(examNumber);
        updateExamLabel();
    }
    //updates average in the title
    public void updateTitle(){
        if(!MainFrame.examData.get(MainFrame.data.getCurrentSem()).getAverageString(subjectNumber).equals(" "))
            titleLabel.setText(MainFrame.data.subjectArrayList.get(subjectNumber).getName() + ": " + MainFrame.examData.get(MainFrame.data.getCurrentSem()).getAverageString(subjectNumber));
        else
            titleLabel.setText(MainFrame.data.subjectArrayList.get(subjectNumber).getName());
        titleLabel.setSize(titleLabel.getPreferredSize());
        titleLabel.setLocation(this.getWidth()/2-titleLabel.getWidth()/2, 34);
    }
}
