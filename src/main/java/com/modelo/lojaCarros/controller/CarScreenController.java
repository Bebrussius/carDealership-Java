package com.modelo.lojaCarros.controller;

import com.modelo.lojaCarros.model.Car;
import com.modelo.lojaCarros.model.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarScreenController {

    @FXML
    private TableView<Car> carTableView;

    @FXML
    private TableColumn<Car, String> modelColumn;

    @FXML
    private TableColumn<Car, String> brandColumn;

    @FXML
    private TableColumn<Car, Integer> yearColumn;

    @FXML
    private TableColumn<Car, Double> priceColumn;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField brandTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button addCarButton;

    private ObservableList<Car> carList;
    private DatabaseConnection database;

    @FXML
    private void initialize() {
        carList = FXCollections.observableArrayList();
        carTableView.setItems(carList);

        modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        database = new DatabaseConnection("root", "123456", "carDealership");

        // Carregar carros do banco de dados ao inicializar a tela
        loadCarsFromDatabase();
    }

    private void loadCarsFromDatabase() {
        if (database.isConnected()) {
            try {
                String query = "SELECT * FROM cars";
                PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String model = resultSet.getString("model");
                    String brand = resultSet.getString("brand");
                    int year = resultSet.getInt("year");
                    double price = resultSet.getDouble("price");

                    Car car = new Car(model, brand, year, price);
                    carList.add(car);
                }
            } catch (SQLException e) {
                System.out.println("Error loading cars from the database: " + e.getMessage());
            }
        } else {
            System.out.println("Database not connected. Unable to load cars.");
        }
    }

    @FXML
    private void onAddCarButtonClick() {
        String model = modelTextField.getText();
        String brand = brandTextField.getText();
        int manufacturingYear = Integer.parseInt(yearTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());

        Car newCar = new Car(model, brand, manufacturingYear, price);
        carList.add(newCar);

        saveCarToDatabase(newCar);

        clearTextFields();
    }

    private void saveCarToDatabase(Car car) {
        if (database.isConnected()) {
            try {
                String query = "INSERT INTO cars (model, brand, year, price) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
                preparedStatement.setString(1, car.getModel());
                preparedStatement.setString(2, car.getBrand());
                preparedStatement.setInt(3, car.getYear());
                preparedStatement.setDouble(4, car.getPrice());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error saving car to the database: " + e.getMessage());
            }
        } else {
            System.out.println("Database not connected. Unable to save car.");
        }
    }

    private void clearTextFields() {
        modelTextField.clear();
        brandTextField.clear();
        yearTextField.clear();
        priceTextField.clear();
    }
}