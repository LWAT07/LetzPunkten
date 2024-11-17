/*
 * Main function of the programm, repeats infinitly until repeat bit goes to false, it then restarts
 */

import java.util.ArrayList;
public class LetzPunkten {
    public static boolean repeat = true;
    private static final ArrayList<MainFrame> frameList = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        for(;;){
            MainFrame frame = new MainFrame(); 
            frameList.add(frame);
            long last=0;
            while(repeat){
                if(System.currentTimeMillis()-last>1){
                    frameList.get(0).loop();
                    last=System.currentTimeMillis();
                }
            }
            frameList.get(0).dispose();
            frameList.clear();
            repeat = true;
        }    
    }
}