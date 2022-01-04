package com.checkers.game;

import javax.swing.*;
import java.awt.*;

public class Driver extends JFrame {

    private JLabel statusbar;

    public Driver() {
        statusbar = new JLabel("");
        this.add(statusbar, BorderLayout.SOUTH);

        this.add(new GameUI(statusbar));

        this.setResizable(false);
        this.pack();

        this.setTitle("Checkers");
        var path = "src/images/5.png";
        Image icon = (new ImageIcon(path)).getImage();
        this.setIconImage(icon);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            var ex = new Driver();
            ex.setVisible(true);
        });
    }
}
