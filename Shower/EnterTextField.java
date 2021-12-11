import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EnterTextField extends JFrame implements ActionListener {
  private static JTextField enterTextField;
  public static String name;
  public static boolean done;

  public EnterTextField() {
		super("You got a top 10 high score!");

    setLayout(new FlowLayout());
    add(new JLabel("Please enter your name:"));
    enterTextField = new JTextField(30);
    enterTextField.addActionListener(this);
    add(enterTextField);
    add(new JLabel("Press 'Enter' when you're done"));

    done = false;
    setBounds(EZ.getWindowWidth() / 2 - 250, EZ.getWindowHeight() / 2 - 50, 500, 100);
		setVisible(true);
	}

  @Override
  public void actionPerformed(ActionEvent e) {
    name = enterTextField.getText();
    done = true;
    dispose();
  }
}
