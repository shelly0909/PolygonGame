package Frame;

import Element.Polygon;
import Manager.BestSolution;
import Manager.BestSolutionController;
import Manager.GameController;
import Manager.PolygonManager;

import javax.swing.*;
import java.awt.*;

/**
 * 最高分数界面
 */
public class BestFrame extends JFrame {
    public static final String TITLE = "算法分析与设计三级项目";// 窗口名
    public static final int WIDTH = 800;// 窗口长度
    public static final int HEIGHT = 600;// 窗口宽度
    public static final int MARGIN = 50;// 窗口宽度


    /**
     * 初始化界面
     */
    public void initFrame(Polygon[] data, PolygonManager pm){
        BestInputPanel input = new BestInputPanel();
        GameFrame game = new GameFrame();
        game.receiveData(data,pm);
        JPanel inputPanel = input.init();// 输入面板
        JPanel gamePanel = game.init(1);// 多边形面板


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

        // 初始化控制器
        BestSolutionController.getInstance();
        BestSolutionController.getInstance().init(gamePanel,game);

        // 窗口初始化
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        // 把窗口位置设置到屏幕的中心
        setLocationRelativeTo(null);
        // 设置窗口可见
        setVisible(true);
        // 设置窗口布局
        setLayout(new BorderLayout());
    }
}
