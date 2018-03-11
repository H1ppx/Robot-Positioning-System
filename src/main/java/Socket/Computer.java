package Socket;

import Vector.Vector2d;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Computer implements NavSocket {

    private Socket socketReceive;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;

    @Override
    public void startSocket(String ip, int port) throws IOException {
        socketReceive = new Socket(ip, port);
    }

    public Vector2d receiveData() throws IOException, ClassNotFoundException {
        inputStream = socketReceive.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
        return (Vector2d) objectInputStream.readObject();
    }


    @Override
    public void stopSocket() throws IOException {
        objectInputStream.close();
        inputStream.close();
        socketReceive.close();
    }

    @Override
    public String getIP() throws UnknownHostException {
        String address[] = InetAddress.getLocalHost().toString().split("/");
        return address[1];
    }
}
