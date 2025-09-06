package server_utility.interfaces;

import server_managers.CollectionManager;
import server_utility.Invoker;


public interface Console {
    void setInvoker(Invoker invoker);
    void setCollectionManager(CollectionManager collectionManager);
    String nextLine();
}