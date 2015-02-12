package com.backfield.tix_tax_toes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainPanel extends JPanel implements MouseListener {

    public static Integer width = 500;
    public static Integer height = 500;
    private GameState gameState;
    private final JFrame window;

    public MainPanel(JFrame window) {
        this.addMouseListener(this);
        this.gameState = new GameState();
        this.window = window;
        this.setTitle();
    }

    public Dimension getPreferredSize() {
        return new Dimension(MainPanel.width,MainPanel.height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine((MainPanel.width/3), 0, MainPanel.width/3, MainPanel.height);
        g.drawLine(((MainPanel.width*2)/3), 0, (MainPanel.width*2)/3, MainPanel.height);
        g.drawLine(0, (MainPanel.height/3), MainPanel.width, MainPanel.height/3);
        g.drawLine(0, ((MainPanel.height*2)/3), MainPanel.width, (MainPanel.height*2)/3);
        this.gameState.drawPlayers(g);
        if(this.gameState.isGameOver()) {
            this.gameState.drawWinningLine(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.gameState.isGameOver()) { return; }
        Integer row;
        Integer col;

        if (e.getY() < (MainPanel.height/3)) {
            row = 0;
        } else if(e.getY() < (MainPanel.height*2)/3) {
            row = 1;
        } else {
            row = 2;
        }

        if (e.getX() < (MainPanel.width/3)) {
            col = 0;
        } else if(e.getX() < (MainPanel.width*2)/3) {
            col = 1;
        } else {
            col = 2;
        }

        if (this.gameState.playerMove(col, row)) {
            this.gameState.nextPlayer();
            final GameState.Player winner = this.gameState.checkWinner();
            if(winner != GameState.Player.EMPTY) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(window, winner + " has won");
                    }
                });
            } else if (gameState.isTie()) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(window, "Game was a tie");
                    }
                });
            }
            this.setTitle();
            this.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void setTitle() {
        this.window.setTitle(this.gameState.getCurrentPlayer() + "'s Turn");
    }

    public void reset() {
        this.gameState = new GameState();
        this.setTitle();
        this.repaint();
    }
}
