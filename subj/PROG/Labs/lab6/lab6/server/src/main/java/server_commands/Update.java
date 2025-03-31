package server_commands;

import common_entities.MusicBand;
import common_utility.ExecutionResponse;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.exceptions.InputBreakException;
import server_utility.interfaces.Console;

import java.io.IOException;

/**
 * Данный класс отвечает за выполнение команды "update"
 *
 * @author ipka23
 */
public class Update extends Command {
    private final Console console;
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
        this.console = console;
        this.collectionManager = collectionManager;
        this.add = new Add(console, collectionManager);
    }

    /**
     * Метод для выполнения команды
     *
     * @param command команда введенная пользователем
     * @return объект utility.ExecutionResponse, содержащий результат выполнения команды
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
            console.println("--------------------------------Введите новые данные для MusicBand--------------------------------");
            newBand = add.inputMusicBand();
        } catch (InputBreakException e) {
            return new ExecutionResponse(true, "Отмена ввода...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        band.update(newBand);
        return new ExecutionResponse(false, "Элемент с id = " + id + " был обновлён!");
    }
}