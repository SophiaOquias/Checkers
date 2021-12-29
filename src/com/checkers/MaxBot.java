package com.checkers;

import java.util.ArrayList;

public class MaxBot {

    // PROPERTIES
    private final int MAX_DEPTH = 3;
    private final int SIZE = 8;

    // METHODS
    // without move ordering
    public void createTree(BoardNode root) {

    }

    public ArrayList<BoardNode> getWhiteChildren(BoardNode board) {
        ArrayList<BoardNode> children = new ArrayList<>();
        GameManager gm = new GameManager();

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                Dame currentDame = board.getDame(i, j);

                if(currentDame.getColor() == Color.WHITE) {

                    // check if move is possible
                    if(currentDame.isQueen()) {
                        for(int row = i - 1; row <= i + 1; row += 2) {
                            for(int col = j - 1; col <= j + 1; col += 2) {

                                // check if within bounds
                                if(row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
                                    if (board.getDame(row, col) instanceof Empty) {
                                        if (gm.isMoveLegal(currentDame, new Position(row, col), board)) {
                                            BoardNode child = new BoardNode(board, false);
                                            child.getDame(i, j).move(row, col, child);
                                            children.add(child);
                                        }
                                    } else {
                                        if(gm.isCaptureLegal(currentDame, board.getDame(row, col), board)) {
                                            BoardNode child = new BoardNode(board, false);
                                            child.getDame(i, j).capture(board.getDame(row, col), board);
                                            children.add(child);
                                        }
                                    }
                                }

                            }
                        }
                    }
                    else {
                        int row = i - 1;

                        for(int col = j - 1; col <= j + 1; col += 2) {
                            if(row >= 0 && col >= 0 && col < SIZE) {
                                if (board.getDame(row, col) instanceof Empty) {
                                    if (gm.isMoveLegal(currentDame, new Position(row, col), board)) {
                                        BoardNode child = new BoardNode(board, false);
                                        child.getDame(i, j).move(row, col, child);
                                        children.add(child);
                                    }
                                } else {
                                    if(gm.isCaptureLegal(currentDame, board.getDame(row, col), board)) {
                                        BoardNode child = new BoardNode(board, false);
                                        child.getDame(i, j).capture(board.getDame(row, col), board);
                                        children.add(child);
                                    }
                                }
                            }
                        }

                    }

                }
            }
        }

        return children;
    }

    public ArrayList<BoardNode> getBlackChildren(BoardNode board) {
        ArrayList<BoardNode> children = new ArrayList<>();

        return children;
    }

    // void for now
    public void getBestMove(Board board) {

    }

    public int determineUtility(BoardNode board) {
        return board.countBlack() - board.countWhite();
    }
}
