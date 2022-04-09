package gui;

import algorithms.MiniMax;
import algorithms.MinimaxAlphaBeta;
import algorithms.TreeNode;
import com.fxgraph.edges.Edge;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.StateOperations;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Main extends Application {

    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
//
//        System.out.println("MinimaxAlphaBeta At Depth 10 ");
//        long startTime = System.currentTimeMillis();
//        long state = -4496562564508441720l;
//        var v = MinimaxAlphaBeta.decision(state);
//        long stopTime = System.currentTimeMillis();
//        long elapsedTime = stopTime - startTime;
//        double runningTime = elapsedTime / 1000.0;
//        Queue<TreeNode> nodeQueue = new LinkedList<>();
//        nodeQueue.add(v.getValue());
//        var count = 1;
//        while (!nodeQueue.isEmpty()) {
//            var node = nodeQueue.remove();
//            for (var c : node.getChildren()) {
//                nodeQueue.add(c);
//                count++;
//            }
//        }
//        System.out.println("Time MinimaxAlphaBeta : " + runningTime);
//        System.out.println("Expanded Nodes MinimaxAlphaBeta : " + count);
//        System.out.println("**************************************************************************");
//        System.out.println("Minimax At Depth 7");
//        startTime = System.currentTimeMillis();
//        state = -4496562564508441720l;
//        v = MiniMax.decision(state);
//        stopTime = System.currentTimeMillis();
//        elapsedTime = stopTime - startTime;
//        runningTime = elapsedTime / 1000.0;
//        nodeQueue = new LinkedList<>();
//        nodeQueue.add(v.getValue());
//         count = 1;
//        while (!nodeQueue.isEmpty()) {
//            var node = nodeQueue.remove();
//            for (var c : node.getChildren()) {
//                nodeQueue.add(c);
//                count++;
//            }
//        }
//        System.out.println("Time MiniMax : " + runningTime);
//        System.out.println("Expanded Nodes Minimax : " + count);

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