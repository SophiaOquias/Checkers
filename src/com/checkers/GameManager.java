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

        }
        else {
            if(dame.getColor() == Color.WHITE) {
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
            }
            else {
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

        int upperBound;
        int lowerBound;

        if(dame.getCol() == 0) { // if dame is on the left edge

            Position newPos;
            if(dame.getColor() == Color.WHITE) {
                newPos = new Position(dame.getRow() - 1, dame.getCol() + 1);
            }
            else {
                newPos = new Position(dame.getRow() + 1, dame.getCol() + 1);
            }
            if (board.getDame(newPos) instanceof Empty) {
                if (isMoveLegal(dame, newPos, board)) {
                    temp.add(newPos);
                }
            } else {
                if (isCaptureLegal(dame, board.getDame(newPos), board)) {
                    temp.add(newPos);
                }
            }
        }
        else if(dame.getCol() == SIZE - 1) { // if dame is on the right edge
            Position newPos;
            if(dame.getColor() == Color.WHITE) {
                newPos = new Position(dame.getRow() - 1, dame.getCol() - 1);
            }
            else {
                newPos = new Position(dame.getRow() + 1, dame.getCol() - 1);
            }

            if (board.getDame(newPos) instanceof Empty) {
                if (isMoveLegal(dame, newPos, board)) {
                    temp.add(newPos);
                }
            } else {
                if (isCaptureLegal(dame, board.getDame(newPos), board)) {
                    temp.add(newPos);
                }
            }
        }
        else {
            for(int i = dame.getCol() - 1; i <= dame.getCol() + 1; i += 2) {
                    if(dame.getColor() == Color.WHITE) {
                        if (board.getDame(dame.getRow() - 1, i) instanceof Empty) {
                            if (isMoveLegal(dame, new Position(dame.getRow() - 1, i), board)) {
                                temp.add(new Position(dame.getRow() - 1, i));
                            }
                        }
                        else {
                            if (isCaptureLegal(dame, board.getDame(new Position(dame.getRow() - 1, i)), board)) {
                                temp.add(new Position(dame.getRow() - 1, i));
                            }
                        }
                    }
                }
        }

        // still have to do queen possible moves

        return (Position[]) temp.toArray();
    }
}
