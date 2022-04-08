package gui;

import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import logic.SlotState;
import logic.StateOperations;
import logic.IComputerAgent;
import algorithms.agent;
public class controller implements Initializable {
    // constants
    private static final int TILE_SIZE = 80;
    private static final int COLUMNS = StateOperations.getColSize();
    private static final int ROWS = StateOperations.getRowSize();
    private static final int COMPUTER = SlotState.AGENT.getValue();
    public static final int PLAYER = SlotState.USER.getValue();
    // used components
    private Disc[][] grid = new Disc[COLUMNS][ROWS];
    public String player = "";
    @FXML
    public Button restart;
    @FXML
    public Pane discRoot = new Pane();
    @FXML
    public Label playerScore;
    @FXML
    public Label computerScore;
    @FXML
    public Label level;
    @FXML
    public Label strategy;
    @FXML
    public Pane connect4Pane = new Pane();
    public static Text textWinnerMessage = new Text();
    List<Rectangle> rectangles = new ArrayList<>();
    List<Circle> circles = new ArrayList<>();
    private IComputerAgent computerAgent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect4Pane.getChildren().add(discRoot);
        Shape gridShape = makeGrid();
        connect4Pane.getChildren().add(gridShape);
        rectangles = makeRec();
        circles = makeCircles();
        connect4Pane.getChildren().addAll(makeCircles());
        connect4Pane.getChildren().addAll(makeRec());

    }

    public void setSettings(String p, String s, int levels) {
        player = p;
        boolean withPruning = false;
        level.setText("" + levels);
        strategy.setText(s);
        if (s.equals("with alpha-beta pruning"))
            withPruning = true;
        computerAgent = new agent (withPruning, levels);
        start();
    }

    public void start() {
        initializeGame();
        if (player.equals("Computer")) {
            placeDisc(new Disc(false), computerAgent.getFirstMove(), false);
        }
    }



    public static class Disc extends Circle {
        private final boolean color;

        public Disc(boolean color) {
            super(TILE_SIZE / 2, color ? Color.RED : Color.YELLOW);
            this.color = color;

            setCenterX(TILE_SIZE / 2);
            setCenterY(TILE_SIZE / 2);
        }

        public boolean getColor() {
            return color;
        }

    }

    public void disablePane() {
        connect4Pane.setDisable(true);
    }

    public void enablePane() {
        connect4Pane.setDisable(false);
    }

    public void restart (ActionEvent event) {

        initializeGame();
        if (player.equals("Computer")) {
            placeDisc(new Disc(false), computerAgent.getFirstMove(), false);
        }

    }

    private void initializeGame() {
        disablePane();
        grid = new Disc[COLUMNS][ROWS];
        discRoot.getChildren().clear();
        computerScore.setText("" + 0);
        playerScore.setText("" + 0);
        computerAgent.restart();
        // initialize board in backend
        enablePane();
    }


    private Shape makeGrid() {
        Shape shape = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                Circle circle = new Circle(TILE_SIZE / 2);
                circle.setCenterX(TILE_SIZE / 2);
                circle.setCenterY(TILE_SIZE / 2);
                circle.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
                circle.setTranslateY(y * (TILE_SIZE + 5) + TILE_SIZE / 4);
                shape = Shape.subtract(shape, circle);
            }
        }

        Light.Distant light = new Light.Distant();
        light.setAzimuth(45.0);
        light.setElevation(40.0);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);
        shape.setFill(Color.BLUE);
        shape.setEffect(lighting);

        return shape;
    }


    private List<Circle> makeCircles() {
        List<Circle> list = new ArrayList<>();
        for (int x = 0; x < COLUMNS; x++) {
            final Circle disk = new Circle(TILE_SIZE / 2);
            disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.TRANSPARENT));
            disk.setOpacity(10);
            disk.setTranslateX(x * (TILE_SIZE + 5) + (TILE_SIZE / 2) + 20);
            disk.setTranslateY(0);
            final int column = x;

            disk.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.RED));
                }
            });

            disk.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.TRANSPARENT));
                }
            });

            disk.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    placeDisc(new Disc(true), column, true);
                    // //update the board at the backend
                }
            });
            list.add(disk);
        }
        return list;
    }

    private List<Rectangle> makeRec() {
        List<Rectangle> list = new ArrayList<>();
        for (int x = 0; x < COLUMNS; x++) {
            Rectangle rect = new Rectangle(TILE_SIZE, (ROWS + 0.5) * TILE_SIZE);
            rect.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
            rect.setFill(Color.TRANSPARENT);

            final int column = x;
            rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    placeDisc(new Disc(true), column, true);
                    // update the board at the backend
                }
            });

            list.add(rect);
        }
        return list;
    }

    private void makeColumns() {
        List<Rectangle> listR = new ArrayList<>();
        List<Circle> listC = new ArrayList<>();

        for (int x = 0; x < COLUMNS; x++) {
            Rectangle rect = new Rectangle(TILE_SIZE, (ROWS + 0.5) * TILE_SIZE);
            rect.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
            rect.setFill(Color.TRANSPARENT);
            final Circle disk = new Circle(TILE_SIZE / 2);
            disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.TRANSPARENT));
            disk.setOpacity(10);
            disk.setTranslateX(x * (TILE_SIZE + 5) + (TILE_SIZE / 2) + 20);
            disk.setTranslateY(0);
            final int column = x;
            // rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
            rect.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    rect.setFill(Color.rgb(200, 200, 50, 0.3));
                    disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.RED));
                }
            });
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    placeDisc(new Disc(true), column, true);
                    // update the board at the backend
                }
            });

            listR.add(rect);
            listC.add(disk);

        }
        List<Circle> list = new ArrayList<>();
        for (int x = 0; x < COLUMNS; x++) {
            final Circle disk = new Circle(TILE_SIZE / 2);
            disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.TRANSPARENT));
            disk.setOpacity(10);
            disk.setTranslateX(x * (TILE_SIZE + 5) + (TILE_SIZE / 2) + 20);
            disk.setTranslateY(0);
            final int column = x;

            disk.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.RED));
                }
            });

            disk.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.TRANSPARENT));
                }
            });

            disk.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    placeDisc(new Disc(true), column, true);
                    // //update the board at the backend
                }
            });
        }
    }

    private void placeDisc(Disc disc, int column, boolean playerTurn) {
        disablePane();
        int row = ROWS - 1;
        while (row >= 0) {
            if (!getDisc(column, row).isPresent())
                break;
            row--;
        }
        if (row < 0){
            enablePane();
            return;
        }
        grid[column][row] = disc;
        discRoot.getChildren().add(disc);
        disc.setTranslateX(column * (TILE_SIZE + 5) + TILE_SIZE / 4);

        final int currentRow = row;

        TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disc);
        animation.setToY(row * (TILE_SIZE + 5) + TILE_SIZE / 4);
        animation.setOnFinished(e -> {
            if (playerTurn) {

                // if the player played then this is the computer move
                placeDisc(new Disc(false), computerAgent.getNextMove(column), false);
                playerScore.setText("" + computerAgent.getPlayerScore());
                computerScore.setText("" + computerAgent.getComputerScore());
                // next turn is player

            }
            enablePane();
        });
        animation.play();

    }


    public Optional<Disc> getDisc(int column, int row) {
        if (column < 0 || column >= COLUMNS
                || row < 0 || row >= ROWS)
            return Optional.empty();

        return Optional.ofNullable(grid[column][row]);
    }

    public void home(ActionEvent event) {
        Main Scene = new Main();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/start.fxml"));
            Scene.setScene(event, root, "Connect4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
