package server_utility.interfaces;

import common_utility.network.Response;

import java.io.IOException;

public interface Executable {
    Response execute(String[] command) throws IOException, ClassNotFoundException;
}
