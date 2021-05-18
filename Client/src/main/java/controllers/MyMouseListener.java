package controllers;

import java.awt.event.MouseAdapter;

public class MyMouseListener extends MouseAdapter {
    private Controller controller;

    public void setController(Controller c){
        this.controller = c;
    }

}
