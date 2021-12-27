package com.checkers;

public class Position {
    // PROPERTIES
    private int row;
    private int col;

    // CONSTRUCTOR
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position() {

    }

    // METHODS
    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
