package com.denglitong.drools_demo.entity;

import java.util.StringJoiner;

public class Product {
    private String category;
    private int stock;
    private double basePrice;
    private double finalPrice;

    public Product(String category, int stock, double basePrice) {
        this.category = category;
        this.stock = stock;
        this.basePrice = basePrice;
    }

    public String getCategory() {
        return category;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public Product setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public Product setBasePrice(double basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public Product setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
                .add("category='" + category + "'")
                .add("stock=" + stock)
                .add("basePrice=" + basePrice)
                .add("finalPrice=" + finalPrice)
                .toString();
    }
}
