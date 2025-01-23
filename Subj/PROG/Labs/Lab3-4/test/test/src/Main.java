import ru.javaipka23.testOOP.people.Visitor;
import ru.javaipka23.testOOP.products.Product;
import ru.javaipka23.testOOP.products.SmartPhone;
import ru.javaipka23.testOOP.products.Tv;

public class Main {
    public static void main(String[] args) {
    SmartPhone iphone = new SmartPhone(60000, 4, 64, "ipka23Phone", "apple");
    System.out.println(iphone.turnOn());
    Tv samsungOled = new Tv(1000000, "8K");
    System.out.println(samsungOled.getPrice());

    Product mouse = new Product("Razer", 6000);
    Product keyboard = new Product("ThunderRobot", 4000);
    Product headphones = new Product("HyperX", 10000);
    Visitor ipka23 = new Visitor("ipka23", 12000);
    Product[] products = {mouse, keyboard, headphones};
    ipka23.buyProducts(products);

    }
}
