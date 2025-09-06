package server_commands;

import common_utility.localization.LanguageManager;
import common_utility.network.Response;
import server_utility.Command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class ShowScripts extends Command {

    public ShowScripts() {
        super(LanguageManager.getBundle().getString("show_scripts"), LanguageManager.getBundle().getString("show_scriptsDescription"));
    }

    @Override
    public Response execute(String[] command) throws IOException, ClassNotFoundException {

        return new Response(false, "=====================");

    }
}
