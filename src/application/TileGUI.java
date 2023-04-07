package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javafx.stage.Stage;

import java.util.ArrayList;

import java.awt.Point;

public class TileGUI extends Application {
    private ArrayList<Tile> tiles;
    private int currentPlayerIndex = 1;
    private Text currentPlayerText;
    private Text tilesRemainText;
    private Pane root;
    private ArrayList<TileBox> tileBoxes = new ArrayList<>();

    private Point[] drawPositions = {
        new Point(440, 690),        //player 1 draw location
        new Point(510, 105),       //player 2 draw location
        new Point(100, 30),          //player 3 draw location
        new Point(30, 615)           //player 4 draw location
    };

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        root.setPrefSize(900, 900);
        root.setStyle("-fx-background-color: black;");

                
        //make rectangle for play area
        Rectangle greenRectangle = new Rectangle();
        greenRectangle.setX(100); // x-coordinate of the top-left corner of the rectangle
        greenRectangle.setY(105); // y-coordinate of the top-left corner of the rectangle
        greenRectangle.setWidth(400); // width of the rectangle
        greenRectangle.setHeight(570); // height of the rectangle
        greenRectangle.setFill(Color.GREEN); // set the fill color to green
        root.getChildren().add(greenRectangle); // add the rectangle to the Pane

        //make rectangles for draw area players 1 - 4
        Rectangle drawBox1 = new Rectangle();
        drawBox1.setX(440); // x-coordinate of the top-left corner of the rectangle
        drawBox1.setY(690); // y-coordinate of the top-left corner of the rectangle
        drawBox1.setWidth(60); // width of the rectangle
        drawBox1.setHeight(60); // height of the rectangle
        drawBox1.setFill(Color.rgb(211,211,211)); // set the fill color to grey
        root.getChildren().add(drawBox1); // add the rectangle to the Pane

        Rectangle drawBox2 = new Rectangle();
        drawBox2.setX(510); // x-coordinate of the top-left corner of the rectangle
        drawBox2.setY(105); // y-coordinate of the top-left corner of the rectangle
        drawBox2.setWidth(60); // width of the rectangle
        drawBox2.setHeight(60); // height of the rectangle
        drawBox2.setFill(Color.rgb(211,211,211)); // set the fill color to grey
        root.getChildren().add(drawBox2); // add the rectangle to the Pane

        Rectangle drawBox3 = new Rectangle();
        drawBox3.setX(100); // x-coordinate of the top-left corner of the rectangle
        drawBox3.setY(30); // y-coordinate of the top-left corner of the rectangle
        drawBox3.setWidth(60); // width of the rectangle
        drawBox3.setHeight(60); // height of the rectangle
        drawBox3.setFill(Color.rgb(211,211,211)); // set the fill color to grey
        root.getChildren().add(drawBox3); // add the rectangle to the Pane

        Rectangle drawBox4 = new Rectangle();
        drawBox4.setX(30); // x-coordinate of the top-left corner of the rectangle
        drawBox4.setY(615); // y-coordinate of the top-left corner of the rectangle
        drawBox4.setWidth(60); // width of the rectangle
        drawBox4.setHeight(60); // height of the rectangle
        drawBox4.setFill(Color.rgb(211,211,211)); // set the fill color to grey
        root.getChildren().add(drawBox4); // add the rectangle to the Pane

        //create the tiles
        Tiles rummikub = new Tiles();
        tiles = rummikub.getTiles();
        
        // Add buttons to a VBox
        Button addButton = new Button("Draw Tile");
        addButton.setOnAction(event -> drawTile());

        Button resetButton = new Button("Reset Game");
        resetButton.setOnAction(event -> resetGame());

        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(event -> startGame());

        Button nextPlayerButton = new Button("Next Player");
        nextPlayerButton.setOnAction(event -> nextPlayer());

        //Player actions box
        VBox actionButtonBox = new VBox(10, addButton, nextPlayerButton);
        actionButtonBox.setAlignment(Pos.TOP_LEFT);
        actionButtonBox.setPadding(new Insets(10));
        actionButtonBox.setLayoutX(510);
        actionButtonBox.setLayoutY(675);

