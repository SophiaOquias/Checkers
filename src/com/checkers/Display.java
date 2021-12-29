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
        GameManager gm = new GameManager();

        d.print(board);

        BoardNode node = new BoardNode(board, true);
        MaxBot bot = new MaxBot();

        node.setChildren(bot.getWhiteChildren(node));

        System.out.println("Number of chidlren: " + node.getChildren().size());

        for(int i = 0; i < node.getChildren().size(); i++) {
            System.out.println("i: " + i);
            d.print(node.getChildren().get(i));
        }

//        System.out.println("Is white winner?: " + gm.isWhiteWinner(board));
//
//        // remove all black dames
//        for(int i = 0; i < 3; i++) {
//            for(int j = 0; j < 8; j++) {
//                board.setDame(i, j, new Empty());
//            }
//        }
//
//        board.setDame(4, 1, new Dame(4, 1, Color.BLACK));
//
//        d.print(board);
//
//        System.out.println("Num Possible moves: " + gm.getNumPossibleMoves(board, Color.WHITE));
//
//        System.out.println("Is white winner?: " + gm.isWhiteWinner(board));
//
//        System.out.println("WHITE: " + board.countWhite() + " BLACK: " + board.countBlack());
//
//        System.out.println("Is there a mandatory move?: " + gm.isMandatory(board));

//        board.getDame(5, 0).move(new Position(4, 1), board);
//        board.getDame(2, 3).move(new Position(3, 2), board);
//
//        d.print(board);
//
//        System.out.println("Is there a mandatory move?: " + gm.isWhiteMandatory(board));
//
//        board.getDame(3, 2).capture(board.getDame(4, 1), board);
//
//        d.print(board);
//
//        System.out.println("WHITE: " + board.countWhite() + " BLACK: " + board.countBlack());

    }
}


