package server_utility.interfaces;

import java.util.Scanner;

public interface ScriptExecutable {
    void setScanner(Scanner scanner);
    String getScriptPrompt();
}
