package ru.akirakozov.sd.refactoring.entity;

public class Product {
    String name;
    long price;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }
}