package utility.consoles;

import managers.CollectionManager;
import utility.Invoker;
import utility.interfaces.Console;

public class AdvancedConsole extends StandartConsole implements Console {
    public AdvancedConsole(CollectionManager collectionManager, Invoker invoker) {
        super(collectionManager, invoker);
    }

}
