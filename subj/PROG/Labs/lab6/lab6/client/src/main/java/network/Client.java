package network;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.Socket;

@Getter
@Setter
public class Client {
    public int port;
    private String hostName;
    private Socket socket;
    private String collectionFile;

    public Client(int port, String hostName, String collectionFile) throws IOException {
        this.port = port;
        this.hostName = hostName;
        this.collectionFile = collectionFile;
        this.socket = new Socket(hostName, port);

    }
}