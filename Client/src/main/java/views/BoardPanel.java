package views;

import controllers.MyMouseListener;
import models.Square;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {

    private Dimension dimension = new Dimension(720, 720);
    private MyMouseListener listener;
    private List<SquarePanel> panels;

    public BoardPanel(MyMouseListener listener) {
        panels = new ArrayList<>();
        setPreferredSize(dimension);
        setLayout(new GridLayout(8, 8));
        initTable();
        this.listener = listener;
    }

    private void initTable() {
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                Square square = new Square(i, k);
                SquarePanel sPanel = new SquarePanel(square);
                panels.add(sPanel);
                add(sPanel);
            }
        }
        repaint();
    }
}
