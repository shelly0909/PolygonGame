package Frame;

import Element.*;
import Element.Polygon;
import Manager.*;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

//这个是用来画多边形的画板
public class GameFrame {
	// 这个就是用来画图的
	public Polygon[] plss;

	public JPanel init() {
		// 获取具体绘制的位置数据
		PolygonManager pm = new PolygonManager();
		Random ran = new Random();
		int n = ran.nextInt(16);
		plss = pm.polygonData(n);
		int length = plss.length;
		for (int i = 0; i < length; i++) {
			plss[i] = new Polygon(plss[i].getEdges(), plss[i].getPoints());
		}

		JPanel game = new JPanel() {
			@Override
			public void paint(Graphics graphics) {
				// 先调用父类的paint方法
				super.paint(graphics);
				Graphics2D g = (Graphics2D) graphics;
				for (int i = 0; i < length; i++) {
					//设置画笔的颜色
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
		};
		game.setPreferredSize(new Dimension(500, 500));
		game.setBackground(Color.WHITE);
		return game;
	}

}
