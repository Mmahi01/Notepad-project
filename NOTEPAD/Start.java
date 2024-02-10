import Classes.F_frame;
import javax.swing.JFrame;

public class Start {
    public static void main(String[] args) {
        F_frame frame = new F_frame();													 // Creating an instance of F_frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 							// Setting close operation
        frame.setVisible(true);															// Setting frame visibility
    }
}

