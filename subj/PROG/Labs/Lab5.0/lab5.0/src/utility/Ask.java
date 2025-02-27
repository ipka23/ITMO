package utility;

import models.Aboba;
import models.Coordinates;
import models.WeaponType;

import java.util.NoSuchElementException;

public class Ask {
    public static class AskBreak extends Exception {}

    public static Aboba askAboba(Console console, int id) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("name: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
            }
            var coordinates = askCoordinates(console);
            var weaponType = askWeaponType(console);
            return new Aboba(id, name, coordinates, weaponType);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            int x;
            while (true) {
                console.print("coordinates.x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        x = Integer.parseInt(line);
                        break;
                    }
                    catch(NumberFormatException e) {
                        console.printError("Неправильный формат числа");
                    }
                }
            }
            float y;
            while (true) {
                console.print("coordinates.y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        y = Float.parseFloat(line);
                        break;
                    }
                    catch(NumberFormatException e) {
                        console.printError("Неправильный формат числа");
                    }
                }
            }
            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static WeaponType askWeaponType(Console console) throws AskBreak {
        try {
            WeaponType r;
            while (true) {
                console.print("WeaponType ("+WeaponType.names()+"): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        r = WeaponType.valueOf(line); break;
                    }
                    catch (NullPointerException | IllegalArgumentException  e) {
                        console.printError("Нет такого типа оружия");
                    }
                } else return null;
            }
            return r;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

}
