package commands;

import managers.CollectionManager;
import models.Ask;
import models.MusicBand;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Данный класс отвечает за выполнение команды "add"
 *
 * @author ipka23
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Add(Console console, CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    /**
     * Метод для выполнения команды
     *
     * @param args аргументы команды
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        try {
            if (!args[1].trim().isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
            console.println("--------------------------------Cоздание новой MusicBand--------------------------------");
            MusicBand band = Ask.askMusicBand(console, collectionManager.getFreeId());
            if (band != null) {
                collectionManager.add(band);
                return new ExecutionResponse(true, "MusicBand была успешно добалена!");
            } else {
                return new ExecutionResponse(false, "Поля MusicBand не валидны, MusicBand не создана!");
            }
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена ввода...");
        }
    }
}
