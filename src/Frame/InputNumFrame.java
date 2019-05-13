package Frame;

import Manager.PolygonManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

//这个是用来记录输入的画板
public class InputNumFrame {
    private int width = 300;
    private int height = 500;
    private MainFrame mainFrame;
    private JLabel tips;
    int[] num = null;
    int N = -1;
    char[] op = null;
    JTextField n;
    JTextField numInput;
    JTextField opInput;
    private int MODE = 0;
    private final int INTEGERMODE = 0;
    private final int RANGEMODE = 1;

    public InputNumFrame(MainFrame parent,JLabel tips){
        this.mainFrame = parent;
        this.tips = tips;
    }

    public JPanel init() {
        JPanel input = new JPanel();
        input.setPreferredSize(new Dimension(width, height));
        input.setBackground(Color.decode("#F18F01"));
        JLabel input_n = new JLabel("输入n：");
        Font lab = new Font("楷体",1,15);
        n = new JTextField();
        JButton preview = new JButton("查看预览");
        Font btn = new Font("楷体",2,15);
        JRadioButton  radio_Integer= new JRadioButton("整数值输入");
        JRadioButton  radio_Range= new JRadioButton("范围值输入");
        Font rad_btn = new Font("楷体",1,15);
        numInput = new JTextField();
        JLabel input_symbol = new JLabel("运算符输入");
        opInput = new JTextField();
        Font lab1 = new Font("楷体",1,15);
        JButton sure = new JButton("确定");

        JButton ranNum = new JButton("r");
        JButton ranChar = new JButton("r");

        //设置控件大小和位置
        input_n.setSize(95,40);
        input_n.setLocation(50,70);
        input_n.setFont(lab);
        n.setSize(135,40);
        n. setLocation(115,70);

        preview.setSize(70,40);
        preview. setLocation(220,15);
        preview.setFont(btn);

        sure.setBounds(120,15,70,40);

        radio_Integer.setSize(120,50);
        radio_Integer. setLocation(50,110);
        radio_Integer.setFont(rad_btn);
        radio_Range.setSize(120,50);
        radio_Range. setLocation(50,150);
        radio_Range.setFont(rad_btn);

        ButtonGroup group = new ButtonGroup();
        group.add(radio_Integer);
        group.add(radio_Range);
        radio_Integer.setSelected(true);


        numInput.setSize(200,100);
        numInput. setLocation(50,190);
        ranNum.setBounds(0,190,10,10);

        input_symbol.setSize(95,50);
        input_symbol. setLocation(50,300);
        input_symbol.setFont(lab1);
        opInput.setSize(200,100);
        opInput.setLocation(50,350);
        ranChar.setBounds(0,350,10,10);
        // 添加控件
        input.add(input_n);
        input.add(n);
        input.add(preview);
        input.add(radio_Integer);
        input.add(radio_Range);
        input.add(numInput);
        input.add(input_symbol);
        input.add(opInput);
        input.setLayout(null);

        input.add(sure);
        input.add(ranChar);
        input.add(ranNum);

        radio_Integer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MODE = INTEGERMODE;
            }
        });
        radio_Range.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MODE = RANGEMODE;
            }
        });

        preview.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {

                // 输入n
                if(n.getText().equals("")){
                    tips.setText("请输入n");
                }else if( numInput.getText().equals("")){
                    if(MODE==INTEGERMODE)
                        tips.setText("请输入数字");
                    else if(MODE==RANGEMODE)
                        tips.setText("请输入范围");
                }else if(opInput.getText().equals("")){
                    tips.setText("请输入运算符");
                }else{
                    if(dealData()!=-1)
                        mainFrame.preview(N,num,op);
                }
            }
        });
        sure.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(N==-1 || num==null ||op==null){
                    dealData();
                }
                mainFrame.createGame(N,num,op);
            }
        });

        ranNum.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(N==-1){
                    if(n.getText().equals("")){
                        tips.setText("请先输入N");
                        return;
                    }else{
                        N = Integer.parseInt(n.getText());
                        if(N>20){
                            tips.setText("N超出范围，请重新输入");
                        }
                    }
                }
                num = randomNum(-10,20);
                String str = "";
                for(int i=0;i<N;i++){
                    if(i==N-1){
                        str += num[i];
                        break;
                    }
                    str += num[i] + ",";
                }
                numInput.setText(str);
            }
        });

        ranChar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(N==-1){
                    if(n.getText().equals("")){
                        tips.setText("请先输入N");
                        return;
                    }else{
                        N = Integer.parseInt(n.getText());
                        if(N>20){
                            tips.setText("N超出范围，请重新输入");
                        }
                    }
                }
                op = randomOp();
                String str = "";
                for(int i=0;i<N;i++){
                    if(i==N-1){
                        str += op[i];
                        break;
                    }
                    str += op[i] + ",";
                }
                opInput.setText(str);
            }
        });

        return input;
    }

    private int dealData(){
        N = Integer.parseInt(n.getText());
        if(N>20){
            tips.setText("N超出范围，请重新输入");
            return -1;
        }

        if(MODE==INTEGERMODE)
            num = getNum(numInput.getText(),N);
        else if(MODE==RANGEMODE){
            num =getRandomNum(numInput.getText());
        }


        if(num==null){
            tips.setText("节点数据输入出错");
            return -1;
        }
        op = getOp(opInput.getText(),N);
        if(op==null){
            tips.setText("节点运算符输入出错");
            return -1;
        }
        return 0;
    }

    private int[] getNum(String num,int n){
        int[] data = new int[n];

        String[] list = num.split(",");
        if(list.length!=n)
            return null;
        else{
            for(int i=0;i<n;i++){
                data[i] = Integer.parseInt(list[i]);
            }
        }
        return data;
    }

    private char[] getOp(String op,int n){
        char[] data=new char[n];
        String[] list = op.split(",");
        if(list.length!=n){
            return null;
        }else{
            for(int i=0;i<n;i++){
                data[i] = list[i].charAt(0);
                if(data[i]!='+'&&data[i]!='*'){
                    return null;
                }
            }
        }
        return data;
    }

    private int[] getRandomNum(String range){
        int min = 0, max = 0;
        String[] r = range.split(",");
        if(r.length!=2)
            return null;
        else{
            min = Integer.parseInt(r[0]);
            max = Integer.parseInt(r[1]);
            if(min>max){
                int tmp = min;
                min = max;
                max = tmp;
            }
        }


        return randomNum(min,max);
    }

    public int[] randomNum(int min,int max){
        int[] num;
        num = new int[N];
        for (int i = 0; i < N; i++) {
            Random ran = new Random();
            num[i] = ran.nextInt(max-min)+min;
        }
        return num;
    }

    public char[] randomOp(){
        char[] operator;
        operator = new char[N];
        for (int i = 0; i < N; i++) {
            Random ran = new Random();
            int choose = ran.nextInt(2);
            switch (choose) {
                case 0:
                    operator[i] = '+';
                    break;
                case 1:
                    operator[i] = '*';
                    break;
            }

        }
        return operator;
    }
}
