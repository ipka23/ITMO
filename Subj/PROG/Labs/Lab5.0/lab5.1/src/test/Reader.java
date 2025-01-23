package test;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Reader {
    private Scanner fileScanner;
    private Scanner defScanner;

    public Reader(Scanner fileScanner, Scanner defScanner) {
        this.fileScanner = fileScanner;
        this.defScanner = defScanner;
    }

    public String readln() throws NoSuchElementException, IllegalStateException {
        return (fileScanner != null ? fileScanner : defScanner).nextLine();
    }

    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner != null ? fileScanner : defScanner).hasNextLine();
    }

    public static void main(String[] args) {
        Scanner fileScanner = null;
        Scanner defScanner = new Scanner(System.in); // Чтение из консоли

        Reader reader = new Reader(fileScanner, defScanner);

        System.out.println("Введите строку:");
        while (reader.isCanReadln()) {
            System.out.println("Прочитано: " + reader.readln());
        }
    }
}

