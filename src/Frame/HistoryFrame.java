package Frame;

import Element.JHistory;
import Element.Polygon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryFrame extends JFrame {
    public static final String TITLE = "历史记录";// 窗口名
    public static final int WIDTH = 450;// 窗口长度
    public static final int HEIGHT = 600;// 窗口宽度
    JPanel hisPanel;
    List<JHistory> history ;
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
//        hisPanel.add(base);
        base = new JScrollPane(hisPanel);
        bar = base.getHorizontalScrollBar();
        this.add(base);

        // 把窗口位置设置到屏幕的中心
        setLocationRelativeTo(null);
        // 设置窗口可见
        setVisible(true);
        hisPanel.setVisible(true);
    }
    public void addHis(Polygon[] plss,int i,int ColorOne,String str){
        JHistory his = new JHistory(plss,ColorOne);
        his.setText("步骤"+i+":"+str);
        his.setVerticalAlignment(SwingConstants.TOP);
        his.setBounds(0,WIDTH*history.size(),WIDTH,WIDTH);
        hisPanel.setPreferredSize(new Dimension(WIDTH,WIDTH*(history.size()+1)));
        bar.setValue(hisPanel.getHeight());
        base.getViewport().setViewPosition(new Point(0,hisPanel.getHeight()));
        hisPanel.add(his);
        history.add(his);
        base.repaint();
    }
    public void clearAll(){
        JHistory h = history.get(0);
        for(int i=0;i<history.size();i++){
            hisPanel.remove(history.get(i));
        }
        hisPanel.add(h);
        hisPanel.setPreferredSize(new Dimension(WIDTH,WIDTH));
        bar.setValue(hisPanel.getHeight());
//        base.getViewport().setViewPosition(new Point(0,WIDTH));
        history.clear();
        history.add(h);
        base.repaint();
    }
}
