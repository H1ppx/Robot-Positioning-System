import javafx.scene.chart.XYChart;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    private static Socket socket;
    private static String data;
    static double x;
    static double y;

    static ArrayList<XYChart.Data> positionLog = new ArrayList<XYChart.Data>();

    public static void start(String ip, int port) throws IOException {
        socket = new Socket(ip, port); //Legal: 5800-5810
    }

    public static void run() throws IOException {

        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        data =  dataInputStream.readUTF();
        System.out.println(data);
        String[]  parts = data.split(",");

        x = Double.parseDouble(parts[0]);
        y = Math.abs(Double.parseDouble(parts[1]));

        boundsCheck(x,0,323);
        boundsCheck(y,0,648);

        positionLog.add(new XYChart.Data(x,y));
        System.out.println(x+","+y);


        dataInputStream.close();
        inputStream.close();
        socket.close();

    }



    private static void boundsCheck(double n, double lowerBound, double upperBound){
        if(n<lowerBound){
            n = lowerBound;
        }else if(n>upperBound){
            n = upperBound;
        }
    }



}