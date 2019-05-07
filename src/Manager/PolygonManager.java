package Manager;

import java.util.*;

import Element.*;

public class PolygonManager {

	public Polygon[] polygonData(int n) {
		int width = 500;// 画板的总体长度
		int height = 500;// 画板的总体宽度
		int margin = 40;// 设置边缘，也是画端点时圆的半径
		int x0 = (width - 2 * margin) / 2;
		int y0 = (height - 2 * margin) / 2;
		int r = (width - 4 * margin) / 2;
		Polygon[] pls;
		pls = new Polygon[n];
		char[] op = new PolygonManager().opRandom(n);
		int[] num = new PolygonManager().numRandom(n);
		Edge[] edges;
		edges = new Edge[n];
		Point[] points;
		points = new Point[n];
		DrawEdge[] drawEdges;
		drawEdges = new DrawEdge[n];
		DrawPoint[] drawPoints;
		drawPoints = new DrawPoint[n];
		double degree = 360.0 / n;

		// 边数组初始化
		for (int i = 0; i < n; i++) {
			edges[i] = new Edge(op[i]);
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

	// 随机生成各条边的运算符
	public char[] opRandom(int n) {
		char[] operator;
		operator = new char[n];
		for (int i = 0; i < n; i++) {
			Random ran = new Random();
			int choose = ran.nextInt(4);
			switch (choose) {
			case 0:
				operator[i] = '+';
				break;
			case 1:
				operator[i] = '-';
				break;
			case 2:
				operator[i] = '*';
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
		int[] num;
		num = new int[n];
		for (int i = 0; i < n; i++) {
			Random ran = new Random();
			num[i] = ran.nextInt(100);
		}
		return num;
	}

}
