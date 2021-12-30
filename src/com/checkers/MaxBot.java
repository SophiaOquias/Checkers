package com.checkers;

import java.util.ArrayList;

public class MaxBot {

    // PROPERTIES
    private final int MAX_DEPTH = 3;
    private final int SIZE = 8;

    // METHODS
    // without move ordering
    public void createTree(BoardNode root, int depth) {

        if(depth < MAX_DEPTH) {
            Color color = (depth % 2 == 0) ? Color.WHITE : Color.BLACK;

            root.setChildren(getChildren(root, color));
            for (int i = 0; i < root.getChildren().size(); i++) {
                createTree(root.getChildren().get(i), depth + 1);
            }
        }

    }

    public ArrayList<BoardNode> getChildren(BoardNode board, Color color) {
        ArrayList<BoardNode> children = new ArrayList<>();
        boolean isMax = color != Color.WHITE;

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                Dame currentDame = board.getDame(i, j);

                if(currentDame.getColor() == color) {

                    // check if move is possible
                    if(currentDame.isQueen()) {
                        for(int row = i - 1; row <= i + 1; row += 2) {
                            for(int col = j - 1; col <= j + 1; col += 2) {

                                // check if within bounds
                                if(row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
                                    makeChildren(board, children, isMax, i, j, currentDame, row, col);
                                }

                            }
                        }
                    }
                    else {
                        int row = (color == Color.WHITE) ? i - 1 : i + 1;

                        for(int col = j - 1; col <= j + 1; col += 2) {
                            if(row >= 0 && col >= 0 && col < SIZE) {
                                makeChildren(board, children, isMax, i, j, currentDame, row, col);
                            }
                        }

                    }

                }
            }
        }

        return children;
    }

    private void makeChildren(BoardNode board, ArrayList<BoardNode> children, boolean isMax, int i, int j, Dame currentDame, int row, int col) {
        GameManager gm = new GameManager();

        // makes sure that there are no mandatory moves to be done
        // if there is a mandatory move, piece does nothing if it cannot capture anything
        // if capture is possible, it performs the capture
        if (!gm.isMandatory(board, currentDame.getColor()) && board.getDame(row, col) instanceof Empty) {
            if (gm.isMoveLegal(currentDame, new Position(row, col), board)) {
                BoardNode child = new BoardNode(board, isMax);
                child.getDame(i, j).move(row, col, child);
                children.add(child);
            }

        // makes sure that there is a dame in the space to avoid erroneous array access
        } else if(!(board.getDame(row, col) instanceof Empty)){
            if(gm.isCaptureLegal(currentDame, board.getDame(row, col), board)) {
                BoardNode child = new BoardNode(board, isMax);
                child.getDame(i, j).capture(child.getDame(row, col), child);
                children.add(child);
            }
        }
    }

    // void for now
    public void getBestMove(Board board) {

    }

    public int determineUtility(BoardNode board) {
        return board.countBlack() - board.countWhite();
    }
}
