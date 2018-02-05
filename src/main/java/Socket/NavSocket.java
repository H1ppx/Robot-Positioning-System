package Socket;

import java.io.IOException;
import java.net.UnknownHostException;

public interface NavSocket {

    public void startSocket(String ip, int port) throws IOException;

    public void stopSocket() throws IOException;

    public String getIP() throws UnknownHostException;

}
