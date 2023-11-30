module com.modelo.programamodelo {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires java.sql;

  opens com.modelo.lojaCarros to javafx.fxml;
  exports com.modelo.lojaCarros;
  exports com.modelo.lojaCarros.controller;
  opens com.modelo.lojaCarros.controller to javafx.fxml;

  exports com.modelo.lojaCarros.model;
  opens com.modelo.lojaCarros.model to javafx.fxml;
}