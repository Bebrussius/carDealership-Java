package com.modelo.lojaCarros;

import com.modelo.lojaCarros.model.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    DatabaseConnection database = new DatabaseConnection("root","123456","carDealership");
    if(database.isConnected()){
      System.out.println("Conexão com o banco estabelecida com sucesso!");
    } else {
      System.out.println(database.getErrMsg());
    }
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainScreen.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Hello!");
    stage.setScene(scene);
    stage.setResizable(true);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

}