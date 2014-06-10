package org.vaadin.addons.buttongroup;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class Test {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);

		ButtonGroup buttonGroup = new ButtonGroup();

		JButton button1 = new JButton("JButton 1");
		JButton button2 = new JButton("JButton 2");
		JButton button3 = new JButton("JButton 3");
		JButton button4 = new JButton("JButton 4");

		JToggleButton toggleButton1 = new JToggleButton("Toggle 1");
		JToggleButton toggleButton2 = new JToggleButton("Toggle 2");
		JToggleButton toggleButton3 = new JToggleButton("Toggle 3");
		JToggleButton toggleButton4 = new JToggleButton("Toggle 4");

		buttonGroup.add(button1);
		buttonGroup.add(toggleButton1);
		buttonGroup.add(button2);
		buttonGroup.add(toggleButton2);
		buttonGroup.add(button3);
		buttonGroup.add(toggleButton3);
		buttonGroup.add(button4);
		buttonGroup.add(toggleButton4);

		JPanel panel = new JPanel(new FlowLayout());
		panel.add(button1);
		panel.add(toggleButton1);
		panel.add(button2);
		panel.add(toggleButton2);
		panel.add(button3);
		panel.add(toggleButton3);
		panel.add(button4);
		panel.add(toggleButton4);

		frame.setContentPane(panel);

		frame.setVisible(true);
	}

}
