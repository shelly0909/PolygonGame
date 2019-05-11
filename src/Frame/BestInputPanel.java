package Frame;

import Manager.BestSolutionController;
import Manager.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 最高分数界面的交互窗口
 */
public class BestInputPanel {
    private int width = 300;
    private int height = 500;

    public JPanel init() {
        JPanel input = new JPanel();
        input.setPreferredSize(new Dimension(width, height));
        input.setBackground(Color.decode("#F18F01"));

        // 上一步按钮
        JButton back = new JButton("上一步");
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                BestSolutionController.getInstance().recall();
            }
        });
        input.add(back);

        // 下一步按钮
        JButton go = new JButton("下一步");
        go.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                BestSolutionController.getInstance().showBestSolution();
            }
        });
        input.add(go);

        return input;
    }
}
