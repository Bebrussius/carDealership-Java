package com.modelo.lojaCarros.controller;

import com.modelo.lojaCarros.model.Transaction;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionScreenController {
    @FXML
    private TableView<Transaction> transactionTableView;
    @FXML
    private TableColumn<Transaction, String> carNameColumn;
    @FXML
    private TableColumn<Transaction, String> clientNameColumn;
    @FXML
    private ComboBox<String> carComboBox;
    @FXML
    private ComboBox<String> clientComboBox;

    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    // Método de inicialização
    @FXML
    public void initialize() {
        initializeTable();
        initializeComboBoxes();
        loadTransactionsFromDatabase();
    }

    private void initializeTable() {
        carNameColumn.setCellValueFactory(cellData -> cellData.getValue().carNameProperty());
        clientNameColumn.setCellValueFactory(cellData -> cellData.getValue().clientNameProperty());
    }

    private void initializeComboBoxes() {
        // Carregar opções de carros do banco de dados
        List<String> carOptions = loadCarOptions();
        carComboBox.setItems(FXCollections.observableArrayList(carOptions));

        // Carregar opções de clientes do banco de dados
        List<String> clientOptions = loadClientOptions();
        clientComboBox.setItems(FXCollections.observableArrayList(clientOptions));
    }

    private List<String> loadClientOptions() {
        List<String> clientOptions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/carDealership", "root", "123456")) {
            String query = "SELECT DISTINCT name FROM customers"; // Alterado de "clientName" para "name"
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String clientName = resultSet.getString("name");
                    clientOptions.add(clientName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading client options from the database: " + e.getMessage());
        }

        return clientOptions;
    }

    private List<String> loadCarOptions() {
        List<String> carOptions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/carDealership", "root", "123456")) {
            String query = "SELECT DISTINCT model FROM cars"; // Alterado de "carName" para "model"
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String carModel = resultSet.getString("model");
                    carOptions.add(carModel);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading car options from the database: " + e.getMessage());
        }

        return carOptions;
    }

    private void loadTransactionsFromDatabase() {
        // Estabelecer uma conexão com o banco de dados
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/carDealership", "root", "123456")) {

            // Consultar a tabela transactions
            String query = "SELECT * FROM transactions";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                // Preencher a lista de transações
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String carName = resultSet.getString("carName");
                    String clientName = resultSet.getString("clientName");

                    Transaction transaction = new Transaction(id, carName, clientName);
                    transactions.add(transaction);
                }

                // Exibir as transações na tabela
                transactionTableView.setItems(transactions);
            }
        } catch (SQLException e) {
            System.out.println("Error loading transactions from the database: " + e.getMessage());
        }
    }

    @FXML
    private void onAddTransactionButtonClick() {
        // Obter os valores selecionados nos ComboBoxes
        String selectedCar = carComboBox.getValue();
        String selectedClient = clientComboBox.getValue();

        // Verificar se todos os campos foram preenchidos
        if (selectedCar == null || selectedClient == null) {
            System.out.println("Por favor, preencha todos os campos.");
            return;
        }

        // Criar uma nova transação com os valores selecionados
        Transaction newTransaction = new Transaction(0, selectedCar, selectedClient);

        // Adicionar a nova transação à lista e ao banco de dados
        transactions.add(newTransaction);
        saveTransactionToDatabase(newTransaction);

        // Limpar os ComboBoxes
        clearComboBoxes();
    }

    private void saveTransactionToDatabase(Transaction transaction) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/carDealership", "root", "123456")) {
            String query = "INSERT INTO transactions (carName, clientName) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, transaction.getCarName());
                preparedStatement.setString(2, transaction.getClientName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar a transação no banco de dados: " + e.getMessage());
        }
    }

    private void clearComboBoxes() {
        carComboBox.getSelectionModel().clearSelection();
        clientComboBox.getSelectionModel().clearSelection();
    }
}