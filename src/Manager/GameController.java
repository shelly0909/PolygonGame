package Manager;

import Element.*;
import Frame.GameFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController {
    private JPanel gamePanel, funcPanel;
    private GameFrame gameFrame;

    private List<Polygon[]> steps;
    private List<JLine> removeLine;
    private int stepNum = 0;

    private static GameController controller;

    public void init(JPanel game, JPanel funcPanel,GameFrame gameFrame){
        this.gamePanel = game;
        this.funcPanel = funcPanel;
        this.gameFrame = gameFrame;
        steps = new ArrayList<>();
        removeLine = new ArrayList<>();
        steps.add(this.gameFrame.plss); // 初始状态
        stepNum++;
    }

    // 单例
    public static GameController getInstance(){
        if(controller==null){
            controller = new GameController();
        }
        return controller;
    }

    public void deleteLine(JLine line) throws CloneNotSupportedException {
        Polygon[] tmp = gameFrame.manager.cloneArray(steps.get(stepNum-1)); // 注意深拷贝

        DrawEdge edge = tmp[0].edges;
        int i = 0;
        for(;i<tmp.length;){
            if(tmp[i].edges!=null && tmp[i].edges.equals(line.i)){
                edge = tmp[i].edges;
                break;
            }
            i++;
        }
        if(stepNum==1){
            // 去除第一条边
            tmp[i].edges=null;
            steps.add(tmp);
            gameFrame.plss = tmp;// 传值
            gamePanel.remove(line);
            removeLine.add(line);
            gamePanel.repaint();
        }else{
            int res;
            if(edge.getEdge().getOp()=='+'){
                res = edge.getP1().getPoint().getNum() + edge.getP2().getPoint().getNum();
            }else{
                res = edge.getP1().getPoint().getNum() * edge.getP2().getPoint().getNum();
            }
            tmp[i].getPoints().getPoint().setNum(res);

            DrawPoint tP = tmp[i].getEdges().getP2();
            if(tmp[(i+1)%tmp.length].edges!=null){
                tmp[i].edges.getEdge().setOp(tmp[(i+1)%tmp.length].edges.getEdge().getOp());
                tmp[i].edges.setP2(tmp[(i+1)%tmp.length].edges.getP2());

            }else{
                tmp[i].edges=null;
            }


            Polygon[] t1 = new Polygon[tmp.length-1];
            int j=0;
            for(int k=0;k<tmp.length-1;j++){
                if(tmp[j].points.equals(tP)){
                    continue;
                }
                t1[k] = tmp[j];
                k++;
            }
            steps.add(t1);
            gameFrame.plss = t1;
            gamePanel.remove(line);
            removeLine.add(line);
            gameFrame.manager.calculateNewPos(t1,t1.length);
            gameFrame.reCalcalateLine();
            gamePanel.repaint();
        }
        stepNum++;
    }

    public void recall(){
        if(stepNum==1)
            return;
        stepNum--;
        steps.remove(steps.size()-1);
        gameFrame.plss = steps.get(steps.size()-1);
        JLine l = removeLine.get(removeLine.size()-1);
        removeLine.remove(removeLine.size()-1);
        gamePanel.add(l);
        gameFrame.manager.calculateNewPos(gameFrame.plss,gameFrame.plss.length);
        gameFrame.reCalcalateLine();
        gamePanel.repaint();
    }
}
