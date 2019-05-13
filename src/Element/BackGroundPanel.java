package Element;

import javax.swing.*;
import java.awt.*;

/**
 * 创建带背景的Panel
 */
public class BackGroundPanel extends JPanel{
    public String imgURL = "";

    public BackGroundPanel(String imgURL){
        this.imgURL = imgURL;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //绘制一张背景图片  view.jpg是图片的路径  lz自己设定为自己想要添加的图片
        Image image = new ImageIcon(imgURL).getImage();
        g.drawImage(image, 0, 0, this);
    }
}
