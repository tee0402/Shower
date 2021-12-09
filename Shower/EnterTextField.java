import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EnterTextField extends JFrame implements ActionListener {
	private static JTextField enterTextField;
  public static String name;
	public static boolean done = false;
	
	public EnterTextField() {
		super("You got a top 10 high score!");

    JLabel label1 = new JLabel("Please enter your name:");
    JLabel label2 = new JLabel("Press 'Enter' when you're done");
    enterTextField = new JTextField(30);
    enterTextField.addActionListener(this);
		
		setLayout(new FlowLayout());
		add(label1);
		add(enterTextField);
		add(label2);
    setBounds(EZ.getWindowWidth() / 2 - 250, EZ.getWindowHeight() / 2 - 50, 500, 100);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (enterTextField.getText() == null) {
			name = "";
		} else {
			name = enterTextField.getText();
		}
		done = true;
		dispose();
	}
}
