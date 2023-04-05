package application;

import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TileGUI extends Application {
    private ArrayList<Tile> tiles;
    private ArrayList<Tile> hand;

    private TilePane handPane;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // create the tiles
        Tiles rummikub = new Tiles();
        tiles = rummikub.getTiles();
        Collections.shuffle(tiles);

        hand = new ArrayList<>();

        // create a label to display the drawn tile
        Label drawnTileLabel = new Label();

        // create a button to draw a tile
        Button drawButton = new Button("Draw");
        drawButton.setOnAction(e -> {
            if (tiles.size() > 0) {
                Tile tile = tiles.remove(0);
                hand.add(tile);
                drawnTileLabel.setText("Drawn tile: " + tile.toString());
                displayHand();
            }
        });

        // create a button to reset the game
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            tiles.addAll(hand);
            hand.clear();
            Collections.shuffle(tiles);
            drawnTileLabel.setText("");
            displayHand();
        });

        // create an HBox to hold the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(drawButton, resetButton);

        // create a TilePane to display the hand
        handPane = new TilePane();
        handPane.setPadding(new Insets(10));
        handPane.setHgap(10);
        handPane.setVgap(10);
        displayHand();

        // add the components to the root pane
        root.setTop(drawnTileLabel);
        root.setCenter(handPane);
        root.setBottom(buttonBox);

        // create the scene and show the stage
        Scene scene = new Scene(root, 1080, 720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tile GUI");
        primaryStage.show();
    }

    private void displayHand() {
        handPane.getChildren().clear();
        for (Tile tile : hand) {
            Button button = new Button();
            button.setText(String.valueOf(tile.getNumber()));
            button.setTextFill(getColorFromTile(tile));
            button.setStyle("-fx-background-color: lightgray");
            button.setPrefSize(50, 60);
            handPane.getChildren().add(button);
        }
    }

    // helper method to get the color of a tile as a Color object
    private static Color getColorFromTile(Tile tile) {
        if (tile.getColor() == null) {
            return Color.MAGENTA;
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
