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

public class TileBox extends Pane {
    private SoundPlayer soundPlayer = new SoundPlayer();
    private boolean isDragging = false;
    private Point2D dragOffset;
    private Text tileText;
    //private GameGUI gameGUI = new GameGUI();
    private ArrayList<TileBox> tileBoxes = new ArrayList<>();

    public TileBox(Tile tile, ArrayList<TileBox> tileBoxes) {
        this.tileBoxes = tileBoxes;
        this.tileBoxes.add(this);
        setPrefSize(30, 40);
        Rectangle background = new Rectangle(30, 40);
        background.setArcWidth(10);
        background.setArcHeight(10);
        background.setFill(Color.GREY);
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
        }
    };

    private EventHandler<MouseEvent> mouseReleasedHandler = event -> {
        isDragging = false;
        soundPlayer.tileSound();
    };
}