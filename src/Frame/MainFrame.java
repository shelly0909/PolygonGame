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

	private InputFrame inputFrame;

	/**
	 * 初始化界面
	 */
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

		// 创建面板
		beforePanel = new BackGroundPanel("src/bg/polygon.jpg");
		beforePanel.add(Welcome);
		beforePanel.add(btn_start);
		beforePanel.setLayout(null);
		add(beforePanel);

		// 设置窗口可见
		setVisible(true);
	}

	/**
	 * 选择随机产生或是自定义模式
	 */
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
				initLabel(); // 初始化上下标签
				initInput(RANDOMMODE); // 初始化交互界面
				initGamePanel(RANDOMMODE,null,null); // 初始化游戏面板
			}
		});

		btn_Define.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				initLabel(); // 初始化上下标签
				iniTipsPanel(); // 初始化提示面板
				initInput(INPUTMODE); // 初始化输入面板
			}
		});
	}


	/**
	 * 初始化上下标签
	 */
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

	/**
	 * 初始化游戏面板
	 * @param mode 数据接受模式，随机或是自定义
	 * @param num 自定义模式传入节点数组，随机模式下置null
	 * @param op 自定义模式下传入操作符数据，随机模式下置null
	 */
	public void initGamePanel(int mode,int[] num,char[] op){
		GameFrame game = new GameFrame();
		if(mode==RANDOMMODE) // 判断数据接受方式
			game.randomData();
		else{
			game.InputData(num.length,num,op);
		}
		JPanel gamePanel = game.init(0);// 多边形面板
		gamePanel.setVisible(true);
		add(gamePanel, BorderLayout.WEST);
		// 初始化控制器
		GameController controller = GameController.getInstance();
		controller.init(gamePanel,game,inputFrame); // 传入
		controller.CreatehistoryWin(); // 初始化历史记录
	}

	/**
	 * 初始化输入界面
	 * @param mode 游戏模式创建交互界面，输入模式创建输入界面
	 */
	public void initInput(int mode) {
		if(mode ==RANDOMMODE){
			InputFrame input = new InputFrame();
			inputNumPanel = input.init();// 输入面板
			inputNumPanel.setVisible(true);
			this.inputFrame = input;
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

	/**
	 * 初始化提示面板
	 */
	public void iniTipsPanel(){
		TipsPanel = new JPanel();
		TipsPanel.setLayout(null);
		TipsPanel.setPreferredSize(new Dimension(450,450));
		JLabel label = new JLabel("<html><body>请输入n，节点及操作符<br/>各节点和操作符之间用英文,分割<br/>" +
				"节点和操作符的关系为：<br/>节点1，节点2...<br/>节点1顺时针的操作符，节点2顺时针的操作符<br/></body></html>");
		label.setBounds(0,0,400,400);
		TipsPanel.add(label);
		add(TipsPanel, BorderLayout.WEST);
	}

	/**
	 * 预览当前输入的界面
	 * @param n 节点个数
	 * @param num 节点数组
	 * @param op 操作符数组
	 */
	public void preview(int n,int[] num,char[] op){
		JLabel label  =(JLabel) TipsPanel.getComponent(0);
		label.setText("");
		// 如果存在前一个预览，则删除
		if(TipsPanel.getComponentCount()==2){
			TipsPanel.remove(1);
		}
		PolygonManager pm = new PolygonManager();
		JHistory history = new JHistory(pm.polygonData(n,num,op),-1);
		history.setBounds(0,0,450,450);
		TipsPanel.add(history);
		TipsPanel.repaint();
	}

	/**
	 * 输入模式下创建游戏
	 * @param n 结点数
	 * @param num 节点数组
	 * @param op 操作数组
	 */
	public void createGame(int n,int[] num,char[] op){
		this.remove(TipsPanel);
		this.remove(inputNumPanel);
		SwingUtilities.updateComponentTreeUI(this);
		initInput(RANDOMMODE);
		initGamePanel(INPUTMODE,num,op);
		this.repaint();
	}
}
