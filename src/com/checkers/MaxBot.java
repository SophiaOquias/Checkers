package com.checkers;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MaxBot {

    // PROPERTIES
    private final int MAX_DEPTH = 3;
    private final int SIZE = 8;

    // METHODS
    // without move ordering
    public void createTree(BoardNode root, int depth) {

        if(depth < MAX_DEPTH) {
            Color color = (depth % 2 != 0) ? Color.WHITE : Color.BLACK;

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
                        for(int move = 0; move < 2; move++) {
                            for (int row = i - 1; row <= i + 1; row += 2) {
                                for (int col = j - 1; col <= j + 1; col += 2) {

                                    // check if within bounds
                                    if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
                                        if(move == 0)
                                            getCaptures(board, children, isMax, i, j, currentDame, row, col);
                                        else
                                            getMoves(board, children, isMax, i, j, currentDame, row, col);
                                    }

                                }
                            }
                        }
                    }
                    else {
                        int row = (color == Color.WHITE) ? i - 1 : i + 1;

                        for(int move = 0; move < 2; move++) {
                            for (int col = j - 1; col <= j + 1; col += 2) {
                                if (row >= 0 && col >= 0 && col < SIZE) {
                                    if(move == 0)
                                        getCaptures(board, children, isMax, i, j, currentDame, row, col);
                                    else
                                        getMoves(board, children, isMax, i, j, currentDame, row, col);
                                }
                            }
                        }

                    }

                }
            }
        }

        sortChildren(children);

        return children;
    }

    private void sortChildren(ArrayList<BoardNode> nodeList) {
        for(int j = 1; j < nodeList.size(); j++) {
            BoardNode key = nodeList.get(j);
            int i = j - 1;
            while(i >= 0 && determineUtility(key) >= determineUtility(nodeList.get(i))) {
                nodeList.set(i + 1, nodeList.get(i));
                i--;
            }
            nodeList.set(i + 1, key);
        }
    }

    private void getCaptures(BoardNode board, ArrayList<BoardNode> children, boolean isMax, int i, int j, Dame currentDame, int row, int col) {
        GameManager gm = new GameManager();

        if(!(board.getDame(row, col) instanceof Empty)){
            if(gm.isCaptureLegal(currentDame, board.getDame(row, col), board)) {
                BoardNode child = new BoardNode(board, isMax);
                Position newPos = gm.getNewPosition(currentDame, board.getDame(row, col));
                child.getDame(i, j).capture(child.getDame(row, col), child);
                children.add(child);

//                System.out.println("Is mandatory?: " + gm.isMandatory(child, currentDame.getColor()));

                while(gm.canStillCapture(child, child.getDame(newPos.getRow(), newPos.getCol()))) {
                    currentDame = child.getDame(newPos.getRow(), newPos.getCol());
                    if(currentDame.isQueen()) {
                        for(int x = newPos.getRow() - 1; x <= newPos.getRow() + 1; x++) {
                            for(int y = newPos.getCol() - 1; y <= newPos.getCol() + 1; y++) {
                                if(x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
                                    if(!(child.getDame(x, y) instanceof Empty)) {
                                        if(gm.isCaptureLegal(currentDame, child.getDame(x, y), child)) {
                                            newPos = gm.getNewPosition(currentDame, child.getDame(x, y));
                                            currentDame.capture(child.getDame(x, y), child);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else {
                        int x = (currentDame.getColor() == Color.WHITE) ? newPos.getRow() - 1 : newPos.getRow() + 1;
                        for(int y = newPos.getCol() - 1; y <= newPos.getCol() + 1; y++) {
                            if(x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
                                if(!(child.getDame(x, y) instanceof Empty)) {
                                    if(gm.isCaptureLegal(currentDame, child.getDame(x, y), child)) {
                                        newPos = gm.getNewPosition(currentDame, child.getDame(x, y));
                                        currentDame.capture(child.getDame(x, y), child);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void getMoves(BoardNode board, ArrayList<BoardNode> children,
                          boolean isMax, int i, int j, Dame currentDame, int row, int col) {
        GameManager gm = new GameManager();

        if (!gm.isMandatory(board, currentDame.getColor()) && board.getDame(row, col) instanceof Empty) {
            if (gm.isMoveLegal(currentDame, new Position(row, col), board)) {
                BoardNode child = new BoardNode(board, isMax);
                child.getDame(i, j).move(row, col, child);
                children.add(child);
            }

            // makes sure that there is a dame in the space to avoid erroneous array access
        }
    }

    // void for now
    public BoardNode getBestMove(Board board) {
        BoardNode node = new BoardNode(board, true);

        createTree(node, 0);

        determineValues(node, true, Integer.MIN_VALUE, Integer.MAX_VALUE);

        Display d = new Display();

        System.out.println(d.getNumChildren(node, 0) + "\t" + d.getNumPrunes(node, 0));

        BoardNode bestNode = node;

        int max = Integer.MIN_VALUE; // initialize max value
        for(int i = 0; i < node.getChildren().size(); i++) {
            if(max < node.getChildren().get(i).getUtility()) {
                max = node.getChildren().get(i).getUtility();
                bestNode = node.getChildren().get(i);
            }
        }

        return bestNode;
    }

    public void determineValues(BoardNode node, boolean isMaxPlayer, int alpha, int beta) {
        if(node.getChildren().size() == 0) {
            node.setUtility(determineUtility(node));
        }

        if(isMaxPlayer) {
            int maxValue = Integer.MIN_VALUE;

            for(int i = 0; i < node.getChildren().size(); i++) {
                determineValues(node.getChildren().get(i), false, alpha, beta);
                maxValue = Math.max(maxValue, node.getChildren().get(i).getUtility());
                node.setUtility(maxValue);
                alpha = Math.max(alpha, maxValue);
                if(alpha >= beta)
                    break;
            }
        }
        else {
            int minValue = Integer.MAX_VALUE;

            for(int i = 0; i < node.getChildren().size(); i++) {
                determineValues(node.getChildren().get(i), true, alpha, beta);
                minValue = Math.min(minValue, node.getChildren().get(i).getUtility());
                node.setUtility(minValue);
                beta = Math.min(beta, minValue);
                if(alpha >= beta)
                    break;
            }
        }
    }

    public int determineUtility(BoardNode board) {
        return board.countBlack() - board.countWhite();
    }
}
