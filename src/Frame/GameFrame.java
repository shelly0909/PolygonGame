package Frame;

import Element.*;
import Element.Polygon;
import Listener.LineClickListener;
import Manager.*;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.Random;

//这个是用来画多边形的画板
public class GameFrame{
	// 这个就是用来画图的
	public Polygon[] plss;
	public JLine[] line; // line控件
	public int n;
	public PolygonManager manager; // 数据计算
	public int colorOne = -1; // 记录计算后的元素
	public static final int PLAYMODE =0; // 点击模式
	public static final int BESTMODE =1; // 最高分查看模式
	private int mode;

	public void randomData(){
		// 获取具体绘制的位置数据
		PolygonManager pm = new PolygonManager();
		Random ran = new Random();
		n = ran.nextInt(16)+2; // 范围大于3
		plss = pm.polygonData(n,null,null);
		this.manager = pm;
	}

	/**
	 * 自定义模式下的输入
	 * @param n 节点个数
	 * @param num 节点数组
	 * @param op 操作符数组
	 */
	public void InputData(int n,int[] num,char[] op){
		// 获取具体绘制的位置数据
		PolygonManager pm = new PolygonManager();
		this.n = n;
		plss = pm.polygonData(n,num,op);
		this.manager = pm;
	}

	/**
	 * best界面传入数据
	 * @param p  多边形数组
	 * @param pm 数据处理器
	 */
	public void receiveData(Polygon[] p,PolygonManager pm){
		this.plss = p;
		this.n = p.length;
		this.manager = pm;
	}

	/**
	 * 界面初始化
	 * @param mode 创建gameFrame的模式
	 * @return gameFrame的JPanel
	 */
	public JPanel init(int mode) {
		this.mode = mode;
		System.out.println("nInput:"+n);
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
				for (int i = 0; i < len; i++) {

					//设置画笔的颜色
					if(colorOne !=-1 && i== colorOne)
						g.setColor(Color.decode("#98F898")); // 当前计算点取不同颜色
					else
						g.setColor(Color.decode("#F18F01"));
					//获取端点坐标值
					int x=plss[i].getPoints().getX();
					int y=plss[i].getPoints().getY();
					//画端点圆
					g.fillOval(x-15,y-15,40,40);
//					g.setColor(Color.BLACK);
//					g.fillOval(x-15,y-15,5,5);
//					g.setColor(Color.red);
//					g.fillOval(x,y,5,5);

					g.setColor(Color.decode("#0A122A"));
					g.setFont(new Font("微软雅黑", Font.BOLD, 20));
					//写上端点对应的数值
					g.drawString(""+plss[i].getPoints().getPoint().getNum(),x-5,y+10);
					g.setColor(Color.decode("#0A122A"));
					if(plss[i].edges==null) continue; // 没有边则不写运算符
					//设置线条的粗细
					g.setStroke(new BasicStroke(2));
					//获取边对应的两个端点的坐标值
					int x1 = plss[i].getEdges().getP1().getX();
					int x2 = plss[i].getEdges().getP2().getX();
					int y1 = plss[i].getEdges().getP1().getY();
					int y2 = plss[i].getEdges().getP2().getY();
					g.setFont(new Font("微软雅黑", Font.BOLD,24));
					//写上边的运算符号
					g.drawString("" + plss[i].getEdges().getEdge().getOp(), (x1 + x2) / 2, (y1 + y2) / 2+8);
				}
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
		int r = 15; // 圆半径，处理线点击区域重叠
		for(int i=0;i<line.length;i++){

			// 计算线所在区域的左上角和右下角点
			DrawPoint[] tPoint = rebuildPoint(plss[i].getEdges().getP1(),plss[i].getEdges().getP2());
			System.out.println(tPoint[1].getX()-tPoint[0].getX());
//			// 防区域交叉
			tPoint[0].setX(tPoint[0].getX()+r);
			tPoint[0].setY(tPoint[0].getY()+r);
			System.out.println(tPoint[1].getX()-tPoint[0].getX());
			// 创建组件
			line[i] = new JLine(plss[i].getEdges(),tPoint[0].getX(),tPoint[0].getY(),0,plss[i].getEdges().getP1(),plss[i].getEdges().getP2());
			// 计算点击区域大小
			int width = tPoint[1].getX()-tPoint[0].getX()>10 ? tPoint[1].getX()-tPoint[0].getX():20;
			int height = tPoint[1].getY()-tPoint[0].getY()>10?tPoint[1].getY()-tPoint[0].getY():20;
			// 仅有一条线时设置线相对点击区域的偏移量
			if(tPoint[1].getX()-tPoint[0].getX()<3||tPoint[1].getY()-tPoint[0].getY()<3){
				line[i].offset=10;
			}
			int x = width==20?(tPoint[0].getX()-20):tPoint[0].getX();
			int y = height==20?(tPoint[0].getY()-20):tPoint[0].getY();
//			System.out.println("old:x"+x+",y:"+y+",width:"+width+",height:"+height);
			line[i].setBounds(x,y,width,height);
			// 线边框
			//line[i].setBorder(BorderFactory.createLineBorder(Color.red));
			if(mode==PLAYMODE) // 游戏模式才加监听
				line[i].addMouseListener(listener);
			panel.add(line[i]);
		}
	}

	public void reCalcalateLine(){
		int r = 15; // 圆半径，处理线点击区域重叠
		for(int i=0;i<line.length;i++){

			if(line[i].isVisible()){
				// 只对显示的线更新坐标
				for(int j=0;j<plss.length;j++){
					// 找处于显示状态的线组件
					if(plss[j].edges!=null && plss[j].edges.getEdge().getIndex()==line[i].i.getEdge().getIndex()){
						DrawPoint[] tPoint = rebuildPoint(plss[j].getEdges().getP1(),plss[j].getEdges().getP2());
							tPoint[0].setX(tPoint[0].getX()+r);
							tPoint[0].setY(tPoint[0].getY()+r);

						line[i].i=plss[j].getEdges();
						line[i].baseX = tPoint[0].getX();
						line[i].baseY = tPoint[0].getY();
						line[i].p1 = plss[j].getEdges().getP1();
						line[i].p2 = plss[j].getEdges().getP2();

						// 计算区域大小
						int width = tPoint[1].getX()-tPoint[0].getX()>10 ? tPoint[1].getX()-tPoint[0].getX():20;
						int height = tPoint[1].getY()-tPoint[0].getY()>10?tPoint[1].getY()-tPoint[0].getY():20;

						if(tPoint[1].getX()-tPoint[0].getX()<3||tPoint[1].getY()-tPoint[0].getY()<3){
							line[i].offset=10;
						}
						int x = width==20?(tPoint[0].getX()-20):tPoint[0].getX();
						int y = height==20?(tPoint[0].getY()-20):tPoint[0].getY();
//						System.out.println("new:x"+x+",y:"+y+",width:"+width+",height:"+height);
						line[i].setBounds(x,y,width,height);
					}
				}

			}
		}
	}


	/**
	 * 计算点击区域的左上角坐标和右下角坐标
	 * @param p1 线的p1
	 * @param p2 线的p2
	 * @return 返回坐标组
	 */
	public DrawPoint[] rebuildPoint(DrawPoint p1,DrawPoint p2){
		DrawPoint[] t = new DrawPoint[2];
		int r=15;
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

	/**
	 * @param i 当前序列的新计算元素
	 */
	public void setColorOne(int i){
		this.colorOne = i;
	}
}
