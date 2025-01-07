package ru.javaipka23.testOOP.products;

public class SmartPhone extends Computer {
    private String firm;
    private String number;

    public SmartPhone(int price, int ram, int psd, String name, String firm) {
        super(price, ram, psd, name);
        this.firm = firm;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
