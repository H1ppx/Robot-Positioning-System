package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Map extends Application {

    @Override public void start(Stage stage) throws IOException {
        stage.setTitle("FRC-PS");
        final NumberAxis xAxis = new NumberAxis(0, 323, 0);
        final NumberAxis yAxis = new NumberAxis(0, 648,0);
        final ScatterChart<Number,Number> sc = new
                ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setOpacity(0);
        yAxis.setOpacity(0);
        sc.setTitle("v0.1 Pre Alpha");
        sc.setPrefSize(200,600);

        XYChart.Series currentPos   = new XYChart.Series();
        currentPos.setName("Position");
        sc.getData().addAll(currentPos);

        Scene scene  = new Scene(new Group());
        String stylesheetPath = "src\\main\\java\\application\\map.css";
        scene.getStylesheets().add((new File(stylesheetPath)).toURI().toURL().toExternalForm());
        final VBox vbox = new VBox();
        final HBox hbox = new HBox();


        final Label label = new Label("Target IP:");
        final TextField textField = new TextField("");
        textField.setPromptText("Only Editable Once");
        final Label statusLabel = new Label("Connected:");
        Circle statusConfirmation = new Circle(10, Paint.valueOf("Red"));

        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Runnable r = new Update(textField.getText(), currentPos);
//                new Thread(r).start();  //TODO: Test Thread

                textField.setEditable(false);
                statusConfirmation.setFill(Paint.valueOf("Green"));
            }
        });

        hbox.setSpacing(15);

        vbox.getChildren().addAll(sc, hbox);
        hbox.getChildren().addAll(label, textField, statusLabel, statusConfirmation);
        hbox.setPadding(new Insets(10, 10, 10, 10));

        ((Group)scene.getRoot()).getChildren().add(vbox);
        stage.setScene(scene);
        stage.setWidth(390);
        stage.setHeight(700);
        stage.setX(0);
        stage.setY(0);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args){
        launch(args);

    }

}
