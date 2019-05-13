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
	private JButton showBest; // 显示最高分
	private JButton recall; // 撤回
	private JButton reset; // 重置按钮
	private JPanel inputPanel; // 创建的输入面板

	public JPanel init() {
		JPanel input = new JPanel();
		this.inputPanel = input;
		input.setPreferredSize(new Dimension(width, height));
		input.setBackground(Color.decode("#F18F01"));

		// 撤回按钮
		recall = new JButton("撤回");
		recall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				GameController.getInstance().recall();
			}
		});
		input.add(recall);

		// 查看最高分按钮
		showBest = new JButton("best");
		showBest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				GameController.getInstance().createBest();
			}
		});
		showBest.setVisible(false);
		input.add(showBest);

		// 重置按钮
		 reset = new JButton("reset");
		reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				GameController.getInstance().reset();
			}
		});
		input.add(reset);

		// 查看历史记录按钮
		JButton history = new JButton("history");
		history.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				GameController.getInstance().showHistoryWin();
			}
		});
		input.add(history);

		// 清除所有历史记录按钮
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

	public void setBestVisible(boolean flag){
		showBest.setVisible(flag);
	}

	public JPanel getInputPanel() {
		return inputPanel;
	}

	public void setrecallEnable(boolean flag){
		recall.setEnabled(flag);
	}
	public void setResetEnable(boolean flag){
		reset.setEnabled(flag);
	}
}
