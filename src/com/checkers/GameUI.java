package com.checkers;

import javax.swing.*;
import java.awt.*;

public class GameUI extends JPanel{

    // PROPERTIES
    private Board board;
    private GameManager gm;
    private MaxBot bot;

    // gui components
    private Image[] images;
    private JLabel statusbar;

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
    }

    @Override
    public void paintComponent(Graphics g) {
        paintBoard(g);
        paintDames(g);
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
}
