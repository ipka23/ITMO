package server_commands;

import common_entities.Album;
import common_entities.Coordinates;
import common_entities.MusicBand;
import common_entities.MusicGenre;
import common_utility.network.Request;
import common_utility.network.Response;
import lombok.Setter;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.consoles.ClientConsole;
import server_utility.exceptions.InputBreakException;
import server_utility.exceptions.InputException;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;


public class Add extends Command {
    @Setter
    private static ObjectInputStream inFromClient;
    @Setter
    private static ObjectOutputStream outToClient;
    private ClientConsole console;
    private CollectionManager collectionManager;
    private String ADD_PROMPT = "* ";
    @Setter
    private File scriptFile;
//    private Scanner scriptScanner = new Scanner(scriptFile);
    public Add(ClientConsole console, CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
        this.scriptFile = console.getScriptFile();
    }
    public Add(ClientConsole console, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient)  {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
        Add.inFromClient = inFromClient;
        Add.outToClient = outToClient;
        this.scriptFile = console.getScriptFile();
    }

    public MusicBand inputMusicBand() throws IOException, ClassNotFoundException {

        String name = inputName();
        Coordinates coordinates = inputCoordinates();
        Long numberOfParticipants = inputNumberOfParticipants();
        Long singlesCount = inputSinglesCount();
        LocalDate establishmentDate = inputEstablishmentDate();
        MusicGenre musicGenre = inputMusicGenre();
        Album bestAlbum = inputAlbum();
        String owner = collectionManager.getDatabaseManager().getUser().getUsername();
        return new MusicBand(owner, name, coordinates, numberOfParticipants, singlesCount, establishmentDate, musicGenre, bestAlbum);
    }

