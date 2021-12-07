package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextField extends JFrame implements ActionListener {
	private static JTextField textField;
  public static String name;
	public static boolean done;
	
	public TextField() {
		super("You got a top 10 high score!");
		
		done = false;

    JLabel label1 = new JLabel("Please enter your name:");
    JLabel label2 = new JLabel("Press 'Enter' when you're done");
		textField = new JTextField(30);
		textField.addActionListener(this);
		
		setLayout(new FlowLayout());
		add(label1);
		add(textField);
		add(label2);
		setSize(500,100);
		setLocation(EZ.getWindowWidth() / 2 - getWidth() / 2, EZ.getWindowHeight() / 2 - getHeight() / 2);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (textField.getText() == null) {
			name = "";
		}
		else {
			name = textField.getText();
		}
		done = true;
		dispose();
	}
}
