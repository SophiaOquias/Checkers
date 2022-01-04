package com.checkers;

import java.util.Scanner;

public class TextUI {

    public void print(Board board) {

        System.out.print(" ");

        for(int i = 0 ; i < 8; i++) {
            System.out.print(" " + i);
        }

        System.out.println();

        for(int i = 0; i < 8; i++) {
            System.out.print(i + " ");
            for(int j = 0; j < 8; j++) {
                if(board.getDame(i, j) instanceof Empty) {
                    System.out.print("- ");
                }
                else {
                    if (board.getDame(i, j).getColor() == Color.WHITE) {
                        if(board.getDame(i, j).isQueen)
                            System.out.print("W ");
                        else
                            System.out.print("w ");
                    }
                    if (board.getDame(i, j).getColor() == Color.BLACK) {
                        if(board.getDame(i, j).isQueen)
                            System.out.print("B ");
                        else
                            System.out.print("b ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println("White: " + board.countWhite() + " Black: " + board.countBlack());

        System.out.println();
    }

    public void printTree(BoardNode node, int depth) {
        if(node.getChildren().size() > 0) {
            // print all children
            System.out.println("Depth: " + depth);
            for(int i = 0; i < node.getChildren().size(); i++) {
                System.out.println("Utility: " + node.getChildren().get(i).getUtility());
                print(node.getChildren().get(i));
            }

            for(int i = 0; i < node.getChildren().size(); i++) {
                printTree(node.getChildren().get(i), depth + 1);
            }
        }
    }

    // prints total number of nodes pruned in tree
    public int getNumPrunes(BoardNode node, int prunes) {

        if(node.getChildren().size() <= 0) {
            if(node.getUtility() == Integer.MIN_VALUE ||
                    node.getUtility() == Integer.MAX_VALUE) {
                return 1;
            }

            return 0;
        }

        int utility = (node.getUtility() == Integer.MIN_VALUE ||
                node.getUtility() == Integer.MAX_VALUE) ? 1 : 0;

        for(int i = 0; i < node.getChildren().size(); i++) {
            prunes += getNumPrunes(node.getChildren().get(i), utility);
        }

        return prunes;
    }

    public int getNumChildren(BoardNode node, int sum) {

        if(node.getChildren().size() <= 0) {
            return 1;
        }

        for(int i = 0; i < node.getChildren().size(); i++) {
            sum += getNumChildren(node.getChildren().get(i), 1);
        }

        return sum;
    }

    public static Position getPlayerInput(Scanner sc) {
        int row;
        int col;

        // get row input
        do {
            System.out.print("Input row: ");
            row = Integer.parseInt(sc.nextLine());
        } while(row < 0 || row >= 8);

        // get col input
        do {
            System.out.print("Input col: ");
            col = Integer.parseInt(sc.nextLine());
        } while(col < 0 || col >= 8);

        return new Position(row, col);
    }

    public static void main(String[] args) {
        TextUI d = new TextUI();
        Board board = new Board();
        GameManager gm = new GameManager();
        MaxBot bot = new MaxBot();
        Scanner sc = new Scanner(System.in);

        d.print(board);

//        // mock game
//        while(!gm.isWhiteWinner(board) && !gm.isBlackWinner(board)) {
//            // white turn
//            // select a dame
//            System.out.println("Select a dame: ");
//            Position selected;
//            do {
//                selected = getPlayerInput(sc);
//            } while(board.getDame(selected) instanceof Empty);
//            Dame selectedDame = board.getDame(selected);
//
//            // select a move
//            System.out.println("Select move: ");
//            Position selection = getPlayerInput(sc);
//            if(board.getDame(selection) instanceof Empty) {
//                selectedDame.move(selection, board);
//            }
//            else {
//                selectedDame.capture(board.getDame(selection), board);
//            }
//
//            d.print(board);
//
//            // black turn
//            board = bot.getBestMove(board);
//
//            d.print(board);
//        }

//        for(int i = 0; i < 8; i++) {
//            for(int j = 0; j < 8; j++) {
//                board.setDame(i, j, new Empty());
//            }
//        }

        BoardNode node = new BoardNode(board, true);

        bot.createTree(node, 0);

        bot.determineValues(node, true, Integer.MIN_VALUE, Integer.MAX_VALUE);

//        d.printTree(node, 0);

//        System.out.println("Num nodes: " + d.getNumChildren(node, 0));
//        System.out.println("Num prunes: " + d.getNumPrunes(node, 0));
        System.out.println(d.getNumChildren(node, 0) + "\t" + d.getNumPrunes(node, 0));

        sc.close();
    }
}


