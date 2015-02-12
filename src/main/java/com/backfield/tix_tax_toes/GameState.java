package com.backfield.tix_tax_toes;

import java.awt.*;

public class GameState {

    public enum Player {
        EMPTY("Empty"),
        PLAYER1("Player 1"),
        PLAYER2("Player 2");

        private String name;

        public void draw(Graphics g, Integer row, Integer col) {
            if(this == Player.PLAYER1) {
                Integer upperLeftX = (MainPanel.width*col)/3;
                Integer upperLeftY = (MainPanel.height*row)/3;
                Integer lowerRightX = (MainPanel.width*(col+1))/3;
                Integer lowerRightY = (MainPanel.height*(row+1))/3;
                g.drawLine(upperLeftX, upperLeftY, lowerRightX, lowerRightY);
                Integer upperRightX = (MainPanel.width*(col+1))/3;
                Integer upperRightY = (MainPanel.height*row)/3;
                Integer lowerLeftX = (MainPanel.width*col)/3;
                Integer lowerLeftY = (MainPanel.height*(row+1))/3;
                g.drawLine(lowerLeftX, lowerLeftY, upperRightX, upperRightY);
            } else if (this == Player.PLAYER2) {
                g.drawOval((MainPanel.width*col)/3, (MainPanel.height*row)/3, MainPanel.width / 3, MainPanel.height / 3);
            }
        }

        Player(String defaultName) { this.name = defaultName; }

        public void setName(String name) { this.name = name; }
        public String toString() { return this.name; }
    }

    private Player playerTurn;

    private Player cells[][];

    private Point winningCells[];

    private Boolean gameOver;

    public GameState() {
        this.playerTurn = Player.PLAYER1;
        this.cells = new Player[3][3];
        this.winningCells = new Point[]{ new Point(), new Point() };
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                this.cells[row][col] = Player.EMPTY;
            }
        }
        this.gameOver = false;
    }

    public Boolean playerMove(Integer col, Integer row) {
        if(this.cells[row][col] != Player.EMPTY) {
            return false;
        } else {
            this.cells[row][col] = this.playerTurn;
            return true;
        }
    }

    public void nextPlayer() {
        this.playerTurn = this.playerTurn == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
    }

    public Player checkWinner() {
        // Check each row
        for (int row = 0; row < 3; row++) {
            if (this.cells[row][0] != Player.EMPTY) {
                Player player = this.cells[row][0];
                for (int col = 0; col < 3; col++) {
                    if (this.cells[row][col] != player) {
                        player = Player.EMPTY;
                        break;
                    }
                }
                if(player != Player.EMPTY) {
                    this.gameOver = true;
                    this.winningCells[0].x = 0;
                    this.winningCells[0].y = row * (MainPanel.height/3) + (MainPanel.height/3/2);
                    this.winningCells[1].x = MainPanel.width;
                    this.winningCells[1].y = row * (MainPanel.height/3) + (MainPanel.height/3/2);
                    return player;
                }
            }
        }
        // Check each column
        for (int col = 0; col < 3; col++) {
            if(this.cells[0][col] != Player.EMPTY) {
                Player player = this.cells[0][col];
                for (int row = 0; row < 3; row++) {
                    if (this.cells[row][col] != player) {
                        player = Player.EMPTY;
                        break;
                    }
                }
                if(player != Player.EMPTY) {
                    this.gameOver = true;
                    this.winningCells[0].x = col * (MainPanel.width/3) + (MainPanel.width/3/2);
                    this.winningCells[0].y = 0;
                    this.winningCells[1].x = col * (MainPanel.width/3) + (MainPanel.width/3/2);
                    this.winningCells[1].y = MainPanel.height;
                    return player;
                }
            }
        }
        // Check upper-left to lower-right horizontal
        if(this.cells[0][0] != Player.EMPTY) {
            Player player = this.cells[0][0];
            for (int i = 0; i < 3; i++) {
                if (this.cells[i][i] != player) {
                    player = Player.EMPTY;
                    break;
                }
            }
            if(player != Player.EMPTY) {
                this.gameOver = true;
                this.winningCells[0].x = 0;
                this.winningCells[0].y = 0;
                this.winningCells[1].x = MainPanel.width;
                this.winningCells[1].y = MainPanel.height;
                return player;
            }
        }
        // Check lower-left to upper-right horizontal
        if(this.cells[2][0] != Player.EMPTY) {
            Player player = this.cells[2][0];
            for (int i = 0; i < 3; i++) {
                if (this.cells[2 - i][i] != player) {
                    player = Player.EMPTY;
                    break;
                }
            }
            if(player != Player.EMPTY) {
                this.gameOver = true;
                this.winningCells[0].x = MainPanel.width;
                this.winningCells[0].y = 0;
                this.winningCells[1].x = 0;
                this.winningCells[1].y = MainPanel.height;
                return player;
            }
        }
        return Player.EMPTY;
    }

    public Boolean isTie() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (this.cells[x][y] == Player.EMPTY) return false;
            }
        }
        return true;
    }

    public void drawPlayers(Graphics g) {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                this.cells[x][y].draw(g, x, y);
            }
        }
    }

    public void drawWinningLine(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine(
                this.winningCells[0].x,
                this.winningCells[0].y,
                this.winningCells[1].x,
                this.winningCells[1].y
        );
    }

    public Player getCurrentPlayer() { return this.playerTurn; }

    public Boolean isGameOver() { return this.gameOver; }
}
