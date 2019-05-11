package Manager;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BestSolution {

    long[][][] m;// i,j的最值，0是最小,1是最大(两张表
    int n ; // 顶点数
    char[] op; // 符号数, 长度n,从1开始
    long minf,maxf; // 主链最小值、最大值
    int[][][] sPos;
    List<Integer> route;


    /**
     * 初始化
     * @param n 元素长度
     * @param op 操作符数组
     * @param num 节点数组
     */
    public BestSolution(int n,char[] op, long[] num){
        this.n = n;
        this.op = new char[n+1];
        System.arraycopy(op,0,this.op,1,op.length);
        this.m = new long[n+1][n+1][2];
        route = new ArrayList<>();
        // m矩阵填充
        for(int k=0;k<2;k++){
            for(int i=1;i<=n;i++){
                for(int j=1;j<=n;j++){
                    if(k==0)
                        m[i][j][k] = Integer.MAX_VALUE;
                    else
                        m[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }

        this.sPos = new int[n+1][n+1][2];
        for(int i=0;i<n;i++){
            // 初始化m,从1开始
            this.m[i+1][1][0] =this.m[i+1][1][1] = num[i];
        }

    }

    public long ployMax(){
        // 生成i-j的最值表
        for(int j=2;j<=n;j++){ // 从第二列开始
            for(int i=1;i<=n;i++){
                for(int s = 1;s<j;s++){
                    minMax(i,s,j); // 找操作符在此处的最值
                    if(m[i][j][0]>minf) {
                        m[i][j][0]=minf; // 更新最小值和最大值,初始0
                        sPos[i][j][0] = s; // 左子链长度
                    }
                    if(m[i][j][1]<maxf) {
                        m[i][j][1]=maxf;
                        sPos[i][j][1] = s;
                    }
                }
            }
        }
        long tmp = m[1][n][1];
        int b = 1;
        for(int i=2;i<=n;i++){
            // 找到从第i个顶点开始的最大值
            if(tmp<m[i][n][1]) {
                tmp = m[i][n][1];
                b = i;
            }
        }

        System.out.println("开始顶点为:"+b+",值:"+m[b][1][0]);
        //printMatrix(sPos);


        if(b-1==0){
            route.add(n);
        }else{
            route.add(b-1);
        }
        getRoute(b,n);
        return tmp;
    }

    /**
     * 递归求从第i个开始长度为j的序列的路径
     * @param i 第i个元素
     * @param j 长度为j
     * @return 点击的操作符
     */
    public long getRoute(int i,int j){
        if(j==1)
            return m[i][j][1];
        long left = getRoute(i,sPos[i][j][1]);
        int r = (i+sPos[i][j][1] - 1)%n;
        long right = getRoute(r+1,j-sPos[i][j][1]);
        if(r==0) r=n;
//        System.out.println(left+" "+op[r]+" "+right);
        route.add(r);
        long result = 0;
        if(op[r]=='+')
            result = left+right;
        else if(op[r]=='*')
            result = left * right;
        return result;
    }

    /**
     * 打印三维数组
     * @param tmp 目标数组
     */
    public void printMatrix(long[][][] tmp){
        for(int k=0;k<2;k++){
            for(int i=1;i<=n;i++){
                for(int j=1;j<=n;j++){
                    System.out.print(tmp[i][j][k]+" ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
    public void printMatrixINT(int[][][] tmp){
        for(int k=0;k<2;k++){
            for(int i=1;i<=n;i++){
                for(int j=1;j<=n;j++){
                    System.out.print(tmp[i][j][k]+" ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
    /**
     * 找出以第i个元素的长度为j的序列在s处中断的最值
     * @param i 第i个元素
     * @param s 中断位置
     * @param j 长度为j
     */
    public void minMax(int i,int s,int j){
        long[] e = new long[5];
         int    r = (i+s-1)%n ;// 多边形封闭，例(3,2,3),算的是从第3个顶点开始长度为3的主链的最值,i+s>n取模轮回去
        long  a = m[i][s][0],
                b = m[i][s][1], // 左子链的最值
                c = m[r+1][j-s][0],
                d = m[r+1][j-s][1]; // 右子链的最值
        if(r==0) r=n;
        //System.out.println("r:"+r);
        if(op[r]=='+'){
            minf = a+c;
            maxf = b+d;
        }else if(op[r]=='*'){
            e[1] = a*c;
            e[2] = a*d;
            e[3] = b*c;
            e[4] = b*d;
            maxf =  minf = e[1];
            for(int k=2;k<5;k++){
                if(minf>e[k]) minf = e[k];
                if(maxf<e[k]) maxf = e[k];
            }
        }
    }


}
