package application;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class TileBox extends Pane implements Comparable<TileBox> {
    private SoundPlayer soundPlayer = new SoundPlayer();
    private boolean isDragging = false;
    private Point2D dragOffset;
    private Text tileText;
    private int number;
    private ArrayList<TileBox> tileBoxes = new ArrayList<>();
    static Tile tile;
    private int ownerIndex;

    public TileBox(Tile tile, ArrayList<TileBox> tileBoxes, int ownerIndex) {
        number = tile.getNumber();
        this.tileBoxes = tileBoxes;
        this.tileBoxes.add(this);
        this.ownerIndex = ownerIndex;
        setPrefSize(30, 40);
        Rectangle background = new Rectangle(30, 40);
        background.setArcWidth(10);
        background.setArcHeight(10);
        background.setFill(Color.GREY);
        background.setOnMouseEntered(event -> {
            background.setStroke(Color.YELLOW); // set the stroke color to yellow when the mouse enters
        });
    
        background.setOnMouseExited(event -> {
            background.setStroke(null); // set the stroke color back to null when the mouse exits
        });
        setOnMousePressed(mousePressedHandler);
        setOnMouseDragged(mouseDraggedHandler);
        setOnMouseReleased(mouseReleasedHandler);

        // add the text to tile
        tileText = new Text();
        tileText.setX(10);
        tileText.setY(25);
        tileText.setText(String.valueOf(tile.getNumber()));
        tileText.setFill(getColorFromTile(tile)); // set text color to tile color
        tileText.setFont(Font.font(null, FontWeight.BOLD, 15)); // set font size to 12 bold
        tileText.setOnMouseEntered(event -> {
            getParent().setOnMouseEntered(background.getOnMouseEntered());
            getParent().setOnMouseExited(background.getOnMouseExited());
            background.getOnMouseEntered().handle(event);
        });
        
        tileText.setOnMouseExited(event -> {
            getParent().setOnMouseEntered(null);
            getParent().setOnMouseExited(null);
            background.getOnMouseExited().handle(event);
        });

        getChildren().add(background);
        getChildren().add(tileText);
    }

    public Tile getTile() {
        return tile; //getter method for the Tile object
    }
    public int getOwnerIndex() {
        //System.out.println("Owner index: " + ownerIndex);
        return ownerIndex;
    }
    public void setOwnerIndex(int i) {

    }

    @Override
    public int compareTo(TileBox other) {
        return Integer.compare(number, other.number);
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
        }
    };

    private EventHandler<MouseEvent> mouseReleasedHandler = event -> {
        isDragging = false;
        soundPlayer.tileSound();

        // Check if the tile is inside the specified area
        if (event.getSceneX() >= 120 && event.getSceneX() <= 750 &&
        event.getSceneY() >= 120 && event.getSceneY() <= 740) {
            //removeTileFromHand();
        } else {
            //tileBox.setTranslateX(initialTileX);
            //tileBox.setTranslateY(initialTileY);
            return;
        }
        event.consume();
        System.out.println(ownerIndex + " has played a tile on the board");
        
    };
}