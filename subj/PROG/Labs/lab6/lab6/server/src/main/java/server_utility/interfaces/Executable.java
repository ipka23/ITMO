package server_utility.interfaces;

import common_utility.network.ExecutionResponse;

public interface Executable {
    ExecutionResponse execute(String[] command);
}
