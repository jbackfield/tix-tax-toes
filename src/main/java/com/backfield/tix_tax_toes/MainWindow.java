package com.backfield.tix_tax_toes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private final MainPanel mainPanel;

    public JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createNewGameMenuItem());
        fileMenu.addSeparator();
        fileMenu.add(createQuitGameMenuItem());
        return fileMenu;
    }

    public JMenuItem createNewGameMenuItem() {
        JMenuItem newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.reset();
            }
        });
        return newGameMenuItem;
    }

    public JMenuItem createQuitGameMenuItem() {
        JMenuItem quitGameMenuItem = new JMenuItem("Quit");
        quitGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        return quitGameMenuItem;
    }

    public JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        editMenu.add(setPlayer1Name());
        editMenu.add(setPlayer2Name());
        return editMenu;
    }

    public JMenuItem setPlayer1Name() {
        JMenuItem setPlayer1NameMenuItem = new JMenuItem("Set Player1 Name");
        setPlayer1NameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Please Input Player 1's Name");
                GameState.Player.PLAYER1.setName(name);
                mainPanel.setTitle();
            }
        });
        return setPlayer1NameMenuItem;
    }

    public JMenuItem setPlayer2Name() {
        JMenuItem setPlayer2NameMenuItem = new JMenuItem("Set Player2 Name");
        setPlayer2NameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Please Input Player 2's Name");
                GameState.Player.PLAYER2.setName(name);
                mainPanel.setTitle();
            }
        });
        return setPlayer2NameMenuItem;
    }

    public MainWindow() {
        this.mainPanel = new MainPanel(this);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.pack();
        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }


}
