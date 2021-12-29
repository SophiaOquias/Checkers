package com.checkers;

import java.util.ArrayList;

public class MaxBot {

    // METHODS
    // without move ordering
    public void createTree(BoardNode root) {

    }

    public ArrayList<BoardNode> getWhiteChildren(BoardNode board) {
        ArrayList<BoardNode> children = new ArrayList<>();

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
