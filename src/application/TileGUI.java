package application;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;

public class Table extends JFrame {
    //private static final int TILE_SIZE = 50;
    private ArrayList<Tile> tiles;

    public Table() {
        super("Tile GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int windowWidth = 540;
        int windowHeight = 1080;
        setSize(windowWidth, windowHeight);

        // create the tiles
        Tiles rummikub = new Tiles();
        tiles = rummikub.getTiles();

        // create a panel to hold the tiles
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // use FlowLayout with LEFT alignment and 10-pixel horizontal and vertical gaps

        // add each tile to the panel
        for (Tile tile : tiles) {
            JButton button = new JButton(); // create a button
            button.setText(String.valueOf(tile.getNumber())); // set the button text to the tile's value
            button.setForeground(getColorFromTile(tile)); // set the button text color to the tile's color
            button.setBackground(Color.LIGHT_GRAY); // set the button background to white
            button.setPreferredSize(new Dimension(50, 60)); // set the button size
            panel.add(button); // add the button to the panel
        }

        add(panel); // add the panel to the frame
        setVisible(true); // show the frame
    }

    // helper method to get the color of a tile as a Color object
    private static Color getColorFromTile(Tile tile) {
        if (tile.getColor() == null) {
            return Color.MAGENTA; // default color if tile color is null
        }
        switch (tile.getColor()) {
            case RED:
                return Color.RED;
            case BLUE:
                return Color.BLUE;
            case YELLOW:
                return Color.YELLOW;
            case BLACK:
                return Color.BLACK;
            default:
                return Color.MAGENTA;
        }
    }

    public static void main(String[] args) {
        new Table();
    }
}
