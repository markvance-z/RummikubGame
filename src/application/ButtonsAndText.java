package application;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.awt.Point;

public class ButtonsAndText {
    
    private static int currentPlayerIndex = 1;
    Text currentPlayerText;
    Text tilesRemainText;
    int gameStarted = 0;
    public static Pane root = new Pane();
    private ArrayList<Tile> tiles;
    public static ArrayList<Tile> player1Hand = new ArrayList<>();
    public static ArrayList<Tile> player2Hand = new ArrayList<>();
    public static ArrayList<Tile> player3Hand = new ArrayList<>();
    public static ArrayList<Tile> player4Hand = new ArrayList<>();

    private ArrayList<TileBox> tileBoxes = new ArrayList<>();
    private SoundPlayer soundPlayer = new SoundPlayer();

    public void startGame() {
        gameStarted = 1;
        Tiles rummikub = new Tiles();
        tiles = rummikub.getTiles();

        if(!tiles.isEmpty()) {
            // Give each player 14 tiles and line them up
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 14; j++) {
                    Tile tile = tiles.remove(0);
                    TileBox tileBox = new TileBox(tile, tileBoxes, i);
                    Point initialPosition = getInitialPosition(i, j, 14);
                    tileBox.setTranslateX(initialPosition.getX());
                    tileBox.setTranslateY(initialPosition.getY());
                    root.getChildren().add(tileBox);
                    tileBoxes.add(tileBox);
    
                    // Add the drawn tile to the corresponding player hand
                    switch(i) {
                        case 0:
                            player1Hand.add(tile);
                            break;
                        case 1:
                            player2Hand.add(tile);
                            break;
                        case 2:
                            player3Hand.add(tile);
                            break;
                        case 3:
                            player4Hand.add(tile);
                            break;
                    }
                }
            }
        }

        System.out.println("The game is started. All players have 14 tiles.");
        checkTiles();
        //ArrayList<Tile> currentPlayerHand = getCurrentPlayerHand();
        for (int i = 0; i < 4; i++) {
            int count = 0;
            for (TileBox tileBox : tileBoxes) {
                if (tileBox.getOwnerIndex() == i) {
                    count++;
                }
            }
            System.out.println("Owner " + i + ": " + count + " tiles");
        }
    }
    
    public void drawTile(ArrayList<Tile> currentPlayerHand) {
        if (gameStarted == 1) {
            // check if there are any tiles left in the ArrayList
            if (!tiles.isEmpty()) {
                Tile tile = tiles.remove(0); // remove the first tile from the ArrayList
                System.out.println("Player " + currentPlayerIndex + " has drawn a tile");
                
                // Add the drawn tile to the corresponding player hand
                switch(currentPlayerIndex) {
                    case 1:
                        player1Hand.add(tile);
                        break;
                    case 2:
                        player2Hand.add(tile);
                        break;
                    case 3:
                        player3Hand.add(tile);
                        break;
                    case 4:
                        player4Hand.add(tile);
                        break;
                }
                checkTiles(); // call checkTiles() method to print the number of remaining tiles
                soundPlayer.tileSound();
                
            }
            Iterator<TileBox> iterator = tileBoxes.iterator();
                while (iterator.hasNext()) {
                    TileBox tileBox = iterator.next();
                    int ownerIndex = tileBox.getOwnerIndex(); // get the index of the owner player of the tileBox
                    if (ownerIndex == currentPlayerIndex - 1) {
                        iterator.remove();
                        root.getChildren().remove(tileBox);
                        System.out.println("Removed TileBox with owner index: " + ownerIndex);
            }
        }
        
            // Create new TileBox objects for each tile in the sorted list and add them to the root
            for (int i = 0; i < currentPlayerHand.size(); i++) {
                Tile tile = currentPlayerHand.get(i);
                TileBox tileBox = new TileBox(tile, tileBoxes, currentPlayerIndex - 1);
                Point initialPosition = getInitialPosition(currentPlayerIndex - 1, i, 14);
                tileBox.setTranslateX(initialPosition.getX());
                tileBox.setTranslateY(initialPosition.getY());
                root.getChildren().add(tileBox);
                tileBoxes.add(tileBox);
            }
        }
    }

    public void resetGame() {
        // Clear the tileBoxes ArrayList and remove all TileBox objects from the root pane
        tileBoxes.clear();
        root.getChildren().removeIf(node -> node instanceof TileBox);
        currentPlayerIndex = 1;
        updateCurrentPlayerText();

        // Clear the tiles ArrayList and create a new set of tiles
        tiles.clear();
        Tiles rummikub = new Tiles();
        tiles.addAll(rummikub.getTiles());
        
        // Clear the playerHands 1-4 ArrayList
        player1Hand.clear();
        player2Hand.clear();
        player3Hand.clear();
        player4Hand.clear();
        
        // Reset any other relevant state variables or UI components
        System.out.println("Game reset. Board cleared. Tiles shuffled.");
        checkTiles(); // call checkTiles() method to print the number of remaining tiles
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex % 4 + 1);
        updateCurrentPlayerText();
        System.out.println("Current Player: " + currentPlayerIndex);
        
        // Update the owner index of all TileBox objects to the new current player index
        for (TileBox tileBox : tileBoxes) {
            if (tileBox.getOwnerIndex() == currentPlayerIndex - 2) {
                tileBox.setOwnerIndex(currentPlayerIndex - 1);
            }
        }
        
        // Get the current player's hand
    }

    public static int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
    public static ArrayList<Tile> getCurrentPlayerHand() {
        ArrayList<Tile> currentPlayerHand = null;
        switch (currentPlayerIndex-1) {
            case 0:
                currentPlayerHand = player1Hand;
                break;
            case 1:
                currentPlayerHand = player2Hand;
                break;
            case 2:
                currentPlayerHand = player3Hand;
                break;
            case 3:
                currentPlayerHand = player4Hand;
                break;
        }
        return currentPlayerHand;
    }

    public Point getInitialPosition(int playerIndex, int tileIndex, int maxTilesPerRowOrColumn) {
        int x = 0, y = 0;
        
        switch (playerIndex) {
            case 0: // Bottom
                maxTilesPerRowOrColumn = 14;
                x = 240 + 30 * (tileIndex % maxTilesPerRowOrColumn);
                y = 785 + 40 * (tileIndex / maxTilesPerRowOrColumn);
                break;
            case 1: // Right
                maxTilesPerRowOrColumn = 14;
                x = 785 + 30 * (tileIndex / maxTilesPerRowOrColumn);
                y = 690 - 40 * (tileIndex % maxTilesPerRowOrColumn);
                break;
            case 2: // Top
                maxTilesPerRowOrColumn = 14;
                x = 630 - 30 * (tileIndex % maxTilesPerRowOrColumn);
                y = 75 - 40 * (tileIndex / maxTilesPerRowOrColumn);
                break;
            case 3: // Left
                maxTilesPerRowOrColumn = 14;
                x = 85 - 30 * (tileIndex / maxTilesPerRowOrColumn);
                y = 170 + 40 * (tileIndex % maxTilesPerRowOrColumn);
                break;
        }
        return new Point(x, y);
    }
    
    public void checkTiles() {
        System.out.println("Tiles Remaining: " + tiles.size());
        System.out.println("Player 1 Hand: " + player1Hand.size());
        System.out.println("Player 2 Hand: " + player2Hand.size());
        System.out.println("Player 3 Hand: " + player3Hand.size());
        System.out.println("Player 4 Hand: " + player4Hand.size());
        tilesRemainText.setText("Tiles Remaining: " + tiles.size());
    }

    public void updateCurrentPlayerText() {
        currentPlayerText.setText("Current Player: " + currentPlayerIndex);
    }
    
    public void sortPlayerTiles(ArrayList<Tile> currentPlayerHand) {
        // Sort the current player's hand by color and number
        Comparator<Tile> tileComparator = new Comparator<Tile>() {
            @Override
            public int compare(Tile tile1, Tile tile2) {
                // Compare tiles by color and number
                if (tile1.getNumber() == 0) {
                    return -1; // tile1 should come first in the list
                } 
                else if (tile2.getNumber() == 0) {
                    return 1; // tile2 should come first in the list
                } 
                else {
                    int colorComparison = tile1.getColor().compareTo(tile2.getColor());
                    if (colorComparison != 0) {
                        return colorComparison;
                    }
                    return Integer.compare(tile1.getNumber(), tile2.getNumber());
                }
            }
        };

        Collections.sort(currentPlayerHand, tileComparator);
        System.out.println("Player " + currentPlayerIndex + " tiles sorted. Result:");
        System.out.println(currentPlayerHand);
        
        // Clear the existing TileBoxes and remove them from the root
        Iterator<TileBox> iterator = tileBoxes.iterator();
        while (iterator.hasNext()) {
            TileBox tileBox = iterator.next();
            int ownerIndex = tileBox.getOwnerIndex(); // get the index of the owner player of the tileBox
            if (ownerIndex == currentPlayerIndex - 1) {
                iterator.remove();
                root.getChildren().remove(tileBox);
                //System.out.println("Removed TileBox with owner index: " + ownerIndex);
            }
        }
        
        // Create new TileBox objects for each tile in the sorted list and add them to the root
        for (int i = 0; i < currentPlayerHand.size(); i++) {
            Tile tile = currentPlayerHand.get(i);
            TileBox tileBox = new TileBox(tile, tileBoxes, currentPlayerIndex - 1);
            Point initialPosition = getInitialPosition(currentPlayerIndex - 1, i, 14);
            tileBox.setTranslateX(initialPosition.getX());
            tileBox.setTranslateY(initialPosition.getY());
            root.getChildren().add(tileBox);
            tileBoxes.add(tileBox);
        }
    }    
    
    public int compare(Tile tile1, Tile tile2) {
        // Check for null colors
        if (tile1.getColor() == null && tile2.getColor() == null) {
            // Both tiles have null colors, so compare by number
            return Integer.compare(tile1.getNumber(), tile2.getNumber());
        } else if (tile1.getColor() == null) {
            // Tile1 has a null color, so it should be placed at the front of the list
            return -1;
        } else if (tile2.getColor() == null) {
            // Tile2 has a null color, so it should be placed at the front of the list
            return 1;
        }
    
        // Compare tiles by color and number
        int colorComparison = tile1.getColor().compareTo(tile2.getColor());
        if (colorComparison != 0) {
            return colorComparison;
        }
        return Integer.compare(tile1.getNumber(), tile2.getNumber());
    }
    
    
}