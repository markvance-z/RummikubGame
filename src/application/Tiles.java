package application;

import java.util.ArrayList;
import java.util.Collections;

public class Tiles {
    private static final int NUM_TILES = 106;
    ArrayList<Tile> tiles;


    public Tiles() {
        tiles = new ArrayList<Tile>(NUM_TILES);
        for (int i = 1; i <= 13; i++) {
            for (Color color : Color.values()) {
                tiles.add(new Tile(color, i));
                tiles.add(new Tile(color, i));
            }
        }
        tiles.add(new Tile(null, 0)); // add two joker tiles
        tiles.add(new Tile(null, 0));
        Collections.shuffle(tiles); // shuffle the tiles
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }   

    //Print tiles to check integrity.
    public static void checkTiles() {
        Tiles rummikub = new Tiles();
        ArrayList<Tile> tiles = rummikub.getTiles();
        System.out.println("Tiles Remaining: " + tiles.size());  
        //for (Tile tile : tiles) {
        //    System.out.println(tile.getColor() + " " + tile.getNumber());
        //}
    }
}

enum Color {
    RED, BLUE, YELLOW, BLACK;

    public static Color fromString(String str) {
        switch (str.toLowerCase()) {
            case "red":
                return RED;
            case "blue":
                return BLUE;
            case "yellow":
                return YELLOW;
            case "black":
                return BLACK;        
            default:
                throw new IllegalArgumentException("Invalid color string: " + str);
        }
    }
}

class Tile {
    private Color color;
    private int number;

    public Tile(Color color, int number) {
        this.color = color;
        this.number = number;

    }
    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    // Override the toString method to return a string representation of the tile
    @Override
    public String toString() {
        return "Tile{" +
                ", number=" + number +
                ", color=" + color +
                '}';
    }
    public void setOwnerIndex(int i) {
    }
}