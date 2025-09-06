package server_commands;

import common_utility.network.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_managers.CollectionManager;
import server_utility.Command;

/**
 * Данный класс отвечает за выполнение команды "exit"
 *
 * @author ipka23
 */
public class Exit extends Command {
    private final CollectionManager collectionManager;
    private Logger log = LoggerFactory.getLogger("Exit");
    /**
     * Конструктор
     */
    public Exit(CollectionManager collectionManager) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(String[] command) {
        collectionManager.saveCollection();
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