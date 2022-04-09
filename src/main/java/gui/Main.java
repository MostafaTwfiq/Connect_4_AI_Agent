package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/startScreen.fxml"));
        scene = new Scene(fxmlLoader.load(), 640, 670);
        stage.setTitle("Connect 4");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void newScreen(Parent parent) {
        scene.setRoot(parent);
    }

    public static void main(String[] args) {
        launch();
    }
}