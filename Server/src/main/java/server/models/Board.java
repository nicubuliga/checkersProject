package server.models;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private Square[][] squares;

    public Board() {
        squares = new Square[8][8];
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j) {
                squares[i][j] = new Square(i, j);
                if (i % 2 != j % 2 && i < 3) {
                    squares[i][j].setIdPlayer(2);
                } else if (i % 2 != j % 2 && i > 4)
                    squares[i][j].setIdPlayer(1);
            }
    }

    public void makeMove(String move) {
        String[] moves = move.split(" ");
        int from = Integer.parseInt(moves[0]);
        int to = Integer.parseInt(moves[1]);

        Square fromSquare = squares[from / 10][from % 10];
        Square toSquare = squares[to / 10][to % 10];

        checkKing(fromSquare, toSquare);

        toSquare.setIdPlayer(fromSquare.getIdPlayer());
        fromSquare.setIdPlayer(0);

        checkJump(fromSquare, toSquare);

    }

    private void checkJump(Square from, Square to) {
        if (Math.abs(from.getRow() - to.getRow()) == 2) {
            int middleRow = (from.getRow() + to.getRow()) / 2;
            int middleCol = (from.getColumn() + to.getColumn()) / 2;

            Square middleSquare = squares[middleRow][middleCol];
            middleSquare.setIdPlayer(0);
        }
    }

    public int checkWinner() {
        int nrWhiteMoves = 0, nrBlackMoves = 0;

        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j)
                if (squares[i][j].getIdPlayer() == 1) {
                    nrWhiteMoves += nrPlayableSquares(squares[i][j]);
                } else {
                    nrBlackMoves += nrPlayableSquares(squares[i][j]);
                }

        if(nrBlackMoves == 0) return 1;
        if(nrWhiteMoves == 0) return 2;

        return 0;
    }

    private int nrPlayableSquares(Square square) {
        List<Square> playableSquares = new LinkedList<Square>();

        int selectedRow = square.getRow();
        int selectedCol = square.getColumn();

        int movableRow = selectedRow;

        if (square.getIdPlayer() == 1) movableRow--;
        else movableRow++;

        //check two front squares
        twoFrontSquares(playableSquares, movableRow, selectedCol);

        if (square.getIdPlayer() == 1)
            crossJumpFront(playableSquares, movableRow - 1, selectedCol, movableRow);
        else
            crossJumpFront(playableSquares, movableRow + 1, selectedCol, movableRow);

        if (square.isKing()) {
            if (square.getIdPlayer() == 1)
                movableRow = selectedRow + 1;
            else
                movableRow = selectedRow - 1;
            twoFrontSquares(playableSquares, movableRow, selectedCol);
            if (square.getIdPlayer() == 1)
                crossJumpFront(playableSquares, movableRow + 1, selectedCol, movableRow);
            else
                crossJumpFront(playableSquares, movableRow - 1, selectedCol, movableRow);
        }
        return playableSquares.size();
    }

    private void checkKing(Square from, Square movedSquare) {
        if (from.isKing()) {
            movedSquare.setKing(true);
            from.setKing(false);
        } else if (movedSquare.getRow() == 7 && from.getIdPlayer() == 2
                || movedSquare.getRow() == 0 && from.getIdPlayer() == 1) {
            movedSquare.setKing(true);
        }
    }

    private void twoFrontSquares(List<Square> pack, int movableRow, int selectedCol) {

        if (movableRow >= 0 && movableRow < 8) {
            //right Corner
            if (selectedCol >= 0 && selectedCol < 7) {
                Square rightCorner = squares[movableRow][selectedCol + 1];
                if (rightCorner.getIdPlayer() == 0) {
                    pack.add(rightCorner);
                }
            }

            //left upper corner
            if (selectedCol > 0 && selectedCol <= 8) {
                Square leftCorner = squares[movableRow][selectedCol - 1];
                if (leftCorner.getIdPlayer() == 0) {
                    pack.add(leftCorner);
                }
            }
        }
    }

    private void crossJumpFront(List<Square> pack, int movableRow, int selectedCol, int middleRow) {

        int idPlayer = squares[middleRow][selectedCol].getIdPlayer();

        int middleCol;

        if (movableRow >= 0 && movableRow < 8) {
            //right upper Corner
            if (selectedCol >= 0 && selectedCol < 6) {
                Square rightCorner = squares[movableRow][selectedCol + 2];
                middleCol = selectedCol + 1;
                if (rightCorner.getIdPlayer() == 0 && isOpponentInbetween(middleRow, middleCol, 3 - idPlayer)) {
                    pack.add(rightCorner);
                }
            }

            //left upper corner
            if (selectedCol > 1 && selectedCol <= 7) {
                Square leftCorner = squares[movableRow][selectedCol - 2];
                middleCol = selectedCol - 1;
                if (leftCorner.getIdPlayer() == 0 && isOpponentInbetween(middleRow, middleCol, 3 - idPlayer)) {
                    pack.add(leftCorner);
                }
            }
        }
    }

    private boolean isOpponentInbetween(int row, int col, int idPlayer) {
        if (squares[row][col].getIdPlayer() != idPlayer && squares[row][col].getIdPlayer() != 0)
            return true;
        return false;
    }
}
