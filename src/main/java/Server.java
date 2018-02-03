import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{

    ServerSocket server;
    Socket socket;
    OutputStream outputStream;
    DataOutputStream dataOutputStream;

    public double xOut;
    public double yOut;
    public ArrayList<String> oldData = new ArrayList<String>();

    public void run(){

        try {
            startServer(5800);
            while (true) {
                send(xOut,yOut);
                Thread.sleep(500);
            }
        }catch (InterruptedException e){
            try {
                closeServer();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void startServer(int port) throws IOException {
        server = new ServerSocket(port); //Legal: 5800-5810
        System.out.println("Server ready........");
    }

    public void send(double x, double y) throws IOException {
        socket = server.accept();
        outputStream = socket.getOutputStream();
        dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(x+","+y);
        oldData.add(x+","+y);
    }


    public void closeServer() throws IOException {
        dataOutputStream.close();
        outputStream.close();
        socket.close();
        server.close();
    }

}
