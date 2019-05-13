package Frame;

import Element.BackGroundPanel;
import Element.JHistory;
import Manager.GameController;
import Manager.PolygonManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//这个是页面集成画板，包括上下标签和两个画板
public class MainFrame extends JFrame {
	public static final String TITLE = "算法分析与设计三级项目";// 窗口名
	public static final int WIDTH = 800;// 窗口长度
	public static final int HEIGHT = 600;// 窗口宽度
	public static final int MARGIN = 50;// 窗口宽度
	public static final int RANDOMMODE = 0;
	public static final int INPUTMODE = 1;

	public BackGroundPanel beforePanel;
	public JPanel inputNumPanel;
	public JPanel TipsPanel;



	public void beforeStartUp(){
		// 窗口初始化
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		// 设置窗口关闭按钮的默认操作(点击关闭时退出进程)
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// 把窗口位置设置到屏幕的中心
		setLocationRelativeTo(null);


		JLabel Welcome = new JLabel("欢迎来到多边形游戏");
		Welcome.setSize(300,50);
		Welcome. setLocation(250,210);
		Welcome.setFont( new Font("楷体",1,30));
		Welcome.setForeground(Color.PINK);
		JButton btn_start = new JButton("开始游戏");
		btn_start.setSize(160, 50);
		btn_start.setLocation(300, 300);
		btn_start.setFont(new Font("黑体",1,25));
		btn_start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				choose();
			}
		});
		beforePanel = new BackGroundPanel("src/bg/polygon.jpg");
		beforePanel.add(Welcome);
		beforePanel.add(btn_start);
		beforePanel.setLayout(null);
		add(beforePanel);

		// 设置窗口可见
		setVisible(true);
	}

	public void choose(){
		beforePanel.removeAll();
		SwingUtilities.updateComponentTreeUI(this);
		JButton btn_Random = new JButton("随机生成");
		JButton btn_Define = new JButton("自定义");
		Font btn = new Font("黑体",2,30);
		btn_Random.setSize(160, 50);
		btn_Random.setLocation(300, 250);
		btn_Random.setFont(btn);
		btn_Define.setSize(160, 50);
		btn_Define.setLocation(300, 350);
		btn_Define.setFont(btn);

		beforePanel.add(btn_Define);
		beforePanel.add(btn_Random);

		btn_Random.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				// 随机生成
				initLabel();
				initInput(RANDOMMODE);
				initGamePanel(RANDOMMODE,null,null);
			}
		});

		btn_Define.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				initLabel();
				iniTipsPanel();
				initInput(INPUTMODE);
			}
		});
	}


	public void initLabel(){
		this.remove(beforePanel);
		SwingUtilities.updateComponentTreeUI(this);
		setLayout(new BorderLayout());
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
		add(bottom, BorderLayout.SOUTH);
	}

	public void initGamePanel(int mode,int[] num,char[] op){
		GameFrame game = new GameFrame();
		if(mode==RANDOMMODE)
			game.randomData();
		else{
			game.InputData(num.length,num,op);
		}
		JPanel gamePanel = game.init(0);// 多边形面板
		gamePanel.setVisible(true);
		add(gamePanel, BorderLayout.WEST);
		// 初始化控制器
		GameController controller = GameController.getInstance();
		controller.init(gamePanel,game); // 传入
		controller.CreatehistoryWin(); // 初始化历史记录
	}

	public void initInput(int mode) {
		if(mode ==RANDOMMODE){
			InputFrame input = new InputFrame();
			inputNumPanel = input.init();// 输入面板
			inputNumPanel.setVisible(true);

			// 添加控件
			add(inputNumPanel, BorderLayout.EAST);
		}else if(mode ==INPUTMODE){
			InputNumFrame input = new InputNumFrame(this,(JLabel)TipsPanel.getComponent(0));
			inputNumPanel = input.init();
			inputNumPanel.setVisible(true);
			// 添加控件
			add(inputNumPanel, BorderLayout.EAST);
		}

		this.repaint();
	}

	public void iniTipsPanel(){
		TipsPanel = new JPanel();
		TipsPanel.setLayout(null);
		TipsPanel.setPreferredSize(new Dimension(450,450));
		JLabel label = new JLabel("请输入");
		label.setBounds(0,0,400,400);
		TipsPanel.add(label);
		add(TipsPanel, BorderLayout.WEST);
	}

	public void preview(int n,int[] num,char[] op){
		JLabel label  =(JLabel) TipsPanel.getComponent(0);
		label.setText("");
		PolygonManager pm = new PolygonManager();
		JHistory history = new JHistory(pm.polygonData(n,num,op),-1);
		history.setBounds(0,0,450,450);
		TipsPanel.add(history);
		TipsPanel.repaint();
	}

	public void createGame(int n,int[] num,char[] op){
		this.remove(TipsPanel);
		this.remove(inputNumPanel);
		SwingUtilities.updateComponentTreeUI(this);
		initInput(RANDOMMODE);
		initGamePanel(INPUTMODE,num,op);
		this.repaint();
	}
}
