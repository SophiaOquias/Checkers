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
    }

    public static void main(String[] args) {
        Display d = new Display();
        Board board = new Board();

        d.print(board);
        
    }
}


