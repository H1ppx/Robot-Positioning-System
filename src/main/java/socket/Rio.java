package socket;

import vector.Vector2d;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Rio extends Thread {

    private ServerSocket rio;
    private Socket socket;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private Vector2d vector2d;

    public void run() {
        try {
            rio = new ServerSocket(5800);
            while (true) {
                socket = rio.accept();
                outputStream = socket.getOutputStream();
                objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(vector2d);
                Thread.sleep(500);
            }
        }catch (InterruptedException e){
            try {
                objectOutputStream.close();
                outputStream.close();
                socket.close();
                rio.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void send(Vector2d vector2d){
        this.vector2d = vector2d;
    }


}
