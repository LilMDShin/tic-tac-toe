package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid(3);
        Player cross = new Player("X");
        Player circle = new Player("O");

        TwoPlayerGame twoPlayerGame = new TwoPlayerGame();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Who do you want to be [X, O] ?");
        String playerInput = scanner.next("[X,O]");
        Player player =cross;
        Player ai =circle;
        if (playerInput.equals("O")) {
            player = circle;
            ai = cross;
        }

        System.out.println("Do you want to play first [Y, N] ?");
        String first = scanner.next("[Y,N]");
        switch (first){
            case "Y":
                twoPlayerGame.playGameWithAI(player, player, ai);
                break;
            case "N":
                twoPlayerGame.playGameWithAI(ai, player, ai);
                break;
        }
    }
}