package utility;


import java.util.Scanner;

/**
 * Данный класс отвечает за работу стандартной консоли (ввод команд и вывод результата)
 *
 * @author ipka23
 */
public class StandartConsole implements Console {
    private static final String PROMPT = "$ ";
    private static final String SCRIPT_PROMPT = "> ";
    private static Scanner fileScanner = null;
    private static Scanner consoleScanner = new Scanner(System.in);


    /**
     * Метод для вывода obj.toString() в консоль
     *
     * @param obj Объект для вывода в консоль
     */
    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Метод для вывода obj.toString() + \n в консоль
     *
     * @param obj Объект для вывода в консоль
     */
    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Метод для вывода Error: obj.toString() + \n в консоль
     *
     * @param obj Ошибка для вывода в консоль
     */
    @Override
    public void printError(Object obj) {
        System.err.println("Error: " + obj);
    }

    /**
     * Метод вывода приглашения для ввода текущей консоли: "$ "
     */
    @Override
    public void printPrompt() {
        System.out.print(PROMPT);
    }

    /**
     * Метод для получения промпта для исполняемого скрипта
     *
     * @return промпт для исполняемого скрипта
     */
    @Override
    public String getScriptPrompt() {
        return SCRIPT_PROMPT;
    }

    /**
     * Метод для чтения следующей строки ввода
     * Если выбран сканер файла, проверка производится в файле, иначе в консоли.
     */
    @Override
    public String readln() {
        if (fileScanner == null) return consoleScanner.nextLine();
        return fileScanner.nextLine();
    }

    /**
     * Метод для проверки наличия следующей строки ввода
     * Если выбран сканер файла, проверка производится в файле, иначе в консоли.
     */
    @Override
    public boolean hasNextLine() throws IllegalStateException {
        if (fileScanner == null) return consoleScanner.hasNextLine();
        return fileScanner.hasNextLine();
    }

    /**
     * Метод для выбора сканера консоли вместо сканера файла
     */
    @Override
    public void selectConsoleScanner() {
        fileScanner = null;
    }

    /**
     * Метод для выбора сканера файла вместо сканера консоли
     */
    @Override
    public void selectFileScanner(Scanner scanner) {
        fileScanner = scanner;
    }


}