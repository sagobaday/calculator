import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * A graphical user interface for the calculator. No calculation is being
 * done here. This class is responsible just for putting up the display on
 * screen. It then refers to the "CalcEngine" to do all the real work.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class UserInterface
        implements ActionListener
{
    private final CalcEngine calc;
    private boolean showingAuthor;
    private boolean hexMode;

    private JFrame frame;
    private JTextField display;
    private JLabel status;

    /**
     * Create a user interface.
     * @param engine The calculator engine.
     */
    public UserInterface(CalcEngine engine)
    {
        calc = engine;
        showingAuthor = true;
        makeFrame();
        frame.setVisible(true);
        hexMode = false;
        toggleHexButtons();
    }

    /**
     * Make the frame for the user interface.
     */

    private void makeFrame()
    {
        frame = new JFrame(calc.getTitle());

        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setLayout(new BorderLayout(8, 8));
        contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));

        display = new JTextField();
        contentPane.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 4));
        String[] buttonLabels = {
            "7", "8", "9", "Clear",
            "4", "5", "6", "?",
            "1", "2", "3", "0",
            "%", "", "", "=",
            "+", "-", "/", "*",
            "A", "B", "C", "",
            "D", "E", "F", "HEX"
        };
        
        for (String label : buttonLabels) {
            addButton(buttonPanel, label);
        }

        contentPane.add(buttonPanel, BorderLayout.CENTER);

        status = new JLabel(calc.getAuthor());
        contentPane.add(status, BorderLayout.SOUTH);

        frame.pack();
    }

    /**
     * Add a button to the button panel.
     * @param panel The panel to receive the button.
     * @param buttonText The text for the button.
     */
    private void addButton(Container panel, String buttonText)
    {
        JButton button = new JButton(buttonText);
        button.addActionListener(this);
        panel.add(button);
    }

    /**
     * An interface action has been performed.
     * Find out what it was and handle it.
     * @param event The event that has occurred.
     */
    public void actionPerformed(ActionEvent event)
    {
        String command = event.getActionCommand();

        switch(command) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                int number = Integer.parseInt(command);
                calc.numberPressed(number);
            }
            case "A", "B", "C", "D", "E", "F" -> {
            	int number = 0;
            	
            	switch(command) {
            		case "A" -> number = 10;
            		case "B" -> number = 11;
            		case "C" -> number = 12;
            		case "D" -> number = 13;
            		case "E" -> number = 14;
            		case "F" -> number = 15;
            	}
            	
            	calc.numberPressed(number);
            }
            case "HEX" -> {
            	calc.switchMode();
            	hexMode = !hexMode;
            	toggleHexButtons();
            }
            case "+", "-", "/", "*", "%" -> calc.applyOperator(command.toCharArray()[0]);
            case "=" -> calc.equals();
            case "Clear" -> calc.clear();
            case "?" -> showInfo();
        }

        // else unknown command.

        redisplay();
    }

    private void toggleHexButtons() {
    	JPanel buttonPanel = (JPanel) frame.getContentPane().getComponent(1);
    	
    	for(int i = buttonPanel.getComponents().length - 2; i >= 20; i--)
    		buttonPanel.getComponent(i).setEnabled(hexMode);
    }
    
    /**
     * Update the interface display to show the current value of the
     * calculator.
     */
    private void redisplay()
    {
        if (!hexMode)
        	display.setText(String.valueOf(calc.getDisplayValue()));
        else
        	display.setText(Long.toHexString(Math.round(calc.getDisplayValue())));
    }

    /**
     * Toggle the info display in the calculator's status area between the
     * author and version information.
     */
    private void showInfo()
    {
        if(showingAuthor)
            status.setText(calc.getVersion());
        else
            status.setText(calc.getAuthor());

        showingAuthor = !showingAuthor;
    }
}
