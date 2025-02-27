package test;

import utility.Console;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TestConsole implements Console {
    private final ByteArrayInputStream inputStream;
    private final ByteArrayOutputStream outputStream;
    private final PrintStream printStream;

    public TestConsole(String input) {
        this.inputStream = new ByteArrayInputStream(input.getBytes());
        this.outputStream = new ByteArrayOutputStream();
        this.printStream = new PrintStream(outputStream);
    }

    @Override
    public void print(Object obj) {
        printStream.print(obj);
    }

    @Override
    public void println(Object obj) {
        printStream.println(obj);
    }

    @Override
    public String readln() {
        Scanner scanner = new Scanner(inputStream);
        return scanner.nextLine();
    }

    @Override
    public boolean isCanReadln() {
        return inputStream.available() > 0;
    }

    @Override
    public void printError(Object obj) {
        System.err.println(obj);
    }

    @Override
    public void printTable(Object obj1, Object obj2) {
        printStream.printf(" %-35s%-1s%n", obj1, obj2);
    }

    @Override
    public void prompt() {
        printStream.print("$ ");
    }

    @Override
    public String getPrompt() {
        return "$ ";
    }

    @Override
    public void selectFileScanner(Scanner obj) {
        // Not implemented in this test
    }

    @Override
    public void selectConsoleScanner() {
        // Not implemented in this test
    }

    // Optionally, expose the output stream for testing purposes
    public String getOutput() {
        return outputStream.toString();
    }
}
