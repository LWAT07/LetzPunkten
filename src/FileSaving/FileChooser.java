package FileSaving;
/*
 * File choser to select the class Data file
 * (With the help of Tiago Santos)
 */

import java.io.File;
import javax.swing.*;
public class FileChooser extends JButton{
    private String path;
    public FileChooser(String title, int radius){
        this.setText(title);
        this.addActionListener(l->{    
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int resonse = fileChooser.showOpenDialog(null);
            if(resonse == JFileChooser.APPROVE_OPTION)            {
                path = fileChooser.getSelectedFile().getAbsolutePath();
            }
        });
        this.setVisible(true);
    } 
    public String getPath(){
        String tempPath = path;
        path="";
        if(tempPath != null)
            return tempPath.replace("\\", "\\\\");
        return null;
    }
}