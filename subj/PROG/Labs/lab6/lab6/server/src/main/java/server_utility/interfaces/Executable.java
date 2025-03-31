package server_utility.interfaces;

import common_utility.ExecutionResponse;

public interface Executable {
    ExecutionResponse execute(String[] command);
}
