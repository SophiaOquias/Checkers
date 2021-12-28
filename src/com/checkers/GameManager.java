package com.checkers;

import java.util.ArrayList;

public class GameManager {

    // PROPERTIES
    private final int SIZE = 8;

    // METHODS
    public boolean isMoveLegal(Dame dame, Position pos, Board board) {

        if(pos.getRow() >= SIZE || pos.getRow() < 0 ||
                pos.getCol() >= SIZE || pos.getCol() < 0)
            return false;

        if(dame.isQueen()) {
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

    public boolean isCaptureLegal(Dame dame, Dame captured, Board board) {
        if(dame.isQueen()) {
            // check if captured dame is opposite color
            if((dame.getColor() == Color.WHITE && captured.getColor() == Color.WHITE) ||
                    (dame.getColor() == Color.BLACK && captured.getColor() == Color.BLACK)) {
                return false;
            }

            // check if captured dame is in bounds
            if(Math.abs(dame.getRow() - captured.getRow()) != 1 ||
                    Math.abs(dame.getCol() - captured.getCol()) != 1) {
                return false;
            }

            // get new position after capture
            Position newPos = new Position();

            // check if captured dame is above dame
            if(dame.getRow() - captured.getRow() == 1) {
                if(dame.getCol() - captured.getCol() == 1) { // if captured dame is to the right
                    newPos.setPos(captured.getRow() - 2, captured.getCol() + 2);
                }
                else if(dame.getCol() - captured.getCol() == -1) { // if captured dame is to the left
                    newPos.setPos(captured.getRow() - 2, captured.getCol() - 2);
                }
            }
            else {
                if(dame.getCol() - captured.getCol() == 1) { // if captured dame is to the right
                    newPos.setPos(captured.getRow() + 2, captured.getCol() - 2);
                }
                else if(dame.getCol() - captured.getCol() == -1) { // if captured dame is to the left
                    newPos.setPos(captured.getRow() + 2, captured.getCol() + 2);
                }
            }

        }
        else {
            if(dame.getColor() == Color.WHITE) {
                // check if captured dame is opposite color
                if(captured.getColor() != Color.BLACK) {
                    return false;
                }

                // check if captured dame is in bounds
                if(dame.getRow() - captured.getRow() != 1 ||
                        Math.abs(dame.getCol() - captured.getCol()) != 1) {
                    return false;
                }

                // get new position of dame after capture
                Position newPos = new Position();

                if(dame.getCol() - captured.getCol() == 1) { // if captured dame is to the right
                    newPos.setPos(captured.getRow() - 2, captured.getCol() + 2);
                }
                else if(dame.getCol() - captured.getCol() == -1) { // if captured dame is to the left
                    newPos.setPos(captured.getRow() - 2, captured.getCol() - 2);
                }

                // check if position after capturing is within bounds
                if(newPos.getRow() >= SIZE || newPos.getRow() < 0 ||
                        newPos.getCol() >= SIZE || newPos.getCol() < 0 ||
                        board.isSquareOccupied(newPos)) {
                    return false;
                }

                // check if position after capturing is within bounds
                if(newPos.getRow() >= SIZE || newPos.getRow() < 0 ||
                        newPos.getCol() >= SIZE || newPos.getCol() < 0 ||
                        board.isSquareOccupied(newPos)) {
                    return false;
                }
            }
            else {
                // check if captured dame is opposite color
                if(captured.getColor() != Color.WHITE) {
                    return false;
                }

                // check if captured dame is in bounds
                if(dame.getRow() - captured.getRow() != -1 ||
                        Math.abs(dame.getCol() - captured.getCol()) != 1) {
                    return false;
                }

                // get new position of dame after capture
                Position newPos = new Position();

                if(dame.getCol() - captured.getCol() == 1) { // if captured dame is to the right
                    newPos.setPos(captured.getRow() + 2, captured.getCol() - 2);
                }
                else if(dame.getCol() - captured.getCol() == -1) { // if captured dame is to the left
                    newPos.setPos(captured.getRow() + 2, captured.getCol() + 2);
                }

                // check if position after capturing is within bounds
                if(newPos.getRow() >= SIZE || newPos.getRow() < 0 ||
                        newPos.getCol() >= SIZE || newPos.getCol() < 0 ||
                        board.isSquareOccupied(newPos)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isPromotable(Dame dame) {
        if(!dame.isQueen()) {
            if(dame.getColor() == Color.WHITE) {
                if(dame.getRow() == 0)
                    return true;
            }
            else {
                if(dame.getRow() == SIZE - 1)
                    return true;
            }
        }

        return false;
    }

    public Position[] getPossibleMoves(Dame dame, Board board) {
        ArrayList<Position> temp = new ArrayList<>();

        int row = dame.getRow();
        int col = dame.getCol();

        if(dame.isQueen()) {

            // check all diagonals
            for(int i = row - 1; i <= row + 1; i += 2) {
                for(int j = col - 1; j <= col + 1; j += 2) {

                    // check if position is within bounds of the board
                    if(i >= 0 && i < SIZE && j >= 0 && j < SIZE) {

                        // check if position is an empty square
                        if(board.getDame(row, col) instanceof Empty) {
                            if(isMoveLegal(dame, new Position(row, col), board)) {
                                temp.add(new Position(row, col));
                            }
                        }
                        // else check if it as an opposing dame (opposite color)
                        else if(dame.getColor() == Color.WHITE &&
                                board.getDame(row, col).getColor() == Color.BLACK){
                            if(isCaptureLegal(dame, board.getDame(row, col), board)) {
                                temp.add(new Position(row, col));
                            }
                        }
                        else if(dame.getColor() == Color.BLACK &&
                                board.getDame(row, col).getColor() == Color.WHITE) {
                            if(isCaptureLegal(dame, board.getDame(row, col), board)) {
                                temp.add(new Position(row, col));
                            }
                        }

                    }

                }
            }

        }
        else {

            int nextrow = (dame.getColor() == Color.WHITE) ? row - 1 : row + 1;

            for(int i = col - 1; i < col + 1; i += 2) {

                // check if position is within bounds
                if(i >= 0 && i < SIZE && nextrow >= 0 && nextrow < SIZE) {

                    // check if position is an empty square
                    if(board.getDame(nextrow, i) instanceof Empty) {
                        if(isMoveLegal(dame, new Position(nextrow, i), board)) {
                            temp.add(new Position(nextrow, i));
                        }
                    }
                    // check if position contains an opposing dame
                    else if(board.getDame(nextrow, i).getColor() == Color.BLACK) {
                        if(isCaptureLegal(dame, board.getDame(nextrow, i), board)) {
                            temp.add(new Position(nextrow, i));
                        }
                    }
                }
            }

        }

        // copy arrayList to array
        Position[] moves = new Position[temp.size()];

        for(int i = 0; i < temp.size(); i++) {
            moves[i] = temp.get(i);
        }

        return moves;
    }

    public boolean isMandatory(Board board) {
        if(isWhiteMandatory(board) || isBlackMandatory(board))
            return true;

        return false; 
    }

    public boolean isWhiteMandatory(Board board) {

        // check all pieces in board
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                // check if selected piece is a dame and is white
                if(!(board.getDame(i, j) instanceof Empty) && board.getDame(i, j).getColor() == Color.WHITE) {
                    // check if queen
                    if(board.getDame(i, j).isQueen()) {
                        // check top and bottom diagonals
                        if (checkAllDiagonals(board, i, j)) return true;
                    }
                    else {
                        int row = i - 1;
                        // check only top diagonals
                        if (checkDiagonals(board, i, j, row)) return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isBlackMandatory(Board board) {
        // check all pieces in board
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                // check if selected piece is a dame and is white
                if(!(board.getDame(i, j) instanceof Empty) && board.getDame(i, j).getColor() == Color.BLACK) {
                    // check if queen
                    if(board.getDame(i, j).isQueen()) {
                        // check top and bottom diagonals
                        if (checkAllDiagonals(board, i, j)) return true;
                    }
                    else {
                        int row = i + 1;
                        // check only bottom diagonals
                        if (checkDiagonals(board, i, j, row)) return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkDiagonals(Board board, int i, int j, int row) {
        for(int col = j - 1; col <= j + 1; col += 2) {
            // check if in bounds
            if(col >= 0 && col < SIZE && row >= 0) {
                if (!(board.getDame(row, col) instanceof Empty) &&
                        isCaptureLegal(board.getDame(i, j), board.getDame(row, col), board)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkAllDiagonals(Board board, int i, int j) {
        for(int row = i - 1; row <= i + 1; row += 2) {
            for(int col = j - 1; col <= j + 1; col += 2) {
                // check if selected position is within bounds
                if(col >= 0 && col < SIZE && row >= 0 && row < SIZE) {
                    if (!(board.getDame(row, col) instanceof Empty) &&
                            isCaptureLegal(board.getDame(i, j), board.getDame(row, col), board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
