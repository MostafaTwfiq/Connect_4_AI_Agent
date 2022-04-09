package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StartScreenController implements Initializable {

    @FXML
    private Button playBtn;

    @FXML
    private ComboBox<String> playerStartComboBox;

    @FXML
    private ComboBox<String> algorithmComboBox;

    private void setupComboBoxes() {
        playerStartComboBox.getItems().addAll("User", "Agent");
        playerStartComboBox.getSelectionModel().select(0);
        algorithmComboBox.getItems().addAll("Min max alpha-beta pruning", "Min max");
        algorithmComboBox.getSelectionModel().select(0);
    }

    private void setupPlayBtn() {
        playBtn.setOnAction(e -> {
            try {
                GameController gameController = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
                loader.setController(gameController);
                Main.newScreen(loader.load());
                gameController = loader.getController();
                gameController.play(playerStartComboBox.getValue().equals("User"), algorithmComboBox.getValue().equals("Min max alpha-beta pruning"));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupComboBoxes();
        setupPlayBtn();
    }
}
