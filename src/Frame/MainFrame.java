package Frame;

import javax.swing.*;
import java.awt.*;

//这个是页面集成画板，包括上下标签和两个画板
public class MainFrame extends JFrame {
	public static final String TITLE = "算法分析与设计三级项目";// 窗口名
	public static final int WIDTH = 800;// 窗口长度
	public static final int HEIGHT = 600;// 窗口宽度
	public static final int MARGIN = 50;// 窗口宽度

	public void initFrame() {
		InputFrame input = new InputFrame();
		GameFrame game = new GameFrame();
		JPanel inputPanel = input.init();// 输入面板
		JPanel gamePanel = game.init();// 多边形面板
		inputPanel.setVisible(true);
		gamePanel.setVisible(true);

		// 上下标签
		JLabel top = new JLabel("多边形游戏");
		JLabel bottom = new JLabel("Copyright © 2018-2019 CDM All Rights Reserved.");
		top.setOpaque(true);
		top.setBackground(Color.decode("#0A122A"));
		top.setForeground(Color.WHITE);
		top.setPreferredSize(new Dimension(WIDTH, MARGIN));
		top.setFont(new Font("微软雅黑", Font.BOLD, 18));
		top.setHorizontalAlignment(SwingConstants.CENTER); // 设置控件左右居中
		top.setVisible(true);
		bottom.setOpaque(true);
		bottom.setBackground(Color.WHITE);
		bottom.setForeground(Color.decode("#0A122A"));
		bottom.setSize(800, 50);
		bottom.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		bottom.setHorizontalAlignment(SwingConstants.CENTER); // 设置控件左右居中
		bottom.setPreferredSize(new Dimension(WIDTH, MARGIN));
		bottom.setVisible(true);

		// 添加控件
		add(top, BorderLayout.NORTH);
		add(gamePanel, BorderLayout.WEST);
		add(inputPanel, BorderLayout.EAST);
		add(bottom, BorderLayout.SOUTH);

		// 窗口初始化
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		// 设置窗口关闭按钮的默认操作(点击关闭时退出进程)
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// 把窗口位置设置到屏幕的中心
		setLocationRelativeTo(null);
		// 设置窗口可见
		setVisible(true);
		// 设置窗口布局
		setLayout(new BorderLayout());

	}
}
