import javax.swing.*;

public class PizzaGUIRunner {
    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PizzaGUIFrame();
            }
        });
    }
}
