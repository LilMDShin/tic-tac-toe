package org.example;

import org.jetbrains.annotations.NotNull;

public class TwoPlayerGame {

    public final Player playerNuts = new Player("O");
    public final Player playerCrosses = new Player("X");

    Grid grid;

    TwoPlayerGame parent;

    public TwoPlayerGame() {
        this.grid = new Grid(3);
    }

    private void setGrid(Grid grid) {
        this.grid = grid;
        playerCrosses.setGrid(grid);
        playerNuts.setGrid(grid);
    }

    public TwoPlayerGame move(@NotNull Player player, int i, int j) {
        TwoPlayerGame twoPlayerGame = new TwoPlayerGame();
        twoPlayerGame.setGrid(new Grid(grid));
        if (player.equals(playerNuts)) {
            twoPlayerGame.playerNuts.place(i, j);
        } else if (player.equals(playerCrosses)) {
            twoPlayerGame.playerCrosses.place(i, j);
        }
        // this.parent = new TwoPlayerGame(this.grid);
        return  twoPlayerGame;
    }

    /**
     * Player nuts has advantage -> positive, Player crosses has advantage -> negative.
     * @return The value of the game.
     */
    public int eval(){

        /*
        Point of view of the player

        Rule 1 : player has a winning move -> take it -> 100
        Rule 2 : opponent has a winning move -> block it -> -100
        Rule 3 : player can create a fork -> take it -> 80
        Rule 4 : player's move cannot let opponent create a fork -> warning -> -80
        (Rule 5 : player's moves should allow a maximum amount of possible ways to win)

        coin -> 30
        centre -> 40
        le rest -> 20
        */
        int value = 0;


        return 0;
    }
}
