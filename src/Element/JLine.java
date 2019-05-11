package Element;

import javax.swing.*;
import java.awt.*;

public class JLine extends JLabel {
    public int baseX,baseY,offset;
    public DrawPoint p1,p2;
    public DrawEdge i;
    public JLine(DrawEdge i,int basex,int basey,int offset,DrawPoint p1,DrawPoint p2){
        this.baseX = basex; // 原始坐标相对于0坐标的基准
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
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLACK);
        int x1=0,x2=0,y1=0,y2=0;
        if(p1.getX()<p2.getX() && p1.getY()<=p2.getY()){
            x1 = y1 = 0;
            x2 = this.getWidth();
            y2 = this.getHeight();
            if(p1.getY()==p2.getY()){
                y1 = y2 = this.getHeight()/2;
            }
        }else if(p1.getX()>=p2.getX() && p1.getY()<p2.getY()){
            x1 = this.getWidth();
            y1 = 0;
            x2 = 0;
            y2 = this.getHeight();
            if(p1.getX()==p2.getX()){
                x1 = x2 = this.getWidth()/2;
            }
        }else if(p1.getX()>p2.getX() && p1.getY()>=p2.getY()){
            x1 = this.getWidth();
            y1 = this.getHeight();
            x2 = y2 =0;
            if(p1.getY()==p2.getY()){
                y1 = y2 = this.getHeight()/2;
            }
        }else if(p1.getX()<=p2.getX() && p1.getY()>p2.getY()){
            x1 = 0;
            y1 = this.getHeight();
            x2 = this.getWidth();
            y2 = 0;
            if(p1.getX()==p2.getX()){
                x1 = x2 = this.getWidth()/2;
            }
        }
//        System.out.println("p1:"+i.getP1().getPoint().getNum()+",p2:"+i.getP2().getPoint().getNum());
//        System.out.println("width:"+this.getWidth()+",height:"+this.getHeight());
//        System.out.println("x1:"+x1+",y1:"+y1+",x2:"+x2+",y2:"+y2);
//        System.out.println();
        g2.drawLine(x1,y1,x2,y2);
        // JPanel的相对位置
    }
}
