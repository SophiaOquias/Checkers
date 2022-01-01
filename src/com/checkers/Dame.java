package com.checkers;

public class Dame {
    // PROPERTIES
    protected Position pos;
    protected Color color;
    protected boolean isQueen;

    // CONSTRUCTORS
    public Dame(Position pos, Color color) {
        this.pos = pos;
        this.color = color;
        this.isQueen = false;
    }

    public Dame(int row, int col, Color color) {
        this.pos = new Position(row, col);
        this.color = color;
        this.isQueen = false;
    }

    public Dame(Dame dame) {
        this.pos = new Position(dame.getRow(), dame.getCol());
        this.color = dame.getColor();
        this.isQueen = dame.isQueen();
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

    public boolean isQueen() {
        return this.isQueen;
    }

    public void move(Position pos, Board board) {
        board.setDame(pos.getRow(), pos.getCol(), this); // move dame to new pos
        board.setDame(this.getRow(), this.getCol(), new Empty()); // set old position to Empty
        this.pos = pos; // set new position of dame
    }

    public void move(int row, int col, Board board) {
        board.setDame(row, col, this);
        board.setDame(this.getRow(), this.getCol(), new Empty());
        this.pos.setRow(row);
        this.pos.setCol(col);
    }

    public void capture(Dame captured, Board board) {
        if(this.isQueen()) {
            if(this.getRow() - captured.getRow() == 1) { // if captured dame is above dame
                captureUp(captured, board);
            }
            else {
                captureDown(captured, board);
            }
        }
        else {
            if (this.color == Color.WHITE) {
                captureUp(captured, board);
            } else {
                captureDown(captured, board);
            }
        }

        board.setDame(captured.getPos(), new Empty());
    }

    private void captureDown(Dame captured, Board board) {
        if (this.getCol() - captured.getCol() == 1) { // if captured piece is to the right
            this.move(this.getRow() + 2, this.getCol() - 2, board);
        } else if (this.getCol() - captured.getCol() == -1) { // if captured piece is to the left
            this.move(this.getRow() + 2, this.getCol() + 2, board);
        }
    }

    private void captureUp(Dame captured, Board board) {
        if (captured.getCol() - this.getCol() == 1) { // if captured piece is to the right
            this.move(this.getRow() - 2, this.getCol() + 2, board);
        } else if (captured.getCol() - this.getCol() == -1) { // if captured piece is to the left
            this.move(this.getRow() - 2, this.getCol() - 2, board);
        }
    }

    public void promote() {
        this.isQueen = true;
    }
}
