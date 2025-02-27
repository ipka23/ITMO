package test;

import managers.DumpManager;
import models.*;
import utility.StandardConsole;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class TestDumpManager {
    public static void main(String[] args) {
        String fileName = new String("test.csv".getBytes(), StandardCharsets.UTF_8);// Имя тестового файла
        StandardConsole console = new StandardConsole();
        DumpManager dumpManager = new DumpManager(fileName, console);

        // 🔹 1. Создаем тестовую коллекцию организаций
        Collection<Organization> organizations = new LinkedList<>();
        organizations.add(new Organization(1L, "Google", new Coordinates("37.422;-122.084"), 1000000.0, new Date(), OrganizationType.COMMERCIAL, new Address("1600 Amphitheatre Parkway")));
        organizations.add(new Organization(2L, "Apple", new Coordinates("37.334;-122.009"), 2000000.0, new Date(), OrganizationType.TRUST, new Address("1 Apple Park Way")));

        // 🔹 2. Записываем коллекцию в CSV
        dumpManager.writeCollection(organizations);

        // 🔹 3. Читаем обратно коллекцию из CSV
        Collection<Organization> loadedOrganizations = new LinkedList<>();
        dumpManager.readCollection(loadedOrganizations);

        // 🔹 4. Проверяем, совпадают ли записанные и прочитанные данные
        System.out.println("\n✅ Оригинальные данные:");
        for (Organization org : organizations) {
            System.out.println(org);
        }

        System.out.println("\n✅ Данные после чтения из CSV:");
        for (Organization org : loadedOrganizations) {
            System.out.println(org);
        }

        // 🔹 5. Финальный тест: сравнение размеров коллекций
        if (organizations.size() == loadedOrganizations.size()) {
            System.out.println("\n✅ Тест пройден: данные корректно записаны и загружены!");
        } else {
            System.err.println("\n❌ Ошибка: Количество объектов после загрузки отличается!");
        }
    }
}
