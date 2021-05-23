package server.models;

public class Square {
    private int idPlayer;
    private int column;
    private int row;
    private boolean isKing = false;
    private int nrWhitePiece;
    private int nrBlackPiece;
    private int nrWhiteKing;
    private int nrBlackKing;
    public Square(int row, int col) {
        this.column = col;
        this.row = row;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        if(king && idPlayer == 1)
        {
            if(this.isKing && idPlayer == 1)
                nrWhiteKing--;
            else
                if(this.isKing && idPlayer == 2)
                    nrBlackKing--;
            nrWhiteKing++;
        }
        else
        if(king && idPlayer == 2)
        {
            if(this.isKing && idPlayer == 1)
                nrWhiteKing--;
            else
            if(this.isKing && idPlayer == 2)
                nrBlackKing--;
            nrBlackKing++;
        }
        isKing = king;
    }

    public void setIdPlayer(int idPlayer) {
        if(idPlayer == 0)
        {
            if(this.idPlayer == 1)
                nrWhitePiece--;
            else
                nrBlackPiece--;
        }
        else
            if(idPlayer == 1)
            {
                if(this.idPlayer == 2)
                {
                    nrBlackPiece--;
                }
                nrWhitePiece++;
            }
            else
                if(idPlayer == 2)
                {
                    if(this.idPlayer == 1)
                        nrWhitePiece--;
                    nrBlackPiece++;
                }
        this.idPlayer = idPlayer;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
