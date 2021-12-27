package com.checkers;

public class Board {

    // PROPERTIES
    protected Dame[][] dames;
    protected final int SIZE = 8;

    // CONSTRUCTOR
    public Board() {
        this.dames = new Dame[SIZE][SIZE];

        // ADD DAMES
        // white dames
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(i % 2 == 0 && j % 2 != 0) {
                    this.dames[i][j] = new Dame(i, j, Color.WHITE);
                }
                else if(i % 2 != 0 && j % 2 == 0) {
                    this.dames[i][j] = new Dame(i, j, Color.WHITE);
                }
                else {
                    this.dames[i][j] = new Empty();
                }
            }
        }

        // black dames
        for(int i = 5; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(i % 2 != 0 && j % 2 == 0) {
                    this.dames[i][j] = new Dame(i, j, Color.BLACK);
                }
                else if(i % 2 == 0 && j % 2 != 0) {
                    this.dames[i][j] = new Dame(i, j, Color.BLACK);
                }
                else {
                    this.dames[i][j] = new Empty();
                }
            }
        }

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {

                // set white dames
                if(i >= 0 && i < 3) {
                    if(i % 2 == 0 && j % 2 != 0) {
                        this.dames[i][j] = new Dame(i, j, Color.WHITE);
                    }
                    else if(i % 2 != 0 && j % 2 == 0) {
                        this.dames[i][j] = new Dame(i, j, Color.WHITE);
                    }
                    else {
                        this.dames[i][j] = new Empty();
                    }
                }
                // set black dames
                else if(i >= 5 && i < SIZE) {
                    if(i % 2 != 0 && j % 2 == 0) {
                        this.dames[i][j] = new Dame(i, j, Color.BLACK);
                    }
                    else if(i % 2 == 0 && j % 2 != 0) {
                        this.dames[i][j] = new Dame(i, j, Color.BLACK);
                    }
                    else {
                        this.dames[i][j] = new Empty();
                    }
                }
                else {
                    this.dames[i][j] = new Empty();
                }
            }
        }
    }

    // copy constructor
    public Board(Board board) {
        this.copy(board);
    }

    // METHODS
    public void copy(Board board) {
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(!(board.getDame(i, j) instanceof Empty)) {
                    if(board.getDame(i, j).getColor() == Color.WHITE) {
                        this.dames[i][j] = new Dame(i, j, Color.WHITE);
                    }
                    else {
                        this.dames[i][j] = new Dame(i, j, Color.BLACK);
                    }
                }
                else {
                    this.dames[i][j] = new Empty();
                }
            }
        }
    }
    public int countWhite() {
        int count = 0;

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(this.dames[i][j].getColor() == Color.WHITE)
                    count++;
            }
        }

        return count;
    }

    public int countBlack() {
        int count = 0;

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(this.dames[i][j].getColor() == Color.BLACK)
                    count++;
            }
        }

        return count;
    }

    public boolean isSquareOccupied(int row, int col) {
        if(this.dames[row][col] instanceof Empty)
            return false;

        return true;
    }

    public boolean isSquareOccupied(Position pos) {
        if(this.dames[pos.getRow()][pos.getCol()] instanceof Empty)
            return false;

        return true;
    }

    public Dame getDame(int row, int col) {
        return this.dames[row][col];
    }

    public Dame getDame(Position pos) {
        return this.dames[pos.getRow()][pos.getCol()];
    }

    public Dame[][] getDames() {
        return dames;
    }

    public void setDame(int row, int col, Dame dame) {
        this.dames[row][col] = dame;
    }

    public void setDame(Position pos, Dame dame) {
        this.dames[pos.getRow()][pos.getCol()] = dame;
    }

    public void setDames(Dame[][] dames) {
        this.dames = dames;
    }
}
