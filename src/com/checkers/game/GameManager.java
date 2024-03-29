package com.checkers.game;

import com.checkers.pieces.Color;
import com.checkers.pieces.Position;
import com.checkers.board.Board;
import com.checkers.pieces.Dame;
import com.checkers.pieces.Empty;

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
                    Math.abs(dame.getCol() - pos.getCol()) != 1) {
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
        // check if captured dame is opposite color
        if(dame.getColor() == captured.getColor()) {
            return false;
        }

        if(dame.isQueen()) {
            // check if captured dame is in bounds
            if(Math.abs(dame.getRow() - captured.getRow()) != 1 ||
                    Math.abs(dame.getCol() - captured.getCol()) != 1) {
                return false;
            }
        }
        else {
            if(dame.getColor() == Color.WHITE) {
                // check if captured dame is in bounds
                if(dame.getRow() - captured.getRow() != 1 ||
                        Math.abs(dame.getCol() - captured.getCol()) != 1) {
                    return false;
                }
            }
            else {
                // check if captured dame is in bounds
                if(dame.getRow() - captured.getRow() != -1 ||
                        Math.abs(dame.getCol() - captured.getCol()) != 1) {
                    return false;
                }
            }
        }

        // get new position of dame after capture
        Position newPos = getNewPosition(dame, captured);

        // check if position after capturing is within bounds
        if(newPos.getRow() >= SIZE || newPos.getRow() < 0 ||
                newPos.getCol() >= SIZE || newPos.getCol() < 0 ||
                board.isSquareOccupied(newPos)) {
            return false;
        }

        return true;
    }

    public Position getNewPosition(Dame dame, Dame captured) {
        if(dame.isQueen()) {
            if(dame.getRow() - captured.getRow() == 1) {
                return getUpRowPos(dame, captured);
            }
            else {
                return getDownRowPos(dame, captured);
            }
        }
        else {
            if(dame.getColor() == Color.WHITE) {
                return getUpRowPos(dame, captured);
            }
            else {
                return getDownRowPos(dame, captured);
            }
        }
    }

    private Position getUpRowPos(Dame dame, Dame captured) {
        if(dame.getCol() - captured.getCol() == 1) { // if captured dame is to the right
            return new Position(dame.getRow() - 2, dame.getCol() - 2);
        }
        // if captured dame is to the left
        return new Position(dame.getRow() - 2, dame.getCol() + 2);
    }

    private Position getDownRowPos(Dame dame, Dame captured) {
        if(dame.getCol() - captured.getCol() == 1) { // if captured dame is to the right
            return new Position(dame.getRow() + 2, dame.getCol() - 2);
        }
        // if captured dame is to the left
        return new Position(dame.getRow() + 2, dame.getCol() + 2);
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
                        // and if there is no mandatory move to be made
                        if(!isMandatory(board, dame.getColor()) &&
                                board.getDame(i, j) instanceof Empty) {
                            if(isMoveLegal(dame, new Position(i, j), board)) {
                                temp.add(new Position(i, j));
                            }
                        }
                        else if(!(board.getDame(i, j) instanceof Empty)){
                            if(isCaptureLegal(dame, board.getDame(i, j), board)) {
                                temp.add(new Position(i, j));
                            }
                        }

                    }

                }
            }

        }
        else {

            int nextrow = (dame.getColor() == Color.WHITE) ? row - 1 : row + 1;

            for(int i = col - 1; i <= col + 1; i += 2) {

                // check if position is within bounds
                if(i >= 0 && i < SIZE && nextrow >= 0 && nextrow < SIZE) {

                    // check if position is an empty square
                    // and if there are any mandatory moves to be made
                    if(board.getDame(nextrow, i) instanceof Empty) {
                        if(!isMandatory(board, dame.getColor()) &&
                                isMoveLegal(dame, new Position(nextrow, i), board)) {
                            temp.add(new Position(nextrow, i));
                        }
                    }
                    // check if position contains an opposing dame
                    else {
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

    public boolean isMandatory(Board board, Color color) {
        if(color == Color.WHITE) {
            return isWhiteMandatory(board);
        }
        else {
            return isBlackMandatory(board);
        }
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
                        if (checkAllDiagonals(board, i, j))
                            return true;
                    }
                    else {
                        int row = i - 1;
                        // check only top diagonals
                        if (checkDiagonals(board, i, j, row))
                            return true;
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
                        if(checkAllDiagonals(board, i, j))
                            return true;
                    }
                    else {
                        int row = i + 1;
                        // check only bottom diagonals
                        if(checkDiagonals(board, i, j, row))
                            return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean canStillCapture(Board board, Dame dame) {
        if(dame.isQueen()) {
            return checkAllDiagonals(board, dame.getRow(), dame.getCol());
        }
        else {
            int row = (dame.getColor() == Color.WHITE) ? dame.getRow() - 1 : dame.getRow() + 1;
            return checkDiagonals(board, dame.getRow(), dame.getCol(), row);
        }
    }

    private boolean checkDiagonals(Board board, int i, int j, int row) {
        for(int col = j - 1; col <= j + 1; col += 2) {
            // check if in bounds
            if(col >= 0 && col < SIZE && row >= 0 && row < SIZE) {
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
            if(checkDiagonals(board, i, j, row))
                return true;
        }
        return false;
    }

    public int getNumPossibleMoves(Board board, Color color) {
        int count = 0;

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(board.getDame(i, j).getColor() == color) {
                    count += getPossibleMoves(board.getDame(i, j), board).length;
                }
            }
        }

        return count;
    }

    public boolean isWhiteWinner(Board board) {

        if(board.countBlack() == 0 || getNumPossibleMoves(board, Color.BLACK) == 0)
            return true;

        return false;
    }

    public boolean isBlackWinner(Board board) {

        if(board.countWhite() == 0 || getNumPossibleMoves(board, Color.WHITE) == 0)
            return true;

        return false;
    }
}
