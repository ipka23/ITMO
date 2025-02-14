package models;

import utility.Console;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.NoSuchElementException;

public class Ask {
    public static class AskBreak extends Exception {}

    public static MusicBand askMusicBand(Console console, long id) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("name: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (name.isEmpty()) continue;
                if (!hasNoDigits(name)) continue;
                break;
            }
            var coordinates = askCoordinates(console);
            Long numberOfParticipants = askNumberOfParticipants(console);
            Long singlesCount = askSinglesCount(console);
            Date establishmentDate = askEstablishmentDate(console);
            var musicGenre = askMusicGenre(console);
            var bestAlbum = askAlbum(console);

            return new MusicBand(name, coordinates, numberOfParticipants, singlesCount, establishmentDate, musicGenre, bestAlbum); //id!
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            Integer x;
            while (true){
                console.print("coordinates.x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Integer.parseInt(line);
                        break;
                    } catch (NumberFormatException e) {}
                }
            }
            float y;
            while (true){
                console.print("coordinates.y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    y = Float.parseFloat(line);
                    if (y > 751) continue;
                    break;
                } catch (NumberFormatException e) {}
            }
            return new Coordinates(x, y);
        } catch (Exception e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Long askNumberOfParticipants(Console console) throws AskBreak {
        try {
            Long numberOfParticipants;
            while (true){
                console.print("numberOfParticipants: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    numberOfParticipants = Long.parseLong(line);
                    if (numberOfParticipants <= 0) continue;
                    break;
                } catch (NumberFormatException e) {}
            }
            return numberOfParticipants;
        } catch (Exception e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Long askSinglesCount(Console console) throws AskBreak {
        try {
            Long singlesCount;
            while (true){
                console.print("singlesCount: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    singlesCount = Long.parseLong(line);
                    if (singlesCount <= 0) continue;
                    break;
                } catch (NumberFormatException e) {}
            }
            return singlesCount;
        } catch (Exception e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    public static Date askEstablishmentDate(Console console) throws AskBreak {
        try {
            Date establishmentDate;
            while (true){
                console.print("data-time (dd.MM.yyyy): ");
                var line = console.readln().trim();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    establishmentDate = dateFormat.parse(line); // ????????????? Thu Aug 03 00:00:00 MSD 2006
                    break;
                } catch (DateTimeParseException e) {}
            }
            return establishmentDate;
        } catch (Exception e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static MusicGenre askMusicGenre(Console console) throws AskBreak {
        try {
            MusicGenre musicGenre;
            while (true){
                console.print("musicGenre (" + MusicGenre.names() + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    musicGenre = MusicGenre.valueOf(line);
                    break;
                } catch (IllegalArgumentException e) {} // | NullPointerException
            }
            return musicGenre;
        } catch (Exception e) { // NoSuchElementException | IllegalStateException e
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Album askAlbum(Console console) throws AskBreak {
        try {
            String name;
            while (true){
                console.print("album.name: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                name = line;
                if (!hasNoDigits(name)) continue;
                break;
            }
            Long tracks;
            while (true){
                console.print("album.tracks: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    tracks = Long.parseLong(line);
                    if (tracks <= 0) continue;
                    break;
                } catch (NumberFormatException e) {}
            }
            long length;
            while (true){
                console.print("album.length: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    length = Long.parseLong(line);
                    if (length <= 0) continue;
                    break;
                } catch (NumberFormatException e) {}
            }
            Double sales;
            while (true){
                console.print("album.sales: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    sales = Double.parseDouble(line);
                    if (sales <= 0) continue;
                    break;
                } catch (NumberFormatException e) {}
            }
            return new Album(name, tracks, length, sales);
        } catch (Exception e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    private static boolean hasNoDigits(String str) {
        return str.replaceAll("[0-9]", "").equals(str);
    }
}
