package Element;

import javax.swing.*;
import java.awt.*;

public class JLine extends JLabel {
    public int baseX,baseY,offset;
    public DrawPoint p1,p2;
    public DrawEdge i;
    public JLine(DrawEdge i,int basex,int basey,int offset,DrawPoint p1,DrawPoint p2){
        this.baseX = basex;
        this.baseY = basey;
        this.offset = offset;
        this.i = i;
        this.p1 = p1;
        this.p2 = p2;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        int x1 = p1.getX()-baseX>0?p1.getX()-baseX:offset;
        int y1 = p1.getY()-baseY>0?p1.getY()-baseY:offset;
        int x2 = p2.getX()-baseX>0?p2.getX()-baseX:offset;
        int y2 = p2.getY()-baseY>0?p2.getY()-baseY:offset;
        g2.drawLine(x1,y1,x2,y2);
        // JPanel的相对位置
    }
}
