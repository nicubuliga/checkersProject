package controllers;

import models.SquareModel;
import views.SquarePanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class MyMouseListener extends MouseAdapter {
    private SquarePanel squarePanel;
    private Controller controller;


    public void setController(Controller c){
        this.controller = c;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e);
        super.mousePressed(e);
        try{
            if(controller.isCurrentPlayerTurn()){
                ToggleSelectPiece(e);
            }else{
                JOptionPane.showMessageDialog(null, "Not your turn",
                        "Error", JOptionPane.ERROR_MESSAGE, null);
            }
        }catch(Exception ex){
            System.out.println("Error");
        }


    }

    private void ToggleSelectPiece(MouseEvent e){
        try{
            squarePanel = (SquarePanel) e.getSource();
            SquareModel s = squarePanel.getSquare();

            //if square is already selected - deselect
            if(s.isSelected()){
                System.out.println("deselect - "+s.getColumn() + " " + s.getRow());
                controller.deselectSquare();
            }
            //else select
            else{
                System.out.println("select - "+s.getColumn() + " " + s.getRow());
                controller.selectSquare(s);
            }
        }catch(Exception ex){
            System.out.println("error");
        }
    }

}
