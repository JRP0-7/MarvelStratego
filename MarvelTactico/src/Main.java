import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import UI.MenuInicio;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                JFrame marco = new JFrame("Marvel Stratego");
                marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                marco.setSize(1000, 700);
                marco.setLocationRelativeTo(null);
                marco.setContentPane(new MenuInicio(marco));
                marco.setVisible(true);
                marco.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
    }
    
}
