package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TileGUI extends Application {
    private ArrayList<Tile> tiles;
    private Button drawTileButton;
    private Button resetButton;
    private Button nextPlayerButton;
    private Pane root;
    private ArrayList<TileBox> tileBoxes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        root.setPrefSize(1080, 720);

        // create the tiles
        Tiles rummikub = new Tiles();
        tiles = rummikub.getTiles();

        // create a panel to hold the tiles
        FlowPane pane = new FlowPane();
        pane.setHgap(10); // set horizontal gap between buttons
        pane.setVgap(10); // set vertical gap between buttons
        pane.setPadding(new Insets(10)); // add padding around the pane
        
        //add button to get a tile
        drawTileButton = new Button("Draw Tile");
        drawTileButton.setLayoutX(540);
        drawTileButton.setLayoutY(10);
        drawTileButton.setOnAction(event -> drawTile());

        //add button to cycle player
        nextPlayerButton = new Button("Next Player");
        nextPlayerButton.setLayoutX(540);
        nextPlayerButton.setLayoutY(10);
        nextPlayerButton.setOnAction(event -> drawTile());

        //add button to reset
        resetButton = new Button("Reset");
        resetButton.setLayoutX(540);
        resetButton.setLayoutY(40);
        resetButton.setOnAction(event -> resetGame());   

        root.getChildren().addAll(drawTileButton, resetButton);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tile GUI");
        primaryStage.show();
    }

    private void drawTile() {
        // check if there are any tiles left in the ArrayList
        if (!tiles.isEmpty()) {
            Tile tile = tiles.remove(0); // remove the first tile from the ArrayList
            checkTiles(); // call checkTiles() method to print the number of remaining tiles
            TileBox tileBox = new TileBox(tile);
            root.getChildren().add(tileBox);
            tileBoxes.add(tileBox);
        }
    }

    public void checkTiles() {
        System.out.println("Tiles Remaining: " + tiles.size());
    }

    public void resetGame() {
        // Clear the tileBoxes ArrayList and remove all TileBox objects from the root pane
        tileBoxes.clear();
        root.getChildren().removeIf(node -> node instanceof TileBox);
    
        // Clear the tiles ArrayList and create a new set of tiles
        tiles.clear();
        Tiles rummikub = new Tiles();
        tiles.addAll(rummikub.getTiles());
    
        // Reset any other relevant state variables or UI components
        checkTiles(); // call checkTiles() method to print the number of remaining tiles
    }

    //Create class for the tiles to be able to drag and snap
    private class TileBox extends Pane {

        private boolean isDragging = false;
        private Point2D dragOffset;
        private Text tileText;

        public TileBox(Tile tile) {
            setPrefSize(30, 40);
            Rectangle background = new Rectangle(30, 40);
            background.setArcWidth(10);
            background.setArcHeight(10);
            background.setFill(Color.rgb(211, 211, 211));
            setOnMousePressed(mousePressedHandler);
            setOnMouseDragged(mouseDraggedHandler);
            setOnMouseReleased(mouseReleasedHandler);
    
            // add the text to tile
            tileText = new Text();
            tileText.setX(10);
            tileText.setY(25);
            tileText.setText(String.valueOf(tile.getNumber()));
            tileText.setFill(getColorFromTile(tile)); // set text color to tile color
            tileText.setFont(Font.font(12)); // set font size to 12
            getChildren().add(background);
            getChildren().add(tileText);
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
    
        private EventHandler<MouseEvent> mousePressedHandler = event -> {
            isDragging = true;
            dragOffset = new Point2D(event.getX(), event.getY());
        };
    
        private EventHandler<MouseEvent> mouseDraggedHandler = event -> {
            if (isDragging) {
                double newX = event.getSceneX() - dragOffset.getX();
                double newY = event.getSceneY() - dragOffset.getY();
                setTranslateX(newX);
                setTranslateY(newY);
                snapIfNearby();
            }
        };
    
        private EventHandler<MouseEvent> mouseReleasedHandler = event -> {
            isDragging = false;
            snapToGrid();
        };
    
        private void snapIfNearby() {
            for (TileBox otherTileBox : tileBoxes) {
                if (otherTileBox != this && isNearby(otherTileBox)) {
                    snapTo(otherTileBox);
                }
            }
        }
    
        private boolean isNearby(TileBox otherTileBox) {
            double distance = getDistance(otherTileBox);
            return distance < 100;
        }
    
        private double getDistance(TileBox otherTileBox) {
            double dx = getTranslateX() - otherTileBox.getTranslateX();
            double dy = getTranslateY() - otherTileBox.getTranslateY();
            return Math.sqrt(dx * dx + dy * dy);
        }
    
        private void snapTo(TileBox otherTileBox) {
            double x = getTranslateX();
            double y = getTranslateY();
            double otherX = otherTileBox.getTranslateX();
            double otherY = otherTileBox.getTranslateY();
            double otherWidth = otherTileBox.getWidth();
            double otherHeight = otherTileBox.getHeight();
            double snapDistance = 10;
    
            // Snap to left edge
            if (x > otherX && x < otherX + snapDistance) {
                setTranslateX(otherX);
            }
            // Snap to right edge
            if (x + getWidth() > otherX + otherWidth - snapDistance && x + getWidth() < otherX + otherWidth) {
                setTranslateX(otherX + otherWidth - getWidth());
            }
            // Snap to top edge
            if (y > otherY && y < otherY + snapDistance) {
                setTranslateY(otherY);
            }
            // Snap to bottom edge
            if (y + getHeight() > otherY + otherHeight - snapDistance && y + getHeight() < otherY + otherHeight) {
                setTranslateY(otherY + otherHeight - getHeight());
            }

            //check if
            if (getBoundsInParent().intersects(otherTileBox.getBoundsInParent())) {
                setTranslateX(x);
                setTranslateY(y);
            }
        }
    
        private void snapToGrid() {
            double cellWidth = 30;
            double cellHeight = 40;
        
            // Calculate the grid cell that the box should snap to
            double gridX = Math.round(getTranslateX() / cellWidth) * cellWidth;
            double gridY = Math.round(getTranslateY() / cellHeight) * cellHeight;
        
            // Check if the grid cell is already occupied
            boolean isOccupied = false;
            for (TileBox otherBox : tileBoxes) {
                if (otherBox != this && otherBox.getTranslateX() == gridX && otherBox.getTranslateY() == gridY) {
                    isOccupied = true;
                    break;
                }
            }
        
            // If the grid cell is occupied, find the nearest empty cell to snap to
            if (isOccupied) {
                double minDistance = Double.MAX_VALUE;
                double newX = gridX;
                double newY = gridY;
        
                for (double x = cellWidth / 2; x <= root.getPrefWidth() - cellWidth / 2; x += cellWidth) {
                    for (double y = cellHeight / 2; y <= root.getPrefHeight() - cellHeight / 2; y += cellHeight) {
                        boolean isOccupiedCell = false;
                        for (TileBox otherBox : tileBoxes) {
                            if (otherBox.getTranslateX() == x && otherBox.getTranslateY() == y) {
                                isOccupiedCell = true;
                                break;
                            }
                        }
                        if (!isOccupiedCell) {
                            double distance = Math.sqrt(Math.pow(x - gridX, 2) + Math.pow(y - gridY, 2));
                            if (distance < minDistance) {
                                minDistance = distance;
                                newX = x;
                                newY = y;
                            }
                        }
                    }
                }
        
                // Snap to the nearest empty cell
                setTranslateX(newX);
                setTranslateY(newY);
            } else {
                // Snap to the original grid cell
                setTranslateX(gridX);
                setTranslateY(gridY);
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
