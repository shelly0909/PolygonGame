package Frame;

import javax.swing.*;
import java.awt.*;

//这个是用来记录输入的画板
public class InputFrame {
	private int width = 300;
	private int height = 500;

	public JPanel init() {
		JPanel input = new JPanel();
		input.setPreferredSize(new Dimension(width, height));
		input.setBackground(Color.decode("#F18F01"));
		return input;
	}

}
