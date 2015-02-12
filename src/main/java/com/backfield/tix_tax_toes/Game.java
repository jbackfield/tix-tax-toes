package com.backfield.tix_tax_toes;

import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
    }

}
