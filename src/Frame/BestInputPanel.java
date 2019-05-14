package Frame;

import Manager.BestSolutionController;
import Manager.GameController;

import javax.swing.*;

import Element.NButton;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 最高分数界面的交互窗口
 */
public class BestInputPanel {
	private int width = 300;
	private int height = 500;

	public JPanel init() {
		JPanel input = new JPanel();
		input.setLayout(null);
		input.setPreferredSize(new Dimension(width, height));
		input.setBackground(Color.decode("#F18F01"));
		// JLabel label = new JLabel("最高分方案展示");
		// label.setFont(new Font("微软雅黑", 1, 20) );
		// label.setForeground(Color.WHITE);
		// label.setLocation(50, 80);
		// label.setVisible(true);
		

		// 按钮字体设置
		Font btfont = new Font("微软雅黑", 1, 16);

		// 上一步按钮
		JButton back = new NButton("#FFFFFF", "#0A122A", "上一步");
		back.setFont(btfont);
		back.setSize(100, 40);
		back.setLocation(110, 180);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				BestSolutionController.getInstance().recall();
			}
		});
		input.add(back);

		// 下一步按钮
		JButton go = new NButton("#FFFFFF", "#0A122A", "下一步");
		go.setFont(btfont);
		go.setSize(100, 40);
		go.setLocation(110, 260);
		go.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				BestSolutionController.getInstance().showBestSolution();
			}
		});
		input.add(go);
		// input.add(label);

		return input;
	}
}
