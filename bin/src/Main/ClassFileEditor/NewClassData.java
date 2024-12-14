package Main.ClassFileEditor;
/*
 * newClassData is the frame that opens when "create new class file" is pressed
 * it contains of the methods:
 *  newClassData() -> cnstructor
 *  update() -> updates the count of subjects
 *  createFile() -> saves informations to variables
 *  writeValues() -> writes those variables to new .json file
 *  scaledCheckBox() -> makes checkBoxes bigger
 */

import FileSaving.*;
import Main.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.swing.*;

import com.google.gson.Gson;


public class NewClassData extends JFrame{
    //variables to save to .json later
    public Gson gson = new Gson();
    public ArrayList<Subject> subjectArrayList = new ArrayList<>();
    public ArrayList<NewSubject> subjectLabel = new ArrayList<>();
    public int subjectCount =0;
    public String className = "";
    public int semCounter=0;
    public boolean splitMath = false;
    public int mathNumber=0;
    public int mathICoeff=0;
    public int mathIICoeff = 0;

    private String filePath = "";
    public String mathName="";

    //GUI elements
    public JPanel mainPanel = new JPanel();
    public JTextField classNameTextField = new JTextField();
    public JCheckBox semesterCheckBox = new JCheckBox("Semester");
    public JCheckBox trimesterCheckBox = new JCheckBox("Trimester");
    public JButton addSubjectButton = new JButton();
    public JButton createButton = new JButton("Create");

    public NewClassData(){
        //setting up the Frame
        this.setSize(550,500);
        this.setLocationRelativeTo(null);
        this.setTitle("Create new ClassData File");
        this.setResizable(false);
        this.setLayout(null);
        ImageIcon icon = new ImageIcon("icon.png");
        this.setIconImage(icon.getImage());

        //top title 
        JLabel titleLabel = new JLabel();
        titleLabel.setText("Create new ClassData File:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 35));
        titleLabel.setSize(450,30);
        titleLabel.setLocation((this.getWidth()-titleLabel.getWidth()) / 2, 40);
        this.add(titleLabel);

        //panel
        mainPanel.setBounds(25, 100, this.getWidth()-60, 170);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.setLayout(null);

        //label left of the textFiled: name
        JLabel nameLabel = new JLabel("Class name: ");
        nameLabel.setSize(225,40);
        nameLabel.setLocation(30,20);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 30));
        mainPanel.add(nameLabel);

        //className TextField
        classNameTextField.setSize(180, 40);
        classNameTextField.setLocation(260,20);
        classNameTextField.setFont(new Font("Arial", Font.BOLD, 30));
        mainPanel.add(classNameTextField);

        //CheckBox for semester
        scaleCheckBoxIcon(semesterCheckBox);
        semesterCheckBox.setSize(140,20);
        semesterCheckBox.setLocation(120, 75);
        semesterCheckBox.setFont(new Font("Arial", Font.BOLD, 15));
        mainPanel.add(semesterCheckBox);

        //checkBox for trimester
        scaleCheckBoxIcon(trimesterCheckBox);
        trimesterCheckBox.setSize(140,20);
        trimesterCheckBox.setLocation(260, 75);
        trimesterCheckBox.setFont(new Font("Arial", Font.BOLD, 15));
        mainPanel.add(trimesterCheckBox);

        semesterCheckBox.addActionListener(e -> trimesterCheckBox.setSelected(false));
        trimesterCheckBox.addActionListener(e -> semesterCheckBox.setSelected(false));

        //button to add a subject
        addSubjectButton.setText("+ New Subject");
        addSubjectButton.setFont(new Font("Arial", Font.BOLD, 15));
        addSubjectButton.setSize(150,40);
        addSubjectButton.setLocation((mainPanel.getWidth()-addSubjectButton.getWidth())/2, mainPanel.getHeight()-60);
        addSubjectButton.setBackground(new Color(230, 245, 250));
        addSubjectButton.addActionListener(e -> new NewSubjectFrame(this));
        mainPanel.add(addSubjectButton);

        //button to create file
        createButton.setFont(new Font("Arial", Font.BOLD, 15));
        createButton.setSize(150,40);
        createButton.setLocation((this.getWidth()-addSubjectButton.getWidth())/2, this.getHeight()-75);
        createButton.setBackground(new Color(230, 245, 250));
        createButton.addActionListener(e ->{
            createFile();
        });
        this.add(createButton);

