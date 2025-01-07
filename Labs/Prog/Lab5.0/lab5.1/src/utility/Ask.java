package utility;

import models.*;


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
            return new Organization(id, name, coordinates, organizationType, annualTurnover, address);
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
                if (!line.equals("")) {
                    try {
                        x = Integer.parseInt(line);
                        break;
                    }
                    catch (NumberFormatException e) {
                        console.printError("Неправильный формат числа");
                    }
                }
            }
            float y;
            while (true) {
                console.print("y_coordinates: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        y = Float.parseFloat(line);
                        break;
                    }
                    catch (NumberFormatException e) {
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



    public static OrganizationType askOrganizationType(Console console) throws AskBreak{
        try {
            OrganizationType organizationType;
            while (true) {
                console.print("organizationType: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
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
                if (!line.equals("")) {
                    try {
                        x = Float.parseFloat(line);
                        break;
                    }
                    catch (IllegalArgumentException e) {
                        console.printError("Неправильный формат числа");
                    }
                }
            }
            double y;
            while (true) {
                console.print("y_location: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        y = Double.parseDouble(line);
                        break;
                    }
                    catch (IllegalArgumentException e) {
                        console.printError("Неправильный формат числа");
                    }
                }
            }
            double z;
            while (true) {
                console.print("z_location: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        z = Double.parseDouble(line);
                        break;
                    }
                    catch (IllegalArgumentException e) {
                        console.printError("Неправильный формат числа");
                    }
                }
            }
            String street;
            while (true) {
                console.print("street: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    street = line;
                    break;
                }
            }
            String zipCode;
            while (true) {
                console.print("zipCode: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    zipCode = line;
                    break;
                }
            }
            String name;
            while (true) {
                console.print("name: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
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
                if (!line.equals("")) {
                    try {
                        annualTurnover = Double.parseDouble(line);
                        break;
                    }
                    catch (NumberFormatException e) {
                        console.printError("Неправильный формат числа");
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
