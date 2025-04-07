import common_entities.MusicBand;
import common_utility.network.Request;
import common_utility.network.Response;
import lombok.AllArgsConstructor;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

@AllArgsConstructor
public class Client {
    public static int PORT = 1123;
    private static Socket socket;
    private static Scanner userInput;
    private static ObjectInputStream inFromServer;
    private static ObjectOutputStream outToServer;
    private static String collectionFile;
    private static Collection<MusicBand> musicBandsCollection = new HashSet<>();
    private static String command;
    private static String arg;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        if (args.length == 0) {
            System.exit(1);
        } else {
            collectionFile = args[0];
        }

        try {
            run();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {
            socket.close();
            inFromServer.close();
            outToServer.close();
            userInput.close();
        }


    }
//    private SocketChannel channel;
//    private final String host;
//    private final int port;
//
//    public Client(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }
//    public void connect() throws IOException {
//        channel = SocketChannel.open();
//        channel.configureBlocking(false);
//        channel.connect(new InetSocketAddress(host, port));
//
//        while (!channel.finishConnect()) {
//            // Ожидание подключения
//        }
//    }

//    public static void sendRequest(Object o) throws IOException {
//        outToServer.writeObject(o);
//        outToServer.flush();
//    }

//    public static Response getResponse() throws IOException, ClassNotFoundException {
//        return (Response) inFromServer.readObject();
//    }

    public static void run() throws IOException, ClassNotFoundException {
        socket = new Socket("localhost", PORT);
        outToServer = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        outToServer.flush();
        inFromServer = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        sendCollectionFile(collectionFile);
        userInput = new Scanner(System.in);
        sendMessage();
    }

    private static void sendCollectionFile(String file) throws IOException, ClassNotFoundException {
        outToServer.writeObject(new Request(file));
        outToServer.flush();

        Response response = (Response) inFromServer.readObject();
        if (response.getExitStatus()) {
            System.out.print(response);
            System.exit(222);
        }

    }



//    private static void sendScriptFile(File file) throws IOException, ClassNotFoundException {
//        outToServer.writeObject(new Request(command, arg, file));
//        outToServer.flush();
//        Response response = (Response) inFromServer.readObject();
//        if (response.getExitStatus()) {
//            System.out.print(response);
//            System.exit(222);
//        }
//    }

    private static void sendScriptFile(String path) throws FileNotFoundException {
//        int bytes = 0;
        File scriptFile = new File(path);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(scriptFile))) {
//            outToServer.writeLong(scriptFile.length());

//            byte[] buffer = new byte[1024 * 1024];
//            while ((bytes = fileInputStream.read(buffer)) != -1) {
//                outToServer.write(buffer, 0, bytes);
//                outToServer.flush();
//            }
            StringBuilder scriptContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                scriptContent.append(line).append("\n");
            }
            scriptContent = new StringBuilder(scriptContent.substring(0, scriptContent.toString().length() - 1));
//            outToServer.writeObject(new Request(scriptContent.toString()));
            outToServer.writeObject(scriptContent.toString());
            outToServer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void sendMessage() {
        Response response;
        try {
            while (true) {
                Request request;
                Response prompt = (Response) inFromServer.readObject();
                System.out.print(prompt);
                String message = userInput.nextLine().trim();
                command = (message + " ").split(" ", 2)[0].trim().toLowerCase();
                arg = (message + " ").split(" ", 2)[1].trim();

                request = new Request(command, arg);

                outToServer.writeObject(request);
                outToServer.flush();

                if (command.equals("execute_script")) {
//                    request = new Request(command, arg, new File(arg));
//                    outToServer.writeObject(request);
//                    outToServer.flush();
                    sendScriptFile(arg);
                }

                if (message.isEmpty()) continue;
                response = (Response) inFromServer.readObject();
                if (response == null) continue;
                if (response.getExitStatus()) {
                    System.out.print(response);
                    System.exit(333);
                }
                else {
                    System.out.println(response);
                }


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}