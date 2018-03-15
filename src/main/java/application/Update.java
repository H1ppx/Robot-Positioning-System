package application;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import socket.Computer;
import vector.Vector2d;

import java.io.IOException;

public class Update implements Runnable{

    Computer myComputer;

    String address;
    XYChart.Series xySeries;

    public Update(String address, XYChart.Series xySeries){
        this.address = address;
        this.xySeries = xySeries;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(600);
                Platform.runLater(() -> xySeries.getData().clear());
                Platform.runLater(() -> {
                    try {
                        myComputer.startSocket(address,5800);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                Platform.runLater(() -> {
                    try {
                        Vector2d pos = myComputer.receiveData();
                        xySeries.getData().add(new XYChart.Data<>(pos.x,pos.y));
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
    }
}


