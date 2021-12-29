package com.checkers;

import java.util.ArrayList;

public class BoardNode extends Board{

    private boolean isVisited;
    private ArrayList<BoardNode> children;
    int alpha;
    int beta;
    boolean isMax;

    public BoardNode(Board board, boolean isMax) {
        super(board);

        this.isVisited = false;
        this.children = new ArrayList<>();
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MIN_VALUE;
        this.isMax = isMax;
    }

    public BoardNode(BoardNode node, boolean isMax) {
        super(node);

        this.isVisited = false;
        this.children = new ArrayList<>();
        this.alpha = node.getAlpha();
        this.beta = node.getBeta();
        this.isMax = isMax;
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
