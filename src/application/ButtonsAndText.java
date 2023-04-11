package application;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

import java.awt.Point;

public class ButtonsAndText {
    
    private int currentPlayerIndex = 1;
    Text currentPlayerText;
    Text tilesRemainText;
    int gameStarted = 0;
    public static Pane root = new Pane();
    private ArrayList<Tile> tiles;
    private ArrayList<TileBox> tileBoxes = new ArrayList<>();
    private SoundPlayer soundPlayer = new SoundPlayer();
    private Point[] drawPositions = {
        new Point(440, 690),        //player 1 draw location
        new Point(510, 105),       //player 2 draw location
        new Point(100, 30),          //player 3 draw location
        new Point(30, 615)           //player 4 draw location
    };

    public void startGame() {
        gameStarted = 1;
        Tiles rummikub = new Tiles();
        tiles = rummikub.getTiles();
        if(!tiles.isEmpty()) {
        // Give each player 14 tiles and line them up
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 14; j++) {
                    Tile tile = tiles.remove(0);
                    TileBox tileBox = new TileBox(tile, tileBoxes);
                    Point initialPosition = getInitialPosition(i, j);
                    tileBox.setTranslateX(initialPosition.getX());
                    tileBox.setTranslateY(initialPosition.getY());
                    root.getChildren().add(tileBox);
                    tileBoxes.add(tileBox);
                }
            }
        }
        System.out.println("The game is started. All players have 14 tiles.");
        checkTiles();
    }

    public void drawTile() {
        if (gameStarted == 1) {
            // check if there are any tiles left in the ArrayList
            if (!tiles.isEmpty()) {
                Tile tile = tiles.remove(0); // remove the first tile from the ArrayList
                TileBox tileBox = new TileBox(tile, tileBoxes);
                Point drawPosition = drawPositions[currentPlayerIndex - 1];
                tileBox.setTranslateX(drawPosition.getX());
                tileBox.setTranslateY(drawPosition.getY());
                root.getChildren().add(tileBox);
                tileBoxes.add(tileBox);
                System.out.println("Player " + currentPlayerIndex + " has drawn a tile");
                checkTiles(); // call checkTiles() method to print the number of remaining tiles
                soundPlayer.tileSound();
            }
        }
    }

    public void resetGame() {
        // Clear the tileBoxes ArrayList and remove all TileBox objects from the root pane
        tileBoxes.clear();
        root.getChildren().removeIf(node -> node instanceof TileBox);
    
        // Clear the tiles ArrayList and create a new set of tiles
        tiles.clear();
        Tiles rummikub = new Tiles();
        tiles.addAll(rummikub.getTiles());
        
        //set current player to player 1 and update text
        currentPlayerIndex = 1;
        updateCurrentPlayerText();
        // Reset any other relevant state variables or UI components
        System.out.println("Game reset. Board cleared. Tiles shuffled.");
        checkTiles(); // call checkTiles() method to print the number of remaining tiles
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex % 4 + 1);
        updateCurrentPlayerText();
        System.out.println("Current Player: " + currentPlayerIndex);
    }

    public Point getInitialPosition(int playerIndex, int tileIndex) {
        int x = 0, y = 0;
        switch (playerIndex) {
            case 0: // Bottom
                x = 240 + 30 * tileIndex;
                y = 785;
                break;
            case 1: // Right
                x = 785;
                y = 690 - 40 * tileIndex;
                break;
            case 2: // Top
                x = 630 - 30 * tileIndex;
                y = 75;
                break;
            case 3: // Left
                x = 85;
                y = 170 + 40 * tileIndex;
                break;
        }
        return new Point(x, y);
    }

    public void checkTiles() {
        System.out.println("Tiles Remaining: " + tiles.size());
        tilesRemainText.setText("Tiles Remaining: " + tiles.size());
    }

    public void updateCurrentPlayerText() {
        currentPlayerText.setText("Current Player: " + currentPlayerIndex);
    }
}