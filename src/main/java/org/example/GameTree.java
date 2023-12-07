package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the tree of grid for the different possibilities
 */
public class GameTree implements Comparable<GameTree> {

    /**
     * 0 is the value for the root node
     */
    int gameValue = 0;
    int minmaxValue = Integer.MAX_VALUE;
    int depth = 0;
    Grid grid;
    GameTree parent;
    List<GameTree> children;

    public GameTree(Grid grid) {
        this.grid = grid;
        children = new LinkedList<>();
    }

    public GameTree(Grid grid, int parentGameValue, int depth) {
        this.grid = grid;
        gameValue = parentGameValue;
        this.depth = depth;
        children = new LinkedList<>();
    }

    /**
     * Add child with gameValue of the parent
     * the evaluation and real game value of the child is calculated afterward.
     * @param childNode The node to add to the tree.
     */
    public void addChild(@NotNull GameTree childNode) {
        childNode.parent = this;
        children.addLast(childNode);
    }

    /**
     * Player one has advantage (the first player to make a move) -> positive, Player 2 has advantage -> negative.
     * @param player The player who has to make a move
     * @param i row
     * @param j col
     * @return The value of the game
     */
    public int evaluateGameValue(Player player, int i, int j, MinOrMax minOrMax, Player previousPlayer) {
        // If depth is an odd number then node is for min player
        // If depth is an even number then node is for max player
        int calculatedValue = 0;
        // Check case of a winner
        Player winner = grid.winner();
        /*
        for (int index_i = 0; index_i < 3; index_i++) {
            for (int index_j = 0; index_j < 3; index_j++) {
                if (this.grid.playerGrid[index_i][index_j].equals(Player.none)) {
                    if (this.linePossibleVictory(previousPlayer, index_i) || this.columnPossibleVictory(previousPlayer, index_j)
                            || this.diagPossibleVictory(previousPlayer, index_i, index_j)) {
                        if (minOrMax == MinOrMax.MAX) {
                            calculatedValue = -150;
                        }
                        else {
                            calculatedValue = +150;
                        }
                    }
                }
            }
        }

        */
        if (!winner.equals(Player.none) && calculatedValue == 0) {
            // The last player making the move can only be the winner
            /*
            if ((depth + 1) % 2 == 0) {
                calculatedValue = 100;
            } else {
                calculatedValue = - 100;
            }

            */
            if (minOrMax == MinOrMax.MAX) {
                calculatedValue = 200;
            }
            else {
                calculatedValue = -200;
            }
        }
        else if (findFork(player, i, j) && calculatedValue == 0) {
            /*
            if ((depth + 1) % 2 == 0) {
                calculatedValue = 80;
            } else {
                calculatedValue = -80;
            }

            */
            if (minOrMax == MinOrMax.MAX) {
                calculatedValue = 80;
            }
            else {
                calculatedValue = -80;
            }

        } else if (calculatedValue == 0) {
            /*
            if (this.parent != null) {
                if (this.parent.linePossibleVictory(previousPlayer, i) || this.parent.columnPossibleVictory(previousPlayer, j)
                        || this.diagPossibleVictory(previousPlayer, i, j)) {
                    if (minOrMax == MinOrMax.MAX) {
                        calculatedValue = 150;
                    }
                    else {
                        calculatedValue = -150;
                    }
                }
                else {
                    calculatedValue = this.valueSimpleTile(i, j);
                }
            }

            else {
                calculatedValue = this.valueSimpleTile(i, j);
            }

            */

            calculatedValue = this.valueSimpleTile(i, j);
        }
        return gameValue + calculatedValue;
    }

    /**
     * Gets the value of a tile
     * @param i row
     * @param j col
     * @return The value of the [i, j] tile
     */
    private int valueSimpleTile(int i, int j) {
        int tileValue;
        if (i == 1 && j == 1) {
            tileValue = 40;
        } else if (i != 1 & j != 1) {
            tileValue = 30;
        } else {
            tileValue = 20;
        }
        // Case of player being the min player
        if ((depth + 1) % 2 == 1) {
            tileValue = (-1) * tileValue;
        }
        return (tileValue);
    }

    /**
     * Gets if there is a possible line victory at i
     * @param player The player who has to make the move
     * @param i The row
     * @return True if there is a possible victory
     */
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

    /**
     * Gets if there is a possible column victory at i
     * @param player The player who has to make the move
     * @param j The col
     * @return True if there is a possible victory
     */
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

    /**
     * Gets if there is a possible diagonal victory at i j
     * @param player The player who has to make the move
     * @param i The row
     * @param j The col
     * @return True if there is a possible victory
     */
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

    /**
     * Finds if there is a fork possible
     * @param player The player who has to make the move
     * @param i The row
     * @param j The col
     * @return True if there is a possible victory
     */
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

    /**
     * To handle tree for the minmax algo
     * Create function to make the tree to a certain depth
     * @param player The player who has to make the move
     * @param i col
     * @param j row
     * @param node The tree node
     * @return tree for the minmax algo
     */
    public static @NotNull GameTree minMaxMove(@NotNull Player player, int i, int j, @NotNull GameTree node, MinOrMax minOrMax, Player previousPlayer) {
        Grid nextGrid = new Grid(node.grid);
        nextGrid.place(i, j, player);
        GameTree childNode = new GameTree(nextGrid, node.gameValue, node.depth + 1);
        childNode.gameValue = childNode.evaluateGameValue(player, i, j, minOrMax, previousPlayer);
        node.addChild(childNode);
        return(childNode);
    }

    /**
     * Gets the next grid move
     * @return The best next grid
     */
    public Grid getNextGrid(){
        return children.stream().filter(gameTree -> gameTree.minmaxValue == this.minmaxValue).toList().getFirst().grid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameTree gameTree = (GameTree) o;
        return(Arrays.deepEquals(this.grid.playerGrid, gameTree.grid.playerGrid));
    }

    @Override
    public int compareTo(@NotNull GameTree gameTree) {
        return (this.depth - gameTree.depth);
    }
}
