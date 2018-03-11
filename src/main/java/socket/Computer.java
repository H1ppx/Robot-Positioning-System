package socket;

import vector.Vector2d;

import java.io.*;
import java.net.Socket;

public class Computer {

    private Socket socketReceive;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;

    public void startSocket(String ip, int port) throws IOException {
        socketReceive = new Socket(ip, port);
    }

    public Vector2d receiveData() throws IOException, ClassNotFoundException {
        inputStream = socketReceive.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
        return (Vector2d) objectInputStream.readObject();
    }

    public void stopSocket() throws IOException {
        objectInputStream.close();
        inputStream.close();
        socketReceive.close();
    }

}
