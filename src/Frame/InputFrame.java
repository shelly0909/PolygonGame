package Frame;

import Manager.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//这个是用来记录输入的画板
public class InputFrame {
	private int width = 300;
	private int height = 500;

	public JPanel init() {
		JPanel input = new JPanel();
		input.setPreferredSize(new Dimension(width, height));
		input.setBackground(Color.decode("#F18F01"));
		JButton button = new JButton("撤回");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				GameController.getInstance().recall();
			}
		});
		input.add(button);
		return input;
	}

}
