package Frame;

import Manager.GameController;

import javax.swing.*;

import Element.NButton;

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
		input.setLayout(null);
		this.inputPanel = input;
		input.setPreferredSize(new Dimension(width, height));
		input.setBackground(Color.decode("#F18F01"));
		
		//按钮字体设置
		Font btfont = new Font("微软雅黑", 1, 16);

		// 撤回按钮
		recall = new NButton("#FFFFFF","#0A122A","撤回");
		recall.setFont(btfont);
		recall.setSize(70, 40);
		recall.setLocation(110, 50);
		recall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				GameController.getInstance().recall();
			}
		});
		input.add(recall);

		// 查看最高分按钮
		showBest = new NButton("#FFFFFF","#0A122A","最高分方案");
		showBest.setFont(btfont);
		showBest.setForeground(Color.decode("#0A122A"));
		showBest.setSize(150, 40);
		showBest.setLocation(80, 230);
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
		 reset = new NButton("#FFFFFF","#0A122A","重置");
		 reset.setFont(btfont);
		 reset.setSize(70, 40);
		 reset.setLocation(110, 150);
		 reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				GameController.getInstance().reset();
			}
		});
		input.add(reset);

		// 查看历史记录按钮
		JButton history = new NButton("#FFFFFF","#0A122A","历史记录");
		history.setFont(btfont);
		history.setSize(100, 40);
		history.setLocation(80, 300);
		history.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				GameController.getInstance().showHistoryWin();
			}
		});
		input.add(history);

		// 清除所有历史记录按钮
		JButton clearHistory = new NButton("#FFFFFF","#0A122A","清空历史记录");
		clearHistory.setFont(btfont);
		clearHistory.setSize(160, 40);
		clearHistory.setLocation(80, 380);
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
