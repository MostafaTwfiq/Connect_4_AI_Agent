package gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.*;
import gui.controller;

import java.io.IOException;
import java.util.List;

public class startPage {

    @FXML
    public Label warning;
    @FXML
    public TextField input;
    @FXML
    public ComboBox<String> playerTurn;
    @FXML
    public ComboBox<String> strategy;
@FXML
    public void Start(ActionEvent event) throws IOException {
        Main Scene = new Main();

            if(input.getText().equals("")) {
                return;
            }
            int val = Integer.parseInt(input.getText());
            if (val >= 0) {
                // warning.setVisible(false);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Game.fxml"));
                Parent root = loader.load();
                controller c = loader.getController();
                Scene.setScene(event, root, "Connect4");
                if (playerTurn.getValue() == null) playerTurn.setValue("Player");
                if (strategy.getValue() == null) strategy.setValue("with alpha-beta pruning");
                c.setSettings(playerTurn.getValue(), strategy.getValue() , val);


            }
    }

    //Main.main(args);

}
