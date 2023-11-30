package com.modelo.lojaCarros.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction {

    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleStringProperty carName = new SimpleStringProperty();
    private final SimpleStringProperty clientName = new SimpleStringProperty();

    public Transaction(int id, String carName, String clientName) {
        this.id.set(id);
        this.carName.set(carName);
        this.clientName.set(clientName);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getCarName() {
        return carName.get();
    }

    public void setCarName(String carName) {
        this.carName.set(carName);
    }

    public SimpleStringProperty carNameProperty() {
        return carName;
    }

    public String getClientName() {
        return clientName.get();
    }

    public void setClientName(String clientName) {
        this.clientName.set(clientName);
    }

    public SimpleStringProperty clientNameProperty() {
        return clientName;
    }
}