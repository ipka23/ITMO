package utility;

import models.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.NoSuchElementException;

public class Ask {
    public static class AskBreak extends Exception {}
    public static Organization askOrganization(Console console, Long id) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("name: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
            }
            var coordinates = askCoordinates(console);
            var organizationType = askOrganizationType(console);
            var annualTurnover = askAnnualTurnover(console);
            var address = askAddress(console);
//            var creationDate = askCreationDate(console);
            LocalDateTime creationDate;
            while (true) {
                console.print("data-time (Exemple: " +
                        LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " or 2023-03-11): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.equals("")) {
                    creationDate = null;
                    break;
                }
                try {
                    creationDate = LocalDateTime.parse(line, DateTimeFormatter.ISO_DATE_TIME);
                    break;
                } catch (DateTimeParseException e) { }
                try {
                    creationDate = LocalDateTime.parse(line+"T00:00:00.0000", DateTimeFormatter.ISO_DATE_TIME);
                    break;
                } catch (DateTimeParseException e) { }
            }
            return new Organization(id, name, coordinates, organizationType,  annualTurnover, address);
        }
        catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            int x;
            while (true) {
                console.print("x_coordinates: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Integer.parseInt(line);
                        if (x >= -80) break;
                    }
                    catch (NumberFormatException e) {
                        System.err.println("Неправильный формат числа");
                    }
                }
            }
            float y;
            while (true) {
                console.print("y_coordinates: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        y = Float.parseFloat(line);
                        break;
                    }
                    catch (NumberFormatException e) {
                        System.err.println("Неправильный формат числа");
                    }
                }
            }
            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }



    public static OrganizationType askOrganizationType(Console console) throws AskBreak{
        try {
            OrganizationType organizationType;
            while (true) {
                console.print("organizationType: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        organizationType = OrganizationType.valueOf(line);
                        break;
                    }
                    catch (IllegalArgumentException e) {
                        System.err.println("Нет такого типа организации. Доступные типы:");
                        OrganizationType.printAvailableTypes();
                    }
                }
            }
            return organizationType;
        }
        catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    public static Address askAddress(Console console) throws AskBreak {
        try {
            Float x;
            while (true) {
                console.print("x_location: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Float.parseFloat(line);
                        break;
                    }
                    catch (IllegalArgumentException e) {
                        System.err.println("Неправильный формат числа");
                    }
                }
            }
            double y;
            while (true) {
                console.print("y_location: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        y = Double.parseDouble(line);
                        break;
                    }
                    catch (IllegalArgumentException e) {
                        System.err.println("Неправильный формат числа");
                    }
                }
            }
            double z;
            while (true) {
                console.print("z_location: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        z = Double.parseDouble(line);
                        break;
                    }
                    catch (IllegalArgumentException e) {
                        System.err.println("Неправильный формат числа");
                    }
                }
            }
            String street;
            while (true) {
                console.print("street: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try{
                        street = line;
                        if (street.length() <= 198) break;
                    } catch (IllegalArgumentException e) {}
                }
            }
            String zipCode;
            while (true) {
                console.print("zipCode: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    zipCode = line;
                    break;
                }
            }
            String name;
            while (true) {
                console.print("name: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    name = line;
                    break;
                }
            }
            return new Address(street, zipCode, new Location(x, y, z, name));
        }
        catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    public static double askAnnualTurnover(Console console) throws AskBreak {
        try {
            double annualTurnover;
            while (true) {
                console.print("annualTurnover: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        annualTurnover = Double.parseDouble(line);
                        break;
                    }
                    catch (NumberFormatException e) {
                        System.err.println("Неправильный формат числа");
                    }
                }
            }
            return annualTurnover;
        }
        catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return 0;
        }
    }

}
