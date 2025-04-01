package server_commands;

import common_entities.Album;
import common_entities.Coordinates;
import common_entities.MusicBand;
import common_entities.MusicGenre;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.exceptions.InputBreakException;
import server_utility.exceptions.InputException;
import server_utility.interfaces.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add extends Command {
    private static ObjectInputStream inFromClient;
    private static ObjectOutputStream outToClient;
    private Console console;
    private CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    public Add(Console console, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
        Add.inFromClient = inFromClient;
        Add.outToClient = outToClient;
    }

    public MusicBand inputMusicBand() throws IOException {
        String name = inputName();
        Coordinates coordinates = inputCoordinates();
        Long numberOfParticipants = inputNumberOfParticipants();
        Long singlesCount = inputSinglesCount();
        Date establishmentDate = inputEstablishmentDate();
        MusicGenre musicGenre = inputMusicGenre();
        Album bestAlbum = inputAlbum();
        return new MusicBand(name, coordinates, numberOfParticipants, singlesCount, establishmentDate, musicGenre, bestAlbum);
    }

    protected String inputName() throws IOException {
        String name;
        while (true) {
            outToClient.writeObject(new Response(false, "Введите название музыкальной банды"));
            outToClient.flush();
            String input = inFromClient.readLine().trim();

            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            name = input;
            break;
        }
        return name;
    }
    protected Coordinates inputCoordinates() throws IOException {
        Integer x;
        float y;
        Response response = new Response(false,"Введите координату \"x\"");
        while (true) {
            outToClient.writeObject(response);
            outToClient.flush();
            String input = inFromClient.readLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                x = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                response = new Response(false,"Введите число!");
            }
        }
        response = new Response(false,"Введите координату \"y\"");
        while (true) {
            outToClient.writeObject(response);
            outToClient.flush();
            String input = inFromClient.readLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                y = Float.parseFloat(input);
                if (y > 751) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                response = new Response(false,"Введите число!");
            } catch (InputException e) {
                response = new Response(false,"Максимальное значение поля \"y\" = 751!");
            }
        }
        return new Coordinates(x, y);
    }
    protected Long inputNumberOfParticipants() throws IOException {
        Long numberOfParticipants;
        Response response = new Response(false,"Введите количество участников");
        while (true) {
            outToClient.writeObject(response);
            outToClient.flush();
            String input = inFromClient.readLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                numberOfParticipants = Long.parseLong(input);
                if (numberOfParticipants < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                response = new Response(false,"Введите число!");
            } catch (InputException e) {
                response = new Response(false,"Количество участников не может быть меньше 0!");
            }
        }
        return numberOfParticipants;
    }

    protected Long inputSinglesCount() throws IOException {
        Long singlesCount;
        Response response = new Response(false,"Введите количество синглов");
        while (true) {
            outToClient.writeObject(response);
            String input = inFromClient.readLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                singlesCount = Long.parseLong(input);
                if (singlesCount < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                response = new Response(false,"Введите число!");
            } catch (InputException e) {
                response = new Response(false,"Количество синглов не может быть меньше 0!");
            }
        }
        return singlesCount;
    }
    protected Date inputEstablishmentDate() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date establishmentDate;
        Response response = new Response(false,"Введите дату создания музыкальной банды в формате \"dd-MM-yyyy\"");
        while (true) {
            outToClient.writeObject(response);
            outToClient.flush();
            String input = inFromClient.readLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                establishmentDate = dateFormat.parse(input);
                break;
            } catch (ParseException e) {
                response = new Response(false,"Неверный формат даты!");
            }
        }
        return establishmentDate;
    }
    protected MusicGenre inputMusicGenre() throws IOException {
        MusicGenre musicGenre;
        Response response = new Response(false,"Введите музыкальный жанр из списка\n" + MusicGenre.names());
        Integer ordinal;
        while (true) {
            outToClient.writeObject(response);
            outToClient.flush();
            String input = inFromClient.readLine().trim();
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
                response = new Response(false,"Введите музыкальный жанр из списка!");
            }
        }
        return musicGenre;
    }
    protected Album inputAlbum() throws IOException {
        String name;
        Long tracks;
        long length;
        Double sales;
        Response response = new Response(false,"Введите имя альбома");
        while (true) {
            outToClient.writeObject(response);
            outToClient.flush();
            String input = inFromClient.readLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            name = input;
            break;
        }
        response = new Response(false,"Введите количество треков альбома");
        while (true) {
            outToClient.writeObject(response);
            outToClient.flush();
            String input = inFromClient.readLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                tracks = Long.parseLong(input);
                if (tracks < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                response = new Response(false,"Введите число!");
            } catch (InputException e) {
                response = new Response(false,"Количество треков альбома не может быть меньше 0!");
            }
        }
        response = new Response(false,"Введите длину альбома");
        while (true) {
            outToClient.writeObject(response);
            outToClient.flush();
            String input = inFromClient.readLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                length = Long.parseLong(input);
                if (length < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                response = new Response(false,"Введите число!");
            } catch (InputException e) {
                response = new Response(false, "Длина альбома не может быть меньше 0!");
            }
        }
        response = new Response(false,"Введите количество продаж альбома");
        while (true) {
            outToClient.writeObject(response);
            outToClient.flush();
            String input = inFromClient.readLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                sales = Double.parseDouble(input);
                if (sales < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                response = new Response(false,"Введите число!");
            } catch (InputException e) {
                response = new Response(false,"Количество продаж альбома не может быть меньше 0!");
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
            collectionManager.addMusicBand(musicBand);
            return new Response(false, "MusicBand была успешно добавлена!");
        } catch (InputBreakException | IOException e) {
            return new Response(false, e.getMessage());
        }
    }
}
