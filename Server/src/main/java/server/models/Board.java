package server.models;

import java.util.LinkedList;
import java.util.List;

public class Board{
    private Square[][] squares;
    private int nrWhitePiece = 0;
    private int nrBlackPiece = 0;
    private int nrWhiteKing = 0;
    private int nrBlackKing = 0;

    public int getNrWhitePiece() {
        return nrWhitePiece;
    }

    public int getNrBlackPiece() {
        return nrBlackPiece;
    }

    public int getNrWhiteKing() {
        return nrWhiteKing;
    }

    public int getNrBlackKing() {
        return nrBlackKing;
    }

    public Board() {
        squares = new Square[8][8];
        initBoard();
    }

    //copy constructor
    public Board(Board board)
    {
        squares = new Square[8][8];
        for(int i=0; i < 8; ++i)
            for(int j=0; j < 8; ++j)
            {
                this.squares[i][j] = new Square(i,j);
                this.squares[i][j].setKing(board.getSquares()[i][j].isKing());
                this.squares[i][j].setIdPlayer(board.getSquares()[i][j].getIdPlayer());
                this.nrWhitePiece = board.getNrWhitePiece();
                this.nrBlackPiece = board.getNrBlackPiece();
                this.nrWhiteKing = board.getNrWhiteKing();
                this.nrBlackKing = board.getNrBlackKing();
            }
    }

    public Square[][] getSquares() {
        return squares;
    }

    private void initBoard() {
        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j) {
                squares[i][j] = new Square(i, j);
                if (i % 2 != j % 2 && i < 3) {
                    {
                        squares[i][j].setIdPlayer(2);
                        nrBlackPiece++;
                    }
                } else if (i % 2 != j % 2 && i > 4)
                {
                    squares[i][j].setIdPlayer(1);
                    nrWhitePiece++;
                }
            }
    }

    public void makeMove(String move, int idPlayer) {
        String[] moves = move.split(" ");
        int from = Integer.parseInt(moves[0]);
        int fromRow = from / 10;
        int fromCol = from % 10;
        int to = Integer.parseInt(moves[1]);
        int toRow = to / 10;
        int toCol = to % 10;
        if (idPlayer == 1) {
            fromRow = 7 - fromRow;
            fromCol = 7 - fromCol;
            toRow = 7 - toRow;
            toCol = 7 - toCol;
        }

        Square fromSquare = squares[fromRow][fromCol];
        Square toSquare = squares[toRow][toCol];

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

            if(middleSquare.getIdPlayer() == 1)
            {

                if(middleSquare.isKing())
                {
                    nrWhiteKing--;
                } else
                    nrWhitePiece--;
            }
            else
            {

                if(middleSquare.isKing())
                    nrBlackKing--;
                else
                    nrBlackPiece--;
            }
            middleSquare.setIdPlayer(0);
            middleSquare.setKing(false);
        }
    }

    public int checkWinner() {
        int nrWhiteMoves = 0, nrBlackMoves = 0;

        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j)
                if (squares[i][j].getIdPlayer() == 1) {
                    nrWhiteMoves += nrPlayableSquares(squares[i][j]);
                } else if (squares[i][j].getIdPlayer() == 2) {
                    nrBlackMoves += nrPlayableSquares(squares[i][j]);
                }
        System.out.println("nr black: " + nrBlackMoves);
        System.out.println("nr white: " + nrWhiteMoves);
        if (nrBlackMoves == 0) return 1;
        if (nrWhiteMoves == 0) return 2;

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
            crossJumpFront(playableSquares, movableRow - 1, selectedCol, movableRow, square.getIdPlayer());
        else
            crossJumpFront(playableSquares, movableRow + 1, selectedCol, movableRow, square.getIdPlayer());

        if (square.isKing()) {
            if (square.getIdPlayer() == 1)
                movableRow = selectedRow + 1;
            else
                movableRow = selectedRow - 1;
            twoFrontSquares(playableSquares, movableRow, selectedCol);
            if (square.getIdPlayer() == 1)
                crossJumpFront(playableSquares, movableRow + 1, selectedCol, movableRow, square.getIdPlayer());
            else
                crossJumpFront(playableSquares, movableRow - 1, selectedCol, movableRow, square.getIdPlayer());
        }
        return playableSquares.size();
    }

    public List<Square> playableSquares(Square square) {
        List<Square> playableSquares = new LinkedList<Square>();

        int selectedRow = square.getRow();
        int selectedCol = square.getColumn();

        int movableRow = selectedRow;

        if (square.getIdPlayer() == 1) movableRow--;
        else movableRow++;

        //check two front squares
        twoFrontSquares(playableSquares, movableRow, selectedCol);

        if (square.getIdPlayer() == 1)
            crossJumpFront(playableSquares, movableRow - 1, selectedCol, movableRow, square.getIdPlayer());
        else
            crossJumpFront(playableSquares, movableRow + 1, selectedCol, movableRow, square.getIdPlayer());

        if (square.isKing()) {
            if (square.getIdPlayer() == 1)
                movableRow = selectedRow + 1;
            else
                movableRow = selectedRow - 1;
            twoFrontSquares(playableSquares, movableRow, selectedCol);
            if (square.getIdPlayer() == 1)
                crossJumpFront(playableSquares, movableRow + 1, selectedCol, movableRow, square.getIdPlayer());
            else
                crossJumpFront(playableSquares, movableRow - 1, selectedCol, movableRow, square.getIdPlayer());
        }
        return playableSquares;
    }

    private void checkKing(Square from, Square movedSquare) {
        if (from.isKing()) {

            movedSquare.setKing(true);

            from.setKing(false);
        } else if (movedSquare.getRow() == 7 && from.getIdPlayer() == 2
                || movedSquare.getRow() == 0 && from.getIdPlayer() == 1) {
            movedSquare.setKing(true);
            if(from.getIdPlayer() == 1)
            {
                nrWhiteKing++;
                nrWhitePiece--;
            }
            else
            {
                nrBlackKing++;
                nrBlackPiece--;
            }
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

    private void crossJumpFront(List<Square> pack, int movableRow, int selectedCol, int middleRow, int currentPLayer) {

        int middleCol;

        if (movableRow >= 0 && movableRow < 8) {

            //right upper Corner
            if (selectedCol >= 0 && selectedCol < 6) {
                Square rightCorner = squares[movableRow][selectedCol + 2];
                middleCol = selectedCol + 1;
                if (rightCorner.getIdPlayer() == 0 && isOpponentInbetween(middleRow, middleCol, currentPLayer)) {
                    pack.add(rightCorner);
                }
            }

            //left upper corner
            if (selectedCol > 1 && selectedCol <= 7) {
                Square leftCorner = squares[movableRow][selectedCol - 2];
                middleCol = selectedCol - 1;
                if (leftCorner.getIdPlayer() == 0 && isOpponentInbetween(middleRow, middleCol, currentPLayer)) {
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

    public int getNumKingPieces(int idPlayer) {
        int nrKing = 0;

        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j)
                if (squares[i][j].isKing() && squares[i][j].getIdPlayer() == idPlayer)
                    ++nrKing;

        return nrKing;
    }

    public int getNumNormalPieces(int idPlayer) {
        int nrPieces = 0;

        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j)
                if (!squares[i][j].isKing() && squares[i][j].getIdPlayer() == idPlayer)
                    ++nrPieces;

        return nrPieces;
    }
    public void printBoard()
    {
        for(int i=0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j)
                System.out.print(squares[i][j].getIdPlayer() + " ");
            System.out.println();
        }
    }
}
