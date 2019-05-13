package Element;

import java.math.BigInteger;

//这个是每一个点
public class Point {
	private BigInteger num; // 端点数值

	// 默认方法
	public Point(BigInteger num) {
		setNum(num);

	}

	public BigInteger getNum() {
		return num;
	}

	public void setNum(BigInteger num) {
		this.num = num;
	}
}
