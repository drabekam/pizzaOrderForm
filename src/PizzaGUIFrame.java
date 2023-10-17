import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

//creating the class and it in a child of the jframe class
public class PizzaGUIFrame extends JFrame {

    // Radio buttons for crust selection combo box for size check boxes for toppings
    // text area for order summary and buttons for control commands
    private JRadioButton thinCrust, regularCrust, deepDish;
    private JComboBox<String> sizeBox;
    private JCheckBox[] toppingBoxes;
    private JTextArea orderSummary;
    private JButton orderButton, clearButton, quitButton;

    // creating the variables for the prices and the tax rate
    private double[] SIZE_PRICES = {8.00, 12.00, 16.00, 20.00};
    private double TOPPING_PRICE = 1.00;
    private double TAX_RATE = 0.07;

    // creating the pizzaguiframe class
    public PizzaGUIFrame() {
        setTitle("Pizza Order");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // this is just declaring the mthods before setting them up as this was recommened and said as best principle so i thought id try it
        setupCrustOptions();
        setupSizeOptions();
        setupToppings();
        setupOrderSummary();
        setupControlButtons();

        //found that i can use pack() insterad of setting the frame size explicitly
        pack();
        setVisible(true);
    }

    // creating the crust option radio button method
    private void setupCrustOptions() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Type of Crust"));

        // Creating radio buttons for each type of crust
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDish = new JRadioButton("Deep-dish");

        // this creates a button group and makes it so that only one of the buttons can be chosen at a time
        ButtonGroup group = new ButtonGroup();
        group.add(thinCrust);
        group.add(regularCrust);
        group.add(deepDish);

        // Adding the radio buttons to the panel.
        panel.add(thinCrust);
        panel.add(regularCrust);
        panel.add(deepDish);

        // adding in the panel and setting the locaton
        add(panel, BorderLayout.NORTH);
    }

    // Method to set up a combo box for for selecting pizza size
    private void setupSizeOptions() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Size"));

        String[] sizes = {"Small", "Medium", "Large", "Super"};
        sizeBox = new JComboBox<>(sizes);
        panel.add(sizeBox);

        // adding the pannel and setting the location
        add(panel, BorderLayout.WEST);
    }

    // setting up the method to check the sizes
    private void setupToppings() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Toppings"));

        // Array of topping choices
        String[] toppings = {"Anchovies", "Eyeballs", "Toes", "Ham", "Fingernails", "Arm Hair"};
        toppingBoxes = new JCheckBox[toppings.length];

        // Create a check box for each topping, add it to the panel, and store the reference to the check box in the toppingBoxes array.
        for (int i = 0; i < toppings.length; i++) {
            toppingBoxes[i] = new JCheckBox(toppings[i]);
            panel.add(toppingBoxes[i]);
        }


        add(panel, BorderLayout.CENTER);
    }

    // creating the method that will display the summary
    private void setupOrderSummary() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Order Summary"));

        // this creates a text area that is not editable and defines the area
        orderSummary = new JTextArea(10, 40);
        orderSummary.setEditable(false);

        // Create a scroll pane wrapping the text area and add it to the panel.
        JScrollPane scrollPane = new JScrollPane(orderSummary);
        panel.add(scrollPane);


        add(panel, BorderLayout.EAST);
    }

    // this is a method that creates the methods for controls buttons
    private void setupControlButtons() {
        JPanel panel = new JPanel(); // Creates a new panel to hold the buttons


        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        // Attach event listeners to buttons which defines the action to be taken when each button is pressed
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processOrder();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm(); // resets the buttons
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmQuit(); // this shows a confirmation to quit the app
            }
        });


        panel.add(orderButton);
        panel.add(clearButton);
        panel.add(quitButton);

        add(panel, BorderLayout.SOUTH);
    }

    // this is a method to compile the order details, calculate the total cost, and display the summary
    private void processOrder() {
        // Create a StringBuilder to build the order summary text.
        StringBuilder summary = new StringBuilder();

        // Add the selected crust type to the summary.
        if (thinCrust.isSelected()) {
            summary.append("Thin Crust: $").append(SIZE_PRICES[sizeBox.getSelectedIndex()]).append("\n");
        } else if (regularCrust.isSelected()) {
            summary.append("Regular Crust: $").append(SIZE_PRICES[sizeBox.getSelectedIndex()]).append("\n");
        } else if (deepDish.isSelected()) {
            summary.append("Deep-dish Crust: $").append(SIZE_PRICES[sizeBox.getSelectedIndex()]).append("\n");
        }

        // Calculate the cost for the selected size.
        double subTotal = SIZE_PRICES[sizeBox.getSelectedIndex()];

        // Adds each selected topping and its cost to the summary.
        for (JCheckBox toppingBox : toppingBoxes) {
            if (toppingBox.isSelected()) {
                summary.append(toppingBox.getText()).append(": $").append(TOPPING_PRICE).append("\n");
                subTotal += TOPPING_PRICE;
            }
        }

        // Add a line to the summary to separate the itemized list from the totals.
        summary.append("=====================\n");

        // Calculate and display all items
        summary.append("Sub-total: $").append(String.format("%.2f", subTotal)).append("\n");
        double tax = subTotal * TAX_RATE;
        summary.append("Tax: $").append(String.format("%.2f", tax)).append("\n");
        double total = subTotal + tax;
        summary.append("---------------------\n");
        summary.append("Total: $").append(String.format("%.2f", total)).append("\n");
        summary.append("=====================\n");

        // Set the text of the order summary area to the built summary string
        orderSummary.setText(summary.toString());
    }

    // Method to rest the items to defualt
    private void resetForm() {
        // Clear all radio buttons adn check boxes and text fields
        thinCrust.setSelected(false);
        regularCrust.setSelected(false);
        deepDish.setSelected(false);
        sizeBox.setSelectedIndex(0);
        for (JCheckBox toppingBox : toppingBoxes) {
            toppingBox.setSelected(false);
        }
        //this clears summary text
        orderSummary.setText("");
    }

    // this is a method to confirm that the user wants to quit the application
    private void confirmQuit() {

        int confirmed = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to quit?", "Exit Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirmed == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
