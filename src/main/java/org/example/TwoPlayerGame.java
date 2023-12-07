package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class TwoPlayerGame {

    public final Player playerNuts = new Player("O");
    public final Player playerCrosses = new Player("X");

    Grid grid;

    /* GameTree root probably not necessary */
    GameTree root;

    // TwoPlayerGame parent;

    // Classic game of Tic-tac-toe
    public TwoPlayerGame() {
        this.grid = new Grid(3);
        /* GameTree root probably not necessary */
        this.root = new GameTree(this.grid);
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
        return twoPlayerGame;
    }

    // To handle tree for the minmax algo
    // To move in GameTree ?
    // Create funct to make the tree to a certain depth
    /*
    public GameTree minMaxMove(@NotNull Player player, int i, int j, @NotNull GameTree node) {
        Grid nextGrid = new Grid(node.grid);
        nextGrid.place(i, j, player);
        GameTree childNode = new GameTree(nextGrid, node.gameValue, node.depth + 1);
        childNode.gameValue = childNode.evaluateGameValue(player, i, j);
        node.addChild(childNode);
        this.grid = nextGrid;
        return(childNode);
    }

    */

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

    /*
    public GameTree buildGameTree(int nbRealMovesMade, Player playerMax, Player playerMin, GameTree node2) {
        int maxDepth = MinMax.maxDepth(nbRealMovesMade);
        int depth = 0;
        int sizeGrid = this.grid.playerGrid[0].length;
        // Root of the tree
        GameTree node = new GameTree(this.grid);
        GameTree childNode;


        while (depth < maxDepth) {
            for (int i = 0; i < sizeGrid; i++) {
                for (int j = 0; j < sizeGrid; j++) {
                    if (node.grid.playerGrid[i][j].equals(Player.none)) {
                        if (node.depth % 2 == 0) {
                            GameTree.minMaxMove(playerMax, i, j, node);
                        }
                        else {
                            GameTree.minMaxMove(playerMin, i, j, node);
                        }
                    }
                }
            }
            depth++;
        }

        return(root);
    }

    */

    // Initially node is the root of the game tree
    public void buildGameTree(int maxDepth, Player playerMax, Player playerMin, GameTree node) {
        if (node.depth < maxDepth) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (node.grid.playerGrid[i][j].equals(Player.none)) {
                        if (node.depth % 2 == 0) {
                            GameTree.minMaxMove(playerMax, i, j, node);
                        }
                        else {
                            GameTree.minMaxMove(playerMin, i, j, node);
                        }
                    }
                }
            }
            for (int index = 0; index < node.children.size(); index++) {
                buildGameTree(maxDepth, playerMax, playerMin, node.children.get(index));
            }
        }
    }

    private Point humanPlay(Player humanPlayer) {
        int i = -1;
        int j = -1;
        int temp;
        Scanner scanner = new Scanner(System.in);
        while (i < 0 || j < 0) {
            System.out.println("Please choose a line between 0 and 2");
            try {
                temp = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter an integer between 0 and 2");
                temp = -1;
            }
            if (temp > -1 && temp <= 2) {
                i = temp;
                System.out.println("Please choose a column between 0 and 2");
                try {
                    temp = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Please enter an integer between 0 and 2");
                    temp = -1;
                }
                if (temp > -1 && temp <= 2) {
                    j = temp;
                }
            }
            if (!this.grid.playerGrid[i][j].equals(Player.none)) {
                i = -1;
                j = -1;
            }
        }
        return(new Point(i, j));
    }

    public void playGameWithAI(Player firstPlayer, Player playerHuman, Player playerAI) {
        int nbRealMovesMade = 0;
        Point point;
        System.out.println(grid);
        while (nbRealMovesMade < 9 && this.grid.winner() == Player.none) {
            if (nbRealMovesMade % 2 == 0) {
                if (firstPlayer.equals(playerHuman)) {
                    point = this.humanPlay(playerHuman);
                    this.grid.place(point.i, point.j, playerHuman);
                }
                else {
                    AIMove(playerHuman, playerAI, nbRealMovesMade);
                }
            }
            else {
                if (!firstPlayer.equals(playerHuman)) {
                    point = this.humanPlay(playerHuman);
                    this.grid.place(point.i, point.j, playerHuman);
                }
                else {
                    AIMove(playerHuman, playerAI, nbRealMovesMade);
                }
            }
            this.root = new GameTree(this.grid);
            System.out.println("−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−");
            System.out.println(grid);
            nbRealMovesMade++;
        }

    }

    private void AIMove(Player playerHuman, Player playerAI, int nbRealMovesMade) {
        int maxDepth;
        maxDepth = MinMax.maxDepth(nbRealMovesMade);
        this.buildGameTree(maxDepth, playerAI, playerHuman, this.root);
        MinMax.evaluateMinMaxValues(this.root);
        grid = root.getNextGrid();
    }
}
