package com.modelo.lojaCarros.controller;

import com.modelo.lojaCarros.model.Customer;
import com.modelo.lojaCarros.model.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerScreenController {

    @FXML
    private TableView<Customer> clientTableView;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private void initialize() {
        // Configuração da tabela e colunas
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());

        // Inicializa a lista observável e vincula à tabela
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        clientTableView.setItems(customerList);

        // Conexão com o banco de dados
        DatabaseConnection database = new DatabaseConnection("root", "123456", "carDealership");

        // Carregar clientes do banco de dados ao inicializar a tela
        loadCustomersFromDatabase(customerList, database);
    }

    @FXML
    private void onAddCustomerButtonClick() {
        // Obter dados dos campos de texto
        String name = nameTextField.getText();
        String phone = phoneTextField.getText();

        // Criar um novo cliente
        Customer newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setPhone(phone);

        // Adicionar o cliente à lista local e ao banco de dados
        ObservableList<Customer> customerList = clientTableView.getItems();
        customerList.add(newCustomer);

        saveCustomerToDatabase(newCustomer);

        // Limpar os campos de texto
        clearTextFields();
    }

    private void loadCustomersFromDatabase(ObservableList<Customer> customerList, DatabaseConnection database) {
        if (database.isConnected()) {
            try {
                String query = "SELECT * FROM customers";
                PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String phone = resultSet.getString("phone");

                    Customer customer = new Customer();
                    customer.setName(name);
                    customer.setPhone(phone);

                    customerList.add(customer);
                }
            } catch (SQLException e) {
                System.out.println("Error loading customers from the database: " + e.getMessage());
            }
        } else {
            System.out.println("Database not connected. Unable to load customers.");
        }
    }

    private void saveCustomerToDatabase(Customer customer) {
        DatabaseConnection database = new DatabaseConnection("root", "123456", "carDealership");

        if (database.isConnected()) {
            try {
                String query = "INSERT INTO customers (name, phone) VALUES (?, ?)";
                PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getPhone());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error saving customer to the database: " + e.getMessage());
            }
        } else {
            System.out.println("Database not connected. Unable to save customer.");
        }
    }

    private void clearTextFields() {
        nameTextField.clear();
        phoneTextField.clear();
    }
}
