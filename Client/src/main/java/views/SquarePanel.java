package views;

import models.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SquarePanel extends JPanel {

    private Square square;
//    @Override
//    protected void paintComponent(Graphics g) {
//        g.setColor(Color.GREEN);
//        g.fillRect(0, 0, getWidth(), getHeight());
//    }

    public SquarePanel (Square square)
    {
        this.square = square;
    }
    @Override
    public void paint(Graphics g) {
        if(square.getColumn() % 2 != square.getRow() % 2)
        {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        try {
            BufferedImage image = ImageIO.read(new File("piece.png"));
            g.drawImage(image,getWidth()/2 - image.getWidth()/2, getHeight()/2 - image.getHeight()/2, null );
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.setColor(Color.RED);
//        int padding= 24;
//        g.fillOval(padding/2, padding/2, getWidth()-padding, getHeight()-padding);
    }

}
