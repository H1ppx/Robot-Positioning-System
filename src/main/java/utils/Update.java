package utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;

public class Update implements Runnable{

    XYChart.Series xySeries;

    public Update(XYChart.Series xySeries){
        this.xySeries = xySeries;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(600);
                Platform.runLater(() -> xySeries.getData().clear());
                Platform.runLater(() -> {
                    xySeries.getData().add(new XYChart.Data<>(
                            SmartDashboard.getNumber("RPS X", 0),
                            SmartDashboard.getNumber("RPS Y", 0)));
                });

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


