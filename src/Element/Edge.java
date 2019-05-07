package Element;

//这个是每一条边
public class Edge {
	private char op;// 边的运算符号

	// 默认方法
	public Edge(char op) {
		setOp(op);
	}

	public char getOp() {
		return op;
	}

	public void setOp(char op) {
		this.op = op;
	}

}
