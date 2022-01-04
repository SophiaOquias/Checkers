package com.checkers.board;

import java.util.ArrayList;

public class BoardNode extends Board{

    private boolean isVisited;
    private ArrayList<BoardNode> children;
    int utility;
    boolean isMax;

    public BoardNode(Board board, boolean isMax) {
        copy(board);

        this.isVisited = false;
        this.children = new ArrayList<>();
        this.isMax = isMax;
        this.utility = (isMax) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    }

    public BoardNode(BoardNode node, boolean isMax) {
        copy(node);

        this.isVisited = false;
        this.children = new ArrayList<>();
        this.isMax = isMax;
        this.utility = (isMax) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    }

    public BoardNode() {

    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public ArrayList<BoardNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<BoardNode> children) {
        this.children = children;
    }

    public void addChild(BoardNode node) {
        this.children.add(node);
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int value) {
        this.utility = value;
    }

    public boolean isMax() {
        return isMax;
    }

    public void setMax(boolean max) {
        isMax = max;
    }
}
