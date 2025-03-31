package server_commands;

import common_entities.Album;
import common_entities.Coordinates;
import common_entities.MusicBand;
import common_entities.MusicGenre;
import common_utility.ExecutionResponse;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.exceptions.InputBreakException;
import server_utility.exceptions.InputException;
import server_utility.interfaces.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add extends Command {
    private static BufferedReader inFromClient;
    private static ObjectOutputStream outToClient;
    private Console console;
    private CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    public Add(Console console, CollectionManager collectionManager, BufferedReader inFromClient, ObjectOutputStream outToClient) {
        super("add {element}", "добавить новый элемент в коллекцию");
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
            outToClient.writeObject(new ExecutionResponse(false, "name: "));
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
        ExecutionResponse response = new ExecutionResponse(false,"coordinates.x: ");
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
                response = new ExecutionResponse(false,"Введите число!");
            }
        }
        response = new ExecutionResponse(false,"coordinates.y: ");
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
                response = new ExecutionResponse(false,"Введите число!");
            } catch (InputException e) {
                response = new ExecutionResponse(false,"Максимальное значение поля \"y\" = 751!");
            }
        }
        return new Coordinates(x, y);
    }
    protected Long inputNumberOfParticipants() throws IOException {
        Long numberOfParticipants;
        ExecutionResponse response = new ExecutionResponse(false,"numberOfParticipants: ");
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
                response = new ExecutionResponse(false,"Введите число!");
            } catch (InputException e) {
                response = new ExecutionResponse(false,"Значение поля \"numberOfParticipants\" должно быть больше 0!");
            }
        }
        return numberOfParticipants;
    }

    protected Long inputSinglesCount() throws IOException {
        Long singlesCount;
        ExecutionResponse response = new ExecutionResponse(false,"singlesCount: ");
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
                response = new ExecutionResponse(false,"Введите число!");
            } catch (InputException e) {
                response = new ExecutionResponse(false,"Значение поля \"singlesCount\" должно быть больше 0!");
            }
        }
        return singlesCount;
    }
    protected Date inputEstablishmentDate() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date establishmentDate;
        ExecutionResponse response = new ExecutionResponse(false,"establishmentDate (dd-MM-yyyy): ");
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
                response = new ExecutionResponse(false,"Неверный формат даты!");
            }
        }
        return establishmentDate;
    }
    protected MusicGenre inputMusicGenre() throws IOException {
        MusicGenre musicGenre;
        ExecutionResponse response = new ExecutionResponse(false,"musicGenre (" + MusicGenre.names() + "): ");
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
                response = new ExecutionResponse(false,"Нет такого MusicGenre!");
            }
        }
        return musicGenre;
    }
    protected Album inputAlbum() throws IOException {
        String name;
        Long tracks;
        long length;
        Double sales;
        ExecutionResponse response = new ExecutionResponse(false,"album.name: ");
        while (true) {
            outToClient.writeObject(response);
            outToClient.flush();
            String input = inFromClient.readLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            name = input;
            break;
        }
        response = new ExecutionResponse(false,"album.tracks: ");
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
                response = new ExecutionResponse(false,"Введите число!");
            } catch (InputException e) {
                response = new ExecutionResponse(false,"Значение поля \"tracks\" должно быть больше 0!");
            }
        }
        response = new ExecutionResponse(false,"album.length: ");
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
                response = new ExecutionResponse(false,"Введите число!");
            } catch (InputException e) {
                response = new ExecutionResponse(false, "Значение поля \"length\" должно быть больше 0!");
            }
        }
        response = new ExecutionResponse(false,"album.sales: ");
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
                response = new ExecutionResponse(false,"Введите число!");
            } catch (InputException e) {
                response = new ExecutionResponse(false,"Значение поля \"sales\" должно быть больше 0!");
            }
        }
        return new Album(name, tracks, length, sales);
    }

    @Override
    public ExecutionResponse execute(String[] command) {
        if (!command[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        try {
            MusicBand musicBand = inputMusicBand();
            collectionManager.addMusicBand(musicBand);
            return new ExecutionResponse(false, "MusicBand была успешно добавлена!");
        } catch (InputBreakException | IOException e) {
            return new ExecutionResponse(false, e.getMessage());
        }
    }
}
