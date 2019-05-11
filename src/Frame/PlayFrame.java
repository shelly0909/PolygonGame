package Frame;

import javax.swing.*;

//这个是封装好的界面入口程序
public class PlayFrame {
	public static void main(String[] arg) {
		MainFrame play = new MainFrame();
//		play.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // 不一起关闭
		play.initFrame();
	}

}
