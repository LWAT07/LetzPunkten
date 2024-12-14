package Main.Subject;
/* 
 * ExamLabel is the label used to display the list of all exams
 */

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
public class ExamLabel extends JButton{
    public JLabel resultLabel = new JLabel();
    public int mainExamNumber;
    //Constructor
    public ExamLabel(int pY, String pName, double pGrade, int pMaxGrade, SubjectFrame parent){
        this.setBounds(10,pY, 445, 40);
        String tempGrade =pGrade%1==0?(int)(pGrade)+"":pGrade +"";
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setText(" "+pName);
        this.setOpaque(true);
        this.setFont(new Font("Arial", Font.BOLD, 30));
        this.setBackground(new Color(255,255,255));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(null);
        resultLabel.setText(tempGrade + " / " + pMaxGrade);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 30));
        resultLabel.setSize((int)resultLabel.getPreferredSize().getWidth(), 40);
        resultLabel.setLocation(this.getWidth()-(int)resultLabel.getPreferredSize().getWidth()-10, 0);
        this.add(resultLabel);
        this.addActionListener(e -> new EditFrame(mainExamNumber, parent));
    }
    //Changes the values after the exam is edited
    public void changeValues(int pY, String pName, double pGrade, int pMaxGrade, int examNumber){
        mainExamNumber = examNumber;
        this.setBounds(10,pY, 445, 40);
        String tempGrade =pGrade%1==0?(int)(pGrade)+"":pGrade +"";
        this.setText(" "+pName);
        this.setOpaque(true);
        this.setFont(new Font("Arial", Font.BOLD, 30));
        this.setBackground(new Color(255,255,255));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resultLabel.setText(tempGrade + " / " + pMaxGrade);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 30));
        resultLabel.setSize((int)resultLabel.getPreferredSize().getWidth(), 40);
        resultLabel.setLocation(this.getWidth()-(int)resultLabel.getPreferredSize().getWidth()-10, 0);
    }
}
