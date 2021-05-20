package models;

public class SquareModel {
    private int row;
    private int column;
    private boolean isSelected = false;
    private boolean isMutable = false;

    public boolean isMutable() {
        return isMutable;
    }

    public void setMutable(boolean mutable) {
        isMutable = mutable;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getIdPLayer() {
        return idPLayer;
    }

    public void setIdPLayer(int idPLayer) {
        this.idPLayer = idPLayer;
    }

    private int idPLayer;
    public SquareModel(int row, int column) {

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
