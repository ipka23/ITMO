import java.util.Scanner;

public class MyTestScanner {
    public static void main(String[] args) {

        Scanner scInt = new Scanner(System.in);
        System.out.println("Введите число:");


        if (!scInt.hasNextInt()) System.out.println("Введите число!");

        Scanner scStr = new Scanner(System.in);
        System.out.println("Введите текст:");
        scStr.nextLine();

        scStr.close();
        scInt.close();
    }
}
