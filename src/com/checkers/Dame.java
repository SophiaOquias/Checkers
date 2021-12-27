package com.checkers;

public class Dame {
    // PROPERTIES
    protected Position pos;
    protected Color color;

    // CONSTRUCTORS
    public Dame(Position pos, Color color) {
        this.pos = pos;
        this.color = color;
    }

    public Dame(int row, int col, Color color) {
        this.pos = new Position(row, col);
        this.color = color;
    }

    // METHODS

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public void setPos(int row, int col) {
        this.pos = new Position(row, col);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void move(Position pos, Board board) {

    }

    // public void capture
}
