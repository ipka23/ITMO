package test;

import models.Aboba;

import utility.Ask;
import utility.StandardConsole;
import utility.Console;
import java.util.Scanner;

public class AskTest {
    public static void main(String[] args) {
        // Инициализируем консоль
        Console console = new StandardConsole();

        // Устанавливаем ввод данных (имитация ввода)
        Scanner scanner = new Scanner("Dragon\n100\n204\nHAMMER\n");
        console.selectFileScanner(scanner);

        try {
            // Тестируем метод askAboba

            Aboba aboba = Ask.askAboba(console, 1);

            if (aboba != null) {
                // Проверяем корректность созданного объекта
                System.out.println("Aboba created:");
                System.out.println(aboba);
            } else {
                System.out.println("Aboba creation failed.");
            }
        } catch (Ask.AskBreak e) {
            System.out.println("Input was interrupted.");
        }

        // Возвращаем консоль к стандартному вводу
        console.selectConsoleScanner();
    }
}
