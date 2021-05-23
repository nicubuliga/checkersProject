package server.ai;

import server.models.Board;
import server.models.Square;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer {

    public List<String> getAllPlayerMoves(Board currBoard, int idPlayer) {
        List<String> moves = new ArrayList<>();

        for(int i = 0; i < 8; ++i)
            for(int j = 0; j < 8; ++j)
                if(currBoard.getSquares()[i][j].getIdPlayer() == idPlayer)
                {
                    List<Square> squareList = currBoard.playableSquares(currBoard.getSquares()[i][j]);

                    if(!squareList.isEmpty()) {
                        for(Square square : squareList) {
                            String move = i + "" + j + " " + square.getRow() + "" + square.getColumn();

                            moves.add(move);
                        }
                    }
                }

        return moves;
    }

    public String getNextMove(int depth, Board currBoard) throws CloneNotSupportedException {
        List<String> moves = new ArrayList<>();

        moves = getAllPlayerMoves(currBoard, 2);

        double maxValue = Double.MIN_VALUE;
        String bestMove = "";

        for(String move : moves) {
            Board tempBoard = (Board) currBoard.clone();
            tempBoard.makeMove(move, 2);
            double value = minimax(tempBoard, depth - 1, 1);

            if(value > maxValue) {
                value = maxValue;
                bestMove = move;
            }
        }

        return  bestMove;
    }

    public double minimax(Board board, int depth, int idPlayer) {
        if(depth == 0)
            return getHeuristic(board, 3 - idPlayer);

        List<String> moves = getAllPlayerMoves(board, idPlayer);

        double value;
        Board tempBoard = null;

        if(idPlayer == 1) {
            value = Double.MIN_VALUE;

            for(String move : moves) {
                tempBoard = board;
                tempBoard.makeMove(move, idPlayer);

                double result = minimax(tempBoard, depth - 1, 3 - idPlayer);

                value = Math.max(result, value);
            }

        } else {
            value = Double.MAX_VALUE;

            for(String move : moves) {
                tempBoard = board;
                tempBoard.makeMove(move, idPlayer);

                double result = minimax(tempBoard, depth - 1, 3 - idPlayer);

                value = Math.min(result, value);
            }
        }

        return value;
    }


    private double getHeuristic(Board b, int idPlayer)
    {
        //naive implementation
//        if(getSide() == Side.WHITE)
//            return b.getNumWhitePieces() - b.getNumBlackPieces();
//        return b.getNumBlackPieces() - b.getNumWhitePieces();

        double kingWeight = 1.2;
        double result = 0;
        if(idPlayer == 1)
            result = b.getNumKingPieces(idPlayer) * kingWeight + b.getNumNormalPieces(idPlayer) - b.getNumKingPieces(idPlayer) *
                    kingWeight -
                    b.getNumNormalPieces(idPlayer);
        else
            result = b.getNumKingPieces(idPlayer) * kingWeight + b.getNumNormalPieces(idPlayer) - b.getNumKingPieces(idPlayer) *
                    kingWeight -
                    b.getNumNormalPieces(idPlayer);
        return result;

    }
}
