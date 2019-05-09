package Element;

//这个是用来画点的，需要有每个点相对于canvas的坐标值
//同时把端点的数值也封装进来了
//因为需要在每个点画完之后在里面写上端点对应的数值
public class DrawPoint {
	private int x;// 端点坐标值
	private int y;
	private Point point;

	// 默认方法
	public DrawPoint(int x, int y, Point point) {
		this.x = x;
		this.y = y;
		this.point = point;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	@Override
	public boolean equals(Object obj) {
		DrawPoint p = (DrawPoint)obj;
		if(this.x==p.x && this.y==p.y && this.point.getNum()==p.point.getNum())
			return true;
		return false;
	}
}
