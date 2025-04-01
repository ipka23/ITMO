package server_utility.interfaces;

import common_utility.network.Response;

public interface Executable {
    Response execute(String[] command);
}
