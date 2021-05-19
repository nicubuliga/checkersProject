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
    private int idPlayer;

    public BoardPanel(MyMouseListener listener, int idPlayer) {
        panels = new ArrayList<>();
        setPreferredSize(dimension);
        setLayout(new GridLayout(8, 8));
        initTable();
        this.listener = listener;
        this.idPlayer = idPlayer;
    }

    private void initTable() {
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                Square square = new Square(i, k);
                if(i < 2)
                    square.setIdPLayer(3-idPlayer);
                else
                    if(i>5)
                        square.setIdPLayer(idPlayer);
                SquarePanel sPanel = new SquarePanel(square);
                panels.add(sPanel);
                add(sPanel);
            }
        }
        repaint();
    }
}
