package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javafx.stage.Stage;

//import java.util.ArrayList;

public class GameGUI extends Application {

    //private ArrayList<Tile> tiles;
    //private ArrayList<TileBox> tileBoxes = new ArrayList<>();
    private ButtonsAndText buttonsAndText = new ButtonsAndText();

    public Pane getRoot() {
    return ButtonsAndText.root;
    }

    @Override
    public void start(Stage primaryStage) {
        ButtonsAndText.root = new Pane();
        ButtonsAndText.root.setPrefSize(900, 900);
        ButtonsAndText.root.setStyle("-fx-background-color: black;");

        
        //make rectangle for play area
        Rectangle greenRectangle = new Rectangle();
        greenRectangle.setX(120); // x-coordinate of the top-left corner of the rectangle
        greenRectangle.setY(120); // y-coordinate of the top-left corner of the rectangle
        greenRectangle.setWidth(660); // width of the rectangle
        greenRectangle.setHeight(660); // height of the rectangle
        greenRectangle.setFill(Color.GREEN); // set the fill color to green
        ButtonsAndText.root.getChildren().add(greenRectangle); // add the rectangle to the Pane

        // Add buttons to a VBox
        Button drawTileButton = new Button("Draw Tile");
        drawTileButton.setOnAction(event -> {
            ArrayList<Tile> currentPlayerHand = ButtonsAndText.getCurrentPlayerHand();
            buttonsAndText.drawTile(currentPlayerHand);
        });

        Button resetButton = new Button("Reset Game");
        resetButton.setOnAction(event -> buttonsAndText.resetGame());

        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(event -> buttonsAndText.startGame());

        Button nextPlayerButton = new Button("Next Player");
        nextPlayerButton.setOnAction(event -> buttonsAndText.nextPlayer());

        Button sortTilesButton = new Button("Sort Tiles");
        sortTilesButton.setOnAction(event -> {
            ArrayList<Tile> currentPlayerHand = ButtonsAndText.getCurrentPlayerHand();
            buttonsAndText.sortPlayerTiles(currentPlayerHand);
        });

        //Player actions box
        VBox actionButtonBox = new VBox(10, drawTileButton, sortTilesButton, nextPlayerButton);
        actionButtonBox.setAlignment(Pos.TOP_LEFT);
        actionButtonBox.setPadding(new Insets(10));
        actionButtonBox.setLayoutX(780);
        actionButtonBox.setLayoutY(780);

        //Game buttons box
        VBox gameButtonBox = new VBox(10, resetButton, startGameButton);
        gameButtonBox.setAlignment(Pos.TOP_LEFT);
        gameButtonBox.setPadding(new Insets(10));
        gameButtonBox.setLayoutX(780);
        gameButtonBox.setLayoutY(5);

        //Current Player Index
        buttonsAndText.currentPlayerText = new Text();
        buttonsAndText.currentPlayerText.setFill(Color.BLACK);
        buttonsAndText.currentPlayerText.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 18));

        //Tiles Remain Text
        buttonsAndText.tilesRemainText = new Text();
        buttonsAndText.tilesRemainText.setFill(Color.BLACK);
        buttonsAndText.tilesRemainText.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 18));

        VBox textBox = new VBox(buttonsAndText.currentPlayerText, buttonsAndText.tilesRemainText);
        textBox.setAlignment(Pos.TOP_RIGHT);
        textBox.setPadding(new Insets(20));
        textBox.setLayoutX(550);
        textBox.setLayoutY(710);

        // Add both VBoxes to a new Pane that is on top of the root Pane
        Pane controlsPane = new Pane();
        controlsPane.setPrefSize(60, 90 );
        controlsPane.getChildren().addAll(actionButtonBox, gameButtonBox, textBox);
        buttonsAndText.updateCurrentPlayerText();
        Tiles.checkTiles();
        ButtonsAndText.root.getChildren().add(controlsPane);
        
        Scene scene = new Scene(ButtonsAndText.root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rummikub");
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    public static void main(String[] args) {
        launch(args);
    }
}