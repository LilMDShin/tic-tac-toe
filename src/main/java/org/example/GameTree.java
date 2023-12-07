package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class GameTree implements Comparable<GameTree> /*implements Iterable<GameTree>*/ {

    // 0 is the value for the root node
    int gameValue = 0;
    int minmaxValue = Integer.MAX_VALUE;
    int depth = 0;
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
        this.children.addLast(childNode);
        // return(childNode);
    }

    /**
     * Player one has advantage (the first player to make a move) -> positive, Player 2 has advantage -> negative.
     */
    public int evaluateGameValue(Player player, int i, int j) {
        // If depth is an odd number then node is for min player
        // If depth is an even number then node is for max player
        int calculatedValue = 0;
        // Check case of a winner
        Player winner = this.grid.winner();
        if (!winner.equals(Player.none)) {
            // The last player making the move can only be the winner
            if ((this.depth + 1) % 2 == 0) {
                calculatedValue = calculatedValue + 100;
            } else {
                calculatedValue = calculatedValue - 100;
            }
        } else if (findFork(player, i, j)) /* findFork() */ {
            if ((this.depth + 1) % 2 == 0) {
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
        if ((this.depth + 1) % 2 == 1) {
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

    /*
    @Override
    public Iterator<GameTree> iterator() {

        return null;
    }

    */

    // To handle tree for the minmax algo
    // Create funct to make the tree to a certain depth
    public static GameTree minMaxMove(@NotNull Player player, int i, int j, @NotNull GameTree node) {
        Grid nextGrid = new Grid(node.grid);
        nextGrid.place(i, j, player);
        GameTree childNode = new GameTree(nextGrid, node.gameValue, node.depth + 1);
        childNode.gameValue = childNode.evaluateGameValue(player, i, j);
        node.addChild(childNode);
        return(childNode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameTree gameTree = (GameTree) o;
        return(Arrays.deepEquals(this.grid.playerGrid, gameTree.grid.playerGrid));
    }

    @Override
    public int compareTo(GameTree gameTree) {
        return (this.depth - gameTree.depth);
    }
}
