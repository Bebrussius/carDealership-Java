package com.modelo.lojaCarros.model;

import javafx.beans.property.*;

public class Car {
    private final StringProperty model;
    private final StringProperty brand;
    private final IntegerProperty manufacturingYear;
    private final DoubleProperty price;

    public Car(String model, String brand, int year, double price) {
        this.model = new SimpleStringProperty(model);
        this.brand = new SimpleStringProperty(brand);
        this.manufacturingYear = new SimpleIntegerProperty(year);
        this.price = new SimpleDoubleProperty(price);
    }

    // Model Property
    public StringProperty modelProperty() {
        return model;
    }

    public String getModel() {
        return model.get();
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    // Brand Property
    public StringProperty brandProperty() {
        return brand;
    }

    public String getBrand() {
        return brand.get();
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    // Year Property
    public IntegerProperty yearProperty() {
        return manufacturingYear;
    }

    public int getYear() {
        return manufacturingYear.get();
    }

    public void setYear(int year) {
        this.manufacturingYear.set(year);
    }

    // Price Property
    public DoubleProperty priceProperty() {
        return price;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
}