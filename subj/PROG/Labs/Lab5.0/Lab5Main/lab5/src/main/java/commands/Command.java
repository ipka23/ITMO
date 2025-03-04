package commands;

import utility.ExecutionResponse;

/**
 * Данный абстрактный класс отвечает за описание команды и ее методов
 *
 * @author ipka23
 */
public abstract class Command {
    public final String name;
    public final String description;

    /**
     * Конструктор
     *
     * @param command     имя команды
     * @param description описание команды
     */
    public Command(String command, String description) {
        this.name = command;
        this.description = description;
    }

    /**
     * Абстрактный метод выполнения команды
     *
     * @param args аргументы команды
     * @return объект ExecutionResponse с результатом выполнения команды
     */
    public abstract ExecutionResponse execute(String[] args);

    /**
     * Метод для получения имени команды
     *
     * @return имя команды
     */
    public String getName() {
        return name;
    }

    /**
     * Метод для получения описания команды
     *
     * @return описание команды
     */
    public String getDescription() {
        return description;
    }
}