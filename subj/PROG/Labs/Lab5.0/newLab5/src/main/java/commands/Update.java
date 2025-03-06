package commands;

import exceptions.AddBreak;
import managers.CollectionManager;
import models.MusicBand;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Данный класс отвечает за выполнение команды "update"
 *
 * @author ipka23
 */
public class Update extends Command {
    private final Console CONSOLE;
    private final CollectionManager COLLECTION_MANAGER;
    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Update(Console console, CollectionManager collectionManager) {
        super("update id", "обновить значение элемента коллекции, id которого равен заданному");
        this.CONSOLE = console;
        this.COLLECTION_MANAGER = collectionManager;
    }

    /**
     * Метод для выполнения команды
     *
     * @param args аргументы команды
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    public ExecutionResponse execute(String[] args) {
        if (args[1].trim().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        long id;
        try {
            id = Long.parseLong(args[1].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Неверный формат id!");
        }
        var band = COLLECTION_MANAGER.getMusicBandById(id);
        if (band == null || !COLLECTION_MANAGER.getMusicBands().contains(band)) {
            return new ExecutionResponse(false, "В коллекции нет элемента с таким id!");
        }
        MusicBand newBand;
        try {
            CONSOLE.println("--------------------------------Введите новые данные для MusicBand--------------------------------");
            newBand = Add.inputMusicBand(CONSOLE, id);
        } catch (AddBreak e) {
            return new ExecutionResponse(true, "Отмена ввода...");
        }
        band.update(newBand);
        return new ExecutionResponse(true, "Элемент с id = " + id + " был обновлён!");
    }
}
