package Element;

//这个是用来画边的，需要有两个端点的位置，
//同时把符号也封装进来了，因为画边的时候需要在边上添加运算符号
public class DrawEdge {
	private DrawPoint p1;
	private DrawPoint p2;
	private Edge edge;

	// 默认方法
	public DrawEdge(DrawPoint p1, DrawPoint p2, Edge edge) {
		setPoint(p1, p2);
		setEdge(edge);
	}

	public void setPoint(DrawPoint p1, DrawPoint p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public void setEdge(Edge edge) {
		this.edge = edge;
	}

	public DrawPoint getP1() {
		return p1;
	}

	public void setP1(DrawPoint p1) {
		this.p1 = p1;
	}

	public DrawPoint getP2() {
		return p2;
	}

	public void setP2(DrawPoint p2) {
		this.p2 = p2;
	}

	public Edge getEdge() {
		return edge;
	}

}
