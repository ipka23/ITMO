package network;

import common_utility.network.Request;
import common_utility.network.Response;

import java.io.*;

public class FileSender {


    public static void sendScriptFile(String filename, ObjectOutputStream outToServer) {
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
