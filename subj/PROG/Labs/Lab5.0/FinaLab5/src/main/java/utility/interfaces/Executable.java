package utility.interfaces;

import utility.ExecutionResponse;

public interface Executable {
    ExecutionResponse execute(String[] command);
}
