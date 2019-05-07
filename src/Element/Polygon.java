package Element;

//这个是绘制一个完整多边形需要的所有元素
//用数组存储画边和端点的元素
public class Polygon {
	public DrawEdge edges;
	public DrawPoint points;

	public Polygon(DrawEdge edges, DrawPoint points) {
		this.edges = edges;
		this.points = points;
	}

	public DrawEdge getEdges() {
		return edges;
	}

	public void setEdges(DrawEdge edges) {
		this.edges = edges;
	}

	public DrawPoint getPoints() {
		return points;
	}

	public void setPoints(DrawPoint points) {
		this.points = points;
	}

}
