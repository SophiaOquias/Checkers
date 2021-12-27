package com.checkers;

public class GameManager {

    // METHODS
    public boolean isMoveLegal(Dame dame, Position pos, Board board) {
        if(dame.isQueen) {
            if(board.isSquareOccupied(pos) ||
                    Math.abs(dame.getRow() - pos.getRow()) != 1 ||
                    Math.abs(dame.getCol()) - pos.getCol() != 1) {
                return false;
            }
        }
        else {
            if (dame.getColor() == Color.WHITE) {
                if(board.isSquareOccupied(pos) ||
                        dame.getRow() - pos.getRow() != 1 ||
                        Math.abs(dame.getCol() - pos.getCol()) != 1) {
                    return false;
                }
            } else {
                if(board.isSquareOccupied(pos) ||
                        dame.getRow() - pos.getRow() != -1 ||
                        Math.abs(dame.getCol() - pos.getCol()) != 1) {
                    return false;
                }
            }
        }

        return true;
    }
}
