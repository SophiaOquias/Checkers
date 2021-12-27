package com.checkers;

import java.util.LinkedList;

public class BoardNode extends Board{

    private boolean isVisited;
    private LinkedList<BoardNode> children;
    int alpha;
    int beta;
    boolean isMax;

    public BoardNode(Board board) {
        super(board);

        this.isVisited = false;
        this.children = new LinkedList<>();
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MIN_VALUE;
        this.isMax = true;

    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public LinkedList<BoardNode> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<BoardNode> children) {
        this.children = children;
    }

    public void addChild(BoardNode node) {
        this.children.add(node); 
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public boolean isMax() {
        return isMax;
    }

    public void setMax(boolean max) {
        isMax = max;
    }
}