        //Game buttons box
        VBox gameButtonBox = new VBox(10, resetButton, startGameButton);
        gameButtonBox.setAlignment(Pos.TOP_LEFT);
        gameButtonBox.setPadding(new Insets(10));
        gameButtonBox.setLayoutX(510);
        gameButtonBox.setLayoutY(5);

        // Add text to a VBox
        currentPlayerText = new Text();
        currentPlayerText.setFill(Color.ORANGE);
        currentPlayerText.setFont(Font.font(null, null, FontPosture.REGULAR, 18));

        tilesRemainText = new Text();
        tilesRemainText.setFill(Color.ORANGE);
        tilesRemainText.setFont(Font.font(null, null, FontPosture.REGULAR, 18));

        VBox textBox = new VBox(currentPlayerText, tilesRemainText);
        textBox.setAlignment(Pos.TOP_LEFT);
        textBox.setPadding(new Insets(10));
        textBox.setLayoutX(510);
        textBox.setLayoutY(750);


        // Add both VBoxes to a new Pane that is on top of the root Pane
        Pane controlsPane = new Pane();
        controlsPane.setPrefSize(60, 90 );
        controlsPane.getChildren().addAll(actionButtonBox, gameButtonBox, textBox);
        updateCurrentPlayerText();
        checkTiles();
        root.getChildren().add(controlsPane);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rummikub");
        primaryStage.show();
    }

    
    //METHODS FOR ACTION BUTTONS ETC
    //METHODS FOR GAME BUTTONS
    //METHODS FOR TEXTBOX

    private void startGame() {
        if(!tiles.isEmpty()) {
        // Give each player 17 tiles and line them up
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 14; j++) {
                    Tile tile = tiles.remove(0);
                    TileBox tileBox = new TileBox(tile);
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

    private void drawTile() {
        // check if there are any tiles left in the ArrayList
        if (!tiles.isEmpty()) {
            Tile tile = tiles.remove(0); // remove the first tile from the ArrayList
            TileBox tileBox = new TileBox(tile);
            Point drawPosition = drawPositions[currentPlayerIndex-1];
            tileBox.setTranslateX(drawPosition.getX());
            tileBox.setTranslateY(drawPosition.getY());
            root.getChildren().add(tileBox);
            tileBoxes.add(tileBox);
            System.out.println("Player "+ currentPlayerIndex + " has drawn a tile");
            checkTiles(); // call checkTiles() method to print the number of remaining tiles
        }
    }

    public void checkTiles() {
        System.out.println("Tiles Remaining: " + tiles.size());
        tilesRemainText.setText("Tiles Remaining: " + tiles.size());
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

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex % 4 + 1);
        updateCurrentPlayerText();
        System.out.println("Current Player: " + currentPlayerIndex);
    }

    private void updateCurrentPlayerText() {
        currentPlayerText.setText("Current Player: " + currentPlayerIndex);
    }

    private Point getInitialPosition(int playerIndex, int tileIndex) {
        int x = 0, y = 0;
        switch (playerIndex) {
            case 0: // Bottom
                x = 120 + 20 * tileIndex;
                y = 690;
                break;
            case 1: // Right
                x = 520;
                y = 630 - 30 * tileIndex;
                break;
            case 2: // Top
                x = 460 - 20 * tileIndex;
                y = 60;
                break;
            case 3: // Left
                x = 60;
                y = 120 + 30 * tileIndex;
                break;
        }
        return new Point(x, y);
    }

    //Create class for the tiles to be able to drag and snap
    private class TileBox extends Pane {

        private boolean isDragging = false;
        private Point2D dragOffset;
        private Text tileText;

        public TileBox(Tile tile) {
            setPrefSize(20, 30);
            Rectangle background = new Rectangle(20, 30);
            background.setArcWidth(10);
            background.setArcHeight(10);
            background.setFill(Color.GREY);
            setOnMousePressed(mousePressedHandler);
            setOnMouseDragged(mouseDraggedHandler);
            setOnMouseReleased(mouseReleasedHandler);
    
            // add the text to tile
            tileText = new Text();
            tileText.setX(4);
            tileText.setY(15);
            tileText.setText(String.valueOf(tile.getNumber()));
            tileText.setFill(getColorFromTile(tile)); // set text color to tile color
            tileText.setFont(Font.font(null, FontWeight.BOLD, 10)); // set font size to 12 bold

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
            double cellWidth = 20;
            double cellHeight = 30;
        
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
