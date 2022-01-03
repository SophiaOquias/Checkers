package com.checkers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameUI extends JPanel{

    // PROPERTIES
    private Board board;
    private GameManager gm;
    private MaxBot bot;
    private boolean isWhiteMove;
    private int totalPrunes = 0;
    private int numTrees = 0;

    // gui components
    private Image[] images;
    private JLabel statusbar;
    private boolean isSelection;
    private Position selection;

    // constants
    private final int BOARD_SIZE = 8;
    private final int IMG_SIZE = 80;

    // image constants
    private final int NUM_IMG = 7;
    private final int B_SQUARE = 0;
    private final int W_SQUARE = 1;
    private final int W_DAME = 2;
    private final int B_DAME = 3;
    private final int W_QUEEN = 4;
    private final int B_QUEEN = 5;
    private final int MOVE = 6;

    // CONSTRUCTOR
    public GameUI(JLabel statusbar) {
        loadImages();
        initBoard();
        this.statusbar = statusbar;
        this.isSelection = false;
        this.isWhiteMove = true;

        this.statusbar.setText("Welcome to checkers!");

        // sets size of panel
        this.setPreferredSize(
                new Dimension(
                        BOARD_SIZE * IMG_SIZE,
                        BOARD_SIZE * IMG_SIZE
                )
        );

    }

    private void loadImages() {
        images = new Image[NUM_IMG];

        for(int i = 0; i < NUM_IMG; i++) {
            var path = "src/images/" + i + ".png";
            this.images[i] = (new ImageIcon(path)).getImage().getScaledInstance(IMG_SIZE, IMG_SIZE, 1);
        }
    }

    private void initBoard() {
        this.board = new Board();
        this.gm = new GameManager();
        this.bot = new MaxBot();
        this.addMouseListener(new PieceSelector());
    }

    @Override
    public void paintComponent(Graphics g) {
        paintBoard(g);
        paintDames(g);

        if(isSelection && isInBoard(selection)) {
            paintMoves(g);
        }
    }

    public void paintBoard(Graphics g) {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {

                int x = j * IMG_SIZE;
                int y = i * IMG_SIZE;

                if(i % 2 == 0) {
                    if(j % 2 == 0)
                        g.drawImage(images[W_SQUARE], x, y, this);
                    else
                        g.drawImage(images[B_SQUARE], x, y, this);
                }
                else {
                    if(j % 2 == 0)
                        g.drawImage(images[B_SQUARE], x, y, this);
                    else
                        g.drawImage(images[W_SQUARE], x, y, this);
                }
            }
        }
    }

    public void paintDames(Graphics g) {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {

                Dame currentDame = board.getDame(i, j);
                int x = j * IMG_SIZE;
                int y = i * IMG_SIZE;

                if(!(currentDame instanceof Empty)) {
                    if (currentDame.isQueen()) {
                        if (currentDame.getColor() == Color.WHITE)
                            g.drawImage(images[W_QUEEN], x, y, this);
                        else
                            g.drawImage(images[B_QUEEN], x, y, this);
                    } else {
                        if (currentDame.getColor() == Color.WHITE)
                            g.drawImage(images[W_DAME], x, y, this);
                        else
                            g.drawImage(images[B_DAME], x, y, this);
                    }
                }
            }
        }
    }

    public void paintMoves(Graphics g) {
        Position[] moves = gm.getPossibleMoves(board.getDame(selection), board);

        for(int i = 0; i < moves.length; i++) {
            int x = moves[i].getCol() * IMG_SIZE;
            int y = moves[i].getRow() * IMG_SIZE;
            g.drawImage(images[MOVE], x, y, this);
        }
    }

    private boolean isInBoard(int x, int y) {
        if(x >= 0 && x < BOARD_SIZE * IMG_SIZE
                && y >= 0 && y < BOARD_SIZE * IMG_SIZE)
            return true;

        return false;
    }

    private boolean isInBoard(Position pos) {
        if(pos.getRow() >= 0 && pos.getRow() < BOARD_SIZE &&
                pos.getCol() >= 0 && pos.getCol() < BOARD_SIZE)
            return true;

        return false;
    }

    private boolean isInMoves(Position pos) {
        Position[] moves = gm.getPossibleMoves(board.getDame(selection), board);

        for(int i = 0; i < moves.length; i++) {
            if(pos.getRow() == moves[i].getRow() &&
                    pos.getCol() == moves[i].getCol()) {
                return true;
            }
        }

        return false;
    }

    private class PieceSelector extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cRow = y / IMG_SIZE;
            int cCol = x / IMG_SIZE;

            if(isInBoard(new Position(cRow, cCol))) {
                if (!gm.isWhiteWinner(board) && !gm.isBlackWinner(board)) {

                    if (isWhiteMove) {
                        if (!isSelection) {
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                if (!(board.getDame(cRow, cCol) instanceof Empty) &&
                                        board.getDame(cRow, cCol).getColor() == Color.WHITE) {
                                    isSelection = true;
                                    selection = new Position(cRow, cCol);
                                    repaint();
                                } else {
                                    isSelection = false;
                                }
                            }
                        } else {
                            Position move = new Position(cRow, cCol);

                            if (isInBoard(x, y)) {
                                if (e.getButton() == MouseEvent.BUTTON1) {
                                    if (isInMoves(move)) {
                                        if (board.getDame(move) instanceof Empty) {
                                            board.getDame(selection).move(move, board);
                                            isWhiteMove = false;
                                        } else {
                                            Dame captured = board.getDame(move);
                                            Position newPos = gm.getNewPosition(board.getDame(selection), captured);
                                            board.getDame(selection).capture(captured, board);
                                            if (!gm.canStillCapture(board, board.getDame(newPos)))
                                                isWhiteMove = false;
                                        }
                                    }
                                }
                            }

                            isSelection = false;
                            repaint();

                        }
                    }

                    // black move
                    if (!isWhiteMove) {
                        // experiment
                        Display d = new Display();
                        BoardNode node = new BoardNode(board, true);
                        bot.createTree(node, 0);
                        bot.determineValues(node, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        totalPrunes += d.getNumPrunes(node, 0);
                        numTrees++;

                        board = bot.getBestMove(board);
                        repaint();
                        isWhiteMove = true;
                    }

                }

                if(gm.isWhiteWinner(board)) {
                    statusbar.setText("Human wins!");
                    System.out.println(totalPrunes + "\t" + numTrees);
//                    System.out.println(numTrees);
                }
                if(gm.isBlackWinner(board)) {
                    statusbar.setText("Bot wins!");
                    System.out.println(totalPrunes + "\t" + numTrees);
//                    System.out.println(numTrees);
                }
            }
        }
    }
}
