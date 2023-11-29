package org.example;

import java.util.StringJoiner;

public class Grid {
    Player[][] playerGrid;

    public Grid(int size) {
        playerGrid = new Player[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                playerGrid[i][j] = Player.none;
            }
        }
    }

    public boolean place(int i, int j, Player player) {
        if (playerGrid[i][j].equals(Player.none)) {
            playerGrid[i][j] = player;
            return true;
        }
        return false;
    }

    public Player winner() {
        Player winner = Player.none;

        winner = diagRWinner(winner);

        if (winner.equals(Player.none)) {
            winner = diagLWinner(winner);
            if (winner.equals(Player.none)) {
                winner = lineWinner(winner);
                if (winner.equals(Player.none)) {
                    winner = columnWinner(winner);
                }
            }
        }


        return winner;
    }

    private Player columnWinner(Player winner) {
        for (int i = 0; i < playerGrid.length; i++) {
            Player column = playerGrid[0][i];
            for (int j = 0; j < playerGrid.length; j++) {
                if (!playerGrid[j][i].equals(column)) {
                    column = Player.none;
                    break;
                }
            }
            winner = !column.equals(Player.none) ? column : winner;
        }
        return winner;
    }

    private Player lineWinner(Player winner) {
        for (Player[] players : playerGrid) {
            Player line = players[0];
            for (int j = 1; j < this.playerGrid.length; j++) {
                if (!line.equals(players[j])) {
                    line = Player.none;
                    break;
                }
            }
            winner = !line.equals(Player.none) ? line : winner;
        }
        return winner;
    }

    private Player diagLWinner(Player winner) {
        Player diagL = playerGrid[0][playerGrid.length - 1];
        for (int i = 1; i < playerGrid.length; i++) {
            if (!diagL.equals(playerGrid[i][playerGrid.length - 1 - i])) {
                diagL = Player.none;
                break;
            }
        }
        winner = !diagL.equals(Player.none) ? diagL : winner;
        return winner;
    }

    private Player diagRWinner(Player winner) {
        Player diagR = playerGrid[0][0];
        for (int i = 1; i < playerGrid.length; i++) {
            if (!diagR.equals(playerGrid[i][i])) {
                diagR = Player.none;
                break;
            }
        }
        winner = !diagR.equals(Player.none) ? diagR : winner;
        return winner;
    }

    @Override
    public String toString() {

        StringJoiner stringJoiner = new StringJoiner("\n");
        for (Player[] players : playerGrid) {
            StringJoiner stringJoiner1 = new StringJoiner(" | ");
            for (Player player : players) {
                stringJoiner1.add(player.toString());
            }
            stringJoiner.add(stringJoiner1.toString());
        }


        return stringJoiner.toString();
    }
}
