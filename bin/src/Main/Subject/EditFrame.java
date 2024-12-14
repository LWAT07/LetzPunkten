package Main.Subject;
/*
 * JDialog to edit name grade and maxgrade of already ssaved exam
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.*;

import Main.MainFrame;
public class EditFrame extends JDialog{
    public static JTextField nameTextField = new JTextField("");
    public static JTextField gradeTextLabel = new JTextField("");
    public static JTextField maxGradeTextLabel = new JTextField("");
    
    private JCheckBox i = new JCheckBox("Mathe I");
    private JCheckBox ii = new JCheckBox("Mathe II");

    private String name;
    private double grade;
    private int maxGrade; 
    private int examNumber;
    private final int subjectNumber;
    
    public EditFrame(int pExamNumber, SubjectFrame parent){
        super(parent);
        examNumber = pExamNumber;
        subjectNumber = parent.subjectNumber;
        name = MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.get(subjectNumber).get(pExamNumber).getName();
        grade = MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.get(subjectNumber).get(pExamNumber).getGrade();
        maxGrade = MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.get(subjectNumber).get(pExamNumber).getMaxGrade();
        parent.updateExamLabel();
        this.setResizable(false);
        this.setSize(300,200);
        if(MainFrame.data.isMathNow(subjectNumber)){
            this.setSize(300,230);
        }
        this.setLocationRelativeTo(parent);
        this.setLayout(null);
        this.setTitle("Edit Exam");

        JLabel nameLabel = new JLabel();
        nameLabel.setText("Name: ");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setSize(nameLabel.getPreferredSize());
        nameLabel.setLocation(15,20);
        this.add(nameLabel);
        
        nameTextField.setText(name);
        nameTextField.setBounds(90, 20, 170, nameLabel.getHeight());
        this.add(nameTextField);

        

        JLabel gradeLabel = new JLabel();
        gradeLabel.setText("Grade: ");
        gradeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gradeLabel.setSize(gradeLabel.getPreferredSize());
        gradeLabel.setLocation(15,60);
        this.add(gradeLabel);

        
        gradeTextLabel.setBounds(90, 60, 60, gradeLabel.getHeight());
        gradeTextLabel.setText(grade%1==0?String.valueOf((int)grade):String.valueOf(grade));
        this.add(gradeTextLabel);

        JLabel slashLabel = new JLabel("/");
        slashLabel.setFont(new Font("Arial", Font.BOLD, 20));
        slashLabel.setSize(slashLabel.getPreferredSize());
        slashLabel.setLocation(170,60);
        this.add(slashLabel);

        
        maxGradeTextLabel.setBounds(200, 60, 60, gradeLabel.getHeight());
        maxGradeTextLabel.setText(String.valueOf(maxGrade));
        this.add(maxGradeTextLabel);

        //ectionlistener to enter press
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(saveValues()){
                    saveValues();
                    parent.updateExamLabel();
                    parent.updateTitle();
                }
            }
        };

        JButton saveButton = new JButton("Save");
        saveButton.setSize(100, 30);
        saveButton.setLocation(160, this.getHeight()-90);
        saveButton.setBackground(new Color(255,255,255));
        saveButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        saveButton.addActionListener(e -> {
            saveValues();
            parent.updateExamLabel();
            parent.updateTitle();
        });
        saveButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "click");
        saveButton.getActionMap().put("click", action);
        this.add(saveButton);

        JButton deleteButton = new JButton("delete");
        deleteButton.setSize(100, 30);
        deleteButton.setLocation(20, this.getHeight()-90);
        deleteButton.setBackground(new Color(255,255,255));
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        deleteButton.addActionListener(e ->{
            parent.deleteExam(examNumber);
            this.dispose();
            MainFrame.examData.get(MainFrame.data.getCurrentSem()).updateAllAverage();
            parent.updateTitle();
        });
        this.add(deleteButton);

        //Checkboxes for Math I and II
        if(MainFrame.data.isMathNow(subjectNumber)){
            if(name.contains(" - Mathe II")){
                ii.setSelected(true);
            }else if(name.contains(" - Mathe I")){
                i.setSelected(true);
            }
            i.setLocation(70,100);
            i.setSize(i.getPreferredSize());
            i.addActionListener(l -> ii.setSelected(false));
            this.add(i);

            ii.setLocation(150, 100);
            ii.setSize(ii.getPreferredSize());
            ii.addActionListener(l-> i.setSelected(false));
            this.add(ii);

            if(MainFrame.data.isMathNow(subjectNumber)){
                if(name.contains(" - Mathe II")){
                    name = name.substring(0, name.length() - 11);
                }else if(name.contains(" - Mathe I")){
                    name = name.substring(0, name.length() - 10);
                }
                nameTextField.setText(name);
            }
        }

        this.setVisible(true);
        gradeTextLabel.requestFocusInWindow();
    }

    private boolean saveValues(){
        //reads information from textFields and saves to variables
        try {
            name = nameTextField.getText();
        } catch (Exception e) { 
            JOptionPane.showMessageDialog(null, "name Text Error", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            String tempGrade = gradeTextLabel.getText();
            if(tempGrade.contains(",")){
                tempGrade = tempGrade.replace(",",".");
            }
            grade = Double.parseDouble(tempGrade);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Grade Error", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            maxGrade = Integer.parseInt(maxGradeTextLabel.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Max Grade Error", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(MainFrame.data.isMathNow(subjectNumber)){
            if(i.isSelected()&&!ii.isSelected()){
                name += " - Mathe I";
            }else if (!i.isSelected()&&ii.isSelected()) {
                name += " - Mathe II";
            }else{
                JOptionPane.showMessageDialog(null, "Select I or II", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        //if negative grade is entered, absolut value is taken
        if(grade < 0){
            grade *=-1;
        }
        if(grade > maxGrade){
            grade = maxGrade;
        }
        this.dispose();
        //Exams in examArrayList are edited
        MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.get(subjectNumber).get(examNumber).setName(name);
        MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.get(subjectNumber).get(examNumber).setGrade(grade);
        MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.get(subjectNumber).get(examNumber).setMaxGrade(maxGrade);
        MainFrame.examData.get(MainFrame.data.getCurrentSem()).updateAllAverage();
        //returns true if succesful
        return true;
    }
}