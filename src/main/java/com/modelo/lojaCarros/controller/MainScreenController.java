package com.modelo.lojaCarros.controller;

import com.modelo.lojaCarros.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {

  @FXML
  private void CarButtonClick() {
    openScreen("Cars", "CarScreen.fxml");
  }

  @FXML
  private void ClientButtonClick() {
    openScreen("Clients", "CustomerScreen.fxml");
  }

  @FXML
  private void TransactionButtonClick() {
    openScreen("Transactions", "TransactionScreen.fxml");
  }

  private void openScreen(String title, String fxmlFileName) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(HelloApplication.class.getResource(fxmlFileName));
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = new Stage();
      stage.setTitle(title);
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error opening " + title + " FXML");
    }
  }
}