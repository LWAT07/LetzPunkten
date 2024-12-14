package Main.Subject;
/*
 * Class NewExam is used to create a new exam inside of the subjectFrame
 *  NewExam(SubjectFrame parent,int subjectNumber) -> constructor, it needs to add a label or remove a label to the subjehtFrame object and the subjectNumber is that it knows what subject it is
 *  saveValues() -> saves the values and creates new exam object and ads it to examArrayList and returns true if successful
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.*;

import FileSaving.Exam;
import Main.MainFrame;


public class NewExam extends JDialog{
    //GUI elements
    public static JTextField nameTextField = new JTextField("");
    public static JTextField gradeTextLabel = new JTextField("");
    public static JTextField maxGradeTextLabel = new JTextField("");
    
    private JCheckBox i = new JCheckBox("Mathe I");
    private JCheckBox ii = new JCheckBox("Mathe II");

    //variables about the exams
    private String name;
    private double grade;
    private int maxGrade; 
    private final int subjectNumber;

    public NewExam(SubjectFrame parent,int subjectNumber){
        //initalises Dialog
        super(parent);
        parent.updateExamLabel();
        this.setResizable(false);
        //this.setModal(true);
        this.setSize(300,200);
        if(MainFrame.data.isMathNow(subjectNumber)){
            this.setSize(300,230);
        }
        this.setLocationRelativeTo(parent);
        this.setLayout(null);
        this.setTitle("new Exam");
        this.subjectNumber=subjectNumber;
        
        //NameLabel left of nameTextField
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Name: ");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setSize(nameLabel.getPreferredSize());
        nameLabel.setLocation(15,20);
        this.add(nameLabel);
        
        //nameTextFiled
        nameTextField.setText("Test");
        nameTextField.setBounds(90, 20, 170, nameLabel.getHeight());
        this.add(nameTextField);

        //GradeLabel left of gradeTextField
        JLabel gradeLabel = new JLabel();
        gradeLabel.setText("Grade: ");
        gradeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gradeLabel.setSize(gradeLabel.getPreferredSize());
        gradeLabel.setLocation(15,60);
        this.add(gradeLabel);

        //gradeTextFiled
        gradeTextLabel.setBounds(90, 60, 60, gradeLabel.getHeight());
        gradeTextLabel.setText("");
        this.add(gradeTextLabel);

        //label to mate the slash in 60/60
        JLabel slashLabel = new JLabel("/");
        slashLabel.setFont(new Font("Arial", Font.BOLD, 20));
        slashLabel.setSize(slashLabel.getPreferredSize());
        slashLabel.setLocation(170,60);
        this.add(slashLabel);

        //maxGradeLabel
        maxGradeTextLabel.setBounds(200, 60, 60, gradeLabel.getHeight());
        //you can change this value if you want an other value to be default maxGrade
        maxGradeTextLabel.setText("60");
        this.add(maxGradeTextLabel);

        //stuff that is done on the press of the enter key
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(saveValues()){
                    ExamLabel ex = new ExamLabel(0, "", 0, 0, parent);
                    parent.examArrayList.add(ex);
                    parent.add(ex);
                    parent.updateExamLabel();
                    parent.updateTitle();
                }
            }
        };

        //save Button + actionListener
        JButton saveButton = new JButton("Save");
        saveButton.setSize(100, 30);
        saveButton.setLocation(this.getWidth()/2-saveButton.getWidth()/2, this.getHeight()-90);
        saveButton.setBackground(new Color(255,255,255));
        saveButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        saveButton.addActionListener(e -> {
            if(saveValues()){
                ExamLabel ex = new ExamLabel(100, "", 0, 0, parent);
                parent.examArrayList.add(ex);
                parent.buttonPanel.add(ex);
                parent.updateExamLabel();
                parent.updateTitle();
            }
        });
        saveButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "click");
        saveButton.getActionMap().put("click", action);
        this.add(saveButton);

        //splitMath checkBoxes
        if(MainFrame.data.isMathNow(subjectNumber)){
            i.setLocation(70,100);
            i.setSize(i.getPreferredSize());
            i.addActionListener(l-> ii.setSelected(false));
            this.add(i);

            ii.setLocation(150, 100);
            ii.setSize(ii.getPreferredSize());
            ii.addActionListener(l-> i.setSelected(false));
            this.add(ii);
        }
        this.setVisible(true);
        gradeTextLabel.requestFocusInWindow();
    }
    private boolean saveValues(){
        //checks if given values fit in the scheme
        try {
            if(nameTextField.getText().equals("null"))
                throw new Exception();
            name = nameTextField.getText();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "name Text Error", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            if(gradeTextLabel.getText().equals("null"))
                throw new NumberFormatException();
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
            if(maxGradeTextLabel.getText().equals("null"))
                throw new NumberFormatException();
            maxGrade = Integer.parseInt(maxGradeTextLabel.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Max Grade Error", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        //adds Math1 or 2 to the name if thats the case
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
        //if gradee is negative it takes absolute value
        if(grade < 0){
            grade *=-1;
        }
        if(grade > maxGrade){
            grade = maxGrade;
        }
        //creates new instance of exam and updates average
        this.dispose();
        MainFrame.examData.get(MainFrame.data.getCurrentSem()).examArrayList.get(subjectNumber).add(new Exam(name, grade, maxGrade));
        MainFrame.examData.get(MainFrame.data.getCurrentSem()).updateAllAverage();
        return true;
    }
}
