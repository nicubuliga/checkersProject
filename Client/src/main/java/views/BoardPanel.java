package views;

import controllers.MyMouseListener;

import javax.swing.*;

public class BoardPanel extends JPanel {

    private MyMouseListener listener;
    public BoardPanel(MyMouseListener listener)
    {
        this.listener = listener;
    }
}
