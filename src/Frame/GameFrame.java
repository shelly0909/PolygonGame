package Frame;

import Element.*;
import Element.Point;
import Element.Polygon;
import Listener.LineClickListener;
import Manager.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.util.Random;

//这个是用来画多边形的画板
public class GameFrame{
	// 这个就是用来画图的
	public Polygon[] plss;
	public JLine[] line;
	public int n;
	public PolygonManager manager;


	public JPanel init() {
		// 获取具体绘制的位置数据
		PolygonManager pm = new PolygonManager();
		Random ran = new Random();
//		int n = ran.nextInt(16);
		n=5;
		plss = pm.polygonData(n);
		this.manager = pm;
		int length = plss.length;
		for (int i = 0; i < length; i++) {
			plss[i] = new Polygon(plss[i].getEdges(), plss[i].getPoints());
		}
		line = new JLine[n];
		LineClickListener listener = new LineClickListener();

		JPanel game = new JPanel() {
			@Override
			public void paint(Graphics graphics) {
				// 先调用父类的paint方法
				super.paint(graphics);
				setLayout(null);
				Graphics2D g = (Graphics2D) graphics;
				int len = plss.length;
//				pm.calculateNewPos(plss,len);
				for (int i = 0; i < len; i++) {

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
					if(plss[i].edges==null) continue;
					//设置线条的粗细
					g.setStroke(new BasicStroke(2));
					//获取边对应的两个端点的坐标值
					int x1 = plss[i].getEdges().getP1().getX();
					int x2 = plss[i].getEdges().getP2().getX();
					int y1 = plss[i].getEdges().getP1().getY();
					int y2 = plss[i].getEdges().getP2().getY();
					g.setFont(new Font("微软雅黑", Font.BOLD,24));
//					g.drawLine(x1,y1,x2,y2);
					//写上边的运算符号
					g.drawString("" + plss[i].getEdges().getEdge().getOp(), (x1 + x2) / 2, (y1 + y2) / 2+8);
				}
			}

			@Override
			public void repaint() {
				super.repaint(); // 点击重绘

			}
		};
		// 添加线
		addLine(game,listener,n);
		game.setLayout(null);
		game.setPreferredSize(new Dimension(500, 500));
		game.setBackground(Color.WHITE);

		return game;
	}

	public void addLine(JPanel panel,MouseAdapter listener,int n){
		int r = 15;
		for(int i=0;i<line.length;i++){
//			if(plss[i].edges==null) continue;
			DrawPoint[] tPoint = rebuildPoint(plss[i].getEdges().getP1(),plss[i].getEdges().getP2());
			tPoint[0].setX(tPoint[0].getX()+r);
			tPoint[0].setY(tPoint[0].getY()+r);
//			tPoint[1].setX(tPoint[1].getX()-r);
//			tPoint[1].setY(tPoint[1].getY()-r);
			line[i] = new JLine(plss[i].getEdges(),tPoint[0].getX(),tPoint[0].getY(),0,plss[i].getEdges().getP1(),plss[i].getEdges().getP2());
			int width = tPoint[1].getX()-tPoint[0].getX()>0 ? tPoint[1].getX()-tPoint[0].getX():20;
			int height = tPoint[1].getY()-tPoint[0].getY()>0?tPoint[1].getY()-tPoint[0].getY():20;
			if(width==20 || height==20)
				line[i].offset=10;
			int x = width==20?(tPoint[0].getX()-20):tPoint[0].getX();
			int y = height==20?(tPoint[0].getY()-20):tPoint[0].getY();
			System.out.println("old:x"+x+",y:"+y+",width:"+width+",height:"+height);
			line[i].setBounds(x,y,width,height);
			line[i].setBorder(BorderFactory.createLineBorder(Color.red));
			line[i].addMouseListener(listener);
			line[i].setText(x+" "+y);
			panel.add(line[i]);
		}
	}

	public void reCalcalateLine(){
		int r = 15;
		System.out.println("------------");
		for(int i=0;i<line.length;i++){

			if(line[i].isVisible()){
				// 只对显示的线更新坐标
				for(int j=0;j<plss.length;j++){
					if(plss[j].getEdges()!=null
							&& plss[j].getEdges().getEdge().getOp()==line[i].i.getEdge().getOp()
							&& (plss[j].getEdges().getP1().getPoint().getNum() == line[i].i.getP1().getPoint().getNum()
							|| plss[j].getEdges().getP2().getPoint().getNum() == line[i].i.getP2().getPoint().getNum() )){

						DrawPoint[] tPoint = rebuildPoint(plss[j].getEdges().getP1(),plss[j].getEdges().getP2());
						tPoint[0].setX(tPoint[0].getX()+r);
						tPoint[0].setY(tPoint[0].getY()+r);

						line[i].i=plss[j].getEdges();
						line[i].baseX = tPoint[0].getX();
						line[i].baseY = tPoint[0].getY();
						line[i].p1 = plss[j].getEdges().getP1();
						line[i].p2 = plss[j].getEdges().getP2();
						int width = tPoint[1].getX()-tPoint[0].getX()>0 ? tPoint[1].getX()-tPoint[0].getX():20;
						int height = tPoint[1].getY()-tPoint[0].getY()>0?tPoint[1].getY()-tPoint[0].getY():20;
						if(width==20 || height==20)
							line[i].offset=10;
						int x = width==20?(tPoint[0].getX()-20):tPoint[0].getX();
						int y = height==20?(tPoint[0].getY()-20):tPoint[0].getY();
						System.out.println("new:x"+x+",y:"+y+",width:"+width+",height:"+height);
						line[i].setText(x+" "+y);
						line[i].setBounds(x,y,width,height);
					}
				}

			}
		}
	}
	public DrawPoint[] rebuildPoint(DrawPoint p1,DrawPoint p2){
		DrawPoint[] t = new DrawPoint[2];

		if(p1.getX()<p2.getX() && p1.getY()<=p2.getY()){
			t[0] = new DrawPoint(p1.getX(),p1.getY(),null);;
			t[1] =  new DrawPoint(p2.getX(),p2.getY(),null);
		}else if(p1.getX()>=p2.getX() && p1.getY()<p2.getY()){
			t[0] = new DrawPoint(p2.getX(),p1.getY(),null);
			t[1] = new DrawPoint(p1.getX(),p2.getY(),null);
		}else if(p1.getX()>p2.getX() && p1.getY()>=p2.getY()){
			t[0] = new DrawPoint(p2.getX(),p2.getY(),null);
			t[1] = new DrawPoint(p1.getX(),p1.getY(),null);
		}else if(p1.getX()<=p2.getX() && p1.getY()>p2.getY()){
			t[0] = new DrawPoint(p1.getX(),p2.getY(),null);
			t[1] = new DrawPoint(p2.getX(),p1.getY(),null);
		}
		return t;
	}
}
