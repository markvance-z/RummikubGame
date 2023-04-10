package application;

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
        greenRectangle.setX(100); // x-coordinate of the top-left corner of the rectangle
        greenRectangle.setY(105); // y-coordinate of the top-left corner of the rectangle
        greenRectangle.setWidth(400); // width of the rectangle
        greenRectangle.setHeight(570); // height of the rectangle
        greenRectangle.setFill(Color.GREEN); // set the fill color to green
        ButtonsAndText.root.getChildren().add(greenRectangle); // add the rectangle to the Pane

        //make rectangles for draw area players 1 - 4
        Rectangle drawBox1 = new Rectangle();
        drawBox1.setX(440); // x-coordinate of the top-left corner of the rectangle
        drawBox1.setY(690); // y-coordinate of the top-left corner of the rectangle
        drawBox1.setWidth(60); // width of the rectangle
        drawBox1.setHeight(60); // height of the rectangle
        drawBox1.setFill(Color.rgb(211,211,211)); // set the fill color to grey
        ButtonsAndText.root.getChildren().add(drawBox1); // add the rectangle to the Pane

        Rectangle drawBox2 = new Rectangle();
        drawBox2.setX(510); // x-coordinate of the top-left corner of the rectangle
        drawBox2.setY(105); // y-coordinate of the top-left corner of the rectangle
        drawBox2.setWidth(60); // width of the rectangle
        drawBox2.setHeight(60); // height of the rectangle
        drawBox2.setFill(Color.rgb(211,211,211)); // set the fill color to grey
        ButtonsAndText.root.getChildren().add(drawBox2); // add the rectangle to the Pane

        Rectangle drawBox3 = new Rectangle();
        drawBox3.setX(100); // x-coordinate of the top-left corner of the rectangle
        drawBox3.setY(30); // y-coordinate of the top-left corner of the rectangle
        drawBox3.setWidth(60); // width of the rectangle
        drawBox3.setHeight(60); // height of the rectangle
        drawBox3.setFill(Color.rgb(211,211,211)); // set the fill color to grey
        ButtonsAndText.root.getChildren().add(drawBox3); // add the rectangle to the Pane

        Rectangle drawBox4 = new Rectangle();
        drawBox4.setX(30); // x-coordinate of the top-left corner of the rectangle
        drawBox4.setY(615); // y-coordinate of the top-left corner of the rectangle
        drawBox4.setWidth(60); // width of the rectangle
        drawBox4.setHeight(60); // height of the rectangle
        drawBox4.setFill(Color.rgb(211,211,211)); // set the fill color to grey
        ButtonsAndText.root.getChildren().add(drawBox4); // add the rectangle to the Pane
        
        // Add buttons to a VBox
        Button addButton = new Button("Draw Tile");
        addButton.setOnAction(event -> buttonsAndText.drawTile());

        Button resetButton = new Button("Reset Game");
        resetButton.setOnAction(event -> buttonsAndText.resetGame());

        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(event -> buttonsAndText.startGame());

        Button nextPlayerButton = new Button("Next Player");
        nextPlayerButton.setOnAction(event -> buttonsAndText.nextPlayer());

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
        buttonsAndText.currentPlayerText = new Text();
        buttonsAndText.currentPlayerText.setFill(Color.ORANGE);
        buttonsAndText.currentPlayerText.setFont(Font.font(null, null, FontPosture.REGULAR, 18));

        buttonsAndText.tilesRemainText = new Text();
        buttonsAndText.tilesRemainText.setFill(Color.ORANGE);
        buttonsAndText.tilesRemainText.setFont(Font.font(null, null, FontPosture.REGULAR, 18));

        VBox textBox = new VBox(buttonsAndText.currentPlayerText, buttonsAndText.tilesRemainText);
        textBox.setAlignment(Pos.TOP_LEFT);
        textBox.setPadding(new Insets(10));
        textBox.setLayoutX(510);
        textBox.setLayoutY(750);


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
    }
    public static void main(String[] args) {
        launch(args);
    }
}
