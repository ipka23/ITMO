package test;

import models.Organization;
import utility.Ask;

public class AskTest {
    public static void main(String[] args) {
        // Создаем симулированный ввод (например, имя "MyOrg", тип организации "COMMERCIAL" и т.д.)
//        String input = "MyOrg\n100\n10.5\nCOMMERCIAL\n1000.0\nSome street\n12345\nOrganizationName\n";
        String input = "88005553535L\nUFO SEARCH\n0\n2f\n23\nPRIVATE_LIMITED_COMPANY\nPortovaya 31/12\n685000\n23f\n323\n32\nMagadan";
        TestConsole console = new TestConsole(input);

        try {
            // Вызываем метод askOrganization, который будет использовать наш TestConsole
            Organization organization = Ask.askOrganization(console, 1L);

            // Проверка: organization не должен быть null
            if (organization != null) {
                System.out.println("Test passed: Organization created successfully");
            } else {
                System.err.println("Test failed: Organization is null");
            }
        } catch (Ask.AskBreak e) {
            System.err.println("Test failed: AskBreak was thrown");
        }
        System.out.println(console.getOutput());  // Вывод содержимого, чтобы проверить, что происходит в консоли

    }
}
