package Manager;

import Element.DrawEdge;
import Element.DrawPoint;
import Element.JLine;
import Element.Polygon;
import Frame.GameFrame;
import Frame.PlayFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BestSolutionController {
    private List<Polygon[]> BestSteps;
    private List<JLine> BestRemoveLine;
    private int stepNum = 0;
    private static BestSolutionController controller;
    private JPanel gamePanel;
    private GameFrame gameFrame;
    private int[] ColorOne;

    public void init(JPanel game, GameFrame gameFrame){
        this.gameFrame = gameFrame;
        this.gamePanel = game;
        BestSteps = new ArrayList<>();
        BestRemoveLine = new ArrayList<>();
        ColorOne = new int[this.gameFrame.plss.length+1];
        ColorOne[0]= ColorOne[1] = -1;
        BestSteps.add(this.gameFrame.plss);
        stepNum=1;
        calBestSolution();
        stepNum=1;
    }

    public static BestSolutionController getInstance(){
        if(controller==null){
            controller = new BestSolutionController();
        }
        return controller;
    }

    public void calBestSolution(){
        Polygon[] origin = BestSteps.get(0);
        int n = origin.length;
        int[] num = new int[n];
        char[] op = new char[n];
        int[] opIndex = new int[n];

        for(int i=0;i<n;i++){
            int j = n-i-1;
            num[i] = origin[j].getPoints().getPoint().getNum();
            if(j-1==-1){
                op[i] = origin[n-1].getEdges().getEdge().getOp();
                opIndex[i] = n-1;
            }else{
                op[i] = origin[j-1].getEdges().getEdge().getOp();
                opIndex[i] = j-1;
            }

        }
        for(int i=0;i<n;i++){
            System.out.print(num[i]+" ");
        }
        System.out.println();
        for(int i=0;i<n;i++){
            System.out.print(op[i]+" ");
        }
        BestSolution BS = new BestSolution(5,op,num);
        System.out.println(BS.ployMax());
        BS.printMatrix(BS.m);
        BS.printMatrix(BS.sPos);


        List<Integer> route = BS.route;
        for(int i=0;i<route.size();i++){
            int r = route.get(i);
            System.out.println(r);
            deleteLine(BestSteps,opIndex[r-1]);

        }
        System.out.println();
    }

    public void deleteLine(List<Polygon[]> steps,int opIndex)  {

        Polygon[] tmp = gameFrame.manager.cloneArray(steps.get(stepNum-1)); // 注意深拷贝

        int i = 0;
        int n = tmp.length;
        for(int j=0;j<tmp.length;j++){
            if(tmp[j].edges!=null && tmp[j].getEdges().getEdge().getIndex() == opIndex){
                i=j;
                break;
            }
        }
        DrawEdge edge = tmp[i].edges;
        JLine line = null;
        for(int j=0;j<gameFrame.line.length;j++){
            line = gameFrame.line[j];
            if(gameFrame.line[j].i.getEdge().getIndex()==opIndex){
                break;
            }

        }

        if(stepNum==1){
            // 去除第一条边
            tmp[i].edges=null;
            steps.add(tmp);
            BestRemoveLine.add(line);
        }else{
//            if(stepNum==)
            int res;
            if(edge.getEdge().getOp()=='+'){
                res = edge.getP1().getPoint().getNum() + edge.getP2().getPoint().getNum();
            }else{
                res = edge.getP1().getPoint().getNum() * edge.getP2().getPoint().getNum();
            }
            tmp[i].getPoints().getPoint().setNum(res);

            DrawPoint tP = tmp[i].getEdges().getP2();
            if(tmp[(i+1)%tmp.length].edges!=null){
//                tmp[i].edges.getEdge().setOp(tmp[(i+1)%tmp.length].edges.getEdge().getOp());
//                tmp[i].edges.setP2(tmp[(i+1)%tmp.length].edges.getP2());
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
                    ColorOne[stepNum] = k;
                }
                t1[k] = tmp[j];
                k++;
            }
            steps.add(t1);
            BestRemoveLine.add(line);
//            gameFrame.manager.calculateNewPos(t1,t1.length);

        }
        stepNum++;
    }

    public void showBestSolution()  {
        gameFrame.plss = BestSteps.get(stepNum);
        gamePanel.remove(BestRemoveLine.get(stepNum-1));
        gameFrame.manager.calculateNewPos(gameFrame.plss,gameFrame.plss.length);
        gameFrame.reCalcalateLine();
        gameFrame.setColorOne(ColorOne[stepNum]);
        gamePanel.repaint();

        stepNum++;
        System.out.println("show-num:"+stepNum);
    }

    public void recall(){
        if(stepNum==1)
            return;
        stepNum--;
        BestSteps.get(stepNum-1);
        gameFrame.plss = BestSteps.get(stepNum-1);
        JLine l = BestRemoveLine.get(stepNum-1);
        gamePanel.add(l);
        gameFrame.manager.calculateNewPos(gameFrame.plss,gameFrame.plss.length);
        gameFrame.reCalcalateLine();
        gameFrame.setColorOne(ColorOne[stepNum-1]);
        gamePanel.repaint();
        System.out.println("recall-num:"+stepNum);
    }
}
