package models;

public class Square {
    private int row;
    private int column;

    public int getIdPLayer() {
        return idPLayer;
    }

    public void setIdPLayer(int idPLayer) {
        this.idPLayer = idPLayer;
    }

    private int idPLayer;
    public Square(int row, int column) {

        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
