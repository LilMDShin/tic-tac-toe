package org.example;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid(3);
        Player cross = new Player("X");
        Player circle = new Player("O");

        cross.setGrid(grid);
        circle.setGrid(grid);

        /*

        System.out.println(grid);

        circle.place(1, 1);

        System.out.println(grid);


        cross.place(0, 0);

        System.out.println(grid);

        circle.place(1, 0);

        System.out.println(grid);

        cross.place(0, 1);

        System.out.println(grid);

        circle.place(2, 0);

        System.out.println(grid);

        System.out.println(grid.winner());

        cross.place(0, 2);

        System.out.println(grid);

        System.out.println();

        System.out.println(grid.winner());
        */

        // Example under -> not a fork -> should not matter

        TwoPlayerGame twoPlayerGame = new TwoPlayerGame();

        GameTree childNode = twoPlayerGame.minMaxMove(cross, 1, 1, twoPlayerGame.root);

        // childNode.evaluateGameValue(cross, 1, 1);

        System.out.println(twoPlayerGame.grid);;

        System.out.println(childNode.gameValue);

        GameTree childNode2 = twoPlayerGame.minMaxMove(circle, 1, 0, childNode);

        System.out.println(twoPlayerGame.grid);;

        System.out.println(childNode2.gameValue);

        // System.out.println(twoPlayerGame.root.children.get(0).children.get(0).grid);


        // System.out.println(twoPlayerGame.findFork(cross, 2, 1));

        // Finish move + funct -> Check class TwoPlayerGame near funct minMaxMove


        // Test of MinMax class -> funct getMinMaxValues




        // define max depth when handling minmax algo
        // max depth can be different depending on the stage of the game
        // early on max depth can be rather high while being lower later on

    }
}