import utils.Update;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        sc.setMinSize(300,580);
        sc.setMaxSize(300,580);

        XYChart.Series currentPos   = new XYChart.Series();
        currentPos.setName("Position");
        sc.getData().addAll(currentPos);

        Scene scene  = new Scene(new Group());
        String stylesheetPath = "src\\main\\java\\application\\map.css";
        scene.getStylesheets().add((new File(stylesheetPath)).toURI().toURL().toExternalForm());
        final VBox vbox = new VBox();
        final HBox hbox = new HBox();

        final Button connectButton = new Button("Connect");

        connectButton.setOnAction(e -> {
            new Thread(new Update(currentPos)).start();  //TODO: Test Thread
        });

        hbox.setSpacing(10);

        vbox.getChildren().addAll(sc, hbox);
        hbox.getChildren().addAll(connectButton);
        hbox.setPadding(new Insets(10, 5, 10, 5));

        ((Group)scene.getRoot()).getChildren().add(vbox);
        stage.setScene(scene);
        stage.setWidth(340);
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
