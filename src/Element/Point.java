package Element;

//这个是每一个点
public class Point {
	private long num; // 端点数值

	// 默认方法
	public Point(long num) {
		setNum(num);

	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}
}
