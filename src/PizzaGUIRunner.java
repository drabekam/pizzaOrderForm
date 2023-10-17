import javax.swing.*;

public class PizzaGUIRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PizzaGUIFrame();
            }
        });
    }
}
