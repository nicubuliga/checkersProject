package server.models;

/*
    Clasa Square mentine datele unui patrat din Board
 */
public class Square {
    private int idPlayer;
    private int column;
    private int row;
    private boolean isKing = false;
    public Square(int row, int col) {
        this.column = col;
        this.row = row;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        isKing = king;
    }

    public void setIdPlayer(int idPlayer) {
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
