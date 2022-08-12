package com.vadpol.ConnectionSQL;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
Класс который содержит данные для подключения к SQL
*/

public class ConnectToSQL {

    public static Connection connection;
    private static final String dbUrl = "jdbc:postgresql://ec2-52-30-75-37.eu-west-1.compute.amazonaws.com:5432/d1cfnt21boubau";
    private static final String username = "nppbhlsnlrefla";
    private static final String password ="f3de020609870a3288e019f02f0a906c132f793f52329e8fc9c2892a03f0b403";
    private static ConnectToSQL mainJava;

    private static Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }

    public static void closeConnection()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void mainJava() {
        mainJava = new ConnectToSQL();
    }

    public ConnectToSQL() {
        try {

            connection = DriverManager.getConnection(dbUrl, username, password);

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

}