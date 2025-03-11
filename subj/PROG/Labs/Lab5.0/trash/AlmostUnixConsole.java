//package utility.consoles;
//
//import managers.CollectionManager;
//import managers.CommandManager;
//import utility.ExecutionResponse;
//import utility.Invoker;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//public class AlmostUnixConsole extends StandartConsole {
//    private String PROMPT = "$ ";
//    private BufferedReader reader;
//    private List<String> history;
//    private int historyIndex;
//    private CollectionManager collectionManager;
//    private CommandManager commandManager;
//    private Invoker invoker;
//
//    public AlmostUnixConsole(CollectionManager collectionManager, CommandManager commandManager, Invoker invoker) {
//        super(collectionManager, invoker);
//        this.reader = new BufferedReader(new InputStreamReader(System.in));
//        this.history = new LinkedList<>();
//        this.historyIndex = 0;
//        this.collectionManager = collectionManager;
//        this.invoker = invoker;
//        this.commandManager = commandManager;
//    }
//
//    @Override
//    public void setInvoker(Invoker invoker) {
//        this.invoker = invoker;
//    }
//    @Override
//    public void setCollectionManager(CollectionManager collectionManager) {
//        this.collectionManager = collectionManager;
//    }
//
//    public void setCommandManager(CommandManager commandManager) {
//        this.commandManager = commandManager;
//    }
//
//    @Override
//    public void printPrompt() {
//        System.out.print(PROMPT);
//    }
//
//    @Override
//    public String nextLine() {
//        if (fileScanner != null) {
//            return fileScanner.nextLine();
//        } else {
//            try {
//                String input = reader.readLine();
//                if (input != null && !input.trim().isEmpty()) {
//                    history.add(input);
//                    historyIndex = history.size();
//                }
//                return input;
//            } catch (IOException e) {
//                throw new RuntimeException("Ошибка чтения ввода пользователя!", e);
//            }
//        }
//    }
//
//
//    public void autoComplete(String prefix) {
////        for (String command : commandManager.getCommandsList()) {
//            if (command.startsWith(prefix)) {
//                System.out.println(command);
//            }
//        }
//    }
//
//    @Override
//    public void launch() {
//        collectionManager.chooseTypeOfCollection();
//        try {
//            ExecutionResponse commandStatus;
//            String input;
//            while (true) {
//                printPrompt();
//                input = nextLine();
//                if (input.isEmpty()) continue;
//                if (input.equals("exit")) {
//                    break;
//                } else if (input.startsWith("-")) {
//                    String prefix = input.substring("-".length());
//                    autoComplete(prefix);
//                } else {
//                    String[] userCommand = (input + " ").split(" ", 2);
//                    userCommand[0] = userCommand[0].toLowerCase().trim();
//                    userCommand[1] = userCommand[1].trim();
//                    commandStatus = invoker.execute(userCommand);
//                    System.out.println(commandStatus.getMessage());
//                }
//            }
//        } catch (NoSuchElementException e) {
//            System.err.println("Ошибка: " + e.getMessage());
//        }
//    }
//}