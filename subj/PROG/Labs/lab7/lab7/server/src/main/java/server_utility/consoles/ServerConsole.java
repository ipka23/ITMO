package server_utility.consoles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_managers.CollectionManager;

import java.util.Scanner;


public class ServerConsole implements Runnable {
    private static CollectionManager collectionManager;
    private static Logger log = LoggerFactory.getLogger("ServerConsole");

    public ServerConsole(CollectionManager collectionManager) {
        ServerConsole.collectionManager = collectionManager;
    }
    @Override
    public void run() {
        while (true) {
            System.out.print("Enter server command: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) continue;
            if (!input.equals("save")) continue;
            collectionManager.saveCollection();
            log.info("Saved collection");
        }
    }
}



