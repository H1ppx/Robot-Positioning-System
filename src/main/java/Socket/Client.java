package Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client implements NavSocket {

    Socket socket;
    InputStream inputStream;
    ObjectInputStream objectInputStream;
    ArrayList<XY> data = new ArrayList<>();

    @Override
    public void startSocket(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Server IP: " + ip);
        System.out.println("Server Port: " + port);
    }

    public void receiveData() throws IOException, ClassNotFoundException {
        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
        data.add((XY) objectInputStream.readObject());
    }

    @Override
    public void stopSocket() throws IOException {
        objectInputStream.close();
        inputStream.close();
        socket.close();
    }

    @Override
    public String getIP() throws UnknownHostException {
        String address[] = InetAddress.getLocalHost().toString().split("/");
        return address[1];
    }
}
