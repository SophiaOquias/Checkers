package com.checkers;

public class Display {

    public void print(Board board) {
        for(int i = 0; i < 8; i++) {
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

        System.out.println();
    }

    public static void main(String[] args) {
        Display d = new Display();
        Board board = new Board();

        d.print(board);

        System.out.println("WHITE: " + board.countWhite() + " BLACK: " + board.countBlack());

        board.getDame(5, 0).move(new Position(4, 1), board);
        board.getDame(2, 3).move(new Position(3, 2), board);

        d.print(board);

        board.getDame(3, 2).capture(board.getDame(4, 1), board);

        d.print(board);

        System.out.println("WHITE: " + board.countWhite() + " BLACK: " + board.countBlack());

    }
}


