package Manager;

import Element.*;
import Frame.*;

import javax.swing.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏控制
 */
public class GameController {
    private JPanel gamePanel;
    private GameFrame gameFrame;
    private InputFrame inputFrame;

    private List<Polygon[]> steps; // 每一步记录
    private List<JLine> removeLine; // 记录对应的移除线记录
    private int stepNum = 0; // 当前步骤
    private static HistoryFrame historyFrame; // 历史记录窗口

    private int[] Color; // 记录对应的新计算值的点的标志
    private int his = 0;  // 历史步数

    private static GameController controller; // 单例
    private JLine line;

    public void init(JPanel game,GameFrame gameFrame,InputFrame inputFrame){
        this.inputFrame = inputFrame;
        this.gamePanel = game;
        this.gameFrame = gameFrame;
        steps = new ArrayList<>();
        removeLine = new ArrayList<>();
        steps.add(this.gameFrame.plss); // 初始状态
        // 初始化Color
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

    /**
     * 移除线并进行记录
     * @param line 点击的线控件
     */
    public void deleteLine(JLine line)  {
        this.line = line;
        Polygon[] tmp = gameFrame.manager.cloneArray(steps.get(stepNum-1)); // 注意深拷贝

        // 找到当前线组件对应的边
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
            // 移除边
            gamePanel.remove(line);
            removeLine.add(line);
            gamePanel.repaint();
        }else{
            // 计算结果
            BigInteger res;
            if(edge.getEdge().getOp()=='+'){
                res = edge.getP1().getPoint().getNum().add(edge.getP2().getPoint().getNum());
            }else{
                res = edge.getP1().getPoint().getNum().multiply(edge.getP2().getPoint().getNum());
            }
            tmp[i].getPoints().getPoint().setNum(res);

            // 处理新产生的线
            DrawPoint tP = tmp[i].getEdges().getP2();
            if(tmp[(i+1)%tmp.length].edges!=null){
                tmp[i].setEdges(tmp[(i+1)%tmp.length].edges);
                tmp[i].edges.setP1(tmp[i].getPoints());

            }else{
                tmp[i].edges=null;
            }

            // 创建步骤记录
            Polygon[] t1 = new Polygon[tmp.length-1];
            int j=0;
            for(int k=0;k<tmp.length-1;j++){
                if(tmp[j].points.equals(tP)){
                    continue;
                }else if(tmp[j].points.getPoint().getNum()==res){
                    gameFrame.setColorOne(k); // 记录新计算的点
                    Color[stepNum] = k;
                }
                t1[k] = tmp[j];
                k++;
            }
            steps.add(t1);
            gameFrame.plss = t1; // 传值
            gamePanel.remove(line); // 移除线组件
            removeLine.add(line);
            // 计算当前点的位置和线的位置
            gameFrame.manager.calculateNewPos(t1,t1.length);
            gameFrame.reCalcalateLine();
            gamePanel.repaint();
        }
        // 新增历史记录
        refreshHis(steps.get(steps.size()-1),stepNum,"点击");
        if(stepNum==steps.get(0).length){
            finishGame(steps.get(stepNum)[0].getPoints().getPoint().getNum());
        }
        stepNum++;
    }

    /**
     * 撤回步骤
     */
    public void recall(){
        if(stepNum==1)
            return;
        stepNum--;

        // 删除当前步骤
        steps.remove(steps.size()-1);
        gameFrame.plss = steps.get(steps.size()-1);
        JLine l = removeLine.get(removeLine.size()-1);
        removeLine.remove(removeLine.size()-1);

        // 往回添加线
        gamePanel.add(l);

        // 重新计算
        gameFrame.manager.calculateNewPos(gameFrame.plss,gameFrame.plss.length);
        gameFrame.reCalcalateLine();
        gameFrame.setColorOne(Color[stepNum-1]);
        gamePanel.repaint();

        // 新增历史记录
        refreshHis(gameFrame.plss,stepNum-1,"撤回");
    }

    /**
     * 创建最高分系统
     */
    public void createBest(){
        BestFrame bestFrame = new BestFrame();
        bestFrame.initFrame(steps.get(0),this.gameFrame.manager);
    }

    /**
     * 重置事件
     */
    public void reset(){

        // 清除所有步骤保留原始状态
        Polygon[] origin = steps.get(0);
        steps.clear();
        steps.add(origin);

        // 往回添加线
        for(int i=0;i<removeLine.size();i++){
            gamePanel.add(removeLine.get(i));
        }
        removeLine.clear();

        // 传值重绘
        gameFrame.plss = origin;
        gameFrame.manager.calculateNewPos(gameFrame.plss,gameFrame.plss.length);
        gameFrame.reCalcalateLine();
        gameFrame.setColorOne(-1);
        gamePanel.repaint();
        stepNum=1;
    }

    /**
     * 创建历史记录窗口
     */
    public void CreatehistoryWin(){
        if(historyFrame == null){
            historyFrame = new HistoryFrame();
            historyFrame.initFrame();
            historyFrame.setVisible(false);
        }
        historyFrame.addHis(steps.get(0),his++,-1,"初始");
    }

    /**
     * 显示历史记录窗口
     */
    public void showHistoryWin(){
        historyFrame.setVisible(true);
    }

    /**
     * 添加历史记录
     * @param plss 历史记录数组
     * @param i 该记录新计算的元素
     * @param str 历史记录注明
     */
    public void refreshHis(Polygon[] plss,int i,String str){
        if(historyFrame!=null)
            historyFrame.addHis(plss,his++,Color[i],str);
    }

    /**
     * 清除所有历史记录
     */
    public void clearHis(){
        if(historyFrame!=null)
            historyFrame.clearAll();
    }

    /**
     * 结束游戏
     * @param playScore 当前玩家玩的分数
     */
    public void finishGame(BigInteger playScore){
        BigInteger result = BestSolutionController.getInstance().getResult();
        JLabel label = new JLabel();
        label.setText("你的分数是:"+playScore.toString()
                +"\n最优解为:"+result);
        // 允许用户查看最高分数，禁止撤回和重置
        inputFrame.getInputPanel().add(label);
        inputFrame.setBestVisible(true);
        inputFrame.setrecallEnable(false);
        inputFrame.setResetEnable(false);
    }
}