        //updates location and sizes
        this.add(mainPanel);   
        this.setSize(this.getWidth(), mainPanel.getHeight()+mainPanel.getY()+ 100);
        createButton.setLocation((this.getWidth()-addSubjectButton.getWidth())/2, this.getHeight()-90);
        this.setVisible(true);

    }
    public void update(){
        //if there is a new subject it ads the subject to the frame
        //if a subject is deleted it is removed
        int originalHeight = 170;
        int i=0;
        for(NewSubject subject: subjectLabel){
            mainPanel.setSize(mainPanel.getWidth(), originalHeight+=45);
            subject.setBounds(40, 160+i*45, 410, 40);
            String s="";
            if(subjectArrayList.get(i).getName().equals(mathName))
                s=" I-II";
            subject.setText(" "+subjectArrayList.get(i).getName()+"  (" + subjectArrayList.get(i).getShortName() + ")  Coef.: "+subjectArrayList.get(i).getCoefficient() +s);
            subject.setSubjectNumber(i);
            mainPanel.add(subject);
            i++;
        }
        if(i==0)
            mainPanel.setSize(mainPanel.getWidth(), originalHeight);
        this.setSize(this.getWidth(), mainPanel.getHeight()+mainPanel.getY()+ 110);
        createButton.setLocation((this.getWidth()-addSubjectButton.getWidth())/2, this.getHeight()-90); 
    }
    @SuppressWarnings("empty-statement")
    private void createFile(){
        //checks the values given and creates messagedialogs when user input is false
        try {
            if(!classNameTextField.getText().isEmpty())
                className = classNameTextField.getText();
            else
                throw new NumberFormatException();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "ClassName Error", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(semesterCheckBox.isSelected()==true){
            semCounter=2;
        }else if(trimesterCheckBox.isSelected()==true){
            semCounter=3;
        }else{
            JOptionPane.showMessageDialog(null, "Select semester or trimester", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(subjectArrayList.isEmpty()){
            JOptionPane.showMessageDialog(null, "create at least 1 subject", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //updates mathNumber
        if(splitMath){
            int i=0;
            for(Subject subject: subjectArrayList){
                if(subject.getName().equals(mathName)){
                    mathNumber=i;
                    break;
                }
                i++;
            }
        }

        //creates filechooser to determine where to save the file
        String path="";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File(""));
        fileChooser.showSaveDialog(this);
        try {
           path = fileChooser.getSelectedFile().getAbsolutePath(); 
        } catch (NullPointerException e) {
            System.out.println("Not common Error");
        }
        
        //checks if the .json file already exists
        //if not it writes on the file
        //if the file already exsts it creates a dialog to ask if it should replase
        filePath = path+"\\"+classNameTextField.getText()+".json";
        if(!new File(filePath).isFile()){
            try {
                File classDataFile = new File(filePath);
                classDataFile.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "could not create file :(", "Error", JOptionPane.ERROR_MESSAGE);
            }
            writeValues();
        }else{
            JDialog replaceDialog = new JDialog();
            replaceDialog.setModal(true);
            replaceDialog.setResizable(false);
            replaceDialog.setSize(400,200);
            replaceDialog.setTitle("Replace?");
            replaceDialog.setLocationRelativeTo(this);
            replaceDialog.setLayout(null);
            JLabel textLabel = new JLabel("File already exists, replace?");
            textLabel.setFont(new Font("Arial", Font.BOLD, 22));
            textLabel.setSize(textLabel.getPreferredSize());
            textLabel.setLocation(replaceDialog.getWidth()/2 - textLabel.getWidth()/2, 30);
            replaceDialog.add(textLabel);
            JButton yesButton = new JButton("Yes");
            yesButton.setBounds(75,100, 110, 30);
            yesButton.addActionListener(l->{
                if(!new File(filePath).delete()){
                    JOptionPane.showMessageDialog(null, "could not delete old FIle", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    File classDataFile = new File(filePath);
                    classDataFile.createNewFile();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "could not create file :(", "Error", JOptionPane.ERROR_MESSAGE);
                }
                writeValues();
                replaceDialog.setVisible(false);
            });
            replaceDialog.add(yesButton);
            JButton noButton = new JButton("No");
            noButton.setBounds(215,100, 110, 30);
            noButton.addActionListener(l->{
                replaceDialog.setVisible(false);
            }); 
            replaceDialog.add(noButton);
            replaceDialog.setVisible(true);
        }
    }
    //writes vlaues to the .json file
    private void writeValues(){
        SavedFile saveFile = new SavedFile(className, semCounter, splitMath, mathNumber, mathICoeff, mathIICoeff, subjectArrayList);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            writer.write(gson.toJson(saveFile));
            writer.close();
            this.dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "FileIO Error : "+ e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        MainFrame.data.setCurrentSem(0);
        MainFrame.doOnClose(filePath);
        LetzPunkten.repeat=false;
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
