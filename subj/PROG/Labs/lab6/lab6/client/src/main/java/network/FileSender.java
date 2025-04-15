package network;

import common_utility.network.Request;
import common_utility.network.Response;

import java.io.*;

public class FileSender {


    public static boolean sendScriptFile(String filename, ObjectOutputStream outToServer) {
        File scriptFile = new File(filename);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(scriptFile))) {
            StringBuilder scriptContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                scriptContent.append(line).append("\n");
            }
            scriptContent = new StringBuilder(scriptContent.substring(0, scriptContent.toString().length() - 1));
            Request request = new Request(scriptContent.toString());
            request.setFileName(filename);
            request.setScriptFile(scriptFile);
            outToServer.writeObject(request);
            outToServer.flush();
        } catch (FileNotFoundException e) {
            System.out.println("Файл \"" + filename + "\" не найден!");
//            System.exit(1);
            return false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static void sendCollectionFile(String file, ObjectOutputStream outToServer, ObjectInputStream inFromServer) throws IOException, ClassNotFoundException {
        outToServer.writeObject(new Request(file));
        outToServer.flush();

        Response response = (Response) inFromServer.readObject();
        if (response.getExitStatus()) {
            System.out.print(response.getMessage());
            System.exit(222);
        }
    }
}
