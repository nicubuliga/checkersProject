package views;

import controllers.MyMouseListener;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private Dimension dimension = new Dimension(1280,720);
    private MyMouseListener listener;
    public BoardPanel(MyMouseListener listener)
    {
        setPreferredSize(dimension);
        setLayout(new GridLayout(8,8));

        this.listener = listener;
    }

}
