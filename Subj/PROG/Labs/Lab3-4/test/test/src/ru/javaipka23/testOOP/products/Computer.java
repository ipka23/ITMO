package ru.javaipka23.testOOP.products;

public class Computer extends Product {
    private int ram;
    private int psd;
    private int price;
    private String cpu;
    private String resolution;
    private boolean isTurnedOn;
    private String name;

    public Computer(int price, String name,int ram, int psd, String cpu, String resolution, boolean isTurnedOn) {
        super(price);
        this.name = name;
        this.ram = ram;
        this.psd = psd;
        this.cpu = cpu;
        this.resolution = resolution;
        this.isTurnedOn = isTurnedOn;
    }

    public Computer(int price, String resolution) {
        super(price);
        this.resolution = resolution;
    }

    public Computer(int price, int ram, int psd, String name) {
        super(price);
        this.ram = ram;
        this.psd = psd;
        this.name = name;
    }

    public Computer(int price) {
        super(price);
    }
    public String turnOff() {
        isTurnedOn = false;
        return name + " выключен";
    }

    public String turnOn() {
        isTurnedOn = true;
        return name + " включен";
    }



    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getPsd() {
        return psd;
    }

    public void setPsd(int psd) {
        this.psd = psd;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
