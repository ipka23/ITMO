package network;

import common_utility.network.Request;

import java.awt.desktop.PreferencesEvent;
import java.io.*;

public class FileSender {


    public static void sendScriptFile(String filename, ObjectOutputStream outToServer) throws IOException {
        File scriptFile = new File(filename);
        if (filename.isEmpty()) return;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/" + filename.trim()))) {
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

            Request request = new Request();
            request.setFlag(false);
            request.setFileName(filename);
            request.setScriptFile(scriptFile);

            outToServer.writeObject(request);
            outToServer.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
