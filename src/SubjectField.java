/*
 * is the button that every subject has on the main screen
 */
import javax.swing.JButton;


public class SubjectField extends JButton{
    //new setText method to display different text in different situations
    public void setText(String topic, int subjectCounter) {
        //topic name
        String mainString = topic;
        //if total tab is active and if average is given
        if(MainFrame.data.getCurrentSem()==0){
            if(!MainFrame.examData.get(MainFrame.data.getCurrentSem()).getTotalAverageString(subjectCounter).equals(" "))
                mainString+=":    "+MainFrame.examData.get(MainFrame.data.getCurrentSem()).getTotalAverageString(subjectCounter);
        }else{
            //if any other tab, and average is given
            if(!MainFrame.examData.get(MainFrame.data.getCurrentSem()).getAverageString(subjectCounter).equals(" "))
                mainString+=":    "+MainFrame.examData.get(MainFrame.data.getCurrentSem()).getAverageString(subjectCounter);
        }
        //if there is no valid .json file 
        if(topic.equals("")){
            super.setText("No File Given");
        }else{
            //sets text of jbutton
            super.setText(mainString);
        }
    }
}
