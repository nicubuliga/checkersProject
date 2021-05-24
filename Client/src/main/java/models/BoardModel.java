package models;

import java.util.LinkedList;
import java.util.List;

public class BoardModel {
    private SquareModel[][] squares;

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
        initBoard(idPlayer);
    }

    private int idPlayer;

    public BoardModel(int idPlayer) {
        squares = new SquareModel[8][8];
        this.idPlayer = idPlayer;
        initBoard(idPlayer);
    }

    private void initBoard(int idPlayer) {
        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j) {
                squares[i][j] = new SquareModel(i, j);
                if (i % 2 != j % 2 && i < 3) {
                    squares[i][j].setIdPLayer(3 - idPlayer);
                } else if (i % 2 != j % 2 && i > 4)
                    squares[i][j].setIdPLayer(idPlayer);
            }
    }

    public SquareModel getSquare(int i, int j) {
        return squares[i][j];
    }

    public List<SquareModel> getPlayableSquares(SquareModel squareModel) {
        LinkedList<SquareModel> playableSquares = new LinkedList<SquareModel>();

        int selectedRow = squareModel.getRow();
        int selectedCol = squareModel.getColumn();

        int movableRow = selectedRow - 1;

        //check two front squares
        twoFrontSquares(playableSquares, movableRow, selectedCol);
        crossJumpFront(playableSquares, movableRow - 1, selectedCol, movableRow);
        if(squareModel.isKing()){
            movableRow = selectedRow + 1;
            twoFrontSquares(playableSquares, movableRow , selectedCol);
            crossJumpFront(playableSquares, movableRow+1, selectedCol, movableRow);
        }
        return playableSquares;
    }

    private void twoFrontSquares(List<SquareModel> pack, int movableRow, int selectedCol) {

        if (movableRow >= 0 && movableRow < 8) {
            //right Corner
            if (selectedCol >= 0 && selectedCol < 7) {
                SquareModel rightCorner = squares[movableRow][selectedCol + 1];
                if (rightCorner.getIdPLayer() == 0) {
                    pack.add(rightCorner);
                }
            }

            //left upper corner
            if (selectedCol > 0 && selectedCol <= 8) {
                SquareModel leftCorner = squares[movableRow][selectedCol - 1];
                if (leftCorner.getIdPLayer() == 0) {
                    pack.add(leftCorner);
                }
            }
        }
    }

    private void crossJumpFront(List<SquareModel> pack, int movableRow, int selectedCol, int middleRow) {

        int middleCol;

        if (movableRow >= 0 && movableRow < 8) {
            //right upper Corner
            if (selectedCol >= 0 && selectedCol < 6) {
                SquareModel rightCorner = squares[movableRow][selectedCol + 2];
                middleCol = selectedCol + 1;
                if (rightCorner.getIdPLayer() == 0 && isOpponentInbetween(middleRow, middleCol)) {
                    pack.add(rightCorner);
                }
            }

            //left upper corner
            if (selectedCol > 1 && selectedCol <= 7) {
                SquareModel leftCorner = squares[movableRow][selectedCol - 2];
                middleCol = selectedCol - 1;
                if (leftCorner.getIdPLayer() == 0 && isOpponentInbetween(middleRow, middleCol)) {
                    pack.add(leftCorner);
                }
            }
        }
    }

    private boolean isOpponentInbetween(int row, int col) {
        if (squares[row][col].getIdPLayer() != idPlayer && squares[row][col].getIdPLayer() != 0)
            return true;
        return false;
    }
}


