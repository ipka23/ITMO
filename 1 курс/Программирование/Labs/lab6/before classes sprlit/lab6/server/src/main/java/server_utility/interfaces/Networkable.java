package server_utility.interfaces;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Networkable {
    void  setObjectInputStream(ObjectInputStream in);
    void setObjectOutputStream(ObjectOutputStream out);
}
