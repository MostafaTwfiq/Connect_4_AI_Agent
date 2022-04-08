package gui;
import algorithms.MiniMax;
import algorithms.MinimaxAlphaBeta;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.StateOperations;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        long state = -4496562564508441720l;
        StateOperations.printState(state);
        System.out.println();
        var v = MinimaxAlphaBeta.decision(state);
        StateOperations.printState(v.getKey());
        System.out.println(v.getValue());
//        System.exit(0);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        double runningTime = elapsedTime / 1000.0;
        System.out.println("Time: " + runningTime);
        System.out.println(v);

        startTime = System.currentTimeMillis();
        state = -4496562564508441720l;
        StateOperations.printState(state);
        System.out.println();
        v = MiniMax.decision(state);
        StateOperations.printState(v.getKey());
        System.out.println(v.getValue());
//        System.exit(0);
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        runningTime = elapsedTime / 1000.0;
        System.out.println("Time: " + runningTime);
        System.out.println(v);

//        launch();

        /*var minmax = new MiniMax(10);

        var node = minmax.max(0, 0);
        System.out.println("Final state:-");
        System.out.println(node.getScore());
        StateOperations.printState(node.getState());*/
        //MinimaxAlphaBeta.decision(0);

    }
}