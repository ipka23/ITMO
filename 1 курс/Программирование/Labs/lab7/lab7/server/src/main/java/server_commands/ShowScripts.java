package server_commands;

import common_utility.network.Response;
import server_utility.Command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class ShowScripts extends Command {

    public ShowScripts() {
        super("show_scripts", "вывести имена всех доступных скриптов");
    }

    @Override
    public Response execute(String[] command) throws IOException, ClassNotFoundException {
        if (!command[1].isEmpty()) return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");

        return new Response(false, "=====================");

    }
}
