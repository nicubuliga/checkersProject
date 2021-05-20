package controllers;

import models.SquareModel;
import views.BoardPanel;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller implements Runnable {

    private PrintWriter out;
    private BufferedReader in;
    private boolean currentPlayerTurn = false;
    public int idPlayer;
    private List<SquareModel> playableSquares = new ArrayList<>();
    private List<SquareModel> selectedSquares = new ArrayList<>();
    private boolean running = true;
    private boolean waitingForAction;

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public boolean isCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public void setCurrentPlayerTurn(boolean currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    private BoardPanel boardPanel;

    public Controller(BufferedReader bufferedReader, PrintWriter printWriter) {
        this.in = bufferedReader;
        this.out = printWriter;
    }

    public void setBoardPanel(BoardPanel panel) {
        this.boardPanel = panel;
    }

    public void selectSquare(SquareModel squareModel) {
        if (selectedSquares.isEmpty() && squareModel.getIdPLayer() != 0)
            addToSelected(squareModel);
        else {
            System.out.println(playableSquares);
            if (playableSquares.contains(squareModel)) {
                //move
                move(selectedSquares.get(0), squareModel);
            } else {
                deselectSquare();
                addToSelected(squareModel);
            }
        }

    }

    public void deselectSquare() {
        for (SquareModel square : selectedSquares)
            square.setSelected(false);

        selectedSquares.clear();

        for (SquareModel square : playableSquares) {
            square.setMutable(false);
        }

        playableSquares.clear();

        boardPanel.repaintPanels();
    }

    private void addToSelected(SquareModel squareModel) {
        squareModel.setSelected(true);
        selectedSquares.add(squareModel);
        getPlayableSquares(squareModel);
    }

    private void getPlayableSquares(SquareModel squareModel) {
        playableSquares.clear();
        playableSquares = boardPanel.getPlayableSquares(squareModel);

        boardPanel.repaintPanels();
    }

    private void checkCrossJump(SquareModel from, SquareModel to) {
        if (Math.abs(from.getRow() - to.getRow()) == 2) {
            int middleRow = (from.getRow() + to.getRow()) / 2;
            int middleCol = (from.getColumn() + to.getColumn()) / 2;

            SquareModel middleSquare = boardPanel.getSquare((middleRow * 8) + middleCol);
            middleSquare.setIdPLayer(0);
//            middleSquare.removeKing();
        }
    }


    public void move(SquareModel from, SquareModel to) {
        to.setIdPLayer(from.getIdPLayer());
        from.setIdPLayer(0);
        checkCrossJump(from, to);
//        checkKing(from, to);
        deselectSquare();

        waitingForAction = false;
        try {
            sendMove(from, to);
        } catch (IOException e) {
            System.out.println("Sending failed");
        }
    }

    // 55 43
    private void sendMove(SquareModel from, SquareModel to) throws IOException {
        if (this.idPlayer == 2) {
            String firstPos = (7 - from.getRow()) + (7 - from.getColumn()) + "";
            String secondPos = (7 - to.getRow()) + (7 - to.getColumn()) + "";
            out.println(firstPos + " " + secondPos);
        } else {
            String firstPos = from.getRow() + from.getColumn() + "";
            String secondPos = to.getRow() + to.getColumn() + "";
            out.println(firstPos + " " + secondPos);
        }
    }

    private void waitForAction() throws InterruptedException {
        this.currentPlayerTurn = true;
        while (waitingForAction) {
            Thread.sleep(100);
        }

        waitingForAction = false;

    }

    @Override
    public void run() {
        while (running) {
            try {
                String responseServer = in.readLine();
                String[] args = responseServer.split(" ");
                String response = args[0];

                if (response.equals("test")) {
                    out.println("ok");
                    out.flush();
                } else if (response.equals("turn")) {
                    waitForAction();
                } else if (response.equals("start")) {
                    this.idPlayer = Integer.parseInt(args[1]);
                }
            } catch (SocketException e) {
                running = false;
                System.out.println("Server disconnected");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
