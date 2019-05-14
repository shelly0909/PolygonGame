package Manager;

import Element.DrawEdge;
import Element.DrawPoint;
import Element.JLine;
import Element.Polygon;
import Frame.GameFrame;
import Frame.PlayFrame;

import javax.swing.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 最高分界面控制
 */
public class BestSolutionController {
    private List<Polygon[]> BestSteps; // 最高分步骤
    private List<JLine> BestRemoveLine; // 最高分移除的线
    private int stepNum = 0;
    private static BestSolutionController controller;
    private JPanel gamePanel;
    private GameFrame gameFrame;
    private int[] ColorOne; // 每步新计算的点的标志

    private BigInteger result;


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

    /**
     * 计算最优解
     */
    private void calBestSolution(){
        Polygon[] origin = BestSteps.get(0);
        int n = origin.length;
        BigInteger[] num = new BigInteger[n];
        char[] op = new char[n];
        int[] opIndex = new int[n];

//        // 界面的数据结构转换算法计算的数据结构
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
//        for(int i=0;i<n;i++){
//            System.out.print(num[i]+" ");
//        }
//        System.out.println();
//        for(int i=0;i<n;i++){
//            System.out.print(op[i]+" ");
//        }

        // 测试性数据
        BestSolution BS = new BestSolution(n,op,num);
        BS.ployMax();
//        BS.printMatrix(BS.m);
//        BS.printMatrixINT(BS.sPos);

        // 获取最优解，产生步骤
        List<Integer> route = BS.route;
        for(int i=0;i<route.size();i++){
            int r = route.get(i);
            deleteLine(BestSteps,opIndex[r-1]);

        }
    }

    /**
     * 创建绘图步骤
     * @param steps 步骤列表
     * @param opIndex 最优解标志符
     */
    private void deleteLine(List<Polygon[]> steps,int opIndex)  {
        System.out.println(stepNum);
        Polygon[] tmp =gameFrame.manager.cloneArray(steps.get(stepNum-1)); // 注意深拷贝

        // 找到最优解对应的列表对象
        int i = 0;
        int n = tmp.length;
        for(int j=0;j<tmp.length;j++){
            if(tmp[j].edges!=null && tmp[j].getEdges().getEdge().getIndex() == opIndex){
                i=j;
                break;
            }
        }

        // 找到最优解对应的线组件
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

            // 计算
            BigInteger res;
            if(edge.getEdge().getOp()=='+'){
                res = edge.getP1().getPoint().getNum().add(edge.getP2().getPoint().getNum());
            }else{
                res = edge.getP1().getPoint().getNum().multiply(edge.getP2().getPoint().getNum());
            }
            tmp[i].getPoints().getPoint().setNum(res);

            // 处理新产生的边
            DrawPoint tP = tmp[i].getEdges().getP2();
            if(tmp[(i+1)%tmp.length].edges!=null){
                tmp[i].setEdges(tmp[(i+1)%tmp.length].edges);
                tmp[i].edges.setP1(tmp[i].getPoints());

            }else{
                // 如果当前对应的边为空则不处理
                tmp[i].edges=null;
            }

            // 储存步骤，存储新计算值位置
            Polygon[] t1 = new Polygon[tmp.length-1];
            int j=0;
            for(int k=0;k<tmp.length-1;j++){
                if(tmp[j].points.equals(tP)){
                    continue;
                }else if(tmp[j]==tmp[i]){
                    ColorOne[stepNum] = k;
                }
                t1[k] = tmp[j];
                k++;
            }
            steps.add(t1);
            BestRemoveLine.add(line);

        }
        stepNum++;
    }

    /**
     * 显示一步最优解
     */
    public void showBestSolution()  {
        gameFrame.plss = BestSteps.get(stepNum); // 更换数据
        gamePanel.remove(BestRemoveLine.get(stepNum-1)); // 移除线组件
        // 重新计算位置
        gameFrame.manager.calculateNewPos(gameFrame.plss,gameFrame.plss.length);
        gameFrame.reCalcalateLine();
        gameFrame.setColorOne(ColorOne[stepNum]);
        gamePanel.repaint();

        stepNum++;
    }

    /**
     * 历史记录上一步
     */
    public void recall(){
        if(stepNum==1)
            return;
        stepNum--;
        // 获取步骤
        BestSteps.get(stepNum-1);
        gameFrame.plss = BestSteps.get(stepNum-1);
        JLine l = BestRemoveLine.get(stepNum-1);
        gamePanel.add(l);

        // 重新计算位置
        gameFrame.manager.calculateNewPos(gameFrame.plss,gameFrame.plss.length);
        gameFrame.reCalcalateLine();
        gameFrame.setColorOne(ColorOne[stepNum-1]);
        gamePanel.repaint();
    }

    /**
     * 仅计算最佳结果
     * @param num 节点数组
     * @param op 操作符数组
     * @param n 节点数
     */
    public void getBestResult(BigInteger[] num,char[] op,int n){
        BestSolution bs = new BestSolution(n,op,num);
        result = bs.ployMax();
    }

    public BigInteger getResult() {
        return result;
    }
}
