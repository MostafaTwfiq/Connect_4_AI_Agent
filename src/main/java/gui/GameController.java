package gui;

import algorithms.MiniMax;
import algorithms.MinimaxAlphaBeta;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import logic.Heuristic;
import logic.SlotState;
import logic.StateOperations;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public static class Disc extends Circle {
        public Disc(SlotState player) {
            super(TILE_SIZE / 2.0, player == SlotState.AGENT ? Color.rgb(255,193,204,1) : Color.rgb(128,128,128,1));
            setStroke(Color.BLACK);
            setCenterX(TILE_SIZE / 2.0);
            setCenterY(TILE_SIZE / 2.0);
        }

    }

    private static final int TILE_SIZE = 80;
    private static final int COLUMNS_SIZE = StateOperations.getColSize();
    private static final int ROWS_SIZE = StateOperations.getRowSize();
    private long currState = 0;
    private boolean algoWithAlphaBeta = true;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private Pane gamePane;

    @FXML
    private Label userScoreLbl;

    @FXML
    private Label agentScoreLbl;

    @FXML
    private Button restartBtn;

    @FXML
    private Button showTreeBtn;

    @FXML
    private Button backBtn;

    public void play(boolean userTurn, boolean algoWithAlphaBeta) {
        currState = 0;
        this.algoWithAlphaBeta = algoWithAlphaBeta;
        if (!userTurn)
            agentTurn();

    }

    private void setupButtons() {
        restartBtn.setOnAction(e -> {
            setGameBoard();
        });

        showTreeBtn.setOnAction(e -> {

        });

        backBtn.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/startScreen.fxml"));
                Main.newScreen(loader.load());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private List<Rectangle> createSelectColumns() {
        List<Rectangle> listR = new ArrayList<>();

        for (int col = 0; col < COLUMNS_SIZE; col++) {
            Rectangle rect = new Rectangle(TILE_SIZE, (ROWS_SIZE + 1) * TILE_SIZE);
            rect.setTranslateX(col * (TILE_SIZE + 5) + TILE_SIZE / 4.0);
            rect.setFill(Color.TRANSPARENT);
            rect.setCursor(Cursor.HAND);

            rect.setOnMouseEntered(e -> {
                rect.setFill(Color.rgb(228,228,228,0.6));
            });
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            int finalCol = col;
            rect.setOnMouseClicked(e -> placeDisc(new Disc(SlotState.USER), finalCol, true));

            listR.add(rect);
        }

        return listR;
    }

    private Shape createGrid() {
        Shape shape = new Rectangle((COLUMNS_SIZE + 1) * TILE_SIZE, (ROWS_SIZE + 1) * TILE_SIZE);

        for (int row = 0; row < ROWS_SIZE; row++) {
            for (int col = 0; col < COLUMNS_SIZE; col++) {
                Circle circle = new Circle(TILE_SIZE / 2.0);
                circle.setCenterX(TILE_SIZE / 2.0);
                circle.setCenterY(TILE_SIZE / 2.0);
                circle.setTranslateX(col * (TILE_SIZE + 5) + TILE_SIZE / 4.0);
                circle.setTranslateY(row * (TILE_SIZE + 5) + TILE_SIZE / 4.0);
                shape = Shape.subtract(shape, circle);
            }
        }

        shape.setFill(Color.WHITESMOKE);
        shape.setEffect(new DropShadow(7, Color.BLACK));

        return shape;
    }

    private void setGameBoard() {
        currState = 0;
        gamePane.getChildren().clear();
        gamePane.getChildren().addAll(createGrid());
        gamePane.getChildren().addAll(createSelectColumns());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setGameBoard();
        setupButtons();
    }

    private void placeDisc(Disc disc, int col, boolean userTurn) {
        parentPane.setDisable(true);

        int row = StateOperations.getRowSize() - StateOperations.numOfElementsAtCol(currState, col) - 1;
        if (row == StateOperations.getRowSize()) { //column is full
            parentPane.setDisable(false);
            return;
        }

        gamePane.getChildren().add(disc);
        disc.setTranslateX(col * (TILE_SIZE + 5) + TILE_SIZE / 4.0);

        TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disc);
        animation.setToY(row * (TILE_SIZE + 5) + TILE_SIZE / 4.0);
        animation.setOnFinished(e -> {
            if (userTurn) {
                currState = StateOperations.playAtCol(currState, col, SlotState.USER);
                userScoreLbl.setText("" + Heuristic.calculatePlayerActualScore(currState, SlotState.USER));
                agentTurn();
            } else {
                agentScoreLbl.setText("" + Heuristic.calculatePlayerActualScore(currState, SlotState.AGENT));
            }
            parentPane.setDisable(false);
        });
        animation.play();

    }

    private void agentTurn() {
        long newState = algoWithAlphaBeta ? MinimaxAlphaBeta.decision(currState).getKey() : MiniMax.decision(currState).getKey();
        int col;
        for (col = 0; col < StateOperations.getColSize(); col++) {
            if (StateOperations.numOfElementsAtCol(newState, col) != StateOperations.numOfElementsAtCol(currState, col))
                break;

        }

        placeDisc(new Disc(SlotState.AGENT), col, false);
        currState = newState;
    }

}
