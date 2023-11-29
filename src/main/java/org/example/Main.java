package org.example;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid(3);
        Player cross = new Player("X");
        Player circle = new Player("O");

        cross.setGrid(grid);
        circle.setGrid(grid);

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

        System.out.println(grid.winner());
    }
}