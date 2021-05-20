package views;

import controllers.MyMouseListener;
import models.SquareModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SquarePanel extends JPanel {

    private SquareModel squareModel;

    public SquareModel getSquare() {
        return squareModel;
    }

    public void setSquare(SquareModel squareModel) {
        this.squareModel = squareModel;
    }

    public SquarePanel(SquareModel squareModel) {
        this.squareModel = squareModel;
    }

//    public void setListner(MyMouseListener MyListner){
////        setListener();
//        if(squareModel.isMutable() || squareModel.getIdPLayer()==1){
//            this.removeMouseListener(MyListner);
//            this.addMouseListener(MyListner);
//        }else{
//            this.removeMouseListener(MyListner);
//        }
//    }

    @Override
    public void paint(Graphics g) {
        if (squareModel.getColumn() % 2 != squareModel.getRow() % 2) {
            g.setColor(new Color(181,136,99));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        else
        {
            g.setColor(new Color(240,217,181));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        try {
            if (squareModel.getIdPLayer() == 1)
            {
                paintPiece("pieceWhite.png",g);
            }
            if (squareModel.getIdPLayer() == 2)
                paintPiece("pieceBlack.png",g);

        } catch (IOException e) {
            e.printStackTrace();
        }
        g.setColor(Color.RED);
//        int padding= 24;
//        g.fillOval(padding/2, padding/2, getWidth()-padding, getHeight()-padding);
    }

    public BufferedImage resizeImg(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public void paintPiece(String pathImage, Graphics g) throws IOException {
        BufferedImage image = ImageIO.read(new File(pathImage));
        image = resizeImg(image,70,70);
        g.drawImage(image, getWidth() / 2 - image.getWidth() / 2, getHeight() / 2 - image.getHeight() / 2, null);
    }
}
