package com.modelo.lojaCarros.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;
    private String errMsg;
    private boolean connected;
    private String user;
    private String password;
    private String databaseName;

    public DatabaseConnection(String user, String password, String databaseName){
        this.user = user;
        this.password = password;
        this.databaseName = databaseName;
        setConnection();
    }

    public boolean isConnected() {
        return connected;
    }
    public String getErrMsg(){
        return errMsg;
    }
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+databaseName,user,password);
            connected = true;
        }
        catch (ClassNotFoundException e){
            connected = false;
            errMsg+="Erro a carregar o driver"+e;
        }
        catch (SQLException e){
            connected = false;
            errMsg+="\nErro de  SQL"+e;
        }
    }
}