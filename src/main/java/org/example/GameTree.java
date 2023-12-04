package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class GameTree /* implements Iterable<Player> */ {

    // 0 is the value for the root node
    int gameValue = 0;
    int minmaxValue = 0;
    // root is of depth = 0
    int depth = -1;
    Grid grid;
    GameTree parent;
    List<GameTree> children;

    public GameTree(Grid grid) {
        this.grid = grid;
        this.children = new LinkedList<>();
    }

    public GameTree(Grid grid, int parentGameValue, int depth) {
        this.grid = grid;
        this.gameValue = parentGameValue;
        this.depth = depth;
        this.children = new LinkedList<>();
    }

    // For now add child with gameValue of the parent,
    // the evaluation and real game value of the child is calculated afterwards
    public void addChild(@NotNull GameTree childNode) {
        childNode.parent = this;
        this.children.add(childNode);
        // return(childNode);
    }

    /**
     * Player max has advantage -> positive, Player min has advantage -> negative.
     */
    public int evaluateGameValue(Player player, int i, int j) {
        // If depth is an odd number then node is for min player
        // If depth is an even number then node is for max player
        int calculatedValue = 0;
        // Check case of a winner
        Player winner = this.grid.winner();
        if (!winner.equals(Player.none)) {
            // The last player making the move can only be the winner
            if (this.depth % 2 == 0) {
                calculatedValue = calculatedValue + 100;
            } else {
                calculatedValue = calculatedValue - 100;
            }
        } else if (findFork(player, i, j)) /* findFork() */ {
            if (this.depth % 2 == 0) {
                calculatedValue = 80;
            } else {
                calculatedValue = -80;
            }

        } else {
            calculatedValue = this.valueSimpleTile(i, j);
        }
        return this.gameValue + calculatedValue;
    }

    private int valueSimpleTile(int i, int j) {
        int tileValue = 0;
        if (i == 1 && j == 1) {
            tileValue = 40;
        } else if (i != 1 & j != 1) {
            tileValue = 30;
        } else {
            tileValue = 20;
        }
        // Case of player being the min player
        if (this.depth % 2 == 1) {
            tileValue = (-1) * tileValue;
        }
        return (tileValue);
    }

    private boolean linePossibleVictory(Player player, int i) {
        int nbPlayerSymbol = 0;
        int nbEmptyTiles = 0;
        for (int j = 0; j < 3; j++) {
            if (this.grid.playerGrid[i][j].equals(player)) {
                nbPlayerSymbol++;
            } else if (this.grid.playerGrid[i][j].equals(Player.none)) {
                nbEmptyTiles++;
            }
        }
        return (nbPlayerSymbol == 2 && nbEmptyTiles == 1);
    }

    private boolean columnPossibleVictory(Player player, int j) {
        int nbPlayerSymbol = 0;
        int nbEmptyTiles = 0;
        for (int i = 0; i < 3; i++) {
            if (this.grid.playerGrid[i][j].equals(player)) {
                nbPlayerSymbol++;
            } else if (this.grid.playerGrid[i][j].equals(Player.none)) {
                nbEmptyTiles++;
            }
        }
        return (nbPlayerSymbol == 2 && nbEmptyTiles == 1);
    }

    private boolean diagPossibleVictory(Player player, int i, int j) {
        // Last move being in the center is handled in findFork
        int nbPlayerSymbol = 0;
        int nbEmptyTiles = 0;
        // Case of last move being in a corner
        if (i != 1 && j != 1) {
            if ((i == 0 && j == 0) || (i == 2 && j == 2)) {
                for (int index = 0; index < 3; index++) {
                    if (this.grid.playerGrid[index][index].equals(player)) {
                        nbPlayerSymbol++;
                    } else if (this.grid.playerGrid[index][index].equals(Player.none)) {
                        nbEmptyTiles++;
                    }
                }
            } else if ((i == 0 && j == 2) || (i == 2 && j == 0)) {
                for (int index = 0; index < 3; index++) {
                    if (this.grid.playerGrid[2 - index][index].equals(player)) {
                        nbPlayerSymbol++;
                    } else if (this.grid.playerGrid[2 - index][index].equals(Player.none)) {
                        nbEmptyTiles++;
                    }
                }
            }
        }
        return (nbPlayerSymbol == 2 && nbEmptyTiles == 1);
    }

    public boolean findFork(Player player, int i, int j) {
        int count = 0;
        // Case of last move being in the center
        if (i == 1 && j == 1) {
            if (this.diagPossibleVictory(player, 0, 0)) {
                count++;
            }
            if (this.diagPossibleVictory(player, 0, 2)) {
                count++;
            }
        }
        // All other cases
        else {
            if (diagPossibleVictory(player, i, j)) {
                count++;
            }
        }
        if (this.linePossibleVictory(player, i)) {
            count++;
        }
        if (this.columnPossibleVictory(player, j)) {
            count++;
        }
        return (count >= 2);
    }

}
