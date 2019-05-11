package Frame;

import Element.JHistory;
import Element.Polygon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 历史记录窗口
 */
public class HistoryFrame extends JFrame {
    public static final String TITLE = "历史记录";// 窗口名
    public static final int WIDTH = 450;// 窗口长度
    public static final int HEIGHT = 600;// 窗口宽度
    JPanel hisPanel;
    List<JHistory> history ; // label组件列表
    JScrollPane base;
    JScrollBar bar;

    public void initFrame(){
        hisPanel = new JPanel();
        hisPanel.setLayout(null);
        history = new ArrayList<>();
        hisPanel.setBackground(Color.white);
        // 窗口初始化
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);

        // 往scroll窗口添加panel
        base = new JScrollPane(hisPanel);
        bar = base.getHorizontalScrollBar();
        this.add(base);

        // 把窗口位置设置到屏幕的中心
        setLocationRelativeTo(null);
        // 设置窗口可见
        setVisible(true);
        hisPanel.setVisible(true);
    }

    /**
     * 添加历史记录
     * @param plss 历史记录的数组
     * @param i 历史步骤
     * @param ColorOne 新计算的点
     * @param str 历史记录提示
     */
    public void addHis(Polygon[] plss,int i,int ColorOne,String str){
        JHistory his = new JHistory(plss,ColorOne);
        his.setText("步骤"+i+":"+str);
        his.setVerticalAlignment(SwingConstants.TOP); // 设置左上角
        his.setBounds(0,WIDTH*history.size(),WIDTH,WIDTH); // 设置新纪录位置

        // 控制滚动条
        hisPanel.setPreferredSize(new Dimension(WIDTH,WIDTH*(history.size()+1)));
        bar.setValue(hisPanel.getHeight());
        base.getViewport().setViewPosition(new Point(0,hisPanel.getHeight()));

        hisPanel.add(his);
        history.add(his);
        base.repaint();
    }

    /**
     * 删除所有历史记录
     */
    public void clearAll(){
        for(int i=0;i<history.size();i++){
            hisPanel.remove(history.get(i)); // 从面板上移去所有组件
        }

        // 控制滚动条
        hisPanel.setPreferredSize(new Dimension(WIDTH,WIDTH));
        bar.setValue(hisPanel.getHeight());
        history.clear();
        base.repaint();
    }
}
