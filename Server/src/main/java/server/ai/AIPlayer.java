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
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        List<String> moves = new ArrayList<>();

        moves = getAllPlayerMoves(currBoard, 2);

        double minValue = Double.POSITIVE_INFINITY;
        String bestMove = "";

        for(String move : moves) {
            Board tempBoard = new Board(currBoard);
            tempBoard.makeMove(move, 2);
            double value = minimax(tempBoard, depth - 1, 1,alpha,beta);
            System.out.println(move + " " + value);
            if(value <= minValue) {
                minValue = value;
                bestMove = move;
            }
        }

        return bestMove;
    }

    public double minimax(Board board, int depth, int idPlayer,double alpha,double beta) throws CloneNotSupportedException {
        if(depth == 0)
//            return getHeuristic(board,3-idPlayer);
            return getHeuristic(board);

        List<String> moves = getAllPlayerMoves(board, idPlayer);

        double value;
        Board tempBoard = null;

        if(idPlayer == 1) {
            value = Double.NEGATIVE_INFINITY;

            for(String move : moves) {
                tempBoard = new Board(board);
                tempBoard.makeMove(move, idPlayer);

                double result = minimax(tempBoard, depth - 1, 3 - idPlayer,alpha,beta);

                value = Math.max(value,result);

                alpha = Math.max(alpha, result);

                if(alpha >= beta)
                    break;
            }
            return value;
        } else {
            value = Double.POSITIVE_INFINITY;
            for(String move : moves) {
                tempBoard = new Board(board);
                tempBoard.makeMove(move, idPlayer);

                double result = minimax(tempBoard, depth - 1, 3 - idPlayer,alpha,beta);

                value = Math.min(result, value);
                beta = Math.min(beta,result);
                if(beta <= alpha)
                    break;
            }
            return value;
        }
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
            result = b.getNumKingPieces(idPlayer) * kingWeight + b.getNumNormalPieces(idPlayer) -
                    b.getNumKingPieces(2) * kingWeight -  b.getNumNormalPieces(2);
        else
            result = b.getNumKingPieces(idPlayer) * kingWeight + b.getNumNormalPieces(idPlayer) -
                    b.getNumKingPieces(1) * kingWeight - b.getNumNormalPieces(1);
        return result;

    }
    private double getHeuristic(Board board)
    {
        return (board.getNrWhitePiece() - board.getNrBlackPiece() +
                (board.getNrWhiteKing() * 2) - (board.getNrBlackKing() * 2));
    }
}
