package org.example;

import java.util.Objects;

public class Player {

    String symbol;
    Grid grid;

    public Player(String symbol) {
        this.symbol = symbol;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public boolean place(int i, int j){
        return grid.place(i, j, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(symbol, player.symbol);
    }


    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }


    @Override
    public String toString() {
        return symbol;
    }

    public static final Player none = new Player(" ");
}
