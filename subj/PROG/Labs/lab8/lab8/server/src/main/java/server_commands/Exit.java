package server_commands;

import common_utility.network.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_managers.CollectionManager;
import server_utility.Command;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Set;
import server_utility.Server;
/**
 * Данный класс отвечает за выполнение команды "exit"
 *
 * @author ipka23
 */
public class Exit extends Command {
    private final CollectionManager collectionManager;
    private Logger log = LoggerFactory.getLogger("Exit");
    private Set<ObjectOutputStream> outputStreams;
    private ObjectOutputStream out;
    /**
     *
     * Конструктор
     */
    public Exit(CollectionManager collectionManager, ObjectOutputStream out) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.collectionManager = collectionManager;
        outputStreams = Server.outputStreams;
        this.out = out;
    }


    @Override
    public Response execute(String[] command) {
        collectionManager.saveCollection();
        Server.outputStreams.remove(out);
        log.info("Client has disconnected!");
        return new Response(true, """
                 :::,
                 '::::'._
                   '.    '.                        __.,,.
                     '.    '.                _..-'''':::"
                       \\     \\,.--""\""--.,-''      _:'
                   /\\   \\  .               .    .-'
                  /  \\   \\                   ':'
                 /    \\  :                     :
                /      \\:                       :
                \\       :                       :
                 \\      :      ,--,         ,-,  :
                  \\    :      |(_):|       |():| :
                   \\   :     __'--'   __    '-'_  :
                    \\  :    /  \\      \\/      / \\ :
                     \\  :  (    )             \\_/ :
                  .-'' . :  \\__/   '--''--'      :
                  \\  . .-:'.                   .:
                   \\' :| :  '-.__      ___...-' :
                    \\::|:        ''''''          '.
                  .,:::':  :                       '.
                   \\::\\:   :       Завершение       '._
                    \\::    :     / работы...   '-._     '.
                     \\:    :    /              .   :-._ :-'
                      :    :   /               :   :  ''
                      :   .'   )'.             :   :
                       :  :  .'   '.          :   :
                        : '..'      :      _.' _.:
                         '._        :..---'\\'''  _)
                            '':---''_)      '-'-'
                               '-'-'
                """);
    }
}