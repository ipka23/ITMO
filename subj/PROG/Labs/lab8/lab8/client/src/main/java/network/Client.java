package network;

import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

@Getter
@Setter
public class Client {
    public int port;
    private String hostName;
    private Socket socket;
    private String collectionFile;

    public Client(int port, String hostName, Socket socket)  {
        this.port = port;
        this.hostName = hostName;
        this.socket = socket;
    }
}