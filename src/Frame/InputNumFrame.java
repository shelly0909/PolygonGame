package Frame;

import javax.swing.*;

import Element.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

//这个是用来记录输入的画板
public class InputNumFrame {
	private int width = 300;
	private int height = 500;

	private int MODE = 0;
	private final int INTEGERMODE = 0; // 整数输入模式
	private final int RANGEMODE = 1; // 范围输入模式

	private MainFrame mainFrame; // 创建本界面的父界面
	private JLabel tips; // 提示面板

	int N = -1; // 节点数
	int[] num = null; // 节点数组
	char[] op = null; // 操作符数组

	JTextField nInput; // 节点数输入组件
	JTextField numInput; // 节点输入组件
	JTextField opInput; // 操作符输入组件

	public InputNumFrame(MainFrame parent, JLabel tips) {
		this.mainFrame = parent;
		this.tips = tips;
	}

	public JPanel init() {
		JPanel input = new JPanel();
		input.setPreferredSize(new Dimension(width, height));
		input.setBackground(Color.decode("#F18F01"));

		// 输入n的组件
		JLabel input_n = new JLabel("输入n：");
		Font lab = new Font("微软雅黑", 1, 16);
		input_n.setForeground(Color.WHITE);
		nInput = new JTextField();
		
		//输入框边框设置
		Border border = new Border(Color.decode("#0A122A"),1,true);

		// 预览按钮
		JButton preview = new NButton("#FFFFFF","#0A122A","预览");
		// 确定
		JButton sure = new NButton("#FFFFFF","#0A122A","确定");
		Font btn = new Font("微软雅黑", 1, 20);

		// 输入功能单选组件
		JRadioButton radio_Integer = new JRadioButton("整数值输入");
		radio_Integer.setBackground(Color.decode("#F18F01"));
		radio_Integer.setForeground(Color.white);
		JRadioButton radio_Range = new JRadioButton("范围值输入");
		radio_Range.setBackground(Color.decode("#F18F01"));
		radio_Range.setForeground(Color.white);
		Font rad_btn = new Font("微软雅黑", 1, 16);
		
		
		// 构造单选组
		ButtonGroup group = new ButtonGroup();
		group.add(radio_Integer);
		group.add(radio_Range);
		radio_Integer.setSelected(true);
		// 数据输入组件
		numInput = new JTextField();
		numInput.setBorder(border);

		// 操作符输入框
		JLabel input_symbol = new JLabel("运算符输入");
		input_symbol.setForeground(Color.white);
		Font lab1 = new Font("微软雅黑", 1, 16);
		opInput = new JTextField();
		opInput.setBorder(border);

		// 数据随机按钮
		JButton ranNum = new NButton("#FFFFFF","#0A122A","随机");
		// 操作符随机按钮
		JButton ranChar = new NButton("#FFFFFF","#0A122A","随机");
		Font btn1 = new Font("微软雅黑", 1, 16);

		// 设置控件大小和位置
		// 输入n
		input_n.setSize(95, 40);
		input_n.setLocation(50, 20);
		input_n.setFont(lab);
		nInput.setBorder(border);
		nInput.setSize(135, 40);
		nInput.setLocation(115, 20);

		// 单选组
		radio_Integer.setSize(120, 50);
		radio_Integer.setLocation(40, 70);
		radio_Integer.setFont(rad_btn);
		radio_Range.setSize(120, 50);
		radio_Range.setLocation(160, 70);
		radio_Range.setFont(rad_btn);

		// 数据输入框
		numInput.setSize(200, 100);
		numInput.setLocation(20, 130);
		// 数据随机按钮
		ranNum.setBounds(225, 150, 70, 40);
		ranNum.setFont(btn1);

		// 符号输入框
		input_symbol.setSize(95, 50);
		input_symbol.setLocation(50, 225);
		input_symbol.setFont(lab1);
		opInput.setSize(200, 100);
		opInput.setLocation(20, 270);
		// 操作符随机按钮
		ranChar.setBounds(225, 300, 70, 40);
		ranChar.setFont(btn1);
		// 预览按钮
		preview.setSize(90, 50);
		preview.setLocation(150, 390);
		preview.setFont(btn);
		// 确认按钮
		sure.setBounds(50, 390, 90, 50);
		sure.setFont(btn);

		// 添加控件
		input.add(input_n);
		input.add(nInput);
		input.add(radio_Integer);
		input.add(radio_Range);
		input.add(numInput);
		input.add(input_symbol);
		input.add(opInput);
		input.add(ranChar);
		input.add(ranNum);
		input.add(sure);
		input.add(preview);
		input.setLayout(null);

		// 单选组点击事件
		radio_Integer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				MODE = INTEGERMODE;
			}
		});
		radio_Range.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				MODE = RANGEMODE;
			}
		});

		// 预览按钮点击事件
		preview.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				// 输入n
				if (nInput.getText().equals("")) {
					tips.setText("请输入n");
				} else if (numInput.getText().equals("")) {
					if (MODE == INTEGERMODE)
						tips.setText("请输入数字");
					else if (MODE == RANGEMODE)
						tips.setText("请输入范围");
				} else if (opInput.getText().equals("")) {
					tips.setText("请输入运算符");
				} else {
					if (dealData() != -1)
						mainFrame.preview(N, num, op);
				}
			}
		});

		// 确认按钮点击事件
		sure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (N == -1 || num == null || op == null) {
					dealData();
				}
				mainFrame.createGame(N, num, op);
			}
		});

		// 产生随机数据
		ranNum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (nInput.getText().equals("")) {
					tips.setText("请先输入N");
					return;
				} else {
					N = Integer.parseInt(nInput.getText());
					if (N > 20) {
						tips.setText("N超出范围，请重新输入");
					}
				}
				String str = "";
				if(MODE==INTEGERMODE){
					num = randomNum(-10, 20,N);
					for (int i = 0; i < N; i++) {
						if (i == N - 1) {
							str += num[i];
							break;
						}
						str += num[i] + ",";
					}
				}
				else if(MODE == RANGEMODE)
				{
					num = randomNum(-10,20,2);
					for (int i = 0; i < 2; i++) {
						if (i == 2 - 1) {
							str += num[i];
							break;
						}
						str += num[i] + ",";
					}
				}


				numInput.setText(str);
			}
		});

		// 产生随机操作符
		ranChar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (nInput.getText().equals("")) {
					tips.setText("请先输入N");
					return;
				} else {
					N = Integer.parseInt(nInput.getText());
					if (N > 20) {
						tips.setText("N超出范围，请重新输入");
					}
				}
				op = randomOp();
				String str = "";
				for (int i = 0; i < N; i++) {
					if (i == N - 1) {
						str += op[i];
						break;
					}
					str += op[i] + ",";
				}
				opInput.setText(str);
			}
		});

		return input;
	}

	/**
	 * 处理输入数据
	 * 
	 * @return 处理状态
	 */
	private int dealData() {
		N = Integer.parseInt(nInput.getText());
		if (N > 20) {
			tips.setText("N超出范围，请重新输入");
			return -1;
		}

		// 判断选择模式
		if (MODE == INTEGERMODE)
			num = getNum(numInput.getText(), N);
		else if (MODE == RANGEMODE) {
			num = getRandomNum(numInput.getText());
		}

		if (num == null) {
			tips.setText("节点数据输入出错");
			return -1;
		}
		// 获取操作符
		op = getOp(opInput.getText(), N);
		if (op == null) {
			tips.setText("节点运算符输入出错");
			return -1;
		}
		return 0;
	}

	/**
	 * 处理节点数据
	 * 
	 * @param num
	 *            节点字符串
	 * @param n
	 *            节点数
	 * @return 节点数组
	 */
	private int[] getNum(String num, int n) {
		int[] data = new int[n];

		String[] list = num.split(",");
		if (list.length != n)
			return null;
		else {
			for (int i = 0; i < n; i++) {
				data[i] = Integer.parseInt(list[i]);
			}
		}
		return data;
	}

	/**
	 * 处理操作字符串
	 * 
	 * @param op
	 *            操作符字符串
	 * @param n
	 *            操作符个数
	 * @return 操作符数组
	 */
	private char[] getOp(String op, int n) {
		char[] data = new char[n];
		String[] list = op.split(",");
		if (list.length != n) {
			return null;
		} else {
			for (int i = 0; i < n; i++) {
				data[i] = list[i].charAt(0);
				if (data[i] != '+' && data[i] != '*') {
					return null;
				}
			}
		}
		return data;
	}

	/**
	 * 范围模式下产生随机数
	 * 
	 * @param range
	 *            范围数组
	 * @return 节点数据
	 */
	private int[] getRandomNum(String range) {
		int min = 0, max = 0;
		String[] r = range.split(",");
		if (r.length != 2)
			return null;
		else {
			min = Integer.parseInt(r[0]);
			max = Integer.parseInt(r[1]);
			if (min > max) {
				int tmp = min;
				min = max;
				max = tmp;
			}
		}

		return randomNum(min, max,N);
	}

	/**
	 * 产生随机数据
	 * 
	 * @param min
	 *            下界
	 * @param max
	 *            上界
	 * @return
	 */
	public int[] randomNum(int min, int max,int N) {
		int[] num;
		num = new int[N];
		for (int i = 0; i < N; i++) {
			Random ran = new Random();
			num[i] = ran.nextInt(max - min) + min;
		}
		return num;
	}

	/**
	 * 产生随机操作符
	 * 
	 * @return 操作符数组
	 */
	public char[] randomOp() {
		char[] operator;
		operator = new char[N];
		for (int i = 0; i < N; i++) {
			Random ran = new Random();
			int choose = ran.nextInt(2);
			switch (choose) {
			case 0:
				operator[i] = '+';
				break;
			case 1:
				operator[i] = '*';
				break;
			}

		}
		return operator;
	}
}
