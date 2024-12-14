package Main;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class LetzPunkten {
    public static boolean repeat = true;
    private static final ArrayList<MainFrame> frameList = new ArrayList<>();
    public static void main(String[] args){
        try{
            for(;;){
                MainFrame frame = new MainFrame(); 
                frameList.add(frame);
                long last=0;
                while(repeat){
                    if(System.currentTimeMillis()-last>10){
                        frameList.get(0).loop();
                        last=System.currentTimeMillis();
                    }
                }
                frameList.get(0).dispose();
                frameList.clear();
                repeat = true;
            }    
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Unfortunately an error occured please restart \n"+ e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}