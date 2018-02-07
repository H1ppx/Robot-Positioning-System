package Socket;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server extends Thread implements NavSocket {

    private ServerSocket server;
    private Socket socket;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private ArrayList<XY> data = new ArrayList<>();

    /**
     *
     * @param ip IP address of your server
     * @param port Port used for server
     * @throws IOException
     */
    @Override
    public void startSocket(String ip, int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Server IP: " + ip);
        System.out.println("Server Port: " + port);
    }

    /**
     *
     * @param x
     * @param y
     * @throws IOException
     */
    public void addData(double x, double y) throws IOException {
        data.add(new XY(x,y));
    }

    /**
     *
     * @param dataIndex
     * @throws IOException
     */
    public void sendData(int dataIndex) throws IOException {
        socket = server.accept();
        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(data.get(dataIndex));
    }


    /**
     *
     * @throws IOException
     */
    @Override
    public void stopSocket() throws IOException {
        objectOutputStream.close();
        outputStream.close();
        socket.close();
        server.close();
    }

    /**
     *
     * @return
     * @throws UnknownHostException
     */
    @Override
    public String getIP() throws UnknownHostException {
        String address[] = InetAddress.getLocalHost().toString().split("/");
        return address[1];
    }

    /**
     *
     */
    public void run(){

        try {
            startSocket(getIP(),5800);
            while (true) {
                sendData(data.size()-1);
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

}
