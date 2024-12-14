package Main.ClassFileEditor;
/*
 * The dialog that opens when you press on a subject, here you can select name and coefficient of subject
 * newSubjectFrame(newClassData parent) --> the constructor that initialises all of the GUI Elements
 * saveValues(newClassData parent) saves all the data to the arraylists from the parent frame 
 *  
 */

import FileSaving.Subject;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class EditSubjectFrame  extends JDialog{
    //gui Elements
    private final JTextField nameTextField = new JTextField();
    private final JTextField shortNameTextField = new JTextField();
    private final JTextField coeffTextField = new JTextField();
    private final JCheckBox splitMathCheckBox = new JCheckBox("  Split Math");
    private JTextField iCoeffTextField = new JTextField();
    private JTextField iiCoeffTextField = new JTextField(); 

    private final int subjectNumber;

    private boolean isSplitMathSelected = false;

    public EditSubjectFrame(NewClassData parent, int subjectNumber){
        //gui  elements
        super(parent);
        this.setTitle("Edit Subject");
        this.setModal(true);
        this.setResizable(false);
        this.setSize(375,340);
        this.setLocationRelativeTo(parent);
        this.setLayout(null);

        this.subjectNumber = subjectNumber;

        JLabel titleLabel= new JLabel("Edit Subject:");
        titleLabel.setBounds(100,22,400,30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 27));
        this.add(titleLabel);

        JLabel nameFieldLabel = new JLabel("Name:");
        nameFieldLabel.setBounds(40, 75, 75, 22);
        nameFieldLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(nameFieldLabel);

        nameTextField.setBounds(150,75, 180, 22);
        nameTextField.setText(parent.subjectArrayList.get(subjectNumber).getName());
        nameTextField.addActionListener(e -> shortNameTextField.requestFocusInWindow());
        this.add(nameTextField);

        JLabel shortNameFieldLabel = new JLabel("Short name:");
        shortNameFieldLabel.setBounds(40, 110, 130, 22);
        shortNameFieldLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(shortNameFieldLabel);

        JLabel shortNameExplainLabel = new JLabel("(5 Characters)");
        shortNameExplainLabel.setBounds(255, 110, 150, 22);
        shortNameExplainLabel.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(shortNameExplainLabel);

        shortNameTextField.setBounds(150,110, 100, 22);
        shortNameTextField.setText(parent.subjectArrayList.get(subjectNumber).getShortName());
        shortNameTextField.addActionListener(e -> coeffTextField.requestFocusInWindow());
        this.add(shortNameTextField);

        JLabel coeffFieldLabel = new JLabel("Coefficient:");
        coeffFieldLabel.setBounds(40, 150, 150, 22);
        coeffFieldLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(coeffFieldLabel);

        coeffTextField.setBounds(150,150, 22, 22);
        coeffTextField.setText(String.valueOf(parent.subjectArrayList.get(subjectNumber).getCoefficient()));
        coeffTextField.addActionListener(e -> {
            saveValues(parent);
        });
        this.add(coeffTextField);

        JButton saveButton = new JButton("Save");
        saveButton.setSize(110,40);
        saveButton.setLocation(130, this.getHeight()-90);
        saveButton.setBackground(new Color(230, 245, 250));
        saveButton.addActionListener(e ->{
            saveValues(parent);
        });
        this.add(saveButton);


        JLabel iCoeffLabel = new JLabel("Mathe I Coefficient:");
        iCoeffLabel.setFont(new Font("Arial", Font.BOLD, 16));
        iCoeffLabel.setSize(iCoeffLabel.getPreferredSize());
        iCoeffLabel.setLocation(15, 230);
        iCoeffLabel.setVisible(false);
        this.add(iCoeffLabel);

        iCoeffTextField.setFont(new Font("Arial", Font.BOLD, 16));
        iCoeffTextField.setSize((int)iCoeffLabel.getPreferredSize().getWidth(), 30);
        iCoeffTextField.setLocation(15, 250);
        iCoeffTextField.setVisible(false);
        this.add(iCoeffTextField);

        JLabel iiCoeffLabel = new JLabel("Mathe II Coefficient:");
        iiCoeffLabel.setFont(new Font("Arial", Font.BOLD, 16));
        iiCoeffLabel.setSize(iiCoeffLabel.getPreferredSize());
        iiCoeffLabel.setLocation(195, 230);
        iiCoeffLabel.setVisible(false);
        this.add(iiCoeffLabel);

        iiCoeffTextField.setFont(new Font("Arial", Font.BOLD, 16));
        iiCoeffTextField.setSize((int)iiCoeffLabel.getPreferredSize().getWidth(), 30);
        iiCoeffTextField.setLocation(195, 250);
        iiCoeffTextField.setVisible(false);
        this.add(iiCoeffTextField);

        //if you select chechbox, more information will show
        scaleCheckBoxIcon(splitMathCheckBox);
        splitMathCheckBox.setBounds(110, 190, 150, 22);
        splitMathCheckBox.setFont(new Font("Arial", Font.BOLD, 16));
        splitMathCheckBox.addActionListener(l->{
            isSplitMathSelected=!isSplitMathSelected;
            if(isSplitMathSelected){
                this.setSize(this.getWidth(),this.getHeight()+50);
                saveButton.setLocation(130, this.getHeight()-90);
                iCoeffLabel.setVisible(true);
                iiCoeffLabel.setVisible(true);
                iCoeffTextField.setVisible(true);
                iiCoeffTextField.setVisible(true);
            }else{
                this.setSize(this.getWidth(),this.getHeight()-50);
                saveButton.setLocation(130, this.getHeight()-90);
                iCoeffLabel.setVisible(false);
                iiCoeffLabel.setVisible(false);
                iCoeffTextField.setVisible(false);
                iiCoeffTextField.setVisible(false);
            }    
        });
        this.add(splitMathCheckBox);

        this.setVisible(true);
    }

    private void saveValues(NewClassData parent){
        //initialises variables
        String name = "";
        String shortName = "";
        int coefficient;

        //sets variables with information from textfields and makes errorframes if it is empty
        try {
            if(!nameTextField.getText().isEmpty())
                name=(nameTextField.getText());
            else
                throw new Exception();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Name Error", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            if(shortNameTextField.getText().length()==5)
                shortName=(shortNameTextField.getText());
            else    
                throw new Exception();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "short name Error", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            coefficient=(Integer.parseInt(coeffTextField.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Coefficient Error", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(splitMathCheckBox.isSelected()==true){
            parent.splitMath=true;
            parent.mathName = nameTextField.getText();
            try {
                parent.mathICoeff = Integer.parseInt(iCoeffTextField.getText());
                parent.mathIICoeff = Integer.parseInt(iiCoeffTextField.getText());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Math Coefficient Error", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //adds variables to arrayLists
        parent.subjectArrayList.set(subjectNumber,new Subject(name,shortName,coefficient));
        parent.update();
        this.dispose();
    }
    

 //----------------------------- from stack overflow:----------------------------------------------------------------------------------------------
    private void scaleCheckBoxIcon(JCheckBox checkbox){
        boolean previousState = checkbox.isSelected();
        checkbox.setSelected(false);
        FontMetrics boxFontMetrics =  checkbox.getFontMetrics(checkbox.getFont());
        Icon boxIcon = UIManager.getIcon("CheckBox.icon");
        BufferedImage boxImage = new BufferedImage(
            boxIcon.getIconWidth(), boxIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB
        );
        Graphics graphics = boxImage.createGraphics();
        try{
            boxIcon.paintIcon(checkbox, graphics, 0, 0);
        }finally{
            graphics.dispose();
        }
        ImageIcon newBoxImage = new ImageIcon(boxImage);
        Image finalBoxImage = newBoxImage.getImage().getScaledInstance(
            boxFontMetrics.getHeight(), boxFontMetrics.getHeight(), Image.SCALE_SMOOTH
        );
        checkbox.setIcon(new ImageIcon(finalBoxImage));

        checkbox.setSelected(true);
        Icon checkedBoxIcon = UIManager.getIcon("CheckBox.icon");
        BufferedImage checkedBoxImage = new BufferedImage(
            checkedBoxIcon.getIconWidth(),  checkedBoxIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB
        );
        Graphics checkedGraphics = checkedBoxImage.createGraphics();
        try{
            checkedBoxIcon.paintIcon(checkbox, checkedGraphics, 0, 0);
        }finally{
            checkedGraphics.dispose();
        }
        ImageIcon newCheckedBoxImage = new ImageIcon(checkedBoxImage);
        Image finalCheckedBoxImage = newCheckedBoxImage.getImage().getScaledInstance(
            boxFontMetrics.getHeight(), boxFontMetrics.getHeight(), Image.SCALE_SMOOTH
        );
        checkbox.setSelectedIcon(new ImageIcon(finalCheckedBoxImage));
        checkbox.setSelected(false);
        checkbox.setSelected(previousState);
    }

}
