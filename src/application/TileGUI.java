package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TileGUI extends Application {
    //private static final int TILE_SIZE = 50;
    private ArrayList<Tile> tiles;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tile GUI");

        // create the tiles
        Tiles rummikub = new Tiles();
        tiles = rummikub.getTiles();

        // create a panel to hold the tiles
        FlowPane pane = new FlowPane();
        pane.setHgap(10); // set horizontal gap between buttons
        pane.setVgap(10); // set vertical gap between buttons
        pane.setPadding(new Insets(10)); // add padding around the pane
        
        // add each tile to the panel
        for (Tile tile : tiles) {
            Button button = new Button(); // create a button
            button.setText(String.valueOf(tile.getNumber())); // set the button text to the tile's value
            button.setTextFill(getColorFromTile(tile)); // set the button text color to the tile's color
            button.setStyle("-fx-background-color: lightgrey;"); // set the button background to light grey
            button.setPrefSize(50, 60); // set the button size
            pane.getChildren().add(button); // add the button to the panel
        }

        Scene scene = new Scene(pane, 1080, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
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
        launch(args);
    }
}