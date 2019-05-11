package Manager;

import Element.*;
import Frame.BestFrame;
import Frame.GameFrame;
import Frame.HistoryFrame;
import Frame.MainFrame;
import com.sun.deploy.util.SyncAccess;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

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
    private static HistoryFrame historyFrame;

    private int[] Color;
    private int his = 0;


    private static GameController controller;

    public void init(JPanel game, JPanel funcPanel,GameFrame gameFrame){
        this.gamePanel = game;
        this.funcPanel = funcPanel;
        this.gameFrame = gameFrame;
        steps = new ArrayList<>();
        removeLine = new ArrayList<>();
        steps.add(this.gameFrame.plss); // 初始状态
        Color = new int[this.gameFrame.plss.length+1];
        Color[0] = Color[1] = -1;
        stepNum++;
    }

    // 单例
    public static GameController getInstance(){
        if(controller==null){
            controller = new GameController();
        }
        return controller;
    }

    public void deleteLine(JLine line)  {
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
                tmp[i].setEdges(tmp[(i+1)%tmp.length].edges);
                tmp[i].edges.setP1(tmp[i].getPoints());

            }else{
                tmp[i].edges=null;
            }


            Polygon[] t1 = new Polygon[tmp.length-1];
            int j=0;
            for(int k=0;k<tmp.length-1;j++){
                if(tmp[j].points.equals(tP)){
                    continue;
                }else if(tmp[j].points.getPoint().getNum()==res){
                    gameFrame.setColorOne(k);
                    Color[stepNum] = k;
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
        refreshHis(steps.get(steps.size()-1),stepNum,"点击");
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
        gameFrame.setColorOne(Color[stepNum-1]);
        gamePanel.repaint();
        refreshHis(gameFrame.plss,stepNum-1,"撤回");
    }

    public void createBest(){
        BestFrame bestFrame = new BestFrame();
        bestFrame.initFrame();
    }

    public void reset(){
        Polygon[] origin = steps.get(0);
        steps.clear();
        steps.add(origin);
        for(int i=0;i<removeLine.size();i++){
            gamePanel.add(removeLine.get(i));
        }
        removeLine.clear();
        gameFrame.plss = origin;
        gameFrame.manager.calculateNewPos(gameFrame.plss,gameFrame.plss.length);
        gameFrame.reCalcalateLine();
        gameFrame.setColorOne(-1);
        gamePanel.repaint();
        stepNum=1;
    }

    public void CreatehistoryWin(){
        if(historyFrame == null){
            historyFrame = new HistoryFrame();
            historyFrame.initFrame();
            historyFrame.setVisible(false);
        }
        historyFrame.addHis(steps.get(0),his++,-1,"初始");
    }
    public void showHistoryWin(){
        historyFrame.setVisible(true);
    }
    public void refreshHis(Polygon[] plss,int i,String str){
        if(historyFrame!=null)
            historyFrame.addHis(plss,his++,Color[i],str);
    }

    public void clearHis(){
        if(historyFrame!=null)
            historyFrame.clearAll();
    }

}
