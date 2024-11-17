
/*
 * newSubject is is the Label in the newClassData frame thet displays information of a new addet subject, and a delete button
 * subjectNumber is there to remove the subjecct from the arrayList
 * parent is the jFrame where the arraylist of the subject is saved, and parent is used to remove the label, whan deleted
 */

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class newSubject extends JLabel{
    int subjectNumber;
    public newSubject(newClassData parent){
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        JButton b = new JButton("Delete");
        b.setSize(75, 30);
        b.setLocation(330, 5);
        b.setOpaque(true);
        b.setBackground(Color.WHITE);
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        b.addActionListener(e->{
            parent.subjectArrayList.remove(subjectNumber);
            parent.subjectLabel.remove(subjectNumber);
            parent.update();
            this.setVisible(false);
        });
        this.add(b);
    }
    //to change subjectnumber, if a subject is deleted
    public void setSubjectNumber(int subjectNumber) {
        this.subjectNumber = subjectNumber;
    }
}
