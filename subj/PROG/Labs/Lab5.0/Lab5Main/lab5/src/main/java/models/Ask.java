
package models;

import utility.Console;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * Данный класс содержит методы для взаимодействия с пользователем и получения данных для создания MusicBand;*
 *
 * @author ipka23
 */
public class Ask {
    /**
     * Исключение для прерывания процесса запроса данных
     */
    public static class AskBreak extends Exception {
    }

    /**
     * Метод для запроса данных для создания объекта MusicBand
     *
     * @param console объект Console для взаимодействия с пользователем
     * @param id идентификатор музыкальной группы
     * @return новый объект MusicBand
     * @throws AskBreak если пользователь вводит "exit"
     */
    public static MusicBand askMusicBand(Console console, long id) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("name: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                if (!hasLetters(line)) continue;
                name = line;
                break;
            }
            var coordinates = askCoordinates(console);
            Long numberOfParticipants = askNumberOfParticipants(console);
            Long singlesCount = askSinglesCount(console);
            Date establishmentDate = askEstablishmentDate(console);
            MusicGenre musicGenre = askMusicGenre(console);
            Album bestAlbum = askAlbum(console);

            return new MusicBand(id, name, coordinates, numberOfParticipants, singlesCount, establishmentDate, musicGenre, bestAlbum);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    /**
     * Метод для запроса данных для создания объекта Coordinates
     *
     * @param console интерфейс Console для взаимодействия с пользователем
     * @return объект Coordinates - координаты
     * @throws AskBreak если пользователь вводит "exit"
     */
    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            Integer x;
            while (true) {
                console.print("coordinates.x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Integer.parseInt(line);
                        break;
                    } catch (NumberFormatException e) {
                    }
                }
            }
            float y;
            while (true) {
                console.print("coordinates.y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    y = Float.parseFloat(line);
                    if (y > 751) {
                        console.println("Значение \"y\" должно быть меньше 751!");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                }
            }
            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    /**
     * Метод для запроса данных для количества участников
     *
     * @param console интерфейс Console для взаимодействия с пользователем
     * @return количество участников
     * @throws AskBreak если пользователь вводит "exit"
     */
    public static Long askNumberOfParticipants(Console console) throws AskBreak {
        try {
            Long numberOfParticipants;
            while (true) {
                console.print("numberOfParticipants: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    numberOfParticipants = Long.parseLong(line);
                    if (numberOfParticipants <= 0) {
                        console.println("Значение \"numberOfParticipants\" должно быть больше 0!");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                }
            }
            return numberOfParticipants;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    /**
     * Метод для запроса данных для количества синглов
     *
     * @param console интерфейс Console для взаимодействия с пользователем
     * @return количество синглов
     * @throws AskBreak если пользователь вводит "exit"
     */
    public static Long askSinglesCount(Console console) throws AskBreak {
        try {
            Long singlesCount;
            while (true) {
                console.print("singlesCount: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    singlesCount = Long.parseLong(line);
                    if (singlesCount <= 0) {
                        console.println("Значение \"singlesCount\" должно быть больше 0!");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                }
            }
            return singlesCount;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Метод для запроса данных для даты основания
     *
     * @param console интерфейс Console для взаимодействия с пользователем
     * @return дата основания
     * @throws AskBreak если пользователь вводит "exit"
     */
    public static Date askEstablishmentDate(Console console) throws AskBreak {
        try {
            Date establishmentDate;
            String formattedDate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            while (true) {
                console.print("data-time (dd.MM.yyyy): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    establishmentDate = dateFormat.parse(line);
                    formattedDate = dateFormat.format(establishmentDate);
                    establishmentDate = dateFormat.parse(formattedDate);
                    break;
                } catch (Exception e) {
                }
            }
            return establishmentDate;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    /**
     * Метод для запроса данных для жанра музыки
     *
     * @param console объект Console для взаимодействия с пользователем
     * @return объект MusicGenre - жанр музыки
     * @throws AskBreak если пользователь вводит "exit"
     */
    public static MusicGenre askMusicGenre(Console console) throws AskBreak {
        try {
            MusicGenre musicGenre;
            while (true) {
                console.print("musicGenre (" + MusicGenre.names() + "): ");
                var line = console.readln().toUpperCase().trim();
                if (line.equals("EXIT")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    musicGenre = MusicGenre.valueOf(line);
                    break;
                } catch (IllegalArgumentException e) {
                }
            }
            return musicGenre;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    /**
     * Метод для запроса данных для создания объекта Album
     *
     * @param console интерфейс Console для взаимодействия с пользователем
     * @return объект Album - альбом
     * @throws AskBreak если пользователь вводит "exit"
     */
    public static Album askAlbum(Console console) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("album.name: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                name = line;
                if (!hasLetters(name)) continue;
                break;
            }
            Long tracks;
            while (true) {
                console.print("album.tracks: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    tracks = Long.parseLong(line);
                    if (tracks <= 0) {
                        console.println("Значение \"tracks\" должно быть больше 0!");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                }
            }
            long length;
            while (true) {
                console.print("album.length: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    length = Long.parseLong(line);
                    if (length <= 0) {
                        console.println("Значение \"length\" должно быть больше 0!");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                }
            }
            Double sales;
            while (true) {
                console.print("album.sales: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    sales = Double.parseDouble(line);
                    if (sales < 0) {
                        console.println("Значение \"length\" не может быть равно 0!");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                }
            }
            return new Album(name, tracks, length, sales);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    /**
     * Метод для проверки, содержит ли строка буквы
     *
     * @param str строка для проверки
     * @return true, если строка содержит буквы, false в противном случае
     */
    private static boolean hasLetters(String str) {
        return str.matches(".*[A-Za-zа-яА-я].*");
    }

}
