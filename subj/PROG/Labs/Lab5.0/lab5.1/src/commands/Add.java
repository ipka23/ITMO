package commands;

import managers.CollectionManager;
import models.Organization;
import utility.Ask;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public ExecutionResponse apply(String[] argument) {
        try {
            if (!argument[0].isEmpty()) return new ExecutionResponse(false,
                    "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

            console.println("* Создание нового organization:");
            Organization organization = Ask.askOrganization(console, collectionManager.getFreeId());

            if (organization != null && organization.validate()) {
                collectionManager.add(organization);
                return new ExecutionResponse("organization успешно добавлен!");
            } else
                return new ExecutionResponse(false, "Поля organization не валидны! organization не создан!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена...");
        }
    }
}