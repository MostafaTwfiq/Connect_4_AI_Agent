package gui;

import algorithms.MiniMax;
import algorithms.MinimaxAlphaBeta;
import algorithms.TreeNode;
import com.fxgraph.cells.RectangleCell;
import com.fxgraph.cells.TriangleCell;
import com.fxgraph.edges.CorneredEdge;
import com.fxgraph.edges.DoubleCorneredEdge;
import com.fxgraph.edges.Edge;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.ICell;
import com.fxgraph.graph.Model;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Heuristic;
import logic.SlotState;
import logic.StateOperations;

import java.net.URL;
import java.util.*;

import com.fxgraph.edges.CorneredEdge.*;
import com.fxgraph.layout.AbegoTreeLayout;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import org.abego.treelayout.Configuration.Location;

public class GameController implements Initializable {

    public static class Disc extends Circle {
        public Disc(SlotState player) {
            super(TILE_SIZE / 2.0, player == SlotState.AGENT ? Color.rgb(255,193,204,1) : Color.rgb(128,128,128,1));
            setStroke(Color.BLACK);
            setCenterX(TILE_SIZE / 2.0);
            setCenterY(TILE_SIZE / 2.0);
        }

    }

    private TreeNode root;
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
            try {
                start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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


    public void start(Stage stage) throws Exception {
        Graph graph = new Graph();
        // Add content to graph
        populateGraph(graph);

        // Layout nodes
        AbegoTreeLayout layout = new AbegoTreeLayout(200, 20, Location.Bottom);
        graph.layout(layout);

        // Configure interaction buttons and behavior

        // Display the graph
        stage.setScene(new Scene(new BorderPane(graph.getCanvas())));
        stage.show();
    }

    private void populateGraph(Graph graph) {
        if(this.root ==null)
            return;
        final Model model = graph.getModel();
        graph.beginUpdate();
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<ICell> cellQueue = new LinkedList<>();
        var rootCell = new TriangleCell();
        graph.getGraphic(rootCell).setMaxSize(7,7);
        nodeQueue.add(this.root);
        cellQueue.add(rootCell);
        model.addCell(rootCell);
        var ind = 0;
        var v = root.getChildren().size();
        while (!nodeQueue.isEmpty()){
            var node = nodeQueue.remove();
            var cell = cellQueue.remove();
            for (var c : node.getChildren()){
                var cCell = new TriangleCell();
                graph.getGraphic(cCell).setMaxSize(7,7);
                Edge edgePC = new Edge(cell, cCell);
                edgePC.textProperty().set(Double.toString(Math.round(c.getVal() * 100.0)/ 100.0));
                nodeQueue.add(c);
                cellQueue.add(cCell);
                model.addCell(cCell);
                model.addEdge(edgePC);
            }
            ind++;
            if(ind == v+1)
                break;
        }
//        final ICell cellA = new TriangleCell();
//        final ICell cellB = new RectangleCell();
//        final ICell cellC = new RectangleCell();
//        final ICell cellD = new RectangleCell();
//        final ICell cellE = new RectangleCell();
//        final ICell cellF = new RectangleCell();
//        final ICell cellG = new RectangleCell();
//        model.addCell(cellA);
//        model.addCell(cellB);
//        model.addCell(cellC);
//        model.addCell(cellD);
//        model.addCell(cellE);
//        model.addCell(cellF);
//        model.addCell(cellG);
//
//        final Edge edgeAB = new Edge(cellA, cellB);
//        edgeAB.textProperty().set("Directed Edge");
//        model.addEdge(edgeAB);
//
//        final CorneredEdge edgeAC = new CorneredEdge(cellA, cellC, Orientation.HORIZONTAL);
//        edgeAC.textProperty().set("Directed CorneredEdge");
//        model.addEdge(edgeAC);
//
//        final DoubleCorneredEdge edgeBE = new DoubleCorneredEdge(cellB, cellE, Orientation.HORIZONTAL);
//        edgeBE.textProperty().set("Directed DoubleCorneredEdge");
//        model.addEdge(edgeBE);
//
//        final Edge edgeCF = new Edge(cellC, cellF);
//        edgeCF.textProperty().set("Directed Edge");
//        model.addEdge(edgeCF);
//
//
//        model.addEdge(cellC, cellG);
//
//        model.addEdge(cellB, cellD);

        graph.endUpdate();
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
        var value = algoWithAlphaBeta ? MinimaxAlphaBeta.decision(currState) : MiniMax.decision(currState);
        var newState = value.getKey();
        this.root = value.getValue();
        int col;
        for (col = 0; col < StateOperations.getColSize(); col++) {
            if (StateOperations.numOfElementsAtCol(newState, col) != StateOperations.numOfElementsAtCol(currState, col))
                break;

        }

        placeDisc(new Disc(SlotState.AGENT), col, false);
        currState = newState;
    }

}
