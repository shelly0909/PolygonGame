package Element;

//这个是每一条边
public class Edge {
	private int index; // 唯一标志符
	private char op;// 边的运算符号

	// 默认方法
	public Edge(char op,int index) {
		setOp(op);
		this.index = index;
	}

	public char getOp() {
		return op;
	}

	public void setOp(char op) {
		this.op = op;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
