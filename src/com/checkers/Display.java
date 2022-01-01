package com.checkers;

import java.util.Scanner;

public class Display {

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
                        System.out.print("W ");
                    }
                    if (board.getDame(i, j).getColor() == Color.BLACK) {
                        System.out.print("B ");
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

    public static Position getPlayerInput() {
        Scanner sc = new Scanner(System.in);
        int row;
        int col;

        // get row input
        do {
            System.out.println("Input row: ");
            row = Integer.parseInt(sc.nextLine());
        } while(row < 0 || row >= 8);

        // get col input
        do {
            System.out.println("Input col: ");
            col = Integer.parseInt(sc.nextLine());
        } while(col < 0 || col >= 8);

        sc.close();

        return new Position(row, col);
    }

    public static void main(String[] args) {
        Display d = new Display();
        Board board = new Board();
        GameManager gm = new GameManager();
        MaxBot bot = new MaxBot();

        d.print(board);

        // mock game
        while(!gm.isWhiteWinner(board) || !gm.isBlackWinner(board)) {
            // white turn
            // select a dame
            System.out.println("Select a dame: ");
            Dame selected = board.getDame(getPlayerInput());
            System.out.println("Select move: ");
            Position selection = getPlayerInput();
            if(board.getDame(selection) instanceof Empty) {
                selected.move(selection, board);
            }
            else {
                selected.capture(board.getDame(selection), board);
            }

            d.print(board);

            // black turn
            board = bot.getBestMove(board);

            d.print(board);
        }
    }
}


