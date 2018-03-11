package Receiver;

import Socket.Computer;
import Vector.Vector2d;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Map extends Application {

    Computer myComputer;

    @Override public void start(Stage stage) throws IOException {
        stage.setTitle("FRC-PS");
        final NumberAxis xAxis = new NumberAxis(0, 323, 323);
        final NumberAxis yAxis = new NumberAxis(0, 648,648);
        final ScatterChart<Number,Number> sc = new
                ScatterChart<Number,Number>(xAxis,yAxis);
        sc.setTitle("FRC-Positioning-System Test ALPHA");

        XYChart.Series currentPos   = new XYChart.Series();

        currentPos.setName("Position");

        sc.getData().addAll(currentPos);
        Scene scene  = new Scene(sc, 319, 630);
        scene.getStylesheets().add("ps.css");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(600);
                    Platform.runLater(() -> currentPos.getData().clear());
                    Platform.runLater(() -> {
                        try {
                            myComputer.startSocket("roborio-555-frc.local",5800);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    Platform.runLater(() -> {
                        try {
                            Vector2d pos = myComputer.receiveData();
                            currentPos.getData().add(new XYChart.Data<>(pos.x,pos.y));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        updateThread.setDaemon(true);
        updateThread.start();

    }

    public static void main(String[] args){
        launch(args);
    }

}

