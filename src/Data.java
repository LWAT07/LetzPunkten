/*
 * Class with all the information of the app, everything is called by its getter
 * 
 * begin(): to read values from properties.ini file 
 * writeFileOnClose(): to write information to properties.ini on close
 */



import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Data {
    private  int mainWidth = 1200;
    private  int mainHeight = 700;
    private  int mainX = 100;
    private  int mainY = 100;
    private  int fullScreen = 0;
    private  int textSize = 24;
    private  String className = "";
    private  String classFile = "";
    private  int semCounter = 0;
    private  int currentSem = 0;
    private  boolean splitMath = false;
    private  int mathNumber = 256;
    private  int mathICoeff = 2;
    private  int mathIICoeff = 1;
    public  Color backgroundLightColor = new Color(0x00246B);//(150,180,220);
    public  Color subjectFieldLightColor = new Color(0xCADCFC);//(160,230,220);
    public  ArrayList<Subject> subjectArrayList = new ArrayList<>();

    public void begin(){
        //creates one field to display when no file is given
        subjectArrayList.add(new Subject("", "",0));
        //creates directory in programData on Windows
        //if directory doesnt exists already it creates example file in that directory
        if(new File("C:\\ProgramData\\LetzPunkten").mkdirs())
            createExampleFile(new File("C:\\ProgramData\\LetzPunkten\\exampleFile.json"));
        //if file exists reads information from ini file else creates ini file to write values in on close
        if(!new File("C://ProgramData//LetzPunkten//properties.ini").isFile()){
            try{
                File properties = new File("C://ProgramData//LetzPunkten//properties.ini");
                properties.createNewFile();
            }catch(IOException e ){
                JOptionPane.showMessageDialog(null, "File IO Constructor: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else if(new File("C://ProgramData//LetzPunkten//properties.ini").isFile()){ 
            try {
                try (BufferedReader reader = new BufferedReader(new FileReader("C://ProgramData//LetzPunkten//properties.ini"))) {
                    mainWidth = Integer.parseInt(reader.readLine());
                    mainHeight = Integer.parseInt(reader.readLine());
                    mainX = Integer.parseInt(reader.readLine());
                    mainY = Integer.parseInt(reader.readLine());
                    fullScreen = Integer.parseInt(reader.readLine());
                    textSize = Integer.parseInt(reader.readLine());
                    classFile = reader.readLine();
                    if(!classFile.contains(".json")){
                        classFile = " ";
                    }
                    currentSem=(Integer.parseInt(reader.readLine()));
                }
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null,e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(null,"properties file doesnt exist", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void writeFileOnClose(int pWidth, int pHeight, int pX, int pY, int pExtendedState, int  pTextSize, int pCurrentSem){
        //writes all the values to the ini file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C://ProgramData//LetzPunkten//properties.ini"))) {
            writer.write(String.valueOf(pWidth)+"\n");
            writer.write(String.valueOf(pHeight)+"\n");
            writer.write(String.valueOf(pX)+"\n");
            writer.write(String.valueOf(pY)+"\n");
            writer.write(String.valueOf(pExtendedState)+"\n");
            writer.write(String.valueOf(pTextSize)+"\n");
            writer.write(classFile + "\n");
            writer.write(currentSem + "\n");
        } catch (IOException e) {
            System.out.println("FileSaving: " + e.toString());
        }
    }

    //all getters and setters
    public int getWidth(){
        return mainWidth;
    }
    public int getHeight(){
        return mainHeight;
    }
    public int getX(){
        return mainX;
    }
    public int getY(){
        return mainY;
    }
    public int getFullScreen(){
        return fullScreen;
    }
    public int getTextSize(){
        return textSize;
    }
    public int getCurrentSem(){
        return currentSem;
    }

    public void setWidth(int pWidth){
        mainWidth = pWidth;
    }
    public void setHeight(int pHeight){
        mainHeight = pHeight;
    }
    public void setX(int pX){
        mainX = pX;
    }
    public void setY(int pY){
        mainY = pY;
    }
    public void setFullScreen(int pFullScreen){
        fullScreen = pFullScreen;
    }
    public void setTextSize( int pTextSize){
        textSize = pTextSize;
    }
    public void setCurrentSem(int pCurrentSem){
        if(pCurrentSem>getSemCounter())
            currentSem=0;
        else
            currentSem = pCurrentSem;
    }


    public String getClassName(){
        return className;
    }
    public String getClassFile(){
        return classFile;
    }
    public void setClassFile(String pPath){
        classFile = pPath;
    }
    public int getSemCounter() {
        return semCounter;
    }
    public boolean isMathNow(int subjectNumber){
        if(splitMath==false)
            return false;
        return subjectNumber==mathNumber;
    }
    public int getMathNumber() {
        return mathNumber;
    }

    public boolean isSplitMath() {
        return splitMath;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setSemCounter(int semCounter) {
        this.semCounter = semCounter;
    }

    public void setSplitMath(boolean splitMath) {
        this.splitMath = splitMath;
    }

    public void setMathNumber(int mathNumber) {
        this.mathNumber = mathNumber;
    }

    public int getSubjectCount() {
        return subjectArrayList.size();
    }

    public int getMathICoeff() {
        return mathICoeff;
    }

    public int getMathIICoeff() {
        return mathIICoeff;
    }

    public void setMathICoeff(int mathICoeff) {
        this.mathICoeff = mathICoeff;
    }

    public void setMathIICoeff(int mathIICoeff) {
        this.mathIICoeff = mathIICoeff;
    }
    private void createExampleFile(File file){
        try {
           file.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), StandardCharsets.UTF_8))) {
                writer.write("{\"className\":\"Example File\",\"semCounter\":2,\"splitMath\":false,\"mathNumber\":0,\"mathICoeff\":0,\"mathIICoeff\":0,\"subjectArray\":[{\"name\":\"Example Subject\",\"shortName\":\"EXAMP\",\"coefficient\":1}],\"examArrayList\":[[[{\"name\":\"Example Exam\",\"grade\":59.0,\"maxGrade\":60}]],[[]]],\"bonuArrayList\":[[1],[0]]}");
            }

        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null,"Error while creating example File: "+ioe.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
            classFile=file.getAbsolutePath();
        }
    }
}