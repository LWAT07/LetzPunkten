/*
 * The dialog that opens when you press create new subject, here you can select name and coefficient of subject
 * newSubjectFrame(newClassData parent) --> the constructor that initialises all of the GUI Elements
 * saveValues(newClassData parent) saves all the data to the arraylists from the parent frame 
 *  
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class newSubjectFrame  extends JDialog{
    //gui Elements
    private final JTextField nameField = new JTextField();
    private final JTextField ShortNameField = new JTextField();
    private final JTextField coeffField = new JTextField();
    private final JCheckBox splitMathCheckBox = new JCheckBox("  Split Math");
    private JTextField iCoeffField = new JTextField();
    private JTextField iiCoeffField = new JTextField(); 

    private boolean isSplitMathSelected = false;

    public newSubjectFrame(newClassData parent){
        //gui  elements
        super(parent);
        this.setTitle("new Subject");
        this.setModal(true);
        this.setResizable(false);
        this.setSize(375,340);
        this.setLocationRelativeTo(parent);
        this.setLayout(null);

        JLabel titleLabel= new JLabel("New Subject:");
        titleLabel.setBounds(100,22,400,30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 27));
        this.add(titleLabel);

        JLabel nameFieldLabel = new JLabel("Name:");
        nameFieldLabel.setBounds(40, 75, 75, 22);
        nameFieldLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(nameFieldLabel);

        nameField.setBounds(150,75, 180, 22);
        this.add(nameField);

        JLabel shortNameFieldLabel = new JLabel("Short name:");
        shortNameFieldLabel.setBounds(40, 110, 130, 22);
        shortNameFieldLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(shortNameFieldLabel);

        JLabel shortNameExplainLabel = new JLabel("(5 Characters)");
        shortNameExplainLabel.setBounds(255, 110, 150, 22);
        shortNameExplainLabel.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(shortNameExplainLabel);

        ShortNameField.setBounds(150,110, 100, 22);
        this.add(ShortNameField);

        JLabel coeffFieldLabel = new JLabel("Coefficient:");
        coeffFieldLabel.setBounds(40, 150, 150, 22);
        coeffFieldLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(coeffFieldLabel);

        coeffField.setBounds(150,150, 22, 22);
        this.add(coeffField);

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

        iCoeffField.setFont(new Font("Arial", Font.BOLD, 16));
        iCoeffField.setSize((int)iCoeffLabel.getPreferredSize().getWidth(), 30);
        iCoeffField.setLocation(15, 250);
        iCoeffField.setVisible(false);
        this.add(iCoeffField);

        JLabel iiCoeffLabel = new JLabel("Mathe II Coefficient:");
        iiCoeffLabel.setFont(new Font("Arial", Font.BOLD, 16));
        iiCoeffLabel.setSize(iiCoeffLabel.getPreferredSize());
        iiCoeffLabel.setLocation(195, 230);
        iiCoeffLabel.setVisible(false);
        this.add(iiCoeffLabel);

        iiCoeffField.setFont(new Font("Arial", Font.BOLD, 16));
        iiCoeffField.setSize((int)iiCoeffLabel.getPreferredSize().getWidth(), 30);
        iiCoeffField.setLocation(195, 250);
        iiCoeffField.setVisible(false);
        this.add(iiCoeffField);

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
                iCoeffField.setVisible(true);
                iiCoeffField.setVisible(true);
            }else{
                this.setSize(this.getWidth(),this.getHeight()-50);
                saveButton.setLocation(130, this.getHeight()-90);
                iCoeffLabel.setVisible(false);
                iiCoeffLabel.setVisible(false);
                iCoeffField.setVisible(false);
                iiCoeffField.setVisible(false);
            }    
        });
        this.add(splitMathCheckBox);

        this.setVisible(true);
    }

    private void saveValues(newClassData parent){
        //initialises variables
        String name = "";
        String shortName = "";
        int coefficient;

        //sets variables with information from textfields and makes errorframes if it is empty
        try {
            if(!nameField.getText().isEmpty())
                name=(nameField.getText());
            else
                throw new Exception();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Name Error", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            if(ShortNameField.getText().length()==5)
                shortName=(ShortNameField.getText());
            else    
                throw new Exception();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "short name Error", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            coefficient=(Integer.parseInt(coeffField.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Coefficient Error", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(splitMathCheckBox.isSelected()==true){
            parent.splitMath=true;
            parent.mathName = nameField.getText();
            try {
                parent.mathICoeff = Integer.parseInt(iCoeffField.getText());
                parent.mathIICoeff = Integer.parseInt(iiCoeffField.getText());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Math Coefficient Error", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //adds variables to arrayLists
        parent.subjectArrayList.add(new Subject(name,shortName,coefficient));
        parent.subjectLabel.add(new newSubject(parent));
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