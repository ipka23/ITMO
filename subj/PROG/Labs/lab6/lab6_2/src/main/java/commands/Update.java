package commands;

import entities.MusicBand;
import managers.CollectionManager;
import utility.Command;
import utility.ExecutionResponse;
import utility.exceptions.InputBreakException;
import utility.interfaces.Console;

/**
 * Данный класс отвечает за выполнение команды "update"
 *
 * @author ipka23
 */
public class Update extends Command {
    private final Console CONSOLE;
    private final CollectionManager collectionManager;
    private final Add add;
    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Update(Console console, CollectionManager collectionManager) {
        super("update id", "обновить значение элемента коллекции, id которого равен заданному");
        this.CONSOLE = console;
        this.collectionManager = collectionManager;
        this.add = new Add(console, collectionManager);
    }

    /**
     * Метод для выполнения команды
     *
     * @param command команда введенная пользователем
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    public ExecutionResponse execute(String[] command) {
        if (command[1].trim().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        long id;
        try {
            id = Long.parseLong(command[1].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Неверный формат id!");
        }
        MusicBand band = collectionManager.getMusicBandById(id);
        if (band == null || !collectionManager.getCollection().contains(band)) {
            return new ExecutionResponse(false, "В коллекции нет элемента с таким id!");
        }
        MusicBand newBand;
        try {
            CONSOLE.println("--------------------------------Введите новые данные для MusicBand--------------------------------");
            newBand = add.inputMusicBand();
        } catch (InputBreakException e) {
            return new ExecutionResponse(true, "Отмена ввода...");
        }
        band.update(newBand);
        return new ExecutionResponse(true, "Элемент с id = " + id + " был обновлён!");
    }
}