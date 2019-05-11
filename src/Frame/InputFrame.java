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
		JButton show = new JButton("best");
		show.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				GameController.getInstance().createBest();
			}
		});
		input.add(show);
		JButton reset = new JButton("reset");
		reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				GameController.getInstance().reset();
			}
		});
		input.add(reset);

		JButton history = new JButton("history");
		history.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				GameController.getInstance().showHistoryWin();
			}
		});
		input.add(history);

		JButton clearHistory = new JButton("clearHistory");
		clearHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				GameController.getInstance().clearHis();
			}
		});
		input.add(clearHistory);

		return input;
	}

}
