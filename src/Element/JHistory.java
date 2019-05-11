package Element;

import javax.swing.*;
import java.awt.*;

public class JHistory extends JLabel {
    private Polygon[] plss;
    private int ColorOne;
    // 一个历史记录
    public JHistory(Polygon[] plss,int ColorOne){
        this.plss = plss;
        this.ColorOne = ColorOne;
        this.setBorder(BorderFactory.createLineBorder(Color.red));
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        for (int i = 0; i < plss.length; i++) {
            //设置画笔的颜色
            if(ColorOne!=-1 && i==ColorOne)
                g.setColor(Color.decode("#98F898"));
            else
            g.setColor(Color.decode("#F18F01"));
            //获取端点坐标值
            int x=plss[i].getPoints().getX();
            int y=plss[i].getPoints().getY();
            //画端点圆
            g.fillOval(x-15,y-15,40,40);

            g.setColor(Color.decode("#0A122A"));
            g.setFont(new Font("微软雅黑", Font.BOLD, 20));
            //写上端点对应的数值
            g.drawString(""+plss[i].getPoints().getPoint().getNum(),x-5,y+10);
            g.setColor(Color.decode("#0A122A"));
            if(plss[i].edges==null)
                continue;
            //设置线条的粗细
            g.setStroke(new BasicStroke(2));
            //获取边对应的两个端点的坐标值
            int x1 = plss[i].getEdges().getP1().getX();
            int x2 = plss[i].getEdges().getP2().getX();
            int y1 = plss[i].getEdges().getP1().getY();
            int y2 = plss[i].getEdges().getP2().getY();
            //画边
            g.drawLine(x1, y1, x2, y2);
            g.setFont(new Font("微软雅黑", Font.BOLD,24));
            //写上边的运算符号
            g.drawString("" + plss[i].getEdges().getEdge().getOp(), (x1 + x2) / 2, (y1 + y2) / 2+8);
        }
    }
}
