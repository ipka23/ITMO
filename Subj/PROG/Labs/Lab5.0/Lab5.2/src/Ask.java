import models.*;
import utility.Console;

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
                if (name.isEmpty()) continue;
                break;
            }
            var coordinates = askCoordinates(console);
            double annualTurnover = askAnnualTurnover(console);
            var organizationType = askOrganizationType(console);
            var address = askAddress(console);

            return new Organization(name, coordinates, annualTurnover, organizationType, address); //id!
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            double x;
            while (true){
                console.print("coordinates.x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Double.parseDouble(line);
                        if (x <= -80) continue;
                        break;
                    } catch (NumberFormatException e) {}
                }


            }
            Float y;
            while (true){
                console.print("coordinates.y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    y = Float.parseFloat(line);
                    break;
                } catch (NumberFormatException e) {}
            }
            return new Coordinates(x, y);
        } catch (Exception e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    public static double askAnnualTurnover(Console console) throws AskBreak {
        try {
            double annualTurnover;
            while (true){
                console.print("annualTurnover: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    annualTurnover = Double.parseDouble(line);
                    if (annualTurnover <= 0) continue;
                    break;
                } catch (NumberFormatException e) {}
            }
            return annualTurnover;
        } catch (Exception e) {
            console.printError("Ошибка чтения");
            return 0;
        }
    }

    public static OrganizationType askOrganizationType(Console console) throws AskBreak {
        try {
            OrganizationType organizationType;
            while (true) {
                console.print("organizationType (" + OrganizationType.printNames() + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    organizationType =  OrganizationType.valueOf(line);
                    break;
                } catch (IllegalArgumentException e) {}
            }
            return organizationType;
        } catch (NoSuchElementException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Address askAddress(Console console) throws AskBreak {
        try {
            String street;
            while (true) {
                console.print("address.street: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    street = line;
                    if (street.length() > 198) continue;
                    break;
                } catch (IllegalArgumentException e) {}
            }
            String zipCode;
            while (true) {
                console.print("address.zipCode: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    zipCode = Integer.toString(Integer.parseInt(line));
                    break;
                } catch (NumberFormatException e) {}
            }


            Location town;
            Float x;
            while (true) {
                console.print("address.town.x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    x = Float.parseFloat(line);
                    break;
                } catch (NumberFormatException e) {}
            }
            double y;
            while (true) {
                console.print("address.town.y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    y = Double.parseDouble(line);
                    break;
                } catch (NumberFormatException e) {}
            }
            double z;
            while (true) {
                console.print("address.town.z: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) continue;
                try {
                    z = Double.parseDouble(line);
                    break;
                } catch (NumberFormatException e) {}
            }
            String name;
            while (true){
                console.print("address.town.name: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (name.isEmpty()) continue;
                if (hasNoDigits(name)) break;
            }

            town = new Location(x, y, z, name);
            return new Address(street, zipCode,  town);

        } catch (Exception e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
    private static boolean hasNoDigits(String str) {
        return str.replaceAll("[0-9]", "").equals(str);
    }

}
