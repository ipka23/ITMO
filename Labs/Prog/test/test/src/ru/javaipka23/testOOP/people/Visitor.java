package ru.javaipka23.testOOP.people;

import ru.javaipka23.testOOP.products.Product;

public class Visitor extends Human{
    private int budget;
    public Visitor(String name, int budget){
        super(name);
        this.setBudget(budget);
    }
    public void buyProducts(Product[] products) {
        for (int i = 0; i < products.length; i++) {
            if (this.budget >= products[i].getPrice()) {
                this.budget -= products[i].getPrice(); // Вычитаем цену из бюджета
                System.out.println("Куплен " + products[i].getName() + " по цене: " + products[i].getPrice());
            } else {
                System.out.println("Недостаточно средств для покупки " + products[i].getName() + " по цене: " + products[i].getPrice());
            }
        }
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
