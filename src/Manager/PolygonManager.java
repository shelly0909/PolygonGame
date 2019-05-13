package Manager;

import java.util.*;

import Element.*;

//这个是用来计算各个点和边的元素的属性的
//之后会传给GameFrame的一个数组把数据全部传过去
public class PolygonManager {
	int width = 500;// 画板的总体长度
	int height = 500;// 画板的总体宽度
	int margin = 40;// 设置边缘，也是画端点时圆的半径
	int x0;
	int y0;
	int r;
	double degree;
	public Polygon[] polygonData(int n,int[] numData,char[] opData) {


		// 如何计算正多边形的端点位置
		// 圆心角a的度数为360/n，弧度计算为2π/n
		// 如果把圆心的坐标为(0,0)，那么顶点P1的坐标为[X1=cos(a),Y1=sin(a)]
		// 以此类推，顶点Pn坐标为[Xn=cos(a*n),Yn=sin(a*n)]
		// 所以Pn的实际坐标是[Xn+Ox,Yn+Oy]
		 x0 = (width - 2 * margin) / 2;// 外接矩形的中心
		 y0 = (height - 2 * margin) / 2;
		 r = (width - 4 * margin) / 2;// 正多边形圆的半径
		 degree = 360.0 / n;// 圆心角
		Polygon[] pls;
		pls = new Polygon[n];
		char[] op;
		int[] num;
		if(numData==null){
			op = new PolygonManager().opRandom(n);// 边的符号赋值
			num = new PolygonManager().numRandom(n);// 边的数值赋值
		}else{
			op = opData;
			num = numData;
		}
//		char[] op = new PolygonManager().opRandom(n);// 边的符号赋值
//		int[] num = new PolygonManager().numRandom(n);// 边的数值赋值
		// 测试用数据
//		int num[]= {16,32, 10, 35, 37, 36, 32, -5, -1, 30, 16 };
//		char[] op = {'+','+','*','*','+','*','*','+','*','+','*'};

		// 对象都初始化好，不然会报错
		Edge[] edges;
		edges = new Edge[n];
		Point[] points;
		points = new Point[n];
		DrawEdge[] drawEdges;
		drawEdges = new DrawEdge[n];
		DrawPoint[] drawPoints;
		drawPoints = new DrawPoint[n];

		// 边数组初始化
		for (int i = 0; i < n; i++) {
			edges[i] = new Edge(op[i],i);
		}
		// 点数初始化
		for (int i = 0; i < n; i++) {
			points[i] = new Point(num[i]);
		}

		// 根据输入的端点数值计算具体边和端点的位置
		// 计算具体端点位置
		for (int i = 0; i < n; i++) {
			double x = x0 + r * Math.sin(i * degree / 180 * Math.PI);
			double y = y0 + r * Math.cos(i * degree / 180 * Math.PI);
			drawPoints[i] = new DrawPoint((int) x, (int) y, points[i]);
		}
		// 计算具体边的位置
		for (int i = 0; i < n; i++) {
			if (i != n - 1) {
				drawEdges[i] = new DrawEdge(drawPoints[i], drawPoints[i + 1], edges[i]);
			} else {
				drawEdges[i] = new DrawEdge(drawPoints[i], drawPoints[0], edges[i]);
			}
		}
		// 边和端点封装到多边形类里
		for (int i = 0; i < n; i++) {
			pls[i] = new Polygon(drawEdges[i], drawPoints[i]);
		}
		return pls;
	}
	public Polygon[] cloneArray(Polygon[] p){
		int n = p.length;
		Polygon[] res = new Polygon[n];
		DrawPoint[] drawPoints = new DrawPoint[n];
		for(int i=0;i<n;i++){
			// 创建点
			drawPoints[i] = new DrawPoint(p[i].points.getX(),p[i].points.getY(),new Point(p[i].points.getPoint().getNum()));
		}
		DrawEdge[] drawEdges = new DrawEdge[n];
		for (int i = 0; i < n; i++) {
			if(p[i].edges!=null){
				if (i != n - 1) {
					drawEdges[i] = new DrawEdge(drawPoints[i], drawPoints[i + 1], new Edge(p[i].edges.getEdge().getOp(),p[i].edges.getEdge().getIndex()));
				} else {
					drawEdges[i] = new DrawEdge(drawPoints[i], drawPoints[0], new Edge(p[i].edges.getEdge().getOp(),p[i].edges.getEdge().getIndex()));
				}
			}else{
				drawEdges[i] = null;
			}

		}
		// 边和端点封装到多边形类里
		for (int i = 0; i < n; i++) {
			res[i] = new Polygon(drawEdges[i], drawPoints[i]);
		}
		return res;
	}
	public Polygon[] calculateNewPos(Polygon[] p,int n){
		degree = 360.0 / n;
		// 计算具体端点位置
		for (int i = 0; i < n; i++) {
			double x = x0 + r * Math.sin(i * degree / 180 * Math.PI);
			double y = y0 + r * Math.cos(i * degree / 180 * Math.PI);
			p[i].getPoints().setX((int) x);
			p[i].getPoints().setY((int) y);

		}

		return p;
	}

	// 随机生成各条边的运算符
	public char[] opRandom(int n) {
		char[] operator;
		operator = new char[n];
		for (int i = 0; i < n; i++) {
			Random ran = new Random();
			int choose = ran.nextInt(2);
			switch (choose) {
			case 0:
				operator[i] = '+';
				break;
			case 1:
				operator[i] = '*';
				break;
			case 2:
				operator[i] = '-';
				break;
			case 3:
				operator[i] = '/';
				break;
			}

		}
		return operator;
	}

	// 随机生成各个端点数值
	public int[] numRandom(int n) {
		int max =40;
		int min =-10;
		int[] num;
		num = new int[n];
		for (int i = 0; i < n; i++) {
			Random ran = new Random();
			num[i] = ran.nextInt(max-min)+min;
		}
		return num;
	}

}
