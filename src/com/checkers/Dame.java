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

    public Dame() {

    }

    // METHODS
    public Position getPos() {
        return pos;
    }

    public int getRow() {
        return this.pos.getRow();
    }

    public int getCol() {
        return this.pos.getCol();
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
        board.setDame(pos.getRow(), pos.getCol(), this); // move dame to new pos
        board.setDame(this.getRow(), this.getCol(), new Empty()); // set old position to Empty
        this.pos = pos; // set new position of dame
    }

    public void capture(Position pos, Board board, Dame captured) {
        this.move(pos, board); // move dame to new position
        board.setDame(captured.getPos(), new Empty()); // remove captured dame
    }
}
