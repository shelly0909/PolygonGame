package Element;

//这个是每一个点
public class Point {
	private int num; // 端点数值

	// 默认方法
	public Point(int num) {
		setNum(num);
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
