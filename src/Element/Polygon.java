package Element;

//这个是绘制一个完整多边形需要的所有元素
//用数组存储画边和端点的元素
public class Polygon implements Cloneable{
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

	@Override
	public Polygon clone() throws CloneNotSupportedException {
		if(edges!=null){
			DrawPoint p1 = new DrawPoint(edges.getP1().getX(),edges.getP1().getY(),new Point(edges.getP1().getPoint().getNum()));
			DrawPoint p2 = new DrawPoint(edges.getP2().getX(),edges.getP2().getY(),new Point(edges.getP2().getPoint().getNum()));
			DrawEdge edge = new DrawEdge(p1,p2,edges.getEdge());
			if(p1.getPoint().getNum()==points.getPoint().getNum())
				return new Polygon(edge,p1);
			else
				return new Polygon(edge,p2);
		}else{
			DrawPoint point = new DrawPoint(points.getX(),points.getY(),points.getPoint());
			return new Polygon(null,point);
		}

	}
}