    protected String inputName() throws IOException, ClassNotFoundException {
        String name;
        Request request;
        String input;
        Response response = new Response(false, "Введите название музыкальной группы\n* ");
        console.sendResponse(null, outToClient);
        while (true) {
            console.sendResponse(response, outToClient);
            request = console.getRequest(inFromClient);
            input = request.getMessage();

            if (input.trim().isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            name = input;
            break;
        }
        return name;
    }

    protected Coordinates inputCoordinates() throws IOException, ClassNotFoundException {
        Integer x;
        float y;
        String input;
        Response response = new Response(false, "Введите координату \"x\"\n* ");
        console.sendResponse(null, outToClient);
        while (true) {
            console.sendResponse(response, outToClient);
            Request request = console.getRequest(inFromClient);
            input = request.getMessage();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                x = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                response = new Response(false, "Введите число!\n* ");
                console.sendResponse(null, outToClient);
            }
        }
        response = new Response(false, "Введите координату \"y\" (\"y\" <= 751)\n* ");
        console.sendResponse(null, outToClient);
        while (true) {
            console.sendResponse(response, outToClient);
            Request request = (Request) inFromClient.readObject();
            input = request.getMessage();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                y = Float.parseFloat(input);
                if (y > 751) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                response = new Response(false, "Введите число!\n* ");
                console.sendResponse(null, outToClient);
            } catch (InputException e) {
                response = new Response(false, "Максимальное значение поля \"y\" = 751!\n* ");
                console.sendResponse(null, outToClient);
            }

        }
        return new Coordinates(x, y);
    }


    protected Long inputNumberOfParticipants() throws IOException, ClassNotFoundException {
        Long numberOfParticipants;
        Response response = new Response(false,"Введите количество участников\n* ");
        console.sendResponse(null, outToClient);
        while (true) {
            console.sendResponse(response, outToClient);
            Request request = console.getRequest(inFromClient);
            String input = request.getMessage();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                numberOfParticipants = Long.parseLong(input);
                if (numberOfParticipants < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                response = new Response(false,"Введите число!\n* ");
                console.sendResponse(null, outToClient);
            } catch (InputException e) {
                response = new Response(false,"Количество участников не может быть меньше 0!\n* ");
                console.sendResponse(null, outToClient);
            }
        }
        return numberOfParticipants;
    }

    protected Long inputSinglesCount() throws IOException, ClassNotFoundException {
        Long singlesCount;
        Response response = new Response(false,"Введите количество синглов\n* ");
        console.sendResponse(null, outToClient);
        while (true) {
            console.sendResponse(response, outToClient);
            Request request = console.getRequest(inFromClient);
            String input = request.getMessage();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                singlesCount = Long.parseLong(input);
                if (singlesCount < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                response = new Response(false,"Введите число!\n* ");
                console.sendResponse(null, outToClient);
            } catch (InputException e) {
                response = new Response(false,"Количество синглов не может быть меньше 0!\n* ");
                console.sendResponse(null, outToClient);
            }
        }
        return singlesCount;
    }
    protected LocalDate inputEstablishmentDate() throws IOException, ClassNotFoundException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate establishmentDate;
        Response response = new Response(false,"Введите дату создания музыкальной банды в формате \"yyyy-dd-MM\"\n* ");
        console.sendResponse(null, outToClient);
        while (true) {
            console.sendResponse(response, outToClient);
            Request request = console.getRequest(inFromClient);
            String input = request.getMessage();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                establishmentDate = LocalDate.parse(input);
                break;
            } catch (Exception e) {
                response = new Response(false,"Неверный формат даты!\nВведите дату в формате \"yyyy-MM-dd\"\n*");
                console.sendResponse(null, outToClient);
            }
        }
        return establishmentDate;
    }
    protected MusicGenre inputMusicGenre() throws IOException, ClassNotFoundException {
        MusicGenre musicGenre;
        Response response = new Response(false,"Введите музыкальный жанр из списка\n" + MusicGenre.names() + "\n* ");
        Integer ordinal;
        console.sendResponse(null, outToClient);
        while (true) {
            console.sendResponse(response, outToClient);
            Request request = console.getRequest(inFromClient);
            String input = request.getMessage();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                musicGenre = MusicGenre.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
            }
            try {
                ordinal = Integer.parseInt(input);
                musicGenre = MusicGenre.values()[ordinal];
                break;
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                response = new Response(false,"Такого музыкального жанра нет в списке!\n" + MusicGenre.names() + "\n* ");
                console.sendResponse(null, outToClient);
            }
        }
        return musicGenre;
    }
    protected Album inputAlbum() throws IOException, ClassNotFoundException {
        String name;
        Long tracks;
        long length;
        Double sales;
        Response response = new Response(false,"Введите имя альбома\n* ");
        console.sendResponse(null, outToClient);
        while (true) {
            console.sendResponse(response, outToClient);
            Request request = console.getRequest(inFromClient);
            String input = request.getMessage();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            name = input;
            break;
        }
        response = new Response(false,"Введите количество треков альбома\n* ");
        console.sendResponse(null, outToClient);
        while (true) {
            console.sendResponse(response, outToClient);
            Request request = console.getRequest(inFromClient);
            String input = request.getMessage();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                tracks = Long.parseLong(input);
                if (tracks < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                response = new Response(false,"Введите число!\n* ");
                console.sendResponse(null, outToClient);
            } catch (InputException e) {
                response = new Response(false,"Количество треков альбома не может быть меньше 0!\n* ");
                console.sendResponse(null, outToClient);
            }
        }
        response = new Response(false,"Введите длину альбома\n* ");
        console.sendResponse(null, outToClient);
        while (true) {
            console.sendResponse(response, outToClient);
            Request request = console.getRequest(inFromClient);
            String input = request.getMessage();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                length = Long.parseLong(input);
                if (length < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                response = new Response(false,"Введите число!\n* ");
                console.sendResponse(null, outToClient);
            } catch (InputException e) {
                response = new Response(false, "Длина альбома не может быть меньше 0!\n* ");
                console.sendResponse(null, outToClient);
            }
        }
        response = new Response(false,"Введите количество продаж альбома\n* ");
        console.sendResponse(null, outToClient);
        while (true) {
            console.sendResponse(response, outToClient);
            Request request = console.getRequest(inFromClient);
            String input = request.getMessage();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                sales = Double.parseDouble(input);
                if (sales < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                response = new Response(false,"Введите число!\n* ");
                console.sendResponse(null, outToClient);
            } catch (InputException e) {
                response = new Response(false,"Количество продаж альбома не может быть меньше 0!\n* ");
                console.sendResponse(null, outToClient);
            }
        }
        return new Album(name, tracks, length, sales);
    }

    @Override
    public Response execute(String[] command) {
        if (!command[1].isEmpty())
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        try {
            MusicBand musicBand = inputMusicBand();
            return collectionManager.addMusicBand(musicBand);
        } catch (InputBreakException | IOException e) {
            return new Response(false, e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
