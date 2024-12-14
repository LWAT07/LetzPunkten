package FileSaving;
/*
 * FileIO is used to save the informations of the exams
 * (Gson is red in vsCode but it somehow works)
 * begin() is called in the constructor of the app
 * onClose() is called while closing the app
 */


import com.google.gson.Gson;

import Main.MainFrame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class FileIo{
    public Gson gson = new Gson();
    //empty instance if SavedFile
    public SavedFile constFile = null;
    
    public void begin(){
        String jsonString = "";
        //Checks if file Exists
        if(!new File(MainFrame.data.getClassFile()).isFile()){
            return;
        }
        //parses variable: jsonString with the content from json file
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(MainFrame.data.getClassFile()), "UTF-8"))){
            while(true){
                String s = reader.readLine();
                if(s==null){
                    break;
                }
                jsonString +=s; 
            }
            reader.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error while reading .json File", "Error", JOptionPane.ERROR_MESSAGE);
        }
        //creates instance of SavedFile with content from json file  and copies it to constFile
        SavedFile  savedFile;
        try {
            savedFile = gson.fromJson(jsonString, SavedFile.class);
            if(savedFile==null)
                throw new Exception();
            constFile=savedFile;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "wrong .json File", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //saves Data from json file to the Data class
        if(savedFile.getSubjectArray().length!=0){
            MainFrame.data.subjectArrayList.clear();
            MainFrame.data.subjectArrayList.addAll(Arrays.asList(savedFile.getSubjectArray()));
        }
        MainFrame.data.setClassName(savedFile.getClassName());
        MainFrame.data.setSemCounter(savedFile.getSemCounter());
        MainFrame.data.setSplitMath(savedFile.isSplitMath());
        MainFrame.data.setMathNumber(savedFile.getMathNumber());
        //Creates semData objects to the ArrayList
        MainFrame.examData.clear();
        for(int i=0; i<=MainFrame.data.getSemCounter(); i++){
            MainFrame.examData.add(new SemData(i));
        }
        //saves the exams from the .json file to the examData ArrayList
        if(savedFile.getExamArrayList()!=null){
            //exams
            for(int semI=1; semI<=MainFrame.data.getSemCounter();semI++){
                MainFrame.examData.get(semI).examArrayList=savedFile.getExamArrayList().get(semI-1);
            }
            //Bonus Points
            for(int semI=1; semI<MainFrame.data.getSemCounter();semI++){  
                MainFrame.examData.get(semI).bonusPoints=savedFile.getBonuArrayList().get(semI-1);
            }
        }
    }
    public void onClose(){
        //if there is no .json file on startup, it exits the function, 
        //this only happens when the program restarts to load a new File in this case there is no inforamtion to save
        if(constFile==null){
            return;
        }
        //reads the content of the .json file
            String tempString="";
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(MainFrame.data.getClassFile()), "UTF-8"))){
                while(true){
                    String s = reader.readLine();
                    if(s==null){
                        break;
                    }
                    tempString +=s; 
                }
                reader.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error while reading .json File", "Error", JOptionPane.ERROR_MESSAGE);
            }
        //compares the className saved on startup with the className read from the current file, to check if the file is still the same
        //if it is not the same it exits the function
        //this only happens when the program restarts to load an other .json file
        if(!constFile.getClassName().equals(gson.fromJson(tempString, SavedFile.class).getClassName())){
            return;
        }
        // saves values fro file on startup to local variables 
        //ClassData
        String className = constFile.getClassName();
        int semCounter = constFile.getSemCounter();
        boolean splitMath = constFile.isSplitMath();
        int mathNumber = constFile.getMathNumber();
        int mathICoeff = constFile.getMathICoeff();
        int mathIICoeff = constFile.getMathIICoeff();
        Subject[] subjectArray = constFile.getSubjectArray();
        //UserData
        ArrayList<ArrayList<ArrayList<Exam>>> examArrayList = new ArrayList<>();
            for(int semI=1; semI<=MainFrame.data.getSemCounter();semI++){
                examArrayList.add(MainFrame.examData.get(semI).examArrayList);
            } 
        ArrayList<ArrayList<Integer>> bonusArrayList = new ArrayList<>();
            for(int semI=1; semI<=MainFrame.data.getSemCounter();semI++){
                bonusArrayList.add(MainFrame.examData.get(semI).bonusPoints);
            } 
        //clears Data so that information isn't tranfered to new File
        //MainFrame.examData.clear();
        //creates a String with a new instance of SavedFile with the local variables
        String jsonString = gson.toJson(new SavedFile(className, semCounter, splitMath, mathNumber, mathICoeff, mathIICoeff, subjectArray, examArrayList, bonusArrayList));
        //puts the String in a .json file
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MainFrame.data.getClassFile()), StandardCharsets.UTF_8))) {
            writer.write(jsonString);
            writer.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "saving files: "+ e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
