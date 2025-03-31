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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add extends Command {
    private Console console;
    private CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public MusicBand inputMusicBand() {
        String name = inputName();
        Coordinates coordinates = inputCoordinates();
        Long numberOfParticipants = inputNumberOfParticipants();
        Long singlesCount = inputSinglesCount();
        Date establishmentDate = inputEstablishmentDate();
        MusicGenre musicGenre = inputMusicGenre();
        Album bestAlbum = inputAlbum();
        return new MusicBand(name, coordinates, numberOfParticipants, singlesCount, establishmentDate, musicGenre, bestAlbum);
    }

    private String inputName() {
        String name;
        while (true) {
            console.print("name: ");
            String input = console.nextLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            name = input;
            break;
        }
        return name;
    }

    private Coordinates inputCoordinates() {
        Integer x;
        float y;
        while (true) {
            console.print("coordinates.x: ");
            String input = console.nextLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                x = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                console.print("Введите число!\n");
            }
        }
        while (true) {
            console.print("coordinates.y: ");
            String input = console.nextLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                y = Float.parseFloat(input);
                if (y > 751) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                console.print("Введите число!\n");
            } catch (InputException e) {
                console.print("Максимальное значение поля \"y\" = 751!\n");
            }
        }
        return new Coordinates(x, y);
    }

    private Long inputNumberOfParticipants() {
        Long numberOfParticipants;
        while (true) {
            console.print("numberOfParticipants: ");
            String input = console.nextLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                numberOfParticipants = Long.parseLong(input);
                if (numberOfParticipants < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
            console.print("Введите число!\n");
            } catch (InputException e) {
                console.print("Значение поля \"numberOfParticipants\" должно быть больше 0!\n");
            }
        }
        return numberOfParticipants;
    }

    private Long inputSinglesCount() {
        Long singlesCount;
        while (true) {
            console.print("singlesCount: ");
            String input = console.nextLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                singlesCount = Long.parseLong(input);
                if (singlesCount < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                console.print("Введите число!\n");
            } catch (InputException e) {
                console.print("Значение поля \"singlesCount\" должно быть больше 0!\n");
            }
        }
        return singlesCount;
    }

    private Date inputEstablishmentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date establishmentDate;
        while (true) {
            console.print("establishmentDate (dd-MM-yyyy): ");
            String input = console.nextLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                establishmentDate = dateFormat.parse(input);
                break;
            } catch (ParseException e) {
                console.print("Неверный формат даты!\n");
            }
        }
        return establishmentDate;
    }

    private MusicGenre inputMusicGenre() {
        MusicGenre musicGenre;
        while (true) {
            console.print("musicGenre (" + MusicGenre.names() + "): ");
            String input = console.nextLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                musicGenre = MusicGenre.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                console.print("Нет такого MusicGenre!\n");
            }
        }
        return musicGenre;
    }

    private Album inputAlbum() {
        String name;
        Long tracks;
        long length;
        Double sales;
        while (true) {
            console.print("album.name: ");
            String input = console.nextLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            name = input;
            break;
        }
        while (true) {
            console.print("album.tracks: ");
            String input = console.nextLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                tracks = Long.parseLong(input);
                if (tracks < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                console.print("Введите число!\n");
            } catch (InputException e) {
                console.print("Значение поля \"tracks\" должно быть больше 0!\n");
            }
        }
        while (true) {
            console.print("album.length: ");
            String input = console.nextLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                length = Long.parseLong(input);
                if (length < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                console.print("Введите число!\n");
            } catch (InputException e) {
                console.print("Значение поля \"length\" должно быть больше 0!\n");
            }
        }
        while (true) {
            console.print("album.sales: ");
            String input = console.nextLine().trim();
            if (input.isEmpty()) continue;
            if (input.equals("exit")) throw new InputBreakException();
            try {
                sales = Double.parseDouble(input);
                if (sales < 0) throw new InputException();
                break;
            } catch (NumberFormatException e) {
                console.print("Введите число!\n");
            } catch (InputException e) {
                console.print("Значение поля \"sales\" должно быть больше 0!\n");
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
            return new ExecutionResponse(true, "MusicBand была успешно добавлена!");
        } catch (InputBreakException e) {
            return new ExecutionResponse(false, e.getMessage());
        }
    }
}