/*
 * MainFrame is the class with the Main JFrame of the app
 * the constructor is called in the begining and after it the loop() function is called in an infinite loop
 * changeOptionsFrame() is used to see if the laft options frame is visible or not
 * doOnCLose(String path) is used to save the values to a file at "path", when closing the programm
 * doOnClose() is called when exiting the program 
 * 
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;
public class MainFrame extends JFrame{
    //ArrayList where  all the data from the exams is saved
    public static ArrayList<SemData> examData = new ArrayList<>();

    //GUI ELements
    private JLabel leftBorder = new JLabel("");
    private JLabel topBorder = new JLabel("");
    private JLabel classNameLabel = new JLabel("");
    private JLabel averageLabel = new JLabel("");
    private JButton optionsButton = new JButton("≡");
    private JButton sem1Button = new JButton("Semester 1");
    private JButton sem2Button = new JButton("Semester 2");
    private JButton sem3Button = new JButton("Semester 3");
    private JButton totalButton = new JButton("Total");
    private JLabel classExplainLabel = new JLabel("",SwingConstants.CENTER);
    public FileChooser classFileChooser = new FileChooser("Select Class File", 10);
    public JButton createClassFile = new JButton("Create class file");
    private ArrayList<SubjectField> subjectFieldArrayList = new ArrayList<>();

    //Constants and class variablles
    private static final double LEFT_BORDER_SIZE = 0.04;
    private static final int LEFT_BORDER_MIN = 40;
    private static final double TOP_BORDER_SIZE = 0.15;
    private static final int TOP_BORDER_MIN = 75;
    private static final double DISTANCE = 0.02;
    private static final double TOP_BUTTONS = 0.4;
    private static int X_COUNTER = 4;
    private static int Y_COUNTER = 3;
    private boolean isOptionFrame = false;
    private boolean isOptionsFrameGettingBigger = false;
    private boolean isOptionsFrameGettingSmaller = false;
    private long last = 0;
    private int currentLeftWidth = 0;

    //object of the classes Data and FIleIo
    public static Data data = new Data();
    public static FileIo fileIO = new FileIo();


    public MainFrame(){
        //initialisation of the instances from data and fileIO
        data.begin();
        fileIO.begin();

        //setting up MainFrame
        this.setMinimumSize(new Dimension(400,300));
        this.setVisible(true);
        this.setLayout(null);
        this.setBounds(100,100,1500,700); // default size
        this.setExtendedState(data.getFullScreen()); 
        this.setBounds(data.getX(), data.getY(), data.getWidth(), data.getHeight()); // previous size
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("LëtzPunkten");
        getContentPane().setBackground(data.backgroundLightColor);

        ImageIcon icon = new ImageIcon("doc//icon.png");
        this.setIconImage(icon.getImage());

        //is called when closing the programm
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            doOnClose();
        }, "Shutdown-thread"));

        //usually the fileIO object does this but if there is no .json file this constructor does it
        if(MainFrame.examData.isEmpty()){
            for(int i=0; i<=MainFrame.data.getSemCounter(); i++){
                MainFrame.examData.add(new SemData(i));
            }
        }
        if(data.subjectArrayList.size()<=6){
            X_COUNTER=3;
            Y_COUNTER=2;
        }else if(data.subjectArrayList.size()<=9){
            X_COUNTER=3;
            Y_COUNTER=3;
        }else if(data.subjectArrayList.size()<=12){
            X_COUNTER=4;
            Y_COUNTER=3;
        }else{
            Y_COUNTER = (int)Math.sqrt(data.subjectArrayList.size());
            X_COUNTER = data.subjectArrayList.size()/(Y_COUNTER%1==0?Y_COUNTER:Y_COUNTER+1);
        }
        //creates the buttons for every subject
        int i=0;
        for(int y=0; y<Y_COUNTER; y++){
            for(int x=0; x<X_COUNTER; x++){
                if(i>=data.subjectArrayList.size())
                    break;
                SubjectField tempField = new SubjectField();
                subjectFieldArrayList.add(tempField);
                tempField.setText(data.subjectArrayList.get(i).getName());
                tempField.setFont(new Font("Arial", Font.BOLD, data.getTextSize()));
                tempField.setBackground(data.subjectFieldLightColor);
                tempField.setBorder(BorderFactory.createLineBorder(Color.black));
                this.add(tempField);
                i++;
            }    
        }
        //adds actionListener to the buttons for every subject
        for(int j=0; j<subjectFieldArrayList.size(); j++){ 
            final int l = j;
            subjectFieldArrayList.get(j).addActionListener(e -> {
                new SubjectFrame(l, this);
                System.out.println(3);
            });
        }

        //label with className        
        this.add(classNameLabel); 
        classNameLabel.setLocation(topBorder.getX()+(int)(0.03*data.getWidth()), (int)(0.01*data.getHeight())); 
        classNameLabel.setSize(classNameLabel.getPreferredSize());
        classNameLabel.setText(data.getClassName()); 
        
        //Label with the main average
        averageLabel.setSize(averageLabel.getPreferredSize());
        averageLabel.setLocation(topBorder.getX()+topBorder.getWidth()-(int)(0.03*data.getWidth()), (int)(0.01*data.getHeight())); 
        averageLabel.setText(String.valueOf(58.0));
        this.add(averageLabel);
          
        //buttons for semester or trimester
        this.add(sem1Button);
        this.add(sem2Button);
        this.add(sem3Button);
        this.add(totalButton);
        sem1Button.setOpaque(true);
        sem2Button.setOpaque(true);
        sem3Button.setOpaque(true);
        totalButton.setOpaque(true);
        sem1Button.setBorder(BorderFactory.createLineBorder(Color.black));
        sem2Button.setBorder(BorderFactory.createLineBorder(Color.black));
        sem3Button.setBorder(BorderFactory.createLineBorder(Color.black));
        totalButton.setBorder(BorderFactory.createLineBorder(Color.black));
        sem1Button.setBackground(new Color(255,255,255));
        sem2Button.setBackground(new Color(255,255,255));
        sem3Button.setBackground(new Color(255,255,255));
        totalButton.setBackground(new Color(255,255,255));
        sem1Button.addActionListener(e -> {
            data.setCurrentSem(1);
            examData.get(1).updateAllAverage();
        });
        sem2Button.addActionListener(e -> {
            data.setCurrentSem(2);
            examData.get(1).updateAllAverage();
        });
        sem3Button.addActionListener(e -> {
            data.setCurrentSem(3);
            examData.get(1).updateAllAverage();
        });
        totalButton.addActionListener(e -> {
            data.setCurrentSem(0);
            examData.get(1).updateAllAverage();
        });


        //top white Border
        this.add(topBorder);
        topBorder.setBounds((int)(LEFT_BORDER_SIZE*data.getWidth()),0,data.getWidth(), (int)(TOP_BORDER_SIZE*data.getHeight()));
        topBorder.setOpaque(true);
        topBorder.setBorder(BorderFactory.createLineBorder(Color.black));
        
        //options button on the top left
        this.add(optionsButton);
        optionsButton.setOpaque(false);
        optionsButton.setBorder(BorderFactory.createLineBorder(Color.black));
        optionsButton.setBackground(new Color(255,255,255));
        optionsButton.addActionListener(e -> {
            changeOptionsFrame();
        });

        //label over the classFileChoser
        this.add(classExplainLabel);
        classExplainLabel.setOpaque(false);
        classExplainLabel.setText("Import .json File");

        //button to select class file
        this.add(classFileChooser);
        classFileChooser.setOpaque(false);
        classFileChooser.setBorder(BorderFactory.createLineBorder(Color.black));
        classFileChooser.setFont(new Font("Arial", Font.BOLD, classFileChooser.getWidth()>= 50? 20 : 10));
        classFileChooser.setBackground(Color.WHITE);

        //button to open the create class window
        this.add(createClassFile);
        createClassFile.setOpaque(true);
        createClassFile.setBorder(BorderFactory.createLineBorder(Color.black));
        createClassFile.setBackground(new Color(255,255,255));
        createClassFile.setFont(new Font("Arial", Font.BOLD, classFileChooser.getWidth()>= 50? 20 : 10));
        createClassFile.addActionListener(e -> 
            new newClassData()
        );
        //Left White Border 
        this.add(leftBorder);
        leftBorder.setBounds(0,0,(int)(LEFT_BORDER_SIZE*data.getWidth()), data.getHeight());
        leftBorder.setOpaque(true); 
        leftBorder.setBorder(BorderFactory.createLineBorder(Color.black));

        //Updates the average of all the subjects
        try {
            if(data.getSemCounter() >=1)
                examData.get(1).updateAllAverage();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unfortunately you got an Error: "+e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void loop(){
        //Width and Height without left and top border
        int usableWidth = data.getWidth()-(int)(data.getWidth()*DISTANCE)-leftBorder.getWidth()-10;
        int usableHeight = data.getHeight()-topBorder.getHeight()-35-(int)(DISTANCE*data.getHeight());

        //saving current size to data class
        data.setWidth(this.getWidth());
        data.setHeight(this.getHeight());
        data.setX(this.getX());
        data.setY(this.getY());
        data.setFullScreen(this.getExtendedState());

        //Setting size and text of all fields
        leftBorder.setBounds(0,0, currentLeftWidth,data.getHeight());
        topBorder.setBounds(leftBorder.getWidth(),0,data.getWidth(), (int)(TOP_BORDER_SIZE*data.getHeight())>=TOP_BORDER_MIN ? (int)(TOP_BORDER_SIZE*data.getHeight()) : TOP_BORDER_MIN);
        classNameLabel.setLocation(topBorder.getX()+(int)(0.02*data.getWidth()), (int)(0.02*data.getHeight())); 
        classNameLabel.setSize(classNameLabel.getPreferredSize());
        classNameLabel.setFont(new Font("Arial" , Font.BOLD, (int)(topBorder.getHeight()*0.6)));

        averageLabel.setSize(averageLabel.getPreferredSize());
        averageLabel.setLocation(data.getWidth()-averageLabel.getWidth()-(int)(0.05*data.getWidth()), (int)(0.01*data.getHeight())); 
       
        if(data.getSemCounter()<=1){
            data.setCurrentSem(0);
        }
        if(data.getSemCounter()<data.getCurrentSem())
            data.setCurrentSem(0);

        if(data.getCurrentSem()==0){
            averageLabel.setText(examData.get(data.getCurrentSem()).getTotalAverageString());
        }else{
            averageLabel.setText(examData.get(data.getCurrentSem()).getTotalSemAverageString());
        }
        averageLabel.setFont(new Font("Arial" , Font.BOLD, (int)(topBorder.getHeight()*0.8)));
        

        switch (data.getSemCounter()) {
            case 0 ->{
                break;
            }
            case 2 -> {
                sem1Button.setBounds(leftBorder.getWidth()+(int)(usableWidth*TOP_BUTTONS), (int)(0.1*topBorder.getHeight()), (int)((1-2*TOP_BUTTONS)*usableWidth/(data.getSemCounter()+1)), (int)(0.8*topBorder.getHeight()));
                sem2Button.setBounds(sem1Button.getX()+sem1Button.getWidth(), sem1Button.getY(), sem1Button.getWidth(), sem1Button.getHeight());
                totalButton.setBounds(sem2Button.getX()+sem1Button.getWidth(), sem1Button.getY(), sem1Button.getWidth(), sem1Button.getHeight());
                totalButton.setFont(new Font("Arial", Font.BOLD, totalButton.getWidth()/5));
                if(sem1Button.getWidth()< 70){
                    sem1Button.setFont(new Font("Arial", Font.BOLD, sem1Button.getWidth()/2));
                    sem1Button.setText("1");
                    sem2Button.setFont(new Font("Arial", Font.BOLD, sem1Button.getWidth()/2));
                    sem2Button.setText("2");
                }else{
                    sem1Button.setFont(new Font("Arial", Font.BOLD, sem1Button.getWidth()/7));
                    sem1Button.setText("Semester 1");
                    sem2Button.setFont(new Font("Arial", Font.BOLD, sem2Button.getWidth()/7));
                    sem2Button.setText("Semester 2");
                }
            }
            case 3 -> {
                sem1Button.setBounds(leftBorder.getWidth()+(int)(usableWidth*TOP_BUTTONS), (int)(0.1*topBorder.getHeight()), (int)((1-2*TOP_BUTTONS)*usableWidth/(data.getSemCounter()+1)), (int)(0.8*topBorder.getHeight()));
                sem2Button.setBounds(sem1Button.getX()+sem1Button.getWidth(), sem1Button.getY(), sem1Button.getWidth(), sem1Button.getHeight());
                sem3Button.setBounds(sem2Button.getX()+sem1Button.getWidth(), sem1Button.getY(), sem1Button.getWidth(), sem1Button.getHeight());
                totalButton.setBounds(sem3Button.getX()+sem1Button.getWidth(), sem1Button.getY(), sem1Button.getWidth(), sem1Button.getHeight());
                totalButton.setFont(new Font("Arial", Font.BOLD, totalButton.getWidth()/5));
                if(sem1Button.getWidth()< 60){
                    sem1Button.setFont(new Font("Arial", Font.BOLD, sem1Button.getWidth()/2));
                    sem1Button.setText("1");
                    sem2Button.setFont(new Font("Arial", Font.BOLD, sem2Button.getWidth()/2));
                    sem2Button.setText("2");
                    sem3Button.setFont(new Font("Arial", Font.BOLD, sem3Button.getWidth()/2));
                    sem3Button.setText("3");
                }else{
                    sem1Button.setFont(new Font("Arial", Font.BOLD, sem1Button.getWidth()/7));
                    sem1Button.setText("Trimester 1");
                    sem2Button.setFont(new Font("Arial", Font.BOLD, sem2Button.getWidth()/7));
                    sem2Button.setText("Trimester 2");
                    sem3Button.setFont(new Font("Arial", Font.BOLD, sem3Button.getWidth()/7));
                    sem3Button.setText("Trimester 3");
                }
            }
            default -> {
                JOptionPane.showMessageDialog(null, "strange semCount: " + data.getSemCounter(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        switch (data.getCurrentSem()) {
            case 1 -> {
                sem1Button.setBackground(new Color(200,200,200));
                sem2Button.setBackground(new Color(255,255,255));
                sem3Button.setBackground(new Color(255,255,255));
                totalButton.setBackground(new Color(255,255, 255));
                sem1Button.setOpaque(true);
            }
            case 2 -> {
                sem1Button.setBackground(new Color(255,255,255));
                sem2Button.setBackground(new Color(200,200,200));
                sem3Button.setBackground(new Color(255,255,255));
                totalButton.setBackground(new Color(255,255, 255));
                sem2Button.setOpaque(true);
            }
            case 3 -> {
                sem1Button.setBackground(new Color(255,255,255));
                sem2Button.setBackground(new Color(255,255,255));
                sem3Button.setBackground(new Color(200,200,200));
                totalButton.setBackground(new Color(255,255, 255));
                sem3Button.setOpaque(true);
            }
            case 0 -> {
                sem1Button.setBackground(new Color(255,255,255));
                sem2Button.setBackground(new Color(255,255,255));
                sem3Button.setBackground(new Color(255,255,255));
                totalButton.setBackground(new Color(200,200, 200));
                totalButton.setOpaque(true);
            }
            default -> {
            }
        }
        
        int oBS = (int)(0.8*data.getWidth()*LEFT_BORDER_SIZE);
        int oBL = (int)(0.1*data.getWidth()*LEFT_BORDER_SIZE);
        if(data.getWidth()*LEFT_BORDER_SIZE < LEFT_BORDER_MIN){
            oBS = (int)(LEFT_BORDER_MIN*0.8);
            oBL = (int)(LEFT_BORDER_MIN *0.1);
        }
        optionsButton.setSize(oBS,oBS);
        optionsButton.setLocation(oBL,oBL);
        optionsButton.setFont(new Font("Arial", Font.PLAIN, (int)(0.6*(optionsButton.getWidth()))));

        if(isOptionFrame){
            classFileChooser.setVisible(true);
            classFileChooser.setLocation(oBL, 4*oBL+3*oBS);
            classFileChooser.setSize(new Dimension(leftBorder.getWidth()-2*oBL, oBS));
            classFileChooser.setBackground(Color.WHITE);
            classFileChooser.setOpaque(true);
            classFileChooser.setFont(new Font("Arial", Font.BOLD, classFileChooser.getWidth()/10));//>= 200? classFileChooser.getWidth()>=600? 40: 20 : 10));

            classExplainLabel.setVisible(true);
            classExplainLabel.setLocation(oBL, 2*oBL+oBS);
            classExplainLabel.setSize(new Dimension(leftBorder.getWidth()-2*oBL,2* oBS));
            classExplainLabel.setFont(new Font("Arial", Font.BOLD, classFileChooser.getWidth()/10));

            createClassFile.setVisible(true);
            createClassFile.setLocation(oBL, 6*oBL+5*oBS);
            createClassFile.setSize(new Dimension(leftBorder.getWidth()-2*oBL,oBS));
            createClassFile.setFont(new Font("Arial", Font.BOLD, classFileChooser.getWidth()/10));

        }else{
            classFileChooser.setVisible(false);
            classExplainLabel.setVisible(false);
            createClassFile.setVisible(false);
        }
        
        int i=0;
        double textCoeficcient=0;
        for(int y=0; y<Y_COUNTER; y++){
            for(int x=0; x<X_COUNTER; x++){
                if(i>=subjectFieldArrayList.size()){
                    break;
                }
                int tempX = leftBorder.getWidth()+(int)(usableWidth*DISTANCE)+x*(subjectFieldArrayList.get(i).getWidth()+(int)(usableWidth*DISTANCE));
                int tempY = topBorder.getHeight()+(int)(usableHeight*DISTANCE)+y*(subjectFieldArrayList.get(i).getHeight()+(int)(usableHeight*DISTANCE));
                int tempWidth = usableWidth/X_COUNTER-(int)(usableWidth*DISTANCE);
                int tempHeight = usableHeight/Y_COUNTER-(int)(usableHeight*DISTANCE);
                subjectFieldArrayList.get(i).setBounds(tempX, tempY,tempWidth, tempHeight);
                subjectFieldArrayList.get(i).setFont(new Font("Arial", Font.BOLD, data.getTextSize())); 
                if(subjectFieldArrayList.get(i).getWidth()<300){
                    subjectFieldArrayList.get(i).setText(data.subjectArrayList.get(i).getShortName(), i);
                    textCoeficcient=0.12;
                }else{
                    subjectFieldArrayList.get(i).setText(data.subjectArrayList.get(i).getName(), i);
                    textCoeficcient=0.08;
                }
                i++; 
            }    
        }
        data.setTextSize((int)(subjectFieldArrayList.get(0).getWidth()*textCoeficcient));
        if(!isOptionFrame){
            currentLeftWidth=(int)(LEFT_BORDER_SIZE*data.getWidth())>=LEFT_BORDER_MIN?(int)(LEFT_BORDER_SIZE*data.getWidth()):LEFT_BORDER_MIN;
        }else if(isOptionFrame && !isOptionsFrameGettingBigger && !isOptionsFrameGettingSmaller){
            currentLeftWidth = (int)(data.getWidth()*0.2);
        }
        //changing size of optionsFrame
        if(isOptionsFrameGettingBigger){
            if(currentLeftWidth<=data.getWidth()*0.2 && System.currentTimeMillis()-last>=1){
                currentLeftWidth+=20;
                last = System.currentTimeMillis();
            }
            if(currentLeftWidth>=data.getWidth()*0.2){
                isOptionsFrameGettingBigger = false;
            }
        }
        if(isOptionsFrameGettingSmaller){
            if(currentLeftWidth>=data.getWidth()*LEFT_BORDER_SIZE && System.currentTimeMillis()-last>=1){
                currentLeftWidth-=20;
                last = System.currentTimeMillis();
            }
            if(currentLeftWidth<=data.getWidth()*LEFT_BORDER_SIZE){
                isOptionsFrameGettingSmaller =false;
                isOptionFrame =false;
            }
        }
        //restarts the app if the filepath of .josn file changed
        String tempPath=classFileChooser.getPath();
        if(tempPath != null&&!"".equals(tempPath)){
            data.setCurrentSem(0);
            doOnClose(tempPath);
            LetzPunkten.repeat=false;
        }
    }
    //is called when pressing optioons button
    private void changeOptionsFrame(){
        if(isOptionFrame){
            isOptionsFrameGettingBigger = false;
            isOptionsFrameGettingSmaller = true;
        }else{
            isOptionFrame =true;
            isOptionsFrameGettingBigger = true; 
            isOptionsFrameGettingSmaller = false;
        }
    }
    //is called on close
    public static void doOnClose(String tempPath){
        fileIO.onClose();
        data.setClassFile(tempPath);
        data.writeFileOnClose(data.getWidth(), data.getHeight(), data.getX(), data.getY(), data.getFullScreen(), data.getTextSize(), data.getCurrentSem());
    }
    //is called on restart
    private void doOnClose(){
        fileIO.onClose();
        data.writeFileOnClose(data.getWidth(), data.getHeight(), data.getX(), data.getY(), data.getFullScreen(), data.getTextSize(), data.getCurrentSem());
    }
}
