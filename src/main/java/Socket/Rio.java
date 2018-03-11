package Socket;

import Vector.Vector2d;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Rio extends Thread implements NavSocket {

    private ServerSocket rio;
    private Socket socket;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;

    private double magnitude;
    private double angle;

    public void run(){

        try {
            startSocket(getIP(),5800);
            while (true) {
                socket = rio.accept();
                outputStream = socket.getOutputStream();
                objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(new Vector2d(magnitude,angle));
                Thread.sleep(500);
            }
        }catch (InterruptedException e){
            try {
                stopSocket();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void startSocket(String ip, int sendPort) throws IOException {
        rio = new ServerSocket(sendPort);
    }

    public void sendData(double magnitude, double angle){
        this.magnitude = magnitude;
        this.angle = angle;
    }

    @Override
    public void stopSocket() throws IOException {
        objectOutputStream.close();
        outputStream.close();
        socket.close();
        rio.close();
    }

    @Override
    public String getIP() throws UnknownHostException {
        String address[] = InetAddress.getLocalHost().toString().split("/");
        return address[1];
    }
}
