package views;

import controllers.MyMouseListener;
import models.BoardModel;
import models.SquareModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BoardPanel extends JPanel {

    private Dimension dimension = new Dimension(720, 720);
    private BoardModel boardModel;
    private MyMouseListener listener;
    private List<SquarePanel> panels;

    public BoardPanel(MyMouseListener listener, int idPlayer) {
        panels = new ArrayList<>();
        setPreferredSize(dimension);
        setLayout(new GridLayout(8, 8));
        boardModel = new BoardModel(idPlayer);
        this.listener = listener;
        initTable();
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    private void initTable() {
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                SquarePanel sPanel = new SquarePanel(boardModel.getSquare(i,k));
                sPanel.addMouseListener(listener);
                panels.add(sPanel);
                add(sPanel);
            }
        }
        repaint();
    }

    public void repaintPanels(){
//        for(SquarePanel panel : panels){
//            panel.setListner(listener);
//        }
        repaint();
    }

    public List<SquareModel> getPlayableSquares(SquareModel squareModel)
    {
        return boardModel.getPlayableSquares(squareModel);
    }

    public SquareModel getSquare(int ID){
        return panels.get(ID).getSquare();
    }

}
